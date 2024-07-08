package com.ig.test.model.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;

import javax.transaction.Transactional;
import java.util.List;

public abstract class AbstractDAO_JPA<P> implements DataAccesObject<P> {

    protected Class<P> type;

    @PersistenceContext
    protected EntityManager entityManager;

    protected AbstractDAO_JPA(Class<P> type) {
        this.type = type;
    }

    @Override
    public P create(P persistentObj) {
        entityManager.persist(persistentObj);
        return persistentObj;
    }

    @Override
    public P read(Object id) {
        return entityManager.find(type, id);
    }

    @Override
    @Transactional
    public void update(P persistentObj) {
        entityManager.merge(persistentObj);
    }

    @Override
    @Transactional
    public void delete(P persistentObj) {
        entityManager.remove(entityManager.contains(persistentObj) ? persistentObj : entityManager.merge(persistentObj));
    }

    @Override
    @Transactional
    public List<P> listAll() {
        return entityManager.createQuery("from " + type.getName(), type).getResultList();
    }

    @Override
    public List<P> listAllID(long id) {
        Query query = entityManager.createQuery("from " + type.getName() + " where cliente.id = :id");
        query.setParameter("id", id);
        return query.getResultList();
    }
}
