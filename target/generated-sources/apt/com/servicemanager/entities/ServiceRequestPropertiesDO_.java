package com.servicemanager.entities;

import com.servicemanager.constants.ServiceRequestProperties;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ServiceRequestPropertiesDO.class)
public abstract class ServiceRequestPropertiesDO_ extends com.servicemanager.entities.BaseDO_ {

	public static volatile SingularAttribute<ServiceRequestPropertiesDO, ServiceRequestDO> serviceRequest;
	public static volatile SingularAttribute<ServiceRequestPropertiesDO, BigDecimal> id;
	public static volatile SingularAttribute<ServiceRequestPropertiesDO, String> value;
	public static volatile SingularAttribute<ServiceRequestPropertiesDO, ServiceRequestProperties> key;

}

