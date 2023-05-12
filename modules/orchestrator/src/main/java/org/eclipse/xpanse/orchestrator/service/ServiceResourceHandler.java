/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.orchestrator.service;

import org.eclipse.xpanse.modules.deployment.DeployResourceHandler;

/**
 * This interface describes orchestrator plugin in charge of interacting with backend fundamental
 * APIs.
 */
public interface ServiceResourceHandler {


    /**
     * get the resource handler of the CSP plugin.
     *
     * @return the resource handler of the plugin.
     */
    DeployResourceHandler getResourceHandler();
}