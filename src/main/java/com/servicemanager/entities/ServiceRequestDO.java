package com.servicemanager.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.servicemanager.constants.OperationType;
import com.servicemanager.constants.ServiceRequestProperties;
import com.servicemanager.constants.ServiceRequestState;
import com.servicemanager.constants.ServiceType;
import com.servicemanager.repositories.ServiceRequestRepository;
import com.servicemanager.util.Util;

@Entity
@Table(name = "SERVICE_REQUEST")
@NamedQueries({
        @NamedQuery(name = ServiceRequestRepository.findServiceRequestsByServiceRequestState_Name, query = ServiceRequestRepository.findServiceRequestsByServiceRequestState_Query),
        @NamedQuery(name = ServiceRequestRepository.findServiceRequestsByServiceRequestStateAndScheduledTime_Name, query = ServiceRequestRepository.findServiceRequestsByServiceRequestStateAndScheduledTime_Query),
        @NamedQuery(name = ServiceRequestRepository.findServiceRequestsByIdentityDomainNameServiceNameServiceTypeAndOperationType_Name, query = ServiceRequestRepository.findServiceRequestsByIdentityDomainNameServiceNameServiceTypeAndOperationType_Query),
        @NamedQuery(name = ServiceRequestRepository.findServiceRequestByCimInstanceId_Name, query = ServiceRequestRepository.findServiceRequestByCimInstanceId_Query),
        @NamedQuery(name = ServiceRequestRepository.findServiceRequestByServiceInstanceIdAndOpType_Name, query = ServiceRequestRepository.findServiceRequestByServiceInstanceIdAndOpType_Query) })
public class ServiceRequestDO extends BaseDO {

    private static final long serialVersionUID = 8337884129844298614L;

    @Id
    @Column(name = "ID", unique = true, nullable = false, precision = 30)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SERVICE_REQUEST_ID_GENERATOR")
    @SequenceGenerator(name = "SERVICE_REQUEST_ID_GENERATOR", sequenceName = "SERVICE_REQUEST_ID", allocationSize = 1)
    private BigDecimal id;

    @Column(name = "USER_REQUEST_NAME", nullable = false, length = 255)
    private String userRequestName;

    @Column(name = "CIM_REQUEST_ID", nullable = false, length = 255)
    private String cimRequestId;

    @Column(name = "CIM_INSTANCE_ID", nullable = false, length = 255)
    private String cimInstanceId;

    @Column(name = "SERVICE_INSTANCE_ID", nullable = false, length = 255)
    private String serviceInstanceId;

    @Column(name = "IDENTITY_DOMAIN_NAME", length = 255)
    private String identityDomainName;

    @Column(name = "SERVICE_NAME", length = 255)
    private String serviceName;

    @Column(name = "STATE", length = 255)
    @Enumerated(EnumType.STRING)
    private ServiceRequestState state;

    @Column(name = "ENTITLEMENT_REQUEST_ID", precision = 30)
    private String entitlementRequestId;

    @Column(name = "SERVICE_TYPE", length = 255)
    @Enumerated(EnumType.STRING)
    private ServiceType serviceType;

    @JsonbTransient
    @Column(name = "PAYLOAD", length = 4000)
    @Lob
    private String payload;

    @Column(name = "SCHEDULED_TIME")
    private Timestamp scheduledTime;

    @Column(name = "OPERATION_TYPE", length = 255)
    @Enumerated(EnumType.STRING)
    private OperationType operationType;

    @OneToMany(targetEntity = TaskDO.class, mappedBy = "serviceRequest", fetch = FetchType.EAGER, cascade = {
            CascadeType.ALL })
    private Set<TaskDO> task;

    @OneToMany(mappedBy = "serviceRequest", fetch = FetchType.EAGER, cascade = {
            CascadeType.ALL }, orphanRemoval = true, targetEntity = ServiceRequestPropertiesDO.class)
    private Set<ServiceRequestPropertiesDO> serviceRequestProperties;

    /** Default constructor. */
    public ServiceRequestDO() {
        super();
        serviceRequestProperties = new HashSet<>();
    }

    /**
     * Access method for id.
     *
     * @return the current value of id
     */
    public BigDecimal getId() {
        return id;
    }

    /**
     * Setter method for id.
     *
     * @param aId the new value for id
     */
    public void setId(BigDecimal aId) {
        id = aId;
    }

    /**
     * Access method for cimRequestId.
     *
     * @return the current value of cimRequestId
     */
    public String getCimRequestId() {
        return cimRequestId;
    }

    /**
     * Setter method for userRequestName.
     *
     * @param aServiceRequestId the new value for userRequestName
     */
    public void setCimRequestId(String aCimRequestId) {
        cimRequestId = aCimRequestId;
    }

    /**
     * Access method for cimInstanceId.
     *
     * @return the current value of cimInstanceId
     */
    public String getCimInstanceId() {
        return cimInstanceId;
    }

    /**
     * Setter method for cimInstanceId.
     *
     * @param aServiceRequestId the new value for cimInstanceId
     */
    public void setCimInstanceId(String acimInstanceId) {
        cimInstanceId = acimInstanceId;
    }

    /**
     * Access method for userRequestName.
     *
     * @return the current value of userRequestName
     */
    public String getUserRequestName() {
        return userRequestName;
    }

    /**
     * Setter method for userRequestName.
     *
     * @param aServiceRequestId the new value for userRequestName
     */
    public void setUserRequestName(String aUserRequestName) {
        userRequestName = aUserRequestName;
    }

    /**
     * Access method for serviceInstanceId.
     *
     * @return the current value of serviceInstanceId
     */
    public String getServiceInstanceId() {
        return serviceInstanceId;
    }

