/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 */

package org.eclipse.xpanse.modules.models.service.modify;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import java.util.Map;
import lombok.Data;
import lombok.ToString;

/**
 * ModifyRequest model.
 */
@ToString(callSuper = true)
@Data
public class ModifyRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 8759112725757850274L;

    /**
     * The id of the user who ordered the Service.
     */
    @Hidden
    private String userId;

    /**
     * The flavor of the Service.
     */
    @Schema(description = "The flavor of the Service.")
    private String flavor;
    /**
     * The property of the Service.
     */
    @Schema(description = "The properties for the requested service")
    private Map<String, Object> serviceRequestProperties;

}
