package com.servicemanager.entities;

import com.servicemanager.constants.OperationType;
import com.servicemanager.constants.ServiceRequestState;
import com.servicemanager.constants.ServiceType;
import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ServiceRequestDO.class)
public abstract class ServiceRequestDO_ extends com.servicemanager.entities.BaseDO_ {

	public static volatile SingularAttribute<ServiceRequestDO, ServiceType> serviceType;
	public static volatile SingularAttribute<ServiceRequestDO, Timestamp> scheduledTime;
	public static volatile SingularAttribute<ServiceRequestDO, String> userRequestName;
	public static volatile SingularAttribute<ServiceRequestDO, String> entitlementRequestId;
	public static volatile SingularAttribute<ServiceRequestDO, String> cimInstanceId;
	public static volatile SingularAttribute<ServiceRequestDO, String> serviceInstanceId;
	public static volatile SingularAttribute<ServiceRequestDO, String> serviceName;
	public static volatile SingularAttribute<ServiceRequestDO, String> identityDomainName;
	public static volatile SetAttribute<ServiceRequestDO, TaskDO> task;
	public static volatile SingularAttribute<ServiceRequestDO, String> payload;
	public static volatile SingularAttribute<ServiceRequestDO, String> cimRequestId;
	public static volatile SingularAttribute<ServiceRequestDO, OperationType> operationType;
	public static volatile SingularAttribute<ServiceRequestDO, BigDecimal> id;
	public static volatile SingularAttribute<ServiceRequestDO, ServiceRequestState> state;
	public static volatile SetAttribute<ServiceRequestDO, ServiceRequestPropertiesDO> serviceRequestProperties;

}

