/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.modules.deployment.deployers.opentofu.callbacks;

import jakarta.annotation.Resource;
import java.util.Objects;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.xpanse.modules.deployment.DeployResultManager;
import org.eclipse.xpanse.modules.deployment.deployers.opentofu.tofumaker.generated.model.OpenTofuResult;
import org.eclipse.xpanse.modules.models.service.deployment.DeployResult;
import org.eclipse.xpanse.modules.models.service.enums.Handler;
import org.springframework.stereotype.Component;

/** Bean for managing deployment and destroy callback functions. */
@Slf4j
@Component
public class OpenTofuDeploymentResultCallbackManager {
    @Resource private DeployResultManager deployResultManager;

    /**
     * Handle the callback of the order task.
     *
     * @param orderId the orderId of the task.
     * @param result execution result of the task.
     */
    public void orderCallback(UUID orderId, OpenTofuResult result) {
        DeployResult deployResult = getDeployResult(result);
        deployResult.setOrderId(orderId);
        deployResultManager.updateServiceWithDeployResult(deployResult, Handler.TERRA_BOOT);
    }

    private DeployResult getDeployResult(OpenTofuResult result) {
        DeployResult deployResult = new DeployResult();
        deployResult.setDeployerVersionUsed(result.getOpenTofuVersionUsed());
        if (Boolean.FALSE.equals(result.getCommandSuccessful())) {
            deployResult.setIsTaskSuccessful(false);
            deployResult.setMessage(result.getCommandStdError());
        } else {
            deployResult.setIsTaskSuccessful(true);
            deployResult.setMessage(null);
        }
        if (StringUtils.isNotBlank(result.getTerraformState())) {
            deployResult.setTfStateContent(result.getTerraformState());
        }
        if (Objects.nonNull(result.getGeneratedFileContentMap())) {
            deployResult.getDeploymentGeneratedFiles().putAll(result.getGeneratedFileContentMap());
        }
        return deployResult;
    }
}
