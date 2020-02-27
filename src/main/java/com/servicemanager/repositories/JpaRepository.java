package com.servicemanager.repositories;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.servicemanager.util.Logger;
import com.servicemanager.util.Util;

public class JpaRepository<T, PK> {
    protected final EntityManager em;
    protected final Class<T> doClass;

    public JpaRepository(Class<T> t, EntityManager em) {
        this.em = em;
        this.doClass = t;
    }

    protected T persistEntity(T t, PK id) {

        if (id == null)
            em.persist(t);
        else
            t = em.merge(t);
        return t;
    }

    protected T findById(PK id) {
        Logger.trace("Going to find entity " + doClass.getSimpleName() + " for id:" + id.toString());
        return em.find(doClass, id);
    }

    protected void deleteById(PK id) {
        Logger.trace("Going to delete entity " + doClass.getSimpleName() + " for id:" + id.toString());
        Object obj = em.find(doClass, id);
        if (obj != null) {
            em.remove(obj);
        }
    }

    public T updateEntity(T object) {
        Logger.trace("Going to update entity " + doClass.getSimpleName());
        T obj = em.merge(object);
        return obj;
    }

    protected void deleteAll() {
        Logger.trace("Going to delete all entries for " + doClass.getSimpleName());
        CriteriaQuery<T> criteria = em.getCriteriaBuilder().createQuery(doClass);
        Root<T> root = criteria.from(doClass);
        criteria.select(root);
        List<T> objects = em.createQuery(criteria).getResultList();
        if (objects != null) {
            for (T obj : objects) {
                em.remove(obj);
            }
        }
    }

    protected List<T> findAll() {
        Logger.trace("Going to get all entries for " + doClass.getSimpleName());
        CriteriaQuery<T> criteria = em.getCriteriaBuilder().createQuery(doClass);
        Root<T> root = criteria.from(doClass);
        criteria.select(root);
        return em.createQuery(criteria).getResultList();
    }

    private TypedQuery<T> createNamedQuery(String queryName, Object... objects) {
        TypedQuery<T> typedQuery = em.createNamedQuery(queryName, doClass);
        if (objects != null) {
            for (int i = 0; i < objects.length; i++) {
                typedQuery.setParameter(i + 1, objects[i]);
            }
        }
        return typedQuery;
    }

    protected List<T> executeNamedQuery(String queryName, Object... objects) {
        Logger.trace("Going to execute query " + queryName + " on " + doClass.getSimpleName() + " with params :"
                + Arrays.asList(objects));
        TypedQuery<T> typedQuery = this.createNamedQuery(queryName, objects);
        return typedQuery.getResultList();
    }

    protected Optional<T> getSingleResult(List<T> list, Object... objects) {
        if (!Util.isCollectionEmpty(list)) {
            if (list.size() > 1) {
                throw new RuntimeException("Expected 1 " + doClass.getSimpleName() + " found " + list.size() + " "
                        + doClass.getSimpleName());
            }
            Logger.trace("Going to return " + doClass.getSimpleName() + " :" + list.get(0));
            return Optional.of(list.get(0));
        }
        Logger.trace("No " + doClass.getSimpleName() + " found for parameters:" + Arrays.asList(objects)
                + ". Hence returning null");
        return Optional.empty();
    }

    protected Optional<Set<T>> getSetFromList(List<T> list, Object... objects) {
        if (!Util.isCollectionEmpty(list)) {
            Logger.trace(list.size() + ": " + doClass.getSimpleName() + " fetched from the database");
            return Optional.of(new HashSet<T>(list));
        }
        Logger.trace("No " + doClass.getSimpleName() + " present for the parameters :" + Arrays.asList(objects)
                + ". Going to return null.");
        return Optional.empty();
    }

    protected Optional<List<T>> getListFromList(List<T> list, Object... objects) {
        if (!Util.isCollectionEmpty(list)) {
            Logger.trace(list.size() + ": " + doClass.getSimpleName() + " fetched from the database");
            return Optional.of(list);
        }
        Logger.trace("No " + doClass.getSimpleName() + " present for the parameters" + Arrays.asList(objects)
                + ". Going to return null.");
        return Optional.empty();
    }

}
