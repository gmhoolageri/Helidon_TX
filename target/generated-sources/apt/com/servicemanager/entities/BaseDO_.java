package com.servicemanager.entities;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(BaseDO.class)
public abstract class BaseDO_ {

	public static volatile SingularAttribute<BaseDO, Timestamp> modified;
	public static volatile SingularAttribute<BaseDO, Integer> version;
	public static volatile SingularAttribute<BaseDO, Timestamp> creation;

}

