/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 */

package org.eclipse.xpanse.modules.deployment.deployers.opentofu.tofumaker;

import jakarta.annotation.Resource;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.xpanse.modules.database.service.ServiceDeploymentEntity;
import org.eclipse.xpanse.modules.database.serviceorder.ServiceOrderEntity;
import org.eclipse.xpanse.modules.deployment.DeployResultManager;
import org.eclipse.xpanse.modules.deployment.deployers.opentofu.callbacks.OpenTofuDeploymentResultCallbackManager;
import org.eclipse.xpanse.modules.deployment.deployers.opentofu.tofumaker.generated.api.RetrieveOpenTofuResultApi;
import org.eclipse.xpanse.modules.deployment.deployers.opentofu.tofumaker.generated.model.OpenTofuResult;
import org.eclipse.xpanse.modules.deployment.deployers.opentofu.tofumaker.generated.model.Response;
import org.eclipse.xpanse.modules.models.response.ErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

/** Bean to manage task result via tofu-maker. */
@Slf4j
@Component
public class TofuMakerResultRefetchManager {

    @Resource private DeployResultManager deployResultManager;

    @Resource
    private OpenTofuDeploymentResultCallbackManager openTofuDeploymentResultCallbackManager;

    @Resource private RetrieveOpenTofuResultApi retrieveOpenTofuResultApi;

    /** retrieve openTofu result. */
    public void retrieveOpenTofuResult(
            ServiceDeploymentEntity serviceDeployment, ServiceOrderEntity serviceOrder) {
        try {
            ResponseEntity<OpenTofuResult> result =
                    retrieveOpenTofuResultApi.getStoredTaskResultByRequestIdWithHttpInfo(
                            String.valueOf(serviceOrder.getOrderId()));
            if (result.getStatusCode() == HttpStatus.NO_CONTENT) {
                return;
            }
            if (Objects.nonNull(result.getBody())) {
                openTofuDeploymentResultCallbackManager.orderCallback(
                        serviceOrder.getOrderId(), result.getBody());
            }
        } catch (HttpClientErrorException e) {
            Response response = e.getResponseBodyAs(Response.class);
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST
                    && Objects.nonNull(response)
                    && response.getResultType()
                            == Response.ResultTypeEnum
                                    .RESULT_ALREADY_RETURNED_OR_REQUEST_ID_INVALID) {
                deployResultManager.saveDeploymentResultWhenErrorReceived(
                        serviceDeployment, serviceOrder, ErrorType.TOFU_MAKER_REQUEST_FAILED, e);
            } else {
                log.error(
                        "Re-fetch tofu-maker result failed with known error. requestId {},"
                                + " error {} ",
                        serviceOrder.getOrderId(),
                        e.getMessage());
            }
        } catch (Exception e) {
            log.error(
                    "Re-fetch tofu-maker result failed with unknown error. requestId {},"
                            + " error {} ",
                    serviceOrder.getOrderId(),
                    e.getMessage());
        }
    }
}
