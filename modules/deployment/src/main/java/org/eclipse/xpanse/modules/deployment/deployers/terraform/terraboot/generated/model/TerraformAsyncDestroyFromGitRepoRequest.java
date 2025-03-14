/*
 * Terra-Boot API
 * RESTful Services to interact with terraform CLI
 *
 * The version of the OpenAPI document: 1.0.21-SNAPSHOT
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package org.eclipse.xpanse.modules.deployment.deployers.terraform.terraboot.generated.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/** TerraformAsyncDestroyFromGitRepoRequest */
@JsonPropertyOrder({
    TerraformAsyncDestroyFromGitRepoRequest.JSON_PROPERTY_REQUEST_ID,
    TerraformAsyncDestroyFromGitRepoRequest.JSON_PROPERTY_TERRAFORM_VERSION,
    TerraformAsyncDestroyFromGitRepoRequest.JSON_PROPERTY_VARIABLES,
    TerraformAsyncDestroyFromGitRepoRequest.JSON_PROPERTY_ENV_VARIABLES,
    TerraformAsyncDestroyFromGitRepoRequest.JSON_PROPERTY_GIT_REPO_DETAILS,
    TerraformAsyncDestroyFromGitRepoRequest.JSON_PROPERTY_TF_STATE,
    TerraformAsyncDestroyFromGitRepoRequest.JSON_PROPERTY_WEBHOOK_CONFIG
})
@jakarta.annotation.Generated(
        value = "org.openapitools.codegen.languages.JavaClientCodegen",
        comments = "Generator version: 7.11.0")
public class TerraformAsyncDestroyFromGitRepoRequest {
    public static final String JSON_PROPERTY_REQUEST_ID = "requestId";
    @jakarta.annotation.Nullable private UUID requestId;

    public static final String JSON_PROPERTY_TERRAFORM_VERSION = "terraformVersion";
    @jakarta.annotation.Nonnull private String terraformVersion;

    public static final String JSON_PROPERTY_VARIABLES = "variables";
    @jakarta.annotation.Nonnull private Map<String, Object> variables = new HashMap<>();

    public static final String JSON_PROPERTY_ENV_VARIABLES = "envVariables";
    @jakarta.annotation.Nullable private Map<String, String> envVariables = new HashMap<>();

    public static final String JSON_PROPERTY_GIT_REPO_DETAILS = "gitRepoDetails";
    @jakarta.annotation.Nullable private TerraformScriptGitRepoDetails gitRepoDetails;

    public static final String JSON_PROPERTY_TF_STATE = "tfState";
    @jakarta.annotation.Nonnull private String tfState;

    public static final String JSON_PROPERTY_WEBHOOK_CONFIG = "webhookConfig";
    @jakarta.annotation.Nonnull private WebhookConfig webhookConfig;

    public TerraformAsyncDestroyFromGitRepoRequest() {}

    public TerraformAsyncDestroyFromGitRepoRequest requestId(
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

    public TerraformAsyncDestroyFromGitRepoRequest terraformVersion(
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

    public TerraformAsyncDestroyFromGitRepoRequest variables(
            @jakarta.annotation.Nonnull Map<String, Object> variables) {

        this.variables = variables;
        return this;
    }

    public TerraformAsyncDestroyFromGitRepoRequest putVariablesItem(
            String key, Object variablesItem) {
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

    public TerraformAsyncDestroyFromGitRepoRequest envVariables(
            @jakarta.annotation.Nullable Map<String, String> envVariables) {

        this.envVariables = envVariables;
        return this;
    }

    public TerraformAsyncDestroyFromGitRepoRequest putEnvVariablesItem(
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

    public TerraformAsyncDestroyFromGitRepoRequest gitRepoDetails(
            @jakarta.annotation.Nullable TerraformScriptGitRepoDetails gitRepoDetails) {

        this.gitRepoDetails = gitRepoDetails;
        return this;
    }

    /**
     * GIT Repo details from where the scripts can be fetched.
     *
     * @return gitRepoDetails
     */
    @jakarta.annotation.Nullable
    @JsonProperty(JSON_PROPERTY_GIT_REPO_DETAILS)
    @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
    public TerraformScriptGitRepoDetails getGitRepoDetails() {
        return gitRepoDetails;
    }

    @JsonProperty(JSON_PROPERTY_GIT_REPO_DETAILS)
    @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
    public void setGitRepoDetails(
            @jakarta.annotation.Nullable TerraformScriptGitRepoDetails gitRepoDetails) {
        this.gitRepoDetails = gitRepoDetails;
    }

    public TerraformAsyncDestroyFromGitRepoRequest tfState(
            @jakarta.annotation.Nonnull String tfState) {

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

    public TerraformAsyncDestroyFromGitRepoRequest webhookConfig(
            @jakarta.annotation.Nonnull WebhookConfig webhookConfig) {

        this.webhookConfig = webhookConfig;
        return this;
    }

    /**
     * Configuration information of webhook.
     *
     * @return webhookConfig
     */
    @jakarta.annotation.Nonnull
    @JsonProperty(JSON_PROPERTY_WEBHOOK_CONFIG)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public WebhookConfig getWebhookConfig() {
        return webhookConfig;
    }

    @JsonProperty(JSON_PROPERTY_WEBHOOK_CONFIG)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setWebhookConfig(@jakarta.annotation.Nonnull WebhookConfig webhookConfig) {
        this.webhookConfig = webhookConfig;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TerraformAsyncDestroyFromGitRepoRequest terraformAsyncDestroyFromGitRepoRequest =
                (TerraformAsyncDestroyFromGitRepoRequest) o;
        return Objects.equals(this.requestId, terraformAsyncDestroyFromGitRepoRequest.requestId)
                && Objects.equals(
                        this.terraformVersion,
                        terraformAsyncDestroyFromGitRepoRequest.terraformVersion)
                && Objects.equals(this.variables, terraformAsyncDestroyFromGitRepoRequest.variables)
                && Objects.equals(
                        this.envVariables, terraformAsyncDestroyFromGitRepoRequest.envVariables)
                && Objects.equals(
                        this.gitRepoDetails, terraformAsyncDestroyFromGitRepoRequest.gitRepoDetails)
                && Objects.equals(this.tfState, terraformAsyncDestroyFromGitRepoRequest.tfState)
                && Objects.equals(
                        this.webhookConfig, terraformAsyncDestroyFromGitRepoRequest.webhookConfig);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                requestId,
                terraformVersion,
                variables,
                envVariables,
                gitRepoDetails,
                tfState,
                webhookConfig);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class TerraformAsyncDestroyFromGitRepoRequest {\n");
        sb.append("    requestId: ").append(toIndentedString(requestId)).append("\n");
        sb.append("    terraformVersion: ").append(toIndentedString(terraformVersion)).append("\n");
        sb.append("    variables: ").append(toIndentedString(variables)).append("\n");
        sb.append("    envVariables: ").append(toIndentedString(envVariables)).append("\n");
        sb.append("    gitRepoDetails: ").append(toIndentedString(gitRepoDetails)).append("\n");
        sb.append("    tfState: ").append(toIndentedString(tfState)).append("\n");
        sb.append("    webhookConfig: ").append(toIndentedString(webhookConfig)).append("\n");
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
