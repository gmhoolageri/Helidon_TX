package com.servicemanager.entities;

import java.math.BigDecimal;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.servicemanager.constants.ServiceRequestProperties;
import com.servicemanager.repositories.ServiceRequestPropertiesRepository;

@Entity
@Table(name = "SERVICE_REQUEST_PROPERTIES")
@NamedQueries({
        @NamedQuery(name = ServiceRequestPropertiesRepository.findServiceRequestPropertiesByKeyAndValue_Name, query = ServiceRequestPropertiesRepository.findServiceRequestPropertiesByKeyAndValue_Query) })

public class ServiceRequestPropertiesDO extends BaseDO {

    private static final long serialVersionUID = 2701329622445041610L;

    @Id
    @Column(name = "ID", unique = true, nullable = false, precision = 30)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SERVICE_REQUEST_PROPERTIES_ID_GENERATOR")
    @SequenceGenerator(name = "SERVICE_REQUEST_PROPERTIES_ID_GENERATOR", sequenceName = "SERVICE_REQUEST_PROPERTIES_ID", allocationSize = 1)
    private BigDecimal id;

    @Column(name = "KEY", length = 255)
    @Enumerated(EnumType.STRING)
    private ServiceRequestProperties key;

    @Column(name = "VALUE", length = 255)
    private String value;

    @JsonbTransient
    @ManyToOne
    @JoinColumn(name = "SERVICE_REQUEST_ID_FK", nullable = false)
    private ServiceRequestDO serviceRequest;

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
     * Access method for key.
     *
     * @return the current value of key
     */
    public ServiceRequestProperties getKey() {
        return key;
    }

    /**
     * Setter method for key.
     *
     * @param aKey the new value for key
     */
    public void setKey(ServiceRequestProperties aKey) {
        key = aKey;
    }

    /**
     * Access method for value.
     *
     * @return the current value of value
     */
    public String getValue() {
        return value;
    }

    /**
     * Setter method for value.
     *
     * @param aValue the new value for value
     */
    public void setValue(String aValue) {
        value = aValue;
    }

    /**
     * Access method for serviceRequest.
     *
     * @return the current value of serviceRequest
     */
    public ServiceRequestDO getServiceRequest() {
        return serviceRequest;
    }

    /**
     * Setter method for serviceRequest.
     *
     * @param aServiceRequest the new value for serviceRequest
     */
    public void setServiceRequest(ServiceRequestDO aServiceRequest) {
        this.serviceRequest = aServiceRequest;
    }

    @Override
    public String toString() {
        return "ServiceRequestPropertiesDO [id=" + id + ", key=" + key + ", value=" + value + ", servicerequest_id="
                + serviceRequest.getId() + "]";
    }
}
