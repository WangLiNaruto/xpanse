package org.eclipse.xpanse.modules.database.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.eclipse.xpanse.modules.models.service.common.enums.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.OptimisticLockingFailureException;

@ExtendWith(MockitoExtension.class)
class DatabaseDeployServiceStorageTest {

    @Mock
    private DeployServiceRepository mockDeployServiceRepository;

    private DatabaseDeployServiceStorage databaseDeployServiceStorageUnderTest;

    @BeforeEach
    void setUp() {
        databaseDeployServiceStorageUnderTest =
                new DatabaseDeployServiceStorage(mockDeployServiceRepository);
    }

    @Test
    void testStore() {
        final DeployServiceEntity deployServiceEntity = new DeployServiceEntity();
        deployServiceEntity.setId(UUID.fromString("9803512b-16b7-4eef-8aba-5e2495aa6fd2"));
        deployServiceEntity.setUserName("userName");
        deployServiceEntity.setCategory(Category.AI);
        deployServiceEntity.setName("name");
        deployServiceEntity.setCustomerServiceName("customerServiceName");
        databaseDeployServiceStorageUnderTest.store(deployServiceEntity);
        final DeployServiceEntity entity = new DeployServiceEntity();
        entity.setId(UUID.fromString("9803512b-16b7-4eef-8aba-5e2495aa6fd2"));
        entity.setUserName("userName");
        entity.setCategory(Category.AI);
        entity.setName("name");
        entity.setCustomerServiceName("customerServiceName");
        verify(mockDeployServiceRepository).save(entity);
    }

    @Test
    void testStore_DeployServiceRepositoryThrowsOptimisticLockingFailureException() {
        final DeployServiceEntity deployServiceEntity = new DeployServiceEntity();
        deployServiceEntity.setId(UUID.fromString("9803512b-16b7-4eef-8aba-5e2495aa6fd2"));
        deployServiceEntity.setUserName("userName");
        deployServiceEntity.setCategory(Category.AI);
        deployServiceEntity.setName("name");
        deployServiceEntity.setCustomerServiceName("customerServiceName");
        final DeployServiceEntity entity = new DeployServiceEntity();
        entity.setId(UUID.fromString("9803512b-16b7-4eef-8aba-5e2495aa6fd2"));
        entity.setUserName("userName");
        entity.setCategory(Category.AI);
        entity.setName("name");
        entity.setCustomerServiceName("customerServiceName");
        when(mockDeployServiceRepository.save(entity))
                .thenThrow(OptimisticLockingFailureException.class);
        assertThatThrownBy(() -> databaseDeployServiceStorageUnderTest.store(
                deployServiceEntity)).isInstanceOf(OptimisticLockingFailureException.class);
    }

    @Test
    void testStoreAndFlush() {
        final DeployServiceEntity deployServiceEntity = new DeployServiceEntity();
        deployServiceEntity.setId(UUID.fromString("9803512b-16b7-4eef-8aba-5e2495aa6fd2"));
        deployServiceEntity.setUserName("userName");
        deployServiceEntity.setCategory(Category.AI);
        deployServiceEntity.setName("name");
        deployServiceEntity.setCustomerServiceName("customerServiceName");
        databaseDeployServiceStorageUnderTest.storeAndFlush(deployServiceEntity);
        final DeployServiceEntity entity = new DeployServiceEntity();
        entity.setId(UUID.fromString("9803512b-16b7-4eef-8aba-5e2495aa6fd2"));
        entity.setUserName("userName");
        entity.setCategory(Category.AI);
        entity.setName("name");
        entity.setCustomerServiceName("customerServiceName");
        verify(mockDeployServiceRepository).saveAndFlush(entity);
    }

    @Test
    void testServices() {
        final DeployServiceEntity deployServiceEntity = new DeployServiceEntity();
        deployServiceEntity.setId(UUID.fromString("9803512b-16b7-4eef-8aba-5e2495aa6fd2"));
        deployServiceEntity.setUserName("userName");
        deployServiceEntity.setCategory(Category.AI);
        deployServiceEntity.setName("name");
        deployServiceEntity.setCustomerServiceName("customerServiceName");
        final List<DeployServiceEntity> expectedResult = List.of(deployServiceEntity);
        final DeployServiceEntity deployServiceEntity1 = new DeployServiceEntity();
        deployServiceEntity1.setId(UUID.fromString("9803512b-16b7-4eef-8aba-5e2495aa6fd2"));
        deployServiceEntity1.setUserName("userName");
        deployServiceEntity1.setCategory(Category.AI);
        deployServiceEntity1.setName("name");
        deployServiceEntity1.setCustomerServiceName("customerServiceName");
        final List<DeployServiceEntity> deployServiceEntities = List.of(deployServiceEntity1);
        when(mockDeployServiceRepository.findAll()).thenReturn(deployServiceEntities);
        final List<DeployServiceEntity> result = databaseDeployServiceStorageUnderTest.services();
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testServices_DeployServiceRepositoryReturnsNoItems() {
        when(mockDeployServiceRepository.findAll()).thenReturn(Collections.emptyList());
        final List<DeployServiceEntity> result = databaseDeployServiceStorageUnderTest.services();
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testFindDeployServiceById() {
        final DeployServiceEntity expectedResult = new DeployServiceEntity();
        expectedResult.setId(UUID.fromString("9803512b-16b7-4eef-8aba-5e2495aa6fd2"));
        expectedResult.setUserName("userName");
        expectedResult.setCategory(Category.AI);
        expectedResult.setName("name");
        expectedResult.setCustomerServiceName("customerServiceName");
        final DeployServiceEntity deployServiceEntity1 = new DeployServiceEntity();
        deployServiceEntity1.setId(UUID.fromString("9803512b-16b7-4eef-8aba-5e2495aa6fd2"));
        deployServiceEntity1.setUserName("userName");
        deployServiceEntity1.setCategory(Category.AI);
        deployServiceEntity1.setName("name");
        deployServiceEntity1.setCustomerServiceName("customerServiceName");
        final Optional<DeployServiceEntity> deployServiceEntity = Optional.of(deployServiceEntity1);
        when(mockDeployServiceRepository.findById(
                UUID.fromString("96e3485f-7b9e-4e7e-a239-851e4c53ec75")))
                .thenReturn(deployServiceEntity);
        final DeployServiceEntity result =
                databaseDeployServiceStorageUnderTest.findDeployServiceById(
                        UUID.fromString("96e3485f-7b9e-4e7e-a239-851e4c53ec75"));
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testFindDeployServiceById_DeployServiceRepositoryReturnsAbsent() {
        when(mockDeployServiceRepository.findById(
                UUID.fromString("96e3485f-7b9e-4e7e-a239-851e4c53ec75")))
                .thenReturn(Optional.empty());
        final DeployServiceEntity result =
                databaseDeployServiceStorageUnderTest.findDeployServiceById(
                        UUID.fromString("96e3485f-7b9e-4e7e-a239-851e4c53ec75"));
        assertThat(result).isNull();
    }
}