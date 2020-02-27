package com.servicemanager.database;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import com.servicemanager.constants.OperationType;
import com.servicemanager.constants.ServiceRequestProperties;
import com.servicemanager.constants.ServiceRequestState;
import com.servicemanager.constants.ServiceType;
import com.servicemanager.constants.TaskState;
import com.servicemanager.entities.ServiceRequestDO;
import com.servicemanager.entities.ServiceRequestPropertiesDO;
import com.servicemanager.entities.TaskDO;
import com.servicemanager.repositories.ServiceRequestPropertiesRepository;
import com.servicemanager.repositories.ServiceRequestRepository;
import com.servicemanager.repositories.TaskRepository;

@Dependent
public class DatabaseFacadeImpl implements DatabaseFacade {

    @Inject
    private PersistenceManager persistenceManager;

    @Override
    public Optional<ServiceRequestDO> findServiceRequestByServiceInstanceIdAndOpType(String serviceInstanceId,
            OperationType opType) {
        EntityManager em = persistenceManager.getEntityManager();
        ServiceRequestRepository serviceRequestRepository = new ServiceRequestRepository(em);
        return serviceRequestRepository.findServiceRequestByServiceInstanceIdAndOpType(serviceInstanceId, opType);
    }

    @Override
    public Optional<ServiceRequestDO> findServiceRequestById(BigDecimal id) {
        EntityManager em = persistenceManager.getEntityManager();

        ServiceRequestRepository serviceRequestRepository = new ServiceRequestRepository(em);
        return serviceRequestRepository.findServiceRequestsById(id);
    }

    @Override
    public Optional<ServiceRequestDO> findServiceRequestByCimInstanceId(String cimInstanceId) {
        EntityManager em = persistenceManager.getEntityManager();
        ServiceRequestRepository serviceRequestRepository = new ServiceRequestRepository(em);
        return serviceRequestRepository.findServiceRequestByCimInstanceId(cimInstanceId);

    }

    @Override
    @Transactional(value = TxType.REQUIRES_NEW)
    public ServiceRequestDO saveServiceRequest(ServiceRequestDO serviceRequestDO) {
        EntityManager em = persistenceManager.getEntityManager();
        ServiceRequestRepository serviceRequestRepository = new ServiceRequestRepository(em);
        return serviceRequestRepository.saveServiceRequest(serviceRequestDO);

    }

    @Override
    public Optional<List<ServiceRequestDO>> findServiceRequestsByIdentityDomainNameServiceNameServiceTypeAndOperationType(
            String identityDomainName, String serviceName, ServiceType serviceType, OperationType create) {
        EntityManager em = persistenceManager.getEntityManager();
        ServiceRequestRepository serviceRequestRepository = new ServiceRequestRepository(em);
        return serviceRequestRepository.findServiceRequestsByIdentityDomainNameServiceNameServiceTypeAndOperationType(
                identityDomainName, serviceName, serviceType, create);
    }

    @Override
    public Optional<ServiceRequestPropertiesDO> findServiceRequestPropertiesById(BigDecimal serviceRequestPropertyId) {
        EntityManager em = persistenceManager.getEntityManager();
        ServiceRequestPropertiesRepository serviceRequestPropertiesRepository = new ServiceRequestPropertiesRepository(
                em);
        return serviceRequestPropertiesRepository.findServiceRequestPropertiesById(serviceRequestPropertyId);

    }

    @Override
    @Transactional(value = TxType.REQUIRES_NEW)
    public void deleteServiceRequestPropertiesById(BigDecimal serviceRequestPropertyId) {
        EntityManager em = persistenceManager.getEntityManager();
        ServiceRequestPropertiesRepository serviceRequestPropertiesRepository = new ServiceRequestPropertiesRepository(
                em);
        serviceRequestPropertiesRepository.deleteServiceRequestPropertiesById(serviceRequestPropertyId);

    }

    @Override
    public Optional<List<ServiceRequestDO>> findAllServiceRequests() {
        EntityManager em = persistenceManager.getEntityManager();
        ServiceRequestRepository serviceRequestRepository = new ServiceRequestRepository(em);
        return serviceRequestRepository.findAllServiceRequests();
    }

    @Override
    public Optional<List<ServiceRequestDO>> findServiceRequestsByServiceRequestState(ServiceRequestState cancelState) {
        EntityManager em = persistenceManager.getEntityManager();

        ServiceRequestRepository serviceRequestRepository = new ServiceRequestRepository(em);
        return serviceRequestRepository.findServiceRequestsByServiceRequestState(cancelState);

    }

    @Override
    public Optional<List<ServiceRequestDO>> findServiceRequestsByServiceRequestStateAndScheduledTime(
            ServiceRequestState scheduled, Timestamp timestamp) {
        EntityManager em = persistenceManager.getEntityManager();

        ServiceRequestRepository serviceRequestRepository = new ServiceRequestRepository(em);
        return serviceRequestRepository.findServiceRequestsByServiceRequestStateAndScheduledTime(scheduled, timestamp);

    }

    @Override
    public Optional<List<TaskDO>> findTasksByTaskStateAndServiceRequestId(TaskState state,
            BigDecimal serviceRequestId) {
        EntityManager em = persistenceManager.getEntityManager();

        TaskRepository taskRepository = new TaskRepository(em);
        return taskRepository.findByTaskStateAndServiceRequestId(state, serviceRequestId);
    }

    @Override
    @Transactional(value = TxType.REQUIRES_NEW)
    public TaskDO saveTask(TaskDO taskDO) {
        EntityManager em = persistenceManager.getEntityManager();
        TaskRepository taskRepository = new TaskRepository(em);
        return taskRepository.saveTask(taskDO);
    }

    @Override
    public Optional<List<ServiceRequestPropertiesDO>> findServiceRequestPropertiesByKeyAndValue(
            ServiceRequestProperties key, String value) {
        EntityManager em = persistenceManager.getEntityManager();
        ServiceRequestPropertiesRepository servReqPropRepository = new ServiceRequestPropertiesRepository(em);
        return servReqPropRepository.findServiceRequestPropertiesByKeyAndValue(key, value);
    }

}