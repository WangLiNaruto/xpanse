/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 */

package org.eclipse.xpanse.modules.deployment.deployers.terraform.terraformlocal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.xpanse.common.systemcmd.SystemCmd;
import org.eclipse.xpanse.common.systemcmd.SystemCmdResult;
import org.eclipse.xpanse.modules.deployment.deployers.terraform.exceptions.TerraformExecutorException;
import org.eclipse.xpanse.modules.deployment.utils.DeployResultFileUtils;
import org.eclipse.xpanse.modules.orchestrator.deployment.DeploymentScriptValidationResult;

/**
 * An executor for terraform.
 */
@Slf4j
public class TerraformLocalExecutor {

    private static final String VARS_FILE_NAME = "variables.tfvars.json";
    private static final String STATE_FILE_NAME = "terraform.tfstate";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final List<String> EXCLUDED_FILE_SUFFIX_LIST =
            Arrays.asList(".tf", ".tfstate", ".binary", ".hcl");

    static {
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Getter
    private final String executorPath;
    private final Map<String, String> env;
    private final Map<String, Object> variables;
    private final String workspace;
    private final DeployResultFileUtils deployResultFileUtils;

    /**
     * Constructor for terraformExecutor.
     *
     * @param executorPath          path of the terraform executor.
     * @param env                   environment for the terraform command line.
     * @param variables             variables for the terraform command line.
     * @param workspace             workspace for the terraform command line.
     * @param deployResultFileUtils file tool class.
     */
    TerraformLocalExecutor(String executorPath,
                           Map<String, String> env,
                           Map<String, Object> variables,
                           String workspace,
                           @Nullable String subDirectory,
                           DeployResultFileUtils deployResultFileUtils) {
        this.executorPath = executorPath;
        this.env = env;
        this.variables = variables;
        this.workspace =
                Objects.nonNull(subDirectory)
                        ? workspace + File.separator + subDirectory
                        : workspace;
        this.deployResultFileUtils = deployResultFileUtils;
    }

    /**
     * Executes terraform init command.
     *
     * @return Returns result of SystemCmd executed.
     */
    public SystemCmdResult tfInit() {
        return execute(this.executorPath + " init -no-color");
    }

    /**
     * Executes terraform plan command.
     *
     * @return Returns result of SystemCmd executed.
     */
    public SystemCmdResult tfPlan() {
        return executeWithVariables(
                new StringBuilder(this.executorPath + " plan -input=false -no-color "));
    }

    /**
     * Executes terraform plan command and output.
     *
     * @return Returns result of SystemCmd executed.
     */
    public SystemCmdResult tfPlanWithOutput() {
        return executeWithVariables(new StringBuilder(
                this.executorPath + " plan -input=false -no-color --out tfplan.binary"));
    }

    /**
     * Executes terraform apply command.
     *
     * @return Returns result of SystemCmd executed.
     */
    public SystemCmdResult tfApply() {
        return executeWithVariables(new StringBuilder(
                this.executorPath + " apply -auto-approve -input=false -no-color "));
    }

    /**
     * Executes terraform destroy command.
     *
     * @return Returns result of SystemCmd executed.
     */
    public SystemCmdResult tfDestroy() {
        return executeWithVariables(new StringBuilder(
                this.executorPath + " destroy -auto-approve -input=false -no-color "));
    }

    /**
     * Executes terraform commands with parameters.
     *
     * @return Returns result of SystemCmd executed.
     */
    private SystemCmdResult executeWithVariables(StringBuilder command) {
        createVariablesFile();
        command.append(" -var-file=");
        command.append(VARS_FILE_NAME);
        SystemCmdResult systemCmdResult = execute(command.toString());
        cleanUpVariablesFile();
        return systemCmdResult;
    }

    /**
     * Executes terraform commands.
     *
     * @return SystemCmdResult
     */
    private SystemCmdResult execute(String cmd) {
        SystemCmd systemCmd = new SystemCmd();
        systemCmd.setEnv(env);
        systemCmd.setWorkDir(workspace);
        return systemCmd.execute(cmd);
    }

    /**
     * Deploy source by terraform.
     */
    public void deploy() {
        SystemCmdResult initResult = tfInit();
        if (!initResult.isCommandSuccessful()) {
            log.error("TFExecutor.tfInit failed.");
            throw new TerraformExecutorException("TFExecutor.tfInit failed.",
                    initResult.getCommandStdError());
        }
        SystemCmdResult planResult = tfPlan();
        if (!planResult.isCommandSuccessful()) {
            log.error("TFExecutor.tfPlan failed.");
            throw new TerraformExecutorException("TFExecutor.tfPlan failed.",
                    planResult.getCommandStdError());
        }
        SystemCmdResult applyResult = tfApply();
        if (!applyResult.isCommandSuccessful()) {
            log.error("TFExecutor.tfApply failed.");
            throw new TerraformExecutorException("TFExecutor.tfApply failed.",
                    applyResult.getCommandStdError());
        }
    }

    /**
     * Destroy resource of the service.
     */
    public void destroy() {
        SystemCmdResult initResult = tfInit();
        if (!initResult.isCommandSuccessful()) {
            log.error("TFExecutor.tfInit failed.");
            throw new TerraformExecutorException("TFExecutor.tfInit failed.",
                    initResult.getCommandStdError());
        }
        SystemCmdResult planResult = tfPlan();
        if (!planResult.isCommandSuccessful()) {
            log.error("TFExecutor.tfPlan failed.");
            throw new TerraformExecutorException("TFExecutor.tfPlan failed.",
                    planResult.getCommandStdError());
        }
        SystemCmdResult destroyResult = tfDestroy();
        if (!destroyResult.isCommandSuccessful()) {
            log.error("TFExecutor.tfDestroy failed.");
            throw new TerraformExecutorException("TFExecutor.tfDestroy failed.",
                    destroyResult.getCommandStdError());
        }
    }

    /**
     * Reads the contents of the this.executorPath + ".tfstate" file from the terraform workspace.
     *
     * @return file contents as string.
     */
    public String getTerraformState() {
        String state = null;
        String path = workspace + File.separator + STATE_FILE_NAME;
        try {
            deployResultFileUtils.waitUntilFileIsNotLocked(path);
            File tfState = new File(path);
            if (tfState.exists()) {
                state = Files.readString(tfState.toPath());
            }
        } catch (IOException ex) {
            log.error("Read state file failed.", ex);
        }
        return state;
    }

    /**
     * Reads the contents of the other important file from the terraform workspace.
     *
     * @return Map fileName as key, contents as value.
     */
    public Map<String, String> getImportantFilesContent() {
        Map<String, String> fileContentMap = new HashMap<>();
        File workPath = new File(workspace);
        if (workPath.isDirectory() && workPath.exists()) {
            File[] files = workPath.listFiles();
            if (Objects.nonNull(files)) {
                Arrays.stream(files).forEach(file -> {
                    if (file.isFile() && !isExcludedFile(file.getName())) {
                        String content = readFileContent(file);
                        fileContentMap.put(file.getName(), content);
                    }
                });
            }
        }
        return fileContentMap;
    }

    /**
     * Method to execute terraform plan and get the plan as a json string.
     */
    public String getTerraformPlanAsJson() {
        SystemCmdResult initResult = tfInit();
        if (!initResult.isCommandSuccessful()) {
            log.error("TFExecutor.tfInit failed.");
            throw new TerraformExecutorException("TFExecutor.tfInit failed.",
                    initResult.getCommandStdError());
        }
        SystemCmdResult tfPlanResult = tfPlanWithOutput();
        if (!tfPlanResult.isCommandSuccessful()) {
            log.error("TFExecutor.tfPlan failed.");
            throw new TerraformExecutorException("TFExecutor.tfPlan failed.",
                    tfPlanResult.getCommandStdError());
        }
        SystemCmdResult planJsonResult = execute(this.executorPath + " show -json tfplan.binary");
        if (!planJsonResult.isCommandSuccessful()) {
            log.error("Reading Terraform plan as JSON failed.");
            throw new TerraformExecutorException("Reading Terraform plan as JSON failed.",
                    planJsonResult.getCommandStdError());
        }
        return planJsonResult.getCommandStdOutput();
    }

    private boolean isExcludedFile(String fileName) {
        String fileSuffix = fileName.substring(fileName.lastIndexOf("."));
        return EXCLUDED_FILE_SUFFIX_LIST.contains(fileSuffix);
    }

    private String readFileContent(File file) {
        String fileContent = "";
        try {
            fileContent = Files.readString(file.toPath());
            log.info("Read file content with name:{} successfully.", file.getName());
        } catch (IOException e) {
            log.error("Read file content with name:{} error.", file.getName(), e);
        }
        return fileContent;
    }


    /**
     * Executes terraform validate command.
     *
     * @return TfValidationResult.
     */
    public DeploymentScriptValidationResult tfValidate() {
        SystemCmdResult initResult = tfInit();
        if (!initResult.isCommandSuccessful()) {
            log.error("TFExecutor.tfInit failed.");
            throw new TerraformExecutorException("TFExecutor.tfInit failed.",
                    initResult.getCommandStdError());
        }
        SystemCmdResult systemCmdResult = execute(this.executorPath + " validate -json -no-color");
        try {
            return new ObjectMapper().readValue(systemCmdResult.getCommandStdOutput(),
                    DeploymentScriptValidationResult.class);
        } catch (JsonProcessingException ex) {
            throw new IllegalStateException("Serialising string to object failed.", ex);
        }
    }

    private void createVariablesFile() {
        try {
            log.info("creating variables file");
            OBJECT_MAPPER.writeValue(new File(getVariablesFilePath()), variables);
        } catch (IOException ioException) {
            throw new TerraformExecutorException("Creating variables file failed", ioException);
        }
    }

    private void cleanUpVariablesFile() {
        File file = new File(getVariablesFilePath());
        try {
            log.info("cleaning up variables file");
            Files.deleteIfExists(file.toPath());
        } catch (IOException ioException) {
            log.error("Cleanup of variables file failed", ioException);
        }
    }

    private String getVariablesFilePath() {
        return this.workspace + File.separator + VARS_FILE_NAME;
    }

}
