/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 */

package org.eclipse.xpanse.modules.models.service.view;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Data;
import org.eclipse.xpanse.modules.models.service.common.enums.Category;
import org.eclipse.xpanse.modules.models.service.common.enums.Csp;
import org.eclipse.xpanse.modules.models.service.deploy.enums.ServiceDeploymentState;
import org.eclipse.xpanse.modules.models.servicetemplate.enums.ServiceHostingType;

/**
 * Define view object for UI Client to query deployed services.
 */
@Data
public class DeployedService {

    @NotNull
    @Schema(description = "The ID of the service")
    private UUID id;

    /**
     * The category of the Service.
     */
    @NotNull
    @Schema(description = "The catalog of the service")
    private Category category;

    /**
     * The name of the Service.
     */
    @NotNull
    @NotBlank
    @Schema(description = "The name of the service")
    private String name;

    /**
     * Customer provided name for the service.
     */
    @Schema(description = "Customer's name for the service. Used only for customer's reference."
            + "If not provided, this value will be auto-generated")
    private String customerServiceName;

    /**
     * The version of the Service.
     */
    @NotNull
    @NotBlank
    @Schema(description = "The version of the service")
    private String version;

    /**
     * The csp of the Service.
     */
    @NotNull
    @Schema(description = "The provider of the service")
    private Csp csp;

    /**
     * The flavor of the Service.
     */
    @Schema(description = "The flavor of the service")
    private String flavor;

    /**
     * The state of the Service.
     */
    @NotNull
    @Schema(description = "The state of the service")
    private ServiceDeploymentState serviceDeploymentState;

    @NotNull
    @Schema(description = "Defines which cloud service account is used "
            + "for deploying cloud resources.")
    private ServiceHostingType serviceHostingType;

    @NotNull
    @Schema(description = "Time of register service.")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss XXX")
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    private OffsetDateTime createTime;

    @NotNull
    @Schema(description = "Time of update service.")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss XXX")
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    private OffsetDateTime lastModifiedTime;
}