/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 */

package org.eclipse.xpanse.modules.deployment.deployers.opentofu.opentofumaker;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.xpanse.modules.deployment.deployers.opentofu.exceptions.OpenTofuMakerRequestFailedException;
import org.eclipse.xpanse.modules.deployment.deployers.opentofu.opentofumaker.generated.api.OpenTofuFromGitRepoApi;
import org.eclipse.xpanse.modules.deployment.deployers.opentofu.opentofumaker.generated.api.OpenTofuFromScriptsApi;
import org.eclipse.xpanse.modules.deployment.deployers.opentofu.opentofumaker.generated.model.OpenTofuAsyncDeployFromGitRepoRequest;
import org.eclipse.xpanse.modules.deployment.deployers.opentofu.opentofumaker.generated.model.OpenTofuAsyncDeployFromScriptsRequest;
import org.eclipse.xpanse.modules.orchestrator.deployment.DeployResult;
import org.eclipse.xpanse.modules.orchestrator.deployment.DeployTask;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

/**
 * Bean to manage service deployment via tofu-maker.
 */
@Slf4j
@Component
@Profile("tofu-maker")
public class OpenTofuMakerServiceDeployer {

    private final OpenTofuFromScriptsApi openTofuFromScriptsApi;
    private final OpenTofuFromGitRepoApi openTofuFromGitRepoApi;
    private final OpenTofuMakerHelper openTofuMakerHelper;

    /**
     * Constructor for OpenTofuMakerServiceDeployer bean.
     */
    public OpenTofuMakerServiceDeployer(OpenTofuFromScriptsApi openTofuFromScriptsApi,
                                        OpenTofuFromGitRepoApi openTofuFromGitRepoApi,
                                        OpenTofuMakerHelper openTofuMakerHelper) {
        this.openTofuFromScriptsApi = openTofuFromScriptsApi;
        this.openTofuFromGitRepoApi = openTofuFromGitRepoApi;
        this.openTofuMakerHelper = openTofuMakerHelper;
    }

    /**
     * method to perform service deployment using scripts provided in OCL.
     */
    public DeployResult deployFromScripts(DeployTask deployTask) {
        DeployResult result = new DeployResult();
        OpenTofuAsyncDeployFromScriptsRequest request = getDeployFromScriptsRequest(deployTask);
        try {
            openTofuMakerHelper.setHeaderTokenByProfiles();
            openTofuFromScriptsApi.asyncDeployWithScripts(request, deployTask.getId());
            result.setId(deployTask.getId());
            return result;
        } catch (RestClientException e) {
            log.error("tofu-maker deploy service failed. service id: {} , error:{} ",
                    deployTask.getId(), e.getMessage());
            throw new OpenTofuMakerRequestFailedException(e.getMessage());
        }
    }

    /**
     * method to perform service deployment using scripts form GIT repo.
     */
    public DeployResult deployFromGitRepo(DeployTask deployTask) {
        DeployResult result = new DeployResult();
        OpenTofuAsyncDeployFromGitRepoRequest request = getDeployFromGitRepoRequest(deployTask);
        try {
            openTofuMakerHelper.setHeaderTokenByProfiles();
            openTofuFromGitRepoApi.asyncDeployFromGitRepo(request, deployTask.getId());
            result.setId(deployTask.getId());
            return result;
        } catch (RestClientException e) {
            log.error("tofu-maker deploy service failed. service id: {} , error:{} ",
                    deployTask.getId(), e.getMessage());
            throw new OpenTofuMakerRequestFailedException(e.getMessage());
        }
    }

    private OpenTofuAsyncDeployFromScriptsRequest getDeployFromScriptsRequest(DeployTask task) {
        OpenTofuAsyncDeployFromScriptsRequest request =
                new OpenTofuAsyncDeployFromScriptsRequest();
        request.setIsPlanOnly(false);
        request.setScripts(openTofuMakerHelper.getFiles(task));
        request.setVariables(openTofuMakerHelper.getInputVariables(task, true));
        request.setEnvVariables(openTofuMakerHelper.getEnvironmentVariables(task));
        request.setWebhookConfig(openTofuMakerHelper.getWebhookConfig(task, false));
        return request;
    }

    private OpenTofuAsyncDeployFromGitRepoRequest getDeployFromGitRepoRequest(DeployTask task) {
        OpenTofuAsyncDeployFromGitRepoRequest request =
                new OpenTofuAsyncDeployFromGitRepoRequest();
        request.setIsPlanOnly(false);
        request.setVariables(openTofuMakerHelper.getInputVariables(task, true));
        request.setEnvVariables(openTofuMakerHelper.getEnvironmentVariables(task));
        request.setWebhookConfig(openTofuMakerHelper.getWebhookConfig(task, false));
        request.setGitRepoDetails(
                openTofuMakerHelper.convertOpenTofuScriptGitRepoDetailsFromDeployFromGitRepo(
                        task.getOcl().getDeployment().getScriptsRepo())
        );
        return request;
    }
}
