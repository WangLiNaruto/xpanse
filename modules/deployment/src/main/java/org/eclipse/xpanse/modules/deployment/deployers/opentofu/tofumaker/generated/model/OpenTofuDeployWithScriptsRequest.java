/*
 * Tofu-Maker API
 * RESTful Services to interact with Tofu-Maker runtime
 *
 * The version of the OpenAPI document: 1.0.7-SNAPSHOT
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.eclipse.xpanse.modules.deployment.deployers.opentofu.tofumaker.generated.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * OpenTofuDeployWithScriptsRequest
 */
@JsonPropertyOrder({
        OpenTofuDeployWithScriptsRequest.JSON_PROPERTY_REQUEST_ID,
        OpenTofuDeployWithScriptsRequest.JSON_PROPERTY_OPEN_TOFU_VERSION,
        OpenTofuDeployWithScriptsRequest.JSON_PROPERTY_IS_PLAN_ONLY,
        OpenTofuDeployWithScriptsRequest.JSON_PROPERTY_VARIABLES,
        OpenTofuDeployWithScriptsRequest.JSON_PROPERTY_ENV_VARIABLES,
        OpenTofuDeployWithScriptsRequest.JSON_PROPERTY_SCRIPTS
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.9.0")
public class OpenTofuDeployWithScriptsRequest {
    public static final String JSON_PROPERTY_REQUEST_ID = "requestId";
    public static final String JSON_PROPERTY_OPEN_TOFU_VERSION = "openTofuVersion";
    public static final String JSON_PROPERTY_IS_PLAN_ONLY = "isPlanOnly";
    public static final String JSON_PROPERTY_VARIABLES = "variables";
    public static final String JSON_PROPERTY_ENV_VARIABLES = "envVariables";
    public static final String JSON_PROPERTY_SCRIPTS = "scripts";
    private UUID requestId;
    private String openTofuVersion;
    private Boolean isPlanOnly;
    private Map<String, Object> variables = new HashMap<>();
    private Map<String, String> envVariables = new HashMap<>();
    private List<String> scripts = new ArrayList<>();

    public OpenTofuDeployWithScriptsRequest() {
    }

    public OpenTofuDeployWithScriptsRequest requestId(UUID requestId) {

        this.requestId = requestId;
        return this;
    }

    /**
     * Id of the request.
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
    public void setRequestId(UUID requestId) {
        this.requestId = requestId;
    }

    public OpenTofuDeployWithScriptsRequest openTofuVersion(String openTofuVersion) {

        this.openTofuVersion = openTofuVersion;
        return this;
    }

    /**
     * The required version of the OpenTofu which will execute the scripts.
     *
     * @return openTofuVersion
     */
    @jakarta.annotation.Nonnull
    @JsonProperty(JSON_PROPERTY_OPEN_TOFU_VERSION)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)

    public String getOpenTofuVersion() {
        return openTofuVersion;
    }


    @JsonProperty(JSON_PROPERTY_OPEN_TOFU_VERSION)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setOpenTofuVersion(String openTofuVersion) {
        this.openTofuVersion = openTofuVersion;
    }

    public OpenTofuDeployWithScriptsRequest isPlanOnly(Boolean isPlanOnly) {

        this.isPlanOnly = isPlanOnly;
        return this;
    }

    /**
     * Flag to control if the deployment must only generate the OpenTofu or it must also apply the changes.
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
    public void setIsPlanOnly(Boolean isPlanOnly) {
        this.isPlanOnly = isPlanOnly;
    }

    public OpenTofuDeployWithScriptsRequest variables(Map<String, Object> variables) {

        this.variables = variables;
        return this;
    }

    public OpenTofuDeployWithScriptsRequest putVariablesItem(String key, Object variablesItem) {
        this.variables.put(key, variablesItem);
        return this;
    }

    /**
     * Key-value pairs of variables that must be used to execute the OpenTofu request.
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
    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }

    public OpenTofuDeployWithScriptsRequest envVariables(Map<String, String> envVariables) {

        this.envVariables = envVariables;
        return this;
    }

    public OpenTofuDeployWithScriptsRequest putEnvVariablesItem(String key,
                                                                String envVariablesItem) {
        if (this.envVariables == null) {
            this.envVariables = new HashMap<>();
        }
        this.envVariables.put(key, envVariablesItem);
        return this;
    }

    /**
     * Key-value pairs of variables that must be injected as environment variables to OpenTofu process.
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
    public void setEnvVariables(Map<String, String> envVariables) {
        this.envVariables = envVariables;
    }

    public OpenTofuDeployWithScriptsRequest scripts(List<String> scripts) {

        this.scripts = scripts;
        return this;
    }

    public OpenTofuDeployWithScriptsRequest addScriptsItem(String scriptsItem) {
        if (this.scripts == null) {
            this.scripts = new ArrayList<>();
        }
        this.scripts.add(scriptsItem);
        return this;
    }

    /**
     * List of OpenTofu script files to be considered for deploying changes.
     *
     * @return scripts
     */
    @jakarta.annotation.Nonnull
    @JsonProperty(JSON_PROPERTY_SCRIPTS)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)

    public List<String> getScripts() {
        return scripts;
    }


    @JsonProperty(JSON_PROPERTY_SCRIPTS)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setScripts(List<String> scripts) {
        this.scripts = scripts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OpenTofuDeployWithScriptsRequest openTofuDeployWithScriptsRequest =
                (OpenTofuDeployWithScriptsRequest) o;
        return Objects.equals(this.requestId, openTofuDeployWithScriptsRequest.requestId) &&
                Objects.equals(this.openTofuVersion,
                        openTofuDeployWithScriptsRequest.openTofuVersion) &&
                Objects.equals(this.isPlanOnly, openTofuDeployWithScriptsRequest.isPlanOnly) &&
                Objects.equals(this.variables, openTofuDeployWithScriptsRequest.variables) &&
                Objects.equals(this.envVariables, openTofuDeployWithScriptsRequest.envVariables) &&
                Objects.equals(this.scripts, openTofuDeployWithScriptsRequest.scripts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestId, openTofuVersion, isPlanOnly, variables, envVariables,
                scripts);
    }

    @Override
    public String toString() {
        String sb = "class OpenTofuDeployWithScriptsRequest {\n"
                + "    requestId: " + toIndentedString(requestId) + "\n"
                + "    openTofuVersion: " + toIndentedString(openTofuVersion) + "\n"
                + "    isPlanOnly: " + toIndentedString(isPlanOnly) + "\n"
                + "    variables: " + toIndentedString(variables) + "\n"
                + "    envVariables: " + toIndentedString(envVariables) + "\n"
                + "    scripts: " + toIndentedString(scripts) + "\n"
                + "}";
        return sb;
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

}