    /**
     * Setter method for serviceInstanceId.
     *
     * @param aServiceInstanceId the new value for serviceInstanceId
     */
    public void setServiceInstanceId(String aServiceInstanceId) {
        serviceInstanceId = aServiceInstanceId;
    }

    /**
     * Access method for identityDomainName.
     *
     * @return the current value of identityDomainName
     */
    public String getIdentityDomainName() {
        return identityDomainName;
    }

    /**
     * Setter method for identityDomainName.
     *
     * @param aIdentityDomainName the new value for identityDomainName
     */
    public void setIdentityDomainName(String aIdentityDomainName) {
        identityDomainName = aIdentityDomainName;
    }

    /**
     * Access method for serviceName.
     *
     * @return the current value of serviceName
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * Setter method for serviceName.
     *
     * @param aServiceName the new value for serviceName
     */
    public void setServiceName(String aServiceName) {
        serviceName = aServiceName;
    }

    /**
     * Access method for state.
     *
     * @return the current value of state
     */
    public ServiceRequestState getState() {
        return state;
    }

    /**
     * Setter method for state.
     *
     * @param aState the new value for state
     */
    public void setState(ServiceRequestState aState) {
        state = aState;
    }

    /**
     * Access method for entitlementRequestId.
     *
     * @return the current value of entitlementRequestId
     */
    public String getEntitlementRequestId() {
        return entitlementRequestId;
    }

    /**
     * Setter method for entitlementRequestId.
     *
     * @param aEntitlementRequestId the new value for entitlementRequestId
     */
    public void setEntitlementRequestId(String aEntitlementRequestId) {
        entitlementRequestId = aEntitlementRequestId;
    }

    /**
     * Access method for serviceType.
     *
     * @return the current value of serviceType
     */
    public ServiceType getServiceType() {
        return serviceType;
    }

    /**
     * Setter method for serviceType.
     *
     * @param aServiceType the new value for serviceType
     */
    public void setServiceType(ServiceType aServiceType) {
        serviceType = aServiceType;
    }

    /**
     * Access method for operationType.
     *
     * @return the current value of operationType
     */
    public OperationType getOperationType() {
        return operationType;
    }

    /**
     * Setter method for operationType.
     *
     * @param aOperationType the new value for operationType
     */
    public void setOperationType(OperationType aOperationType) {
        this.operationType = aOperationType;
    }

    /**
     * Access method for task.
     *
     * @return the current value of task
     */
    public Set<TaskDO> getTask() {
        return task;
    }

    /**
     * Setter method for task.
     *
     * @param aTask the new value for task
     */
    public void setTask(Set<TaskDO> aTask) {
        task = aTask;
    }

    /**
     * Access method for payload.
     *
     * @return the current value of payload
     */
    public String getPayload() {
        return payload;
    }

    /**
     * Setter method for payload.
     *
     * @param aPayload the new value for payload
     */
    public void setPayload(String aPayload) {
        payload = aPayload;
    }

    /**
     * Access method for serviceRequestProperties.
     *
     * @return the current value of serviceRequestProperties
     */
    public Set<ServiceRequestPropertiesDO> getServiceRequestProperties() {
        return serviceRequestProperties;
    }

    /**
     * Setter method for serviceRequestProperties.
     *
     * @param aServiceRequestProperties the new value for serviceRequestProperties
     */
    public void setServiceRequestProperties(Set<ServiceRequestPropertiesDO> aServiceRequestProperties) {
        this.serviceRequestProperties = aServiceRequestProperties;
    }

    public void addServiceRequestProperty(ServiceRequestPropertiesDO serviceRequestProperty) {
        this.serviceRequestProperties.add(serviceRequestProperty);
    }

    public void removeAllServiceRequestProperties() {
        this.serviceRequestProperties.removeAll(serviceRequestProperties);
    }

    public void removeServiceRequestProperty(ServiceRequestPropertiesDO serviceRequestProperty) {
        this.serviceRequestProperties.remove(serviceRequestProperty);
    }

    @Override
    public String toString() {
        return "ServiceRequestDO [id=" + id + ", userRequestName=" + userRequestName + ", cimRequestId=" + cimRequestId
                + ", cimInstanceId=" + cimInstanceId + ", serviceInstanceId=" + serviceInstanceId
                + ", identityDomainName=" + identityDomainName + ", serviceName=" + serviceName + ", state=" + state
                + ", entitlementRequestId=" + entitlementRequestId + ", serviceType=" + serviceType + ", scheduledTime="
                + scheduledTime + ", operationType=" + operationType + "]";
    }

    public Timestamp getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(Timestamp scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public void addReplaceServiceRequestProperty(ServiceRequestPropertiesDO serviceRequestPropertiesDO) {
        if (Util.isCollectionEmpty(this.getServiceRequestProperties()))
            addServiceRequestProperty(serviceRequestPropertiesDO);
        else {
            List<ServiceRequestPropertiesDO> serviceRequestPropertyList = new ArrayList<ServiceRequestPropertiesDO>(
                    this.getServiceRequestProperties());
            ServiceRequestPropertiesDO srpDO = getExistingServiceRequestPropertiesDO(
                    serviceRequestPropertiesDO.getKey(), serviceRequestPropertyList);
            if (srpDO == null)
                addServiceRequestProperty(serviceRequestPropertiesDO);
            else {
                removeServiceRequestProperty(srpDO);
                addServiceRequestProperty(serviceRequestPropertiesDO);
            }
        }

    }

    private ServiceRequestPropertiesDO getExistingServiceRequestPropertiesDO(ServiceRequestProperties key,
            List<ServiceRequestPropertiesDO> serviceRequestProperties) {
        for (ServiceRequestPropertiesDO srpDO : serviceRequestProperties) {
            if (key.equals(srpDO.getKey()))
                return srpDO;
        }
        return null;
    }
}
