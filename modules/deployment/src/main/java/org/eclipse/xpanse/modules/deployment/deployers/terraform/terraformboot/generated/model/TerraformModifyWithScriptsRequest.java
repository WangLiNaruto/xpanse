/*
 * Terraform-Boot API
 * RESTful Services to interact with terraform CLI
 *
 * The version of the OpenAPI document: 1.0.18-SNAPSHOT
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package org.eclipse.xpanse.modules.deployment.deployers.terraform.terraformboot.generated.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/** TerraformModifyWithScriptsRequest */
@JsonPropertyOrder({
    TerraformModifyWithScriptsRequest.JSON_PROPERTY_REQUEST_ID,
    TerraformModifyWithScriptsRequest.JSON_PROPERTY_TERRAFORM_VERSION,
    TerraformModifyWithScriptsRequest.JSON_PROPERTY_IS_PLAN_ONLY,
    TerraformModifyWithScriptsRequest.JSON_PROPERTY_VARIABLES,
    TerraformModifyWithScriptsRequest.JSON_PROPERTY_ENV_VARIABLES,
    TerraformModifyWithScriptsRequest.JSON_PROPERTY_TASK_ID,
    TerraformModifyWithScriptsRequest.JSON_PROPERTY_SCRIPT_FILES,
    TerraformModifyWithScriptsRequest.JSON_PROPERTY_TF_STATE
})
@jakarta.annotation.Generated(
        value = "org.openapitools.codegen.languages.JavaClientCodegen",
        comments = "Generator version: 7.11.0")
public class TerraformModifyWithScriptsRequest {
    public static final String JSON_PROPERTY_REQUEST_ID = "requestId";
    @jakarta.annotation.Nullable private UUID requestId;

    public static final String JSON_PROPERTY_TERRAFORM_VERSION = "terraformVersion";
    @jakarta.annotation.Nonnull private String terraformVersion;

    public static final String JSON_PROPERTY_IS_PLAN_ONLY = "isPlanOnly";
    @jakarta.annotation.Nonnull private Boolean isPlanOnly;

    public static final String JSON_PROPERTY_VARIABLES = "variables";
    @jakarta.annotation.Nonnull private Map<String, Object> variables = new HashMap<>();

    public static final String JSON_PROPERTY_ENV_VARIABLES = "envVariables";
    @jakarta.annotation.Nullable private Map<String, String> envVariables = new HashMap<>();

    public static final String JSON_PROPERTY_TASK_ID = "taskId";
    @jakarta.annotation.Nullable private UUID taskId;

    public static final String JSON_PROPERTY_SCRIPT_FILES = "scriptFiles";
    @jakarta.annotation.Nonnull private Map<String, String> scriptFiles = new HashMap<>();

    public static final String JSON_PROPERTY_TF_STATE = "tfState";
    @jakarta.annotation.Nonnull private String tfState;

    public TerraformModifyWithScriptsRequest() {}

    public TerraformModifyWithScriptsRequest requestId(
            @jakarta.annotation.Nullable UUID requestId) {

        this.requestId = requestId;
        return this;
    }

    /**
     * Id of the request
     *
     * @return requestId
     */
    @jakarta.annotation.Nullable
    @JsonProperty(JSON_PROPERTY_REQUEST_ID)
    @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
    public UUID getRequestId() {
        return requestId;
    }

    @JsonProperty(JSON_PROPERTY_REQUEST_ID)
    @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
    public void setRequestId(@jakarta.annotation.Nullable UUID requestId) {
        this.requestId = requestId;
    }

    public TerraformModifyWithScriptsRequest terraformVersion(
            @jakarta.annotation.Nonnull String terraformVersion) {

        this.terraformVersion = terraformVersion;
        return this;
    }

    /**
     * The required version of terraform which will execute the scripts.
     *
     * @return terraformVersion
     */
    @jakarta.annotation.Nonnull
    @JsonProperty(JSON_PROPERTY_TERRAFORM_VERSION)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public String getTerraformVersion() {
        return terraformVersion;
    }

    @JsonProperty(JSON_PROPERTY_TERRAFORM_VERSION)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setTerraformVersion(@jakarta.annotation.Nonnull String terraformVersion) {
        this.terraformVersion = terraformVersion;
    }

    public TerraformModifyWithScriptsRequest isPlanOnly(
            @jakarta.annotation.Nonnull Boolean isPlanOnly) {

        this.isPlanOnly = isPlanOnly;
        return this;
    }

