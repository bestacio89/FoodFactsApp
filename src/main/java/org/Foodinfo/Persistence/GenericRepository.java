package org.Foodinfo.Persistence;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class GenericRepository<T, ID extends Serializable> {

    @PersistenceContext
    protected EntityManager entityManager;

    protected Class<T> entityClass;

    @SuppressWarnings("unchecked")
    public GenericRepository() {
        this.entityClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Transactional
    public void save(T entity) {
        entityManager.persist(entity);
    }

    @Transactional
    public T findById(ID id) {
        return entityManager.find(entityClass, id);
    }

    @Transactional
    public List<T> findAll() {
        return entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass)
                            .getResultList();
    }

    @Transactional
    public T update(T entity) {
       return entityManager.merge(entity);
    }

    @Transactional
    public void delete(T entity) {
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }

    // Add more utility methods as needed
}
