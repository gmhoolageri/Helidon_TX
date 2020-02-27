package com.servicemanager.repositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import com.servicemanager.constants.ServiceRequestProperties;
import com.servicemanager.entities.ServiceRequestPropertiesDO;

public class ServiceRequestPropertiesRepository extends JpaRepository<ServiceRequestPropertiesDO, BigDecimal> {

    public ServiceRequestPropertiesRepository(EntityManager em) {
        super(ServiceRequestPropertiesDO.class, em);
    }

    public Optional<ServiceRequestPropertiesDO> findServiceRequestPropertiesById(BigDecimal serviceRequestPropertyId) {
        return Optional.of(findById(serviceRequestPropertyId));
    }

    public void deleteServiceRequestPropertiesById(BigDecimal serviceRequestPropertyId) {
        deleteById(serviceRequestPropertyId);
    }

    public static final String findServiceRequestPropertiesByKeyAndValue_Query = "SELECT r FROM com.servicemanager.entities.ServiceRequestPropertiesDO r WHERE r.key = ?1 and r.value = ?2";
    public static final String findServiceRequestPropertiesByKeyAndValue_Name = "findServiceRequestPropertiesByKeyAndValue";

    public Optional<List<ServiceRequestPropertiesDO>> findServiceRequestPropertiesByKeyAndValue(
            ServiceRequestProperties key, String value) {
        List<ServiceRequestPropertiesDO> doList = executeNamedQuery(findServiceRequestPropertiesByKeyAndValue_Name, key,
                value);
        return getListFromList(doList, key, value);
    }

}
