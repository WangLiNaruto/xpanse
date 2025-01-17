/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 */

package org.eclipse.xpanse.modules.deployment.deployers.terraform.terraformboot;

import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.xpanse.modules.models.service.deployment.DeployResult;
import org.eclipse.xpanse.modules.models.servicetemplate.Deployment;
import org.eclipse.xpanse.modules.models.servicetemplate.enums.DeployerKind;
import org.eclipse.xpanse.modules.orchestrator.deployment.DeployTask;
import org.eclipse.xpanse.modules.orchestrator.deployment.Deployer;
import org.eclipse.xpanse.modules.orchestrator.deployment.DeploymentScriptValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/** Implementation of the deployment with terraform-boot. */
@Slf4j
@Profile("terraform-boot")
@Component
public class TerraformBootDeployment implements Deployer {

    private final TerraformBootScriptValidator terraformBootScriptValidator;
    private final TerraformBootDeploymentPlanManage terraformBootDeploymentPlanManage;
    private final TerraformBootServiceDeployer terraformBootServiceDeployer;
    private final TerraformBootServiceDestroyer terraformBootServiceDestroyer;
    private final TerraformBootServiceModifier terraformBootServiceModifier;

    /** Initializes the TerraformBoot deployer. */
    @Autowired
    public TerraformBootDeployment(
            TerraformBootScriptValidator terraformBootScriptValidator,
            TerraformBootDeploymentPlanManage terraformBootDeploymentPlanManage,
            TerraformBootServiceDeployer terraformBootServiceDeployer,
            TerraformBootServiceDestroyer terraformBootServiceDestroyer,
            TerraformBootServiceModifier terraformBootServiceModifier) {
        this.terraformBootServiceDestroyer = terraformBootServiceDestroyer;
        this.terraformBootScriptValidator = terraformBootScriptValidator;
        this.terraformBootDeploymentPlanManage = terraformBootDeploymentPlanManage;
        this.terraformBootServiceDeployer = terraformBootServiceDeployer;
        this.terraformBootServiceModifier = terraformBootServiceModifier;
    }

    @Override
    public DeployResult deploy(DeployTask deployTask) {
        Map<String, String> scriptsMap = deployTask.getOcl().getDeployment().getScriptFiles();
        if (Objects.nonNull(scriptsMap) && !scriptsMap.isEmpty()) {
            return terraformBootServiceDeployer.deployFromScripts(deployTask);
        }
        return terraformBootServiceDeployer.deployFromGitRepo(deployTask);
    }

    @Override
    public DeployResult modify(DeployTask deployTask) {
        Map<String, String> scriptsMap = deployTask.getOcl().getDeployment().getScriptFiles();
        if (Objects.nonNull(scriptsMap) && !scriptsMap.isEmpty()) {
            return terraformBootServiceModifier.modifyFromScripts(deployTask);
        }
        return terraformBootServiceModifier.modifyFromGitRepo(deployTask);
    }

    @Override
    public DeployResult destroy(DeployTask deployTask) {
        Map<String, String> scriptsMap = deployTask.getOcl().getDeployment().getScriptFiles();
        if (Objects.nonNull(scriptsMap) && !scriptsMap.isEmpty()) {
            return terraformBootServiceDestroyer.destroyFromScripts(deployTask);
        }
        return terraformBootServiceDestroyer.destroyFromGitRepo(deployTask);
    }

    /** Get the deployer kind. */
    @Override
    public DeployerKind getDeployerKind() {
        return DeployerKind.TERRAFORM;
    }

    /** Validates the Terraform script. */
    @Override
    public DeploymentScriptValidationResult validate(Deployment deployment) {
        DeploymentScriptValidationResult result = null;
        Map<String, String> scriptsMap = deployment.getScriptFiles();
        if (Objects.nonNull(scriptsMap) && !scriptsMap.isEmpty()) {
            result = terraformBootScriptValidator.validateTerraformScripts(deployment);
        }
        if (Objects.nonNull(deployment.getScriptsRepo())) {
            result = terraformBootScriptValidator.validateTerraformScriptsFromGitRepo(deployment);
        }
        return result;
    }

    @Override
    public String getDeploymentPlanAsJson(DeployTask task) {
        Map<String, String> scriptsMap = task.getOcl().getDeployment().getScriptFiles();
        if (Objects.nonNull(scriptsMap) && !scriptsMap.isEmpty()) {
            return terraformBootDeploymentPlanManage.getTerraformPlanFromScripts(task).getPlan();
        }
        return terraformBootDeploymentPlanManage.getTerraformPlanFromGitRepo(task).getPlan();
    }
}
