/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.modules.models.servicetemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;
import org.eclipse.xpanse.modules.models.billing.Billing;
import org.eclipse.xpanse.modules.models.common.enums.Category;
import org.eclipse.xpanse.modules.models.servicetemplate.enums.ServiceHostingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.BeanUtils;

/** Test of Ocl. */
class OclTest {

    private final Category category = Category.COMPUTE;
    private final String version = "2.0";
    private final String name = "kafka";
    private final String serviceVersion = "1.0.0";
    private final String description = "description";
    private final String serviceVendor = "serviceVendor";
    private final String icon = "icon";
    private final String eula = "eula";
    private final ServiceHostingType serviceHostingType = ServiceHostingType.SELF;
    @Mock private CloudServiceProvider cloudServiceProvider;
    @Mock private Deployment deployment;
    @Mock private FlavorsWithPrice flavors;
    @Mock private Billing billing;
    @Mock private ServiceProviderContactDetails serviceProviderContactDetails;
    @Mock private ServiceChangeManage serviceConfigurationManage;
    @Mock private List<ServiceAction> serviceActions;
    @Mock private List<ServiceObject> serviceObjects;

    private Ocl ocl;

    @BeforeEach
    void setUp() {
        ocl = new Ocl();
        ocl.setCategory(category);
        ocl.setVersion(version);
        ocl.setName(name);
        ocl.setServiceVersion(serviceVersion);
        ocl.setDescription(description);
        ocl.setServiceVendor(serviceVendor);
        ocl.setIcon(icon);
        ocl.setCloudServiceProvider(cloudServiceProvider);
        ocl.setDeployment(deployment);
        ocl.setFlavors(flavors);
        ocl.setBilling(billing);
        ocl.setServiceHostingType(serviceHostingType);
        ocl.setEula(eula);
        ocl.setServiceProviderContactDetails(serviceProviderContactDetails);
        ocl.setServiceConfigurationManage(serviceConfigurationManage);
        ocl.setServiceActions(serviceActions);
        ocl.setServiceObjects(serviceObjects);
    }

    @Test
    void testGetters() {
        assertEquals(category, ocl.getCategory());
        assertEquals(version, ocl.getVersion());
        assertEquals(name, ocl.getName());
        assertEquals(serviceVersion, ocl.getServiceVersion());
        assertEquals(description, ocl.getDescription());
        assertEquals(serviceVendor, ocl.getServiceVendor());
        assertEquals(icon, ocl.getIcon());
        assertEquals(cloudServiceProvider, ocl.getCloudServiceProvider());
        assertEquals(deployment, ocl.getDeployment());
        assertEquals(billing, ocl.getBilling());
        assertEquals(serviceHostingType, ocl.getServiceHostingType());
        assertEquals(serviceProviderContactDetails, ocl.getServiceProviderContactDetails());
        assertEquals(flavors, ocl.getFlavors());
        assertEquals(eula, ocl.getEula());
        assertEquals(serviceConfigurationManage, ocl.getServiceConfigurationManage());
        assertEquals(serviceActions, ocl.getServiceActions());
        assertEquals(serviceObjects, ocl.getServiceObjects());
    }

    @Test
    public void testEqualsAndHashCode() {
        Object obj = new Object();
        assertNotEquals(ocl, obj);
        assertNotEquals(ocl.hashCode(), obj.hashCode());

        Ocl ocl1 = new Ocl();
        assertNotEquals(ocl, ocl1);
        assertNotEquals(ocl.hashCode(), ocl1.hashCode());

        BeanUtils.copyProperties(ocl, ocl1);
        assertEquals(ocl, ocl1);
        assertEquals(ocl.hashCode(), ocl1.hashCode());
    }

    @Test
    void testToString() {
        String expectedString =
                "Ocl("
                        + "category="
                        + category
                        + ", version="
                        + version
                        + ", name="
                        + name
                        + ", serviceVersion="
                        + serviceVersion
                        + ", description="
                        + description
                        + ", serviceVendor="
                        + serviceVendor
                        + ", icon="
                        + icon
                        + ", cloudServiceProvider="
                        + cloudServiceProvider
                        + ", deployment="
                        + deployment
                        + ", flavors="
                        + flavors
                        + ", billing="
                        + billing
                        + ", serviceHostingType="
                        + serviceHostingType
                        + ", serviceProviderContactDetails="
                        + serviceProviderContactDetails
                        + ", eula="
                        + eula
                        + ", "
                        + "serviceConfigurationManage="
                        + serviceConfigurationManage
                        + ", "
                        + "serviceActions="
                        + serviceActions
                        + ", "
                        + "serviceObjects="
                        + serviceObjects
                        + ")";
        assertEquals(expectedString, ocl.toString());
    }
}
