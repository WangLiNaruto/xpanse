/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.plugins.scs.manage;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.xpanse.modules.credential.CredentialCenter;
import org.eclipse.xpanse.modules.models.common.enums.Csp;
import org.eclipse.xpanse.modules.models.credential.AbstractCredentialInfo;
import org.eclipse.xpanse.modules.models.credential.enums.CredentialType;
import org.eclipse.xpanse.modules.models.service.deploy.enums.DeployResourceKind;
import org.eclipse.xpanse.plugins.scs.common.keystone.ScsKeystoneManager;
import org.openstack4j.api.OSClient;
import org.openstack4j.api.OSClient.OSClientV3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Scs Resource Manager.
 */
@Slf4j
@Component
public class ScsResourceManager {

    private final CredentialCenter credentialCenter;
    private final ScsKeystoneManager scsKeystoneManager;

    @Autowired
    public ScsResourceManager(
            CredentialCenter credentialCenter,
            ScsKeystoneManager scsKeystoneManager) {
        this.credentialCenter = credentialCenter;
        this.scsKeystoneManager = scsKeystoneManager;
    }

    /**
     * List Scs resource by the kind of ReusableCloudResource.
     */
    public List<String> getExistingResourcesOfType(String userId,
            String region, DeployResourceKind kind) {
        if (kind == DeployResourceKind.VPC) {
            return getVpcList(userId, region);
        } else if (kind == DeployResourceKind.SUBNET) {
            return getSubnetList(userId, region);
        } else if (kind == DeployResourceKind.SECURITY_GROUP) {
            return getSecurityGroupsList(userId, region);
        } else if (kind == DeployResourceKind.SECURITY_GROUP_RULE) {
            return getSecurityGroupRuleList(userId, region);
        } else if (kind == DeployResourceKind.PUBLIC_IP) {
            return getPublicIpList(userId, region);
        } else if (kind == DeployResourceKind.VOLUME) {
            return getVolumeList(userId, region);
        } else if (kind == DeployResourceKind.KEYPAIR) {
            return getKeyPairsList(userId, region);
        } else {
            return new ArrayList<>();
        }
    }

    private List<String> getVpcList(String userId, String region) {
        try {
            OSClientV3 osClient = getOsClient(userId, null, region);
            List<String> vpcs = new ArrayList<>();
            osClient.networking().network().list().stream().forEach(network -> {
                vpcs.add(network.getName());
            });
            return vpcs;
        } catch (RuntimeException e) {
            log.error("Get Scs vpc resources failed, error:{}", e.getMessage());
            return new ArrayList<>();
        }
    }

    private List<String> getSubnetList(String userId, String region) {
        try {
            OSClientV3 osClient = getOsClient(userId, null, region);
            List<String> subnets = new ArrayList<>();
            osClient.networking().subnet().list().stream().forEach(subnet -> {
                subnets.add(subnet.getName());
            });
            return subnets;
        } catch (RuntimeException e) {
            log.error("Get Scs subnet resources failed, error:{}", e.getMessage());
            return new ArrayList<>();
        }
    }

    private List<String> getSecurityGroupsList(String userId, String region) {
        try {
            OSClientV3 osClient = getOsClient(userId, null, region);
            List<String> securityGroups = new ArrayList<>();
            osClient.networking().securitygroup().list().stream().forEach(securityGroup -> {
                securityGroups.add(securityGroup.getName());
            });
            return securityGroups;
        } catch (RuntimeException e) {
            log.error("Get Scs SecurityGroup resources failed, error:{}", e.getMessage());
            return new ArrayList<>();
        }
    }

    private List<String> getSecurityGroupRuleList(String userId, String region) {
        try {
            OSClientV3 osClient = getOsClient(userId, null, region);
            List<String> securityGroupRules = new ArrayList<>();
            osClient.networking().securityrule().list().stream().forEach(securityGroupRule -> {
                securityGroupRules.add(securityGroupRule.getId());
            });
            return securityGroupRules;
        } catch (RuntimeException e) {
            log.error("Get Scs SecurityGroupRule resources failed, error:{}", e.getMessage());
            return new ArrayList<>();
        }
    }

    private List<String> getPublicIpList(String userId, String region) {
        try {
            OSClientV3 osClient = getOsClient(userId, null, region);
            List<String> publicIps = new ArrayList<>();
            osClient.networking().floatingip().list().stream().forEach(floatingip -> {
                publicIps.add(floatingip.getFloatingIpAddress());
            });
            return publicIps;
        } catch (RuntimeException e) {
            log.error("Get Scs publicIp resources failed, error:{}", e.getMessage());
            return new ArrayList<>();
        }
    }

    private List<String> getVolumeList(String userId, String region) {
        try {
            OSClientV3 osClient = getOsClient(userId, null, region);
            List<String> volumes = new ArrayList<>();
            osClient.blockStorage().volumes().list().stream().forEach(volume -> {
                volumes.add(volume.getName());
            });
            return volumes;
        } catch (RuntimeException e) {
            log.error("Get Scs volume resources failed, error:{}", e.getMessage());
            return new ArrayList<>();
        }
    }

    private List<String> getKeyPairsList(String userId, String region) {
        try {
            OSClientV3 osClient = getOsClient(userId, null, region);
            List<String> keyPairs = new ArrayList<>();
            osClient.compute().keypairs().list().stream().forEach(keyPair -> {
                keyPairs.add(keyPair.getName());
            });
            return keyPairs;
        } catch (RuntimeException e) {
            log.error("Get Scs keyPair resources failed, error:{}", e.getMessage());
            return new ArrayList<>();
        }
    }

    private OSClient.OSClientV3 getOsClient(String userId, UUID serviceId, String region) {
        AbstractCredentialInfo credentialInfo =
                credentialCenter.getCredential(Csp.SCS, CredentialType.VARIABLES, userId);
        return scsKeystoneManager.getAuthenticatedClient(serviceId, credentialInfo)
                .useRegion(region);
    }

}