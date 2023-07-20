/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.runtime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.c4_soft.springaddons.security.oauth2.test.annotations.OpenIdClaims;
import com.c4_soft.springaddons.security.oauth2.test.annotations.WithMockBearerTokenAuthentication;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.xpanse.modules.models.response.Response;
import org.eclipse.xpanse.modules.models.response.ResultType;
import org.eclipse.xpanse.modules.models.system.BackendSystemStatus;
import org.eclipse.xpanse.modules.models.system.SystemStatus;
import org.eclipse.xpanse.modules.models.system.enums.BackendSystemType;
import org.eclipse.xpanse.modules.models.system.enums.DatabaseType;
import org.eclipse.xpanse.modules.models.system.enums.HealthStatus;
import org.eclipse.xpanse.modules.models.system.enums.IdentityProviderType;
import org.eclipse.xpanse.runtime.database.mariadb.AbstractMariaDbIntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Test for AdminServicesApi with zitadel and mariadb is active.
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.profiles.active=mariadb,zitadel,zitadel-testbed"})
@AutoConfigureMockMvc
class AdminServicesApiWithZitadelAndMariaDbTest extends AbstractMariaDbIntegrationTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${spring.datasource.url:jdbc:h2:file:./testdb}")
    private String dataSourceUrl;

    @Value("${authorization-server-endpoint}")
    private String iamServerEndpoint;

    @Resource
    private MockMvc mockMvc;

    @Test
    void testHealthCheckUnauthorized() throws Exception {
        // SetUp
        Response responseModel = Response.errorResponse(ResultType.UNAUTHORIZED,
                Collections.singletonList(ResultType.UNAUTHORIZED.toValue()));
        String resBody = objectMapper.writeValueAsString(responseModel);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/xpanse/health")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(response.getStatus(), HttpStatus.UNAUTHORIZED.value());
        assertTrue(StringUtils.isNotEmpty(response.getContentAsString()));
        assertEquals(resBody, response.getContentAsString());

    }


    @Test
    @WithMockBearerTokenAuthentication(authorities = {"admin"},
            attributes = @OpenIdClaims(sub = "admin-id", preferredUsername = "xpanse-admin"))
    void testHealthCheckWithRoleAdmin() throws Exception {
        // SetUp
        SystemStatus systemStatus = new SystemStatus();
        systemStatus.setHealthStatus(HealthStatus.OK);
        systemStatus.setBackendSystemStatuses(setUpBackendSystemStatusList(true));
        String resBody = objectMapper.writeValueAsString(systemStatus);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/xpanse/health")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(response.getStatus(), HttpStatus.OK.value());
        assertTrue(StringUtils.isNotEmpty(response.getContentAsString()));
        assertEquals(resBody, response.getContentAsString());

    }


    @Test
    @WithMockBearerTokenAuthentication(authorities = {"user", "csp"},
            attributes = @OpenIdClaims(sub = "user-id", preferredUsername = "xpanse-user"))
    void testHealthCheckWithRoleNotAdmin() throws Exception {
        // SetUp
        SystemStatus systemStatus = new SystemStatus();
        systemStatus.setHealthStatus(HealthStatus.OK);
        systemStatus.setBackendSystemStatuses(setUpBackendSystemStatusList(false));
        String resBody = objectMapper.writeValueAsString(systemStatus);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/xpanse/health")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(response.getStatus(), HttpStatus.OK.value());
        assertTrue(StringUtils.isNotEmpty(response.getContentAsString()));
        assertEquals(resBody, response.getContentAsString());

    }

    private List<BackendSystemStatus> setUpBackendSystemStatusList(boolean isAdmin) {
        BackendSystemStatus databaseStatus = new BackendSystemStatus();
        databaseStatus.setBackendSystemType(BackendSystemType.DATABASE);
        databaseStatus.setHealthStatus(HealthStatus.OK);
        databaseStatus.setName(DatabaseType.MARIADB.toValue());

        BackendSystemStatus identityProviderStatus = new BackendSystemStatus();
        identityProviderStatus.setBackendSystemType(BackendSystemType.IDENTITY_PROVIDER);
        identityProviderStatus.setHealthStatus(HealthStatus.OK);
        identityProviderStatus.setName(IdentityProviderType.ZITADEL.toValue());

        if (isAdmin) {
            databaseStatus.setEndpoint(dataSourceUrl);
            identityProviderStatus.setEndpoint(iamServerEndpoint);
        }
        List<BackendSystemStatus> backendSystemStatuses = new ArrayList<>();
        backendSystemStatuses.add(identityProviderStatus);
        backendSystemStatuses.add(databaseStatus);

        return backendSystemStatuses;
    }


}