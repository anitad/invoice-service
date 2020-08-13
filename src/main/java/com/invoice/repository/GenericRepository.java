package com.invoice.repository;

import java.io.Serializable;
import java.util.List;

/**
 * Generic repository with set of apis for crud operations
 */

public interface GenericRepository<T extends Serializable> {

    /**
     * Returns list of entities of
     * type T
     * @return list of entities of type T
     */
    List<T> getAll();

    /**
     * Saves an entity of type T
     * @param entity
     * @return
     */
    T save(T entity);

    /**
     * Saves a list of entities of
     * type T
     * @param entities
     * @return list of saved entities
     */
    List<T> save(List<T> entities);

    /**
     * Finds an entity and returns it.
     * @param id
     * @return Entity
     */
    T findById(long id);

    /**
     * get all entities
     * @param offset
     * @param limit
     * @return
     */
    List<T> getAll(int offset, int limit);
}

