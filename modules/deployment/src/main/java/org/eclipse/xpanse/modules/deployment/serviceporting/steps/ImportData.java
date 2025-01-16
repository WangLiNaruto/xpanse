/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.modules.deployment.serviceporting.steps;

import java.io.Serializable;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** Service porting process import data processing class. */
@Slf4j
@Component
public class ImportData implements Serializable, JavaDelegate {

    private final RuntimeService runtimeService;

    @Autowired
    public ImportData(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    /** Service porting process, import data link business logic(Not yet developed). */
    @Override
    public void execute(DelegateExecution delegateExecution) {
        String processInstanceId = delegateExecution.getProcessInstanceId();
        log.info("start import data.ProcessInstanceId:{}", processInstanceId);
        // TODO Export data process not yet implemented. Skipping.
    }
}