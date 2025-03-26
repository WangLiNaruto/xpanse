/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.api.controllers;

import static org.eclipse.xpanse.modules.security.auth.common.RoleConstants.ROLE_ADMIN;
import static org.eclipse.xpanse.modules.security.auth.common.RoleConstants.ROLE_USER;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.xpanse.api.config.AuditApiRequest;
import org.eclipse.xpanse.modules.deployment.ServiceObjectManager;
import org.eclipse.xpanse.modules.models.service.order.ServiceOrder;
import org.eclipse.xpanse.modules.models.serviceobject.ServiceObjectRequest;
import org.eclipse.xpanse.modules.models.servicetemplate.enums.ObjectActionType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/** Service Objects Api. */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/xpanse")
@Secured({ROLE_ADMIN, ROLE_USER})
@ConditionalOnProperty(name = "enable.agent.api.only", havingValue = "false", matchIfMissing = true)
public class ServiceObjectsApi {

    @Resource private ServiceObjectManager serviceObjectManager;

    @Tag(name = "Service Object", description = "APIs for managing service's object.")
    @PostMapping(
            value = "/services/object/{serviceId}/{objectType}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Create an order create service object using service id.")
    @AuditApiRequest(methodName = "getCspFromServiceId", paramTypes = UUID.class)
    private ServiceOrder createServiceObject(
            @Parameter(name = "serviceId", description = "The id of the deployed service.")
                    @PathVariable("serviceId")
                    UUID serviceId,
            @Parameter(name = "objectType", description = "The type of the service object type.")
                    @PathVariable("objectType")
                    ObjectActionType objectType,
            @Valid @RequestBody ServiceObjectRequest request) {
        return serviceObjectManager.createServiceObject(serviceId, objectType, request);
    }
}
