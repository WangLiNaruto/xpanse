/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 */

package org.eclipse.xpanse.modules.deployment.deployers.terraform.callbacks;

import jakarta.annotation.Resource;
import java.util.Objects;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.xpanse.modules.database.service.DeployServiceEntity;
import org.eclipse.xpanse.modules.database.servicemigration.ServiceMigrationEntity;
import org.eclipse.xpanse.modules.database.servicetemplate.ServiceTemplateEntity;
import org.eclipse.xpanse.modules.database.servicetemplate.ServiceTemplateStorage;
import org.eclipse.xpanse.modules.deployment.DeployResultManager;
import org.eclipse.xpanse.modules.deployment.DeployService;
import org.eclipse.xpanse.modules.deployment.DeployServiceEntityHandler;
import org.eclipse.xpanse.modules.deployment.DeployServiceEntityToDeployTaskConverter;
import org.eclipse.xpanse.modules.deployment.ResourceHandlerManager;
import org.eclipse.xpanse.modules.deployment.deployers.terraform.terraformboot.generated.model.TerraformResult;
import org.eclipse.xpanse.modules.deployment.deployers.terraform.utils.TfResourceTransUtils;
import org.eclipse.xpanse.modules.deployment.migration.MigrationService;
import org.eclipse.xpanse.modules.deployment.migration.consts.MigrateConstants;
import org.eclipse.xpanse.modules.models.service.deploy.enums.DeployerTaskStatus;
import org.eclipse.xpanse.modules.models.service.deploy.enums.ServiceDeploymentState;
import org.eclipse.xpanse.modules.orchestrator.deployment.DeployResult;
import org.eclipse.xpanse.modules.orchestrator.deployment.DeployTask;
import org.eclipse.xpanse.modules.orchestrator.deployment.DeploymentScenario;
import org.eclipse.xpanse.modules.workflow.utils.WorkflowUtils;
import org.springframework.stereotype.Component;

/**
 * Bean for managing deployer terraform callback functions.
 */
@Slf4j
@Component
public class TerraformDeploymentResultCallbackManager {

    @Resource
    DeployServiceEntityHandler deployServiceEntityHandler;

    @Resource
    ResourceHandlerManager resourceHandlerManager;

    @Resource
    DeployResultManager deployResultManager;

    @Resource
    private DeployServiceEntityToDeployTaskConverter deployServiceEntityToDeployTaskConverter;

    @Resource
    private DeployService deployService;

    @Resource
    private MigrationService migrationService;

    @Resource
    private WorkflowUtils workflowUtils;

    @Resource
    private ServiceTemplateStorage serviceTemplateStorage;

    /**
     * Callback method after the deployment task is completed.
     */
    public void deployCallback(UUID taskId, TerraformResult result) {
        DeployServiceEntity updatedDeployServiceEntity =
                handleCallbackTerraformResult(taskId, result, DeploymentScenario.DEPLOY);
        if (ServiceDeploymentState.DEPLOY_FAILED
                == updatedDeployServiceEntity.getServiceDeploymentState()) {
            DeployTask deployTask =
                    deployServiceEntityToDeployTaskConverter.getDeployTaskByStoredService(
                            updatedDeployServiceEntity);
            deployService.rollbackOnDeploymentFailure(deployTask, updatedDeployServiceEntity);
        }

        ServiceMigrationEntity serviceMigrationEntity =
                migrationService.getServiceMigrationEntityByNewServiceId(taskId.toString());
        if (Objects.nonNull(serviceMigrationEntity)) {
            workflowUtils.completeReceiveTask(serviceMigrationEntity.getMigrationId().toString(),
                    MigrateConstants.MIGRATION_DEPLOY_RECEIVE_TASK_ACTIVITY_ID);
        }
    }

    /**
     * Callback method after the modification task is completed.
     */
    public void modifyCallback(UUID taskId, TerraformResult terraformResult) {
        handleCallbackTerraformResult(taskId, terraformResult, DeploymentScenario.MODIFY);
    }

    /**
     * Callback method after the destroy/rollback/purge task is completed.
     */
    public void destroyCallback(UUID taskId, TerraformResult result, DeploymentScenario scenario) {
        handleCallbackTerraformResult(taskId, result, scenario);
        ServiceMigrationEntity serviceMigrationEntity =
                migrationService.getServiceMigrationEntityByOldServiceId(taskId.toString());
        if (Objects.nonNull(serviceMigrationEntity)) {
            workflowUtils.completeReceiveTask(serviceMigrationEntity.getMigrationId().toString(),
                    MigrateConstants.MIGRATION_DESTROY_RECEIVE_TASK_ACTIVITY_ID);
        }
    }

    private DeployServiceEntity handleCallbackTerraformResult(UUID taskId, TerraformResult result,
                                                              DeploymentScenario scenario) {
        log.info("Handle terraform callback result of task with id:{} in scenario:{}. ", taskId,
                scenario.toValue());
        DeployServiceEntity deployServiceEntity =
                deployServiceEntityHandler.getDeployServiceEntity(taskId);

        DeployResult deployResult = new DeployResult();
        deployResult.setId(taskId);
        if (Boolean.FALSE.equals(result.getCommandSuccessful())) {
            deployResult.setMessage(result.getCommandStdError());
        }
        deployResult.setState(getDeployerTaskStatus(scenario, result.getCommandSuccessful()));
        deployResult.getPrivateProperties()
                .put(TfResourceTransUtils.STATE_FILE_NAME, result.getTerraformState());
        if (Objects.nonNull(result.getImportantFileContentMap())) {
            deployResult.getPrivateProperties().putAll(result.getImportantFileContentMap());
        }

        if (StringUtils.isNotBlank(result.getTerraformState())) {
            ServiceTemplateEntity serviceTemplateEntity =
                    serviceTemplateStorage.getServiceTemplateById(
                            deployServiceEntity.getServiceTemplateId());
            resourceHandlerManager.getResourceHandler(deployServiceEntity.getCsp(),
                    serviceTemplateEntity.getOcl().getDeployment().getKind()).handler(deployResult);
        }
        return deployResultManager.updateDeployServiceEntityWithDeployResult(deployResult,
                deployServiceEntity);

    }

    private DeployerTaskStatus getDeployerTaskStatus(DeploymentScenario scenario,
                                                     Boolean isCommandSuccessful) {
        return switch (scenario) {
            case DEPLOY -> isCommandSuccessful ? DeployerTaskStatus.DEPLOY_SUCCESS
                    : DeployerTaskStatus.DEPLOY_FAILED;
            case MODIFY -> isCommandSuccessful ? DeployerTaskStatus.MODIFICATION_SUCCESSFUL
                    : DeployerTaskStatus.MODIFICATION_FAILED;
            case DESTROY -> isCommandSuccessful ? DeployerTaskStatus.DESTROY_SUCCESS
                    : DeployerTaskStatus.DESTROY_FAILED;
            case ROLLBACK -> isCommandSuccessful ? DeployerTaskStatus.ROLLBACK_SUCCESS
                    : DeployerTaskStatus.ROLLBACK_FAILED;
            case PURGE -> isCommandSuccessful ? DeployerTaskStatus.PURGE_SUCCESS
                    : DeployerTaskStatus.PURGE_FAILED;
        };
    }
}
