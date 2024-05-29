/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 */

package org.eclipse.xpanse.api.exceptions.handler;

import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.xpanse.modules.models.response.Response;
import org.eclipse.xpanse.modules.models.response.ResultType;
import org.eclipse.xpanse.modules.models.servicetemplate.exceptions.IconProcessingFailedException;
import org.eclipse.xpanse.modules.models.servicetemplate.exceptions.InvalidServiceFlavorsException;
import org.eclipse.xpanse.modules.models.servicetemplate.exceptions.InvalidServiceVersionException;
import org.eclipse.xpanse.modules.models.servicetemplate.exceptions.InvalidValueSchemaException;
import org.eclipse.xpanse.modules.models.servicetemplate.exceptions.ServiceTemplateAlreadyRegistered;
import org.eclipse.xpanse.modules.models.servicetemplate.exceptions.ServiceTemplateAlreadyReviewed;
import org.eclipse.xpanse.modules.models.servicetemplate.exceptions.ServiceTemplateNotApproved;
import org.eclipse.xpanse.modules.models.servicetemplate.exceptions.ServiceTemplateNotRegistered;
import org.eclipse.xpanse.modules.models.servicetemplate.exceptions.ServiceTemplateStillInUseException;
import org.eclipse.xpanse.modules.models.servicetemplate.exceptions.ServiceTemplateUpdateNotAllowed;
import org.eclipse.xpanse.modules.models.servicetemplate.exceptions.TerraformScriptFormatInvalidException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Exception handler related to service registration requests.
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RegistrationExceptionHandler {

    /**
     * Exception handler for ServiceAlreadyRegisteredException.
     */
    @ExceptionHandler({ServiceTemplateAlreadyRegistered.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Response handleServiceTemplateAlreadyRegisteredException(
            ServiceTemplateAlreadyRegistered ex) {
        return Response.errorResponse(ResultType.SERVICE_TEMPLATE_ALREADY_REGISTERED,
                Collections.singletonList(ex.getMessage()));

    }

    /**
     * Exception handler for IconProcessingFailedException.
     */
    @ExceptionHandler({IconProcessingFailedException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Response handleIconProcessingFailedException(
            IconProcessingFailedException ex) {
        return Response.errorResponse(ResultType.ICON_PROCESSING_FAILED,
                Collections.singletonList(ex.getMessage()));

    }

    /**
     * Exception handler for ServiceRequestedNotFoundException.
     */
    @ExceptionHandler({ServiceTemplateNotRegistered.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Response handleServiceTemplateNotRegisteredException(
            ServiceTemplateNotRegistered ex) {
        return Response.errorResponse(ResultType.SERVICE_TEMPLATE_NOT_REGISTERED,
                Collections.singletonList(ex.getMessage()));

    }

    /**
     * Exception handler for TerraformScriptFormatInvalidException.
     */
    @ExceptionHandler({TerraformScriptFormatInvalidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Response handleTerraformScriptFormatInvalidException(
            TerraformScriptFormatInvalidException ex) {
        return Response.errorResponse(ResultType.TERRAFORM_SCRIPT_INVALID, ex.getErrorReasons());
    }

    /**
     * Exception handler for ServiceTemplateUpdateNotAllowed.
     */
    @ExceptionHandler({ServiceTemplateUpdateNotAllowed.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Response handleServiceTemplateUpdateNotAllowed(
            ServiceTemplateUpdateNotAllowed ex) {
        return Response.errorResponse(ResultType.SERVICE_TEMPLATE_UPDATE_NOT_ALLOWED,
                Collections.singletonList(ex.getMessage()));
    }

    /**
     * Exception handler for ServiceTemplateUpdateNotAllowed.
     */
    @ExceptionHandler({InvalidValueSchemaException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Response handleInvalidValueSchemaException(
            InvalidValueSchemaException ex) {
        return Response.errorResponse(ResultType.VARIABLE_SCHEMA_DEFINITION_INVALID,
                ex.getInvalidValueSchemaKeys());
    }

    /**
     * Exception handler for ServiceTemplateNotApproved.
     */
    @ExceptionHandler({ServiceTemplateNotApproved.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Response handleServiceTemplateNotApproved(
            ServiceTemplateNotApproved ex) {
        return Response.errorResponse(ResultType.SERVICE_TEMPLATE_NOT_APPROVED,
                Collections.singletonList(ex.getMessage()));
    }


    /**
     * Exception handler for ServiceTemplateAlreadyReviewed.
     */
    @ExceptionHandler({ServiceTemplateAlreadyReviewed.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Response handleServiceTemplateAlreadyReviewed(
            ServiceTemplateAlreadyReviewed ex) {
        return Response.errorResponse(ResultType.SERVICE_TEMPLATE_ALREADY_REVIEWED,
                Collections.singletonList(ex.getMessage()));
    }

    /**
     * Exception handler for InvalidServiceVersionException.
     */
    @ExceptionHandler({InvalidServiceVersionException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Response handleInvalidServiceVersionException(
            InvalidServiceVersionException ex) {
        return Response.errorResponse(ResultType.INVALID_SERVICE_VERSION,
                Collections.singletonList(ex.getMessage()));
    }

    /**
     * Exception handler for InvalidServiceFlavorsException.
     */
    @ExceptionHandler({InvalidServiceFlavorsException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Response handleInvalidServiceFlavorsException(
            InvalidServiceFlavorsException ex) {
        return Response.errorResponse(ResultType.INVALID_SERVICE_FLAVORS, ex.getErrorReasons());
    }

    /**
     * Exception handler for InvalidServiceFlavorsException.
     */
    @ExceptionHandler({ServiceTemplateStillInUseException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Response handleServiceTemplateStillInUseException(
            ServiceTemplateStillInUseException ex) {
        return Response.errorResponse(ResultType.SERVICE_TEMPLATE_STILL_IN_USE,
                Collections.singletonList(ex.getMessage()));
    }
}