    /**
     * Flag to control if the deployment must only generate the terraform or it must also apply the
     * changes.
     *
     * @return isPlanOnly
     */
    @jakarta.annotation.Nonnull
    @JsonProperty(JSON_PROPERTY_IS_PLAN_ONLY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public Boolean getIsPlanOnly() {
        return isPlanOnly;
    }

    @JsonProperty(JSON_PROPERTY_IS_PLAN_ONLY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setIsPlanOnly(@jakarta.annotation.Nonnull Boolean isPlanOnly) {
        this.isPlanOnly = isPlanOnly;
    }

    public TerraformModifyWithScriptsRequest variables(
            @jakarta.annotation.Nonnull Map<String, Object> variables) {

        this.variables = variables;
        return this;
    }

    public TerraformModifyWithScriptsRequest putVariablesItem(String key, Object variablesItem) {
        this.variables.put(key, variablesItem);
        return this;
    }

    /**
     * Key-value pairs of regular variables that must be used to execute the Terraform request.
     *
     * @return variables
     */
    @jakarta.annotation.Nonnull
    @JsonProperty(JSON_PROPERTY_VARIABLES)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public Map<String, Object> getVariables() {
        return variables;
    }

    @JsonProperty(JSON_PROPERTY_VARIABLES)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setVariables(@jakarta.annotation.Nonnull Map<String, Object> variables) {
        this.variables = variables;
    }

    public TerraformModifyWithScriptsRequest envVariables(
            @jakarta.annotation.Nullable Map<String, String> envVariables) {

        this.envVariables = envVariables;
        return this;
    }

    public TerraformModifyWithScriptsRequest putEnvVariablesItem(
            String key, String envVariablesItem) {
        if (this.envVariables == null) {
            this.envVariables = new HashMap<>();
        }
        this.envVariables.put(key, envVariablesItem);
        return this;
    }

    /**
     * Key-value pairs of variables that must be injected as environment variables to terraform
     * process.
     *
     * @return envVariables
     */
    @jakarta.annotation.Nullable
    @JsonProperty(JSON_PROPERTY_ENV_VARIABLES)
    @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
    public Map<String, String> getEnvVariables() {
        return envVariables;
    }

    @JsonProperty(JSON_PROPERTY_ENV_VARIABLES)
    @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
    public void setEnvVariables(@jakarta.annotation.Nullable Map<String, String> envVariables) {
        this.envVariables = envVariables;
    }

    public TerraformModifyWithScriptsRequest taskId(@jakarta.annotation.Nullable UUID taskId) {

        this.taskId = taskId;
        return this;
    }

    /**
     * Id of the request.
     *
     * @return taskId
     */
    @jakarta.annotation.Nullable
    @JsonProperty(JSON_PROPERTY_TASK_ID)
    @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
    public UUID getTaskId() {
        return taskId;
    }

    @JsonProperty(JSON_PROPERTY_TASK_ID)
    @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
    public void setTaskId(@jakarta.annotation.Nullable UUID taskId) {
        this.taskId = taskId;
    }

    public TerraformModifyWithScriptsRequest scriptFiles(
            @jakarta.annotation.Nonnull Map<String, String> scriptFiles) {

        this.scriptFiles = scriptFiles;
        return this;
    }

    public TerraformModifyWithScriptsRequest putScriptFilesItem(
            String key, String scriptFilesItem) {
        this.scriptFiles.put(key, scriptFilesItem);
        return this;
    }

    /**
     * Map stores file name and content of all script files for modify request.
     *
     * @return scriptFiles
     */
    @jakarta.annotation.Nonnull
    @JsonProperty(JSON_PROPERTY_SCRIPT_FILES)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public Map<String, String> getScriptFiles() {
        return scriptFiles;
    }

    @JsonProperty(JSON_PROPERTY_SCRIPT_FILES)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setScriptFiles(@jakarta.annotation.Nonnull Map<String, String> scriptFiles) {
        this.scriptFiles = scriptFiles;
    }

    public TerraformModifyWithScriptsRequest tfState(@jakarta.annotation.Nonnull String tfState) {

        this.tfState = tfState;
        return this;
    }

    /**
     * The .tfState file content after deployment
     *
     * @return tfState
     */
    @jakarta.annotation.Nonnull
    @JsonProperty(JSON_PROPERTY_TF_STATE)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public String getTfState() {
        return tfState;
    }

    @JsonProperty(JSON_PROPERTY_TF_STATE)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setTfState(@jakarta.annotation.Nonnull String tfState) {
        this.tfState = tfState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TerraformModifyWithScriptsRequest terraformModifyWithScriptsRequest =
                (TerraformModifyWithScriptsRequest) o;
        return Objects.equals(this.requestId, terraformModifyWithScriptsRequest.requestId)
                && Objects.equals(
                        this.terraformVersion, terraformModifyWithScriptsRequest.terraformVersion)
                && Objects.equals(this.isPlanOnly, terraformModifyWithScriptsRequest.isPlanOnly)
                && Objects.equals(this.variables, terraformModifyWithScriptsRequest.variables)
                && Objects.equals(this.envVariables, terraformModifyWithScriptsRequest.envVariables)
                && Objects.equals(this.taskId, terraformModifyWithScriptsRequest.taskId)
                && Objects.equals(this.scriptFiles, terraformModifyWithScriptsRequest.scriptFiles)
                && Objects.equals(this.tfState, terraformModifyWithScriptsRequest.tfState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                requestId,
                terraformVersion,
                isPlanOnly,
                variables,
                envVariables,
                taskId,
                scriptFiles,
                tfState);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class TerraformModifyWithScriptsRequest {\n");
        sb.append("    requestId: ").append(toIndentedString(requestId)).append("\n");
        sb.append("    terraformVersion: ").append(toIndentedString(terraformVersion)).append("\n");
        sb.append("    isPlanOnly: ").append(toIndentedString(isPlanOnly)).append("\n");
        sb.append("    variables: ").append(toIndentedString(variables)).append("\n");
        sb.append("    envVariables: ").append(toIndentedString(envVariables)).append("\n");
        sb.append("    taskId: ").append(toIndentedString(taskId)).append("\n");
        sb.append("    scriptFiles: ").append(toIndentedString(scriptFiles)).append("\n");
        sb.append("    tfState: ").append(toIndentedString(tfState)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces (except the first
     * line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
