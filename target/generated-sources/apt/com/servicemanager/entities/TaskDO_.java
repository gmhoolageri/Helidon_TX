package com.servicemanager.entities;

import com.servicemanager.constants.TaskState;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TaskDO.class)
public abstract class TaskDO_ extends com.servicemanager.entities.BaseDO_ {

	public static volatile SingularAttribute<TaskDO, String> invocationURI;
	public static volatile SingularAttribute<TaskDO, ServiceRequestDO> serviceRequest;
	public static volatile SingularAttribute<TaskDO, BigDecimal> executionSequence;
	public static volatile SingularAttribute<TaskDO, BigDecimal> id;
	public static volatile SingularAttribute<TaskDO, TaskState> state;
	public static volatile SingularAttribute<TaskDO, String> invocationClass;

}

