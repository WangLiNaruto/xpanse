/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.api.controllers;

import static org.eclipse.xpanse.modules.models.security.constant.RoleConstants.ROLE_ADMIN;
import static org.eclipse.xpanse.modules.models.security.constant.RoleConstants.ROLE_USER;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.xpanse.modules.models.workflow.WorkFlowTask;
import org.eclipse.xpanse.modules.security.IdentityProviderManager;
import org.eclipse.xpanse.modules.workflow.consts.MigrateConstants;
import org.eclipse.xpanse.modules.workflow.utils.WorkflowProcessUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API methods for workflow.
 */
@Slf4j
@RestController
@RequestMapping("/xpanse")
@CrossOrigin
@Secured({ROLE_ADMIN, ROLE_USER})
public class WorkFlowApi {

    @Resource
    private WorkflowProcessUtils workflowProcessUtils;

    @Resource
    private IdentityProviderManager identityProviderManager;

    /**
     * Query the tasks that need to be done by me.
     */
    @Tag(name = "WorkFlow", description = "APIs to manage the WorkFlow")
    @Operation(description = "Query the tasks that need to be done by me")
    @GetMapping(value = "/workflow/task/todo", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<WorkFlowTask> queryTodoTasks() {
        String userId = identityProviderManager.getCurrentUserInfo().getUserId();
        return workflowProcessUtils.todoTasks(userId);
    }

    /**
     * Query the tasks I have completed.
     */
    @Tag(name = "WorkFlow", description = "APIs to manage the WorkFlow")
    @Operation(description = "Query the tasks I have completed")
    @GetMapping(value = "/workflow/task/done", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<WorkFlowTask> queryDoneTasks() {
        String userId = identityProviderManager.getCurrentUserInfo().getUserId();
        return workflowProcessUtils.doneTasks(userId);
    }

    /**
     * Complete tasks based on task ID and set global process variables.
     */
    @Tag(name = "WorkFlow", description = "APIs to manage the WorkFlow")
    @Operation(description = "Complete tasks by task ID and set global process variables .")
    @PutMapping(value = "/workflow/task/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void completeTask(@PathVariable("id") String taskId, @RequestBody Map<String,
            Object> variables) {
        workflowProcessUtils.completeTask(taskId, variables);
    }

    /**
     * Manage failed task orders.
     */
    @Tag(name = "WorkFlow", description = "APIs to manage the WorkFlow")
    @Operation(description = "Manage failed task orders.")
    @PutMapping(value = "/workflow/task/{id}/{retryOrder}", produces =
            MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void manageFailedOrder(@PathVariable("id") String taskId,
            @PathVariable boolean retryOrder) {
        Map<String, Object> variables = new HashMap<>();
        variables.put(MigrateConstants.IS_RETRY_TASK, retryOrder);
        workflowProcessUtils.completeTask(taskId, variables);
    }

}