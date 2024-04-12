/*
 * Tofu-Maker API
 * RESTful Services to interact with Tofu-Maker runtime
 *
 * The version of the OpenAPI document: 1.0.0-SNAPSHOT
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.eclipse.xpanse.modules.deployment.deployers.opentofu.tofumaker.generated.model;

import java.util.Objects;
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.xpanse.modules.deployment.deployers.opentofu.tofumaker.generated.model.OpenTofuScriptGitRepoDetails;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * OpenTofuPlanFromGitRepoRequest
 */
@JsonPropertyOrder({
  OpenTofuPlanFromGitRepoRequest.JSON_PROPERTY_VARIABLES,
  OpenTofuPlanFromGitRepoRequest.JSON_PROPERTY_ENV_VARIABLES,
  OpenTofuPlanFromGitRepoRequest.JSON_PROPERTY_GIT_REPO_DETAILS
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.4.0")
public class OpenTofuPlanFromGitRepoRequest {
  public static final String JSON_PROPERTY_VARIABLES = "variables";
  private Map<String, Object> variables = new HashMap<>();

  public static final String JSON_PROPERTY_ENV_VARIABLES = "envVariables";
  private Map<String, String> envVariables = new HashMap<>();

  public static final String JSON_PROPERTY_GIT_REPO_DETAILS = "gitRepoDetails";
  private OpenTofuScriptGitRepoDetails gitRepoDetails;

  public OpenTofuPlanFromGitRepoRequest() {
  }

  public OpenTofuPlanFromGitRepoRequest variables(Map<String, Object> variables) {
    
    this.variables = variables;
    return this;
  }

  public OpenTofuPlanFromGitRepoRequest putVariablesItem(String key, Object variablesItem) {
    this.variables.put(key, variablesItem);
    return this;
  }

   /**
   * Key-value pairs of variables that must be used to execute the OpenTofu request.
   * @return variables
  **/
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


  public OpenTofuPlanFromGitRepoRequest envVariables(Map<String, String> envVariables) {
    
    this.envVariables = envVariables;
    return this;
  }

  public OpenTofuPlanFromGitRepoRequest putEnvVariablesItem(String key, String envVariablesItem) {
    if (this.envVariables == null) {
      this.envVariables = new HashMap<>();
    }
    this.envVariables.put(key, envVariablesItem);
    return this;
  }

   /**
   * Key-value pairs of variables that must be injected as environment variables to open tofu process.
   * @return envVariables
  **/
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


  public OpenTofuPlanFromGitRepoRequest gitRepoDetails(OpenTofuScriptGitRepoDetails gitRepoDetails) {
    
    this.gitRepoDetails = gitRepoDetails;
    return this;
  }

   /**
   * Get gitRepoDetails
   * @return gitRepoDetails
  **/
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_GIT_REPO_DETAILS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public OpenTofuScriptGitRepoDetails getGitRepoDetails() {
    return gitRepoDetails;
  }


  @JsonProperty(JSON_PROPERTY_GIT_REPO_DETAILS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setGitRepoDetails(OpenTofuScriptGitRepoDetails gitRepoDetails) {
    this.gitRepoDetails = gitRepoDetails;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OpenTofuPlanFromGitRepoRequest openTofuPlanFromGitRepoRequest = (OpenTofuPlanFromGitRepoRequest) o;
    return Objects.equals(this.variables, openTofuPlanFromGitRepoRequest.variables) &&
        Objects.equals(this.envVariables, openTofuPlanFromGitRepoRequest.envVariables) &&
        Objects.equals(this.gitRepoDetails, openTofuPlanFromGitRepoRequest.gitRepoDetails);
  }

  @Override
  public int hashCode() {
    return Objects.hash(variables, envVariables, gitRepoDetails);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OpenTofuPlanFromGitRepoRequest {\n");
    sb.append("    variables: ").append(toIndentedString(variables)).append("\n");
    sb.append("    envVariables: ").append(toIndentedString(envVariables)).append("\n");
    sb.append("    gitRepoDetails: ").append(toIndentedString(gitRepoDetails)).append("\n");
    sb.append("}");
    return sb.toString();
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

