package com.example.printer_api.shared.database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public abstract class BaseRepository<T extends BaseEntity> {
    @PersistenceContext()
    protected EntityManager entityManager;

    public T create(T entity) {
        this.entityManager.persist(entity);
        return entity;
    }

    public T update(T entity) {
        return this.entityManager.merge(entity);
    }

    public void delete(T entity) {
        this.entityManager.remove(entity);
    }
}
