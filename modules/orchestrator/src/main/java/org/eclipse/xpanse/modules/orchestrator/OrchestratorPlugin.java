/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.modules.orchestrator;

import java.util.List;
import org.eclipse.xpanse.modules.models.common.enums.Csp;
import org.eclipse.xpanse.modules.orchestrator.audit.OperationalAudit;
import org.eclipse.xpanse.modules.orchestrator.credential.AuthenticationCapabilities;
import org.eclipse.xpanse.modules.orchestrator.deployment.ServiceResourceHandler;
import org.eclipse.xpanse.modules.orchestrator.monitor.ServiceMetricsExporter;
import org.eclipse.xpanse.modules.orchestrator.price.ServicePriceCalculator;
import org.eclipse.xpanse.modules.orchestrator.servicestate.ServiceStateManager;


/**
 * This interface describes orchestrator plugin in charge of interacting with backend fundamental
 * APIs.
 */
public interface OrchestratorPlugin extends ServiceResourceHandler, AuthenticationCapabilities,
        ServiceMetricsExporter, ServiceStateManager, OperationalAudit, ServicePriceCalculator {

    /**
     * Get the Csp of the plugin.
     *
     * @return cloud service provider.
     */
    Csp getCsp();

    /**
     * Get the required properties of the plugin.
     *
     * @return required properties.
     */
    List<String> requiredProperties();


    /**
     * Check if auto approve service template is enabled.
     *
     * @return true if auto approve service template is enabled.
     */
    boolean autoApproveServiceTemplateIsEnabled();
}
