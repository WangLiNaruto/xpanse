/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.modules.deployment;

import jakarta.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.xpanse.modules.database.service.ServiceDeploymentEntity;
import org.eclipse.xpanse.modules.database.serviceorder.ServiceOrderEntity;
import org.eclipse.xpanse.modules.database.servicetemplate.ServiceTemplateEntity;
import org.eclipse.xpanse.modules.models.service.deployment.DeployResource;
import org.eclipse.xpanse.modules.models.service.enums.DeployResourceKind;
import org.eclipse.xpanse.modules.models.service.enums.Handler;
import org.eclipse.xpanse.modules.models.service.enums.OrderStatus;
import org.eclipse.xpanse.modules.models.service.order.ServiceOrder;
import org.eclipse.xpanse.modules.models.service.order.enums.ServiceOrderType;
import org.eclipse.xpanse.modules.models.service.utils.ServiceObjectVariablesJsonSchemaGenerator;
import org.eclipse.xpanse.modules.models.service.utils.ServiceObjectVariablesJsonSchemaValidator;
import org.eclipse.xpanse.modules.models.serviceconfiguration.exceptions.ServiceConfigurationInvalidException;
import org.eclipse.xpanse.modules.models.serviceconfiguration.exceptions.ServiceObjectChangeOrderAlreadyExistsException;
import org.eclipse.xpanse.modules.models.serviceobject.ServiceObjectRequest;
import org.eclipse.xpanse.modules.models.servicetemplate.ObjectManage;
import org.eclipse.xpanse.modules.models.servicetemplate.ObjectParameter;
import org.eclipse.xpanse.modules.models.servicetemplate.Ocl;
import org.eclipse.xpanse.modules.models.servicetemplate.ServiceChangeScript;
import org.eclipse.xpanse.modules.models.servicetemplate.enums.ObjectActionType;
import org.eclipse.xpanse.modules.models.servicetemplate.exceptions.ServiceTemplateNotRegistered;
import org.eclipse.xpanse.modules.models.servicetemplate.utils.JsonObjectSchema;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/** Bean to manage service configuration. */
@Slf4j
@Component
public class ServiceObjectManager {

    @Resource private ServiceDeploymentEntityHandler serviceDeploymentEntityHandler;

    @Resource private DeployService deployService;

    @Resource private ServiceChangeRequestsManager serviceChangeRequestsManager;

    @Resource
    private ServiceObjectVariablesJsonSchemaValidator serviceObjectVariablesJsonSchemaValidator;

    @Resource
    private ServiceObjectVariablesJsonSchemaGenerator serviceObjectVariablesJsonSchemaGenerator;

    /** create service action. */
    public ServiceOrder createServiceObject(
            UUID serviceId, ObjectActionType objectType, ServiceObjectRequest request) {
        try {
            ServiceDeploymentEntity serviceDeploymentEntity =
                    serviceDeploymentEntityHandler.getServiceDeploymentEntity(serviceId);
            ServiceTemplateEntity serviceTemplateEntity =
                    serviceDeploymentEntity.getServiceTemplateEntity();
            if (Objects.isNull(serviceTemplateEntity)) {
                String errMsg = String.format("Service template not found.");
                log.error(errMsg);
                throw new ServiceTemplateNotRegistered(errMsg);
            }
            validateAllObjectsOrdersCompleted(serviceDeploymentEntity);
            validateServiceObjects(serviceTemplateEntity, objectType, request);
            UUID orderId =
                    addServiceChangeRequestsForServiceObject(
                            serviceId,
                            objectType,
                            serviceDeploymentEntity,
                            serviceTemplateEntity.getOcl(),
                            request);
            return new ServiceOrder(orderId, serviceId);
        } catch (ServiceConfigurationInvalidException e) {
            String errorMsg =
                    String.format("Change service configuration error, %s", e.getErrorReasons());
            log.error(errorMsg);
            throw e;
        }
    }

    private UUID addServiceChangeRequestsForServiceObject(
            UUID serviceId,
            ObjectActionType objectType,
            ServiceDeploymentEntity serviceDeployment,
            Ocl ocl,
            ServiceObjectRequest serviceObjectRequest) {

        Map<String, List<DeployResource>> deployResourceMap =
                deployService
                        .listResourcesOfDeployedService(serviceId, DeployResourceKind.VM)
                        .stream()
                        .collect(Collectors.groupingBy(DeployResource::getGroupName));

        List<ServiceChangeScript> objectManageScripts =
                ocl.getServiceObjects().stream()
                        .filter(
                                serviceObject ->
                                        serviceObjectRequest
                                                .getObjectIdentifier()
                                                .equals(
                                                        serviceObject
                                                                .getObjectIdentifier()
                                                                .getName()))
                        .flatMap(
                                serviceObject ->
                                        serviceObject.getObjectsManage().stream()
                                                .filter(
                                                        objectManage ->
                                                                objectType.equals(
                                                                        objectManage
                                                                                .getObjectActionType()))
                                                .map(ObjectManage::getObjectHandlerScript)
                                                .filter(Objects::nonNull))
                        .distinct()
                        .collect(Collectors.toList());

        return serviceChangeRequestsManager.createServiceOrderAndQueueServiceChangeRequests(
                serviceDeployment,
                serviceObjectRequest,
                serviceObjectRequest.getServiceObjectParameters(),
                mergeRequestWithDefaultValuesForMissingParameters(
                        ocl,
                        objectType,
                        serviceObjectRequest.getServiceObjectParameters(),
                        serviceObjectRequest.getObjectIdentifier()),
                deployResourceMap,
                objectManageScripts,
                ServiceOrderType.OBJECT_CREATE,
                Handler.AGENT);
    }

