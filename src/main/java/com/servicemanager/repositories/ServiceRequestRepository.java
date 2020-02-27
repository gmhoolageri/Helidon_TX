package com.servicemanager.repositories;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import com.servicemanager.constants.OperationType;
import com.servicemanager.constants.ServiceRequestState;
import com.servicemanager.constants.ServiceType;
import com.servicemanager.entities.ServiceRequestDO;

public class ServiceRequestRepository extends JpaRepository<ServiceRequestDO, BigDecimal> {

    public ServiceRequestRepository(EntityManager em) {
        super(ServiceRequestDO.class, em);
    }

    public static final String findServiceRequestByServiceInstanceIdAndOpType_Query = "SELECT r FROM com.servicemanager.entities.ServiceRequestDO r WHERE r.serviceInstanceId = ?1 and r.operationType = ?2 order by r.creation desc";
    public static final String findServiceRequestByServiceInstanceIdAndOpType_Name = "findServiceRequestByServiceInstanceIdAndOpType";

    public Optional<ServiceRequestDO> findServiceRequestByServiceInstanceIdAndOpType(String serviceInstanceId,
            OperationType operationType) {
        List<ServiceRequestDO> serviceRequestDOList = executeNamedQuery(
                findServiceRequestByServiceInstanceIdAndOpType_Name, serviceInstanceId, operationType);
        return getSingleResult(serviceRequestDOList, serviceInstanceId, operationType);
    }

    public static final String findServiceRequestsByServiceRequestState_Query = "SELECT r FROM com.servicemanager.entities.ServiceRequestDO r WHERE r.state = ?1 order by r.creation desc";
    public static final String findServiceRequestsByServiceRequestState_Name = "findServiceRequestsByServiceRequestState";

    public Optional<List<ServiceRequestDO>> findServiceRequestsByServiceRequestState(ServiceRequestState state) {
        List<ServiceRequestDO> serviceRequestDOList = executeNamedQuery(findServiceRequestsByServiceRequestState_Name,
                state);

        Predicate<ServiceRequestDO> matchingServiceRequests = serviceRequest -> serviceRequest.getState() == state;
        // serviceRequestDOList.stream().filter(matchingServiceRequests).collect(Collectors.toList());

        return getListFromList(
                serviceRequestDOList.stream().filter(matchingServiceRequests).collect(Collectors.toList()), state);
    }

    public Optional<ServiceRequestDO> findServiceRequestsById(BigDecimal bigDecimal) {
        return Optional.of(findById(bigDecimal));
    }

    public static final String findServiceRequestByCimInstanceId_Query = "SELECT r FROM com.servicemanager.entities.ServiceRequestDO r WHERE r.cimInstanceId = ?1";
    public static final String findServiceRequestByCimInstanceId_Name = "findServiceRequestByCimInstanceId";

    public Optional<ServiceRequestDO> findServiceRequestByCimInstanceId(String cimInstanceId) {
        List<ServiceRequestDO> serviceRequestDOList = executeNamedQuery(findServiceRequestByCimInstanceId_Name,
                cimInstanceId);
        return getSingleResult(serviceRequestDOList, cimInstanceId);
    }

    public static final String findServiceRequestsByIdentityDomainNameServiceNameServiceTypeAndOperationType_Query = "SELECT r FROM com.servicemanager.entities.ServiceRequestDO r WHERE r.identityDomainName = ?1 and r.serviceName = ?2 and r.serviceType = ?3 and r.operationType = ?4";
    public static final String findServiceRequestsByIdentityDomainNameServiceNameServiceTypeAndOperationType_Name = "findServiceRequestsByIdentityDomainNameServiceNameServiceTypeAndOperationType";

    public Optional<List<ServiceRequestDO>> findServiceRequestsByIdentityDomainNameServiceNameServiceTypeAndOperationType(
            String identityDomainName, String serviceName, ServiceType serviceType, OperationType create) {
        List<ServiceRequestDO> serviceRequestDOList = executeNamedQuery(
                findServiceRequestsByIdentityDomainNameServiceNameServiceTypeAndOperationType_Name, identityDomainName,
                serviceName, serviceType, create);
        return getListFromList(serviceRequestDOList, identityDomainName, serviceName, serviceType, create);
    }

    public Optional<ServiceRequestDO> findServiceRequestById(BigDecimal serviceRequestId) {
        return Optional.of(findById(serviceRequestId));
    }

    public ServiceRequestDO saveServiceRequest(ServiceRequestDO serviceRequestDO) {
        return persistEntity(serviceRequestDO, serviceRequestDO.getId());
    }

    public Optional<List<ServiceRequestDO>> findAllServiceRequests() {
        return Optional.of(findAll());
    }

    public static final String findServiceRequestsByServiceRequestStateAndScheduledTime_Query = "SELECT r FROM com.servicemanager.entities.ServiceRequestDO r WHERE r.state = ?1 and r.scheduledTime <= ?2 order by r.creation desc";
    public static final String findServiceRequestsByServiceRequestStateAndScheduledTime_Name = "findServiceRequestsByServiceRequestStateAndScheduledTime";

    public Optional<List<ServiceRequestDO>> findServiceRequestsByServiceRequestStateAndScheduledTime(
            ServiceRequestState scheduled, Timestamp timestamp) {
        List<ServiceRequestDO> serviceRequestDOList = executeNamedQuery(
                findServiceRequestsByServiceRequestStateAndScheduledTime_Name, scheduled, timestamp);
        return getListFromList(serviceRequestDOList, scheduled, timestamp);
    }

}
