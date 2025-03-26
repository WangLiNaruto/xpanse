/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 */

package org.eclipse.xpanse.modules.models.serviceconfiguration.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

/** Exception thrown when service action change order already exists. */
@EqualsAndHashCode(callSuper = true)
@Data
public class ServiceObjectChangeOrderAlreadyExistsException extends RuntimeException {
    public ServiceObjectChangeOrderAlreadyExistsException(String message) {
        super(message);
    }
}
