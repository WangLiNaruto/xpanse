/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 */

package org.eclipse.xpanse.modules.deployment.deployers.terraform.terraformboot;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.xpanse.modules.database.service.ServiceDeploymentEntity;
import org.eclipse.xpanse.modules.deployment.ServiceDeploymentEntityHandler;
import org.eclipse.xpanse.modules.deployment.deployers.terraform.exceptions.TerraformBootRequestFailedException;
import org.eclipse.xpanse.modules.deployment.deployers.terraform.terraformboot.generated.api.TerraformFromGitRepoApi;
import org.eclipse.xpanse.modules.deployment.deployers.terraform.terraformboot.generated.api.TerraformFromScriptsApi;
import org.eclipse.xpanse.modules.deployment.deployers.terraform.terraformboot.generated.model.TerraformAsyncModifyFromGitRepoRequest;
import org.eclipse.xpanse.modules.deployment.deployers.terraform.terraformboot.generated.model.TerraformAsyncModifyFromScriptsRequest;
import org.eclipse.xpanse.modules.deployment.deployers.terraform.utils.TfResourceTransUtils;
import org.eclipse.xpanse.modules.models.service.deployment.DeployResult;
import org.eclipse.xpanse.modules.orchestrator.deployment.DeployTask;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

/** Bean to manage service modify via terraform-boot. */
@Slf4j
@Component
@Profile("terraform-boot")
public class TerraformBootServiceModifier {

    private final TerraformFromScriptsApi terraformFromScriptsApi;
    private final TerraformFromGitRepoApi terraformFromGitRepoApi;
    private final TerraformBootHelper terraformBootHelper;
    private final ServiceDeploymentEntityHandler deploymentEntityHandler;

    /** Constructor for TerraformBootServiceDestroyer bean. */
    public TerraformBootServiceModifier(
            TerraformFromScriptsApi terraformFromScriptsApi,
            TerraformFromGitRepoApi terraformFromGitRepoApi,
            TerraformBootHelper terraformBootHelper,
            ServiceDeploymentEntityHandler deploymentEntityHandler) {
        this.terraformFromScriptsApi = terraformFromScriptsApi;
        this.terraformFromGitRepoApi = terraformFromGitRepoApi;
        this.terraformBootHelper = terraformBootHelper;
        this.deploymentEntityHandler = deploymentEntityHandler;
    }

    /** method to perform service modify using scripts provided in OCL. */
    public DeployResult modifyFromScripts(DeployTask deployTask) {
        ServiceDeploymentEntity serviceDeploymentEntity =
                this.deploymentEntityHandler.getServiceDeploymentEntity(deployTask.getServiceId());
        String resourceState = TfResourceTransUtils.getStoredStateContent(serviceDeploymentEntity);
        DeployResult result = new DeployResult();
        result.setOrderId(deployTask.getOrderId());
        TerraformAsyncModifyFromScriptsRequest request =
                getModifyFromScriptsRequest(deployTask, resourceState);
        try {
            terraformFromScriptsApi.asyncModifyWithScripts(request);
            return result;
        } catch (RestClientException e) {
            throw new TerraformBootRequestFailedException(getErrorMessage(deployTask, e));
        }
    }

    /** method to perform service modify using scripts form GIT repo. */
    public DeployResult modifyFromGitRepo(DeployTask deployTask) {
        ServiceDeploymentEntity serviceDeploymentEntity =
                this.deploymentEntityHandler.getServiceDeploymentEntity(deployTask.getServiceId());
        String resourceState = TfResourceTransUtils.getStoredStateContent(serviceDeploymentEntity);
        DeployResult result = new DeployResult();
        result.setOrderId(deployTask.getOrderId());
        TerraformAsyncModifyFromGitRepoRequest request =
                getModifyFromGitRepoRequest(deployTask, resourceState);
        try {
            terraformFromGitRepoApi.asyncModifyFromGitRepo(request);
            return result;
        } catch (RestClientException e) {
            throw new TerraformBootRequestFailedException(getErrorMessage(deployTask, e));
        }
    }

    private TerraformAsyncModifyFromScriptsRequest getModifyFromScriptsRequest(
            DeployTask task, String stateFile) throws TerraformBootRequestFailedException {
        TerraformAsyncModifyFromScriptsRequest request =
                new TerraformAsyncModifyFromScriptsRequest();
        request.setRequestId(task.getOrderId());
        request.setTerraformVersion(task.getOcl().getDeployment().getDeployerTool().getVersion());
        request.setScriptFiles(task.getOcl().getDeployment().getScriptFiles());
        request.setTfState(stateFile);
        request.setVariables(terraformBootHelper.getInputVariables(task, false));
        request.setEnvVariables(terraformBootHelper.getEnvironmentVariables(task));
        request.setWebhookConfig(terraformBootHelper.getWebhookConfigWithTask(task));
        return request;
    }

    private TerraformAsyncModifyFromGitRepoRequest getModifyFromGitRepoRequest(
            DeployTask task, String stateFile) throws TerraformBootRequestFailedException {
        TerraformAsyncModifyFromGitRepoRequest request =
                new TerraformAsyncModifyFromGitRepoRequest();
        request.setRequestId(task.getOrderId());
        request.setTerraformVersion(task.getOcl().getDeployment().getDeployerTool().getVersion());
        request.setTfState(stateFile);
        request.setVariables(terraformBootHelper.getInputVariables(task, false));
        request.setEnvVariables(terraformBootHelper.getEnvironmentVariables(task));
        request.setWebhookConfig(terraformBootHelper.getWebhookConfigWithTask(task));
        request.setGitRepoDetails(
                terraformBootHelper.convertTerraformScriptGitRepoDetailsFromDeployFromGitRepo(
                        task.getOcl().getDeployment().getScriptsRepo()));
        return request;
    }

    private String getErrorMessage(DeployTask deployTask, RestClientException e) {
        String errorMsg =
                String.format(
                        "Failed to modify service %s by order %s using terraform-boot. Error: %s",
                        deployTask.getServiceId(), deployTask.getOrderId(), e.getMessage());
        log.error(errorMsg, e);
        return errorMsg;
    }
}
