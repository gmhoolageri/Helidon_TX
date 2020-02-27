package com.servicemanager.database;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

@ApplicationScoped
public class PersistenceManager {

    @PersistenceUnit(unitName = "otmsm_persistence_unit")
    private EntityManagerFactory entityManagerFactory;

    public EntityManager getEntityManager() {
        EntityManager em = entityManagerFactory.createEntityManager();
        return em;
    }

}