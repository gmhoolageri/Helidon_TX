package com.servicemanager.database;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import com.servicemanager.constants.OperationType;
import com.servicemanager.constants.ServiceRequestProperties;
import com.servicemanager.constants.ServiceRequestState;
import com.servicemanager.constants.ServiceType;
import com.servicemanager.constants.TaskState;
import com.servicemanager.entities.ServiceRequestDO;
import com.servicemanager.entities.ServiceRequestPropertiesDO;
import com.servicemanager.entities.TaskDO;

public interface DatabaseFacade {

    Optional<ServiceRequestDO> findServiceRequestByServiceInstanceIdAndOpType(String serviceInstanceId,
            OperationType opType);

    Optional<ServiceRequestDO> findServiceRequestById(BigDecimal id);

    Optional<ServiceRequestDO> findServiceRequestByCimInstanceId(String cimInstanceId);

    ServiceRequestDO saveServiceRequest(ServiceRequestDO serviceRequestDO);

    Optional<List<ServiceRequestDO>> findServiceRequestsByIdentityDomainNameServiceNameServiceTypeAndOperationType(
            String identityDomainName, String serviceName, ServiceType serviceType, OperationType create);

    Optional<ServiceRequestPropertiesDO> findServiceRequestPropertiesById(BigDecimal serviceRequestPropertyId);

    void deleteServiceRequestPropertiesById(BigDecimal serviceRequestPropertyId);

    Optional<List<ServiceRequestDO>> findAllServiceRequests();

    Optional<List<ServiceRequestDO>> findServiceRequestsByServiceRequestState(ServiceRequestState cancelState);

    Optional<List<ServiceRequestDO>> findServiceRequestsByServiceRequestStateAndScheduledTime(
            ServiceRequestState scheduled, Timestamp timestamp);

    Optional<List<TaskDO>> findTasksByTaskStateAndServiceRequestId(TaskState state, BigDecimal serviceRequestId);

    TaskDO saveTask(TaskDO taskDO);

    Optional<List<ServiceRequestPropertiesDO>> findServiceRequestPropertiesByKeyAndValue(ServiceRequestProperties key,
            String value);

}