    /**
     * update request parameters with missing parameters. The user can send only some parameters. We
     * fill the rest with default values provided in the service template.
     */
    private Map<String, Object> mergeRequestWithDefaultValuesForMissingParameters(
            Ocl ocl,
            ObjectActionType objectType,
            Map<String, Object> updateRequestMap,
            String objectIdentifierName) {
        Map<String, Object> updatedObjectParametersRequest = new HashMap<>(updateRequestMap);
        ocl.getServiceObjects().stream()
                .filter(
                        serviceObject ->
                                objectIdentifierName.equals(
                                        serviceObject.getObjectIdentifier().getName()))
                .flatMap(
                        serviceObject ->
                                serviceObject.getObjectsManage().stream()
                                        .filter(
                                                objectManage ->
                                                        objectType.equals(
                                                                objectManage.getObjectActionType()))
                                        .flatMap(
                                                objectManage ->
                                                        objectManage
                                                                .getObjectParameters()
                                                                .stream()))
                .distinct()
                .toList()
                .forEach(
                        serviceChangeParameter -> {
                            if (!(updateRequestMap.containsKey(serviceChangeParameter.getName()))) {
                                updatedObjectParametersRequest.put(
                                        serviceChangeParameter.getName(),
                                        updateRequestMap.get(serviceChangeParameter.getName()));
                            }
                        });

        return updatedObjectParametersRequest;
    }

    private void validateAllObjectsOrdersCompleted(
            ServiceDeploymentEntity serviceDeploymentEntity) {
        List<ServiceOrderEntity> serviceOrders = serviceDeploymentEntity.getServiceOrders();
        if (CollectionUtils.isEmpty(serviceOrders)) {
            return;
        }
        List<ServiceOrderEntity> serviceObjectOrders =
                serviceOrders.stream()
                        .filter(
                                serviceOrder ->
                                        serviceOrder.getTaskType() == ServiceOrderType.OBJECT_CREATE
                                                || serviceOrder.getTaskType()
                                                        == ServiceOrderType.OBJECT_MODIFY
                                                || serviceOrder.getTaskType()
                                                        == ServiceOrderType.OBJECT_DELETE)
                        .toList();
        if (!CollectionUtils.isEmpty(serviceObjectOrders)) {
            List<UUID> pendingOrderIds =
                    serviceObjectOrders.stream()
                            .filter(
                                    order ->
                                            order.getOrderStatus() != OrderStatus.SUCCESSFUL
                                                    && order.getOrderStatus() != OrderStatus.FAILED)
                            .map(ServiceOrderEntity::getOrderId)
                            .toList();

            if (!CollectionUtils.isEmpty(pendingOrderIds)) {
                throw new ServiceObjectChangeOrderAlreadyExistsException(
                        String.format(
                                "There is already a pending Object change order. Order ID - %s",
                                pendingOrderIds.getFirst().toString()));
            }
        }
    }

    private void validateServiceObjects(
            ServiceTemplateEntity serviceTemplateEntity,
            ObjectActionType objectType,
            ServiceObjectRequest request) {

        List<ObjectParameter> objectParameters =
                serviceTemplateEntity.getOcl().getServiceObjects().stream()
                        .filter(
                                serviceObject ->
                                        request.getObjectIdentifier()
                                                .equals(
                                                        serviceObject
                                                                .getObjectIdentifier()
                                                                .getName()))
                        .flatMap(
                                serviceObject ->
                                        serviceObject.getObjectsManage().stream()
                                                .filter(
                                                        objectManage ->
                                                                objectType.equals(
                                                                        objectManage
                                                                                .getObjectActionType()))
                                                .flatMap(
                                                        objectManage ->
                                                                objectManage
                                                                        .getObjectParameters()
                                                                        .stream()))
                        .distinct()
                        .toList();

        if (CollectionUtils.isEmpty(objectParameters)) {
            String errorMsg =
                    String.format(
                            "Service template %s has no service objects manage",
                            serviceTemplateEntity.getId());
            log.error(errorMsg);
            throw new ServiceConfigurationInvalidException(List.of(errorMsg));
        }

        JsonObjectSchema jsonObjectSchema =
                serviceObjectVariablesJsonSchemaGenerator.buildServiceConfigurationJsonSchema(
                        objectParameters);
        serviceObjectVariablesJsonSchemaValidator.validateServiceObjectParameters(
                request.getServiceObjectParameters(), jsonObjectSchema);
    }
}
