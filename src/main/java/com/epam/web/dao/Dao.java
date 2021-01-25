package com.epam.web.dao;

import com.epam.web.exception.DaoException;
import com.epam.web.model.Entity;

import java.util.List;
import java.util.Optional;

/**
 * Interface as a template for accessing to entity data in database.
 * Contains CRUD methods.
 *
 * @param  <T>  type of handling entities.
 */
public interface Dao<T extends Entity> {
    /**
     * Finds specific object by id and returns container of object if exist
     * or empty container if not.
     *
     * @param  id  an id value of object to search.
     *
     * @return  an optional container of <T> object.
     *
     * @throws  DaoException  if database errors occurs.
     */
    Optional<T> findById(long id) throws DaoException;

    /**
     * Gets list of all available objects.
     *
     * @return  a list of all available <T> objects in database.
     *
     * @throws  DaoException  if database errors occurs.
     */
    List<T> getAll() throws DaoException;

    /**
     * Saves specific object to database or updates if object id is exist in database.
     *
     * @param  item  <T> object to save or update.
     *
     * @throws  DaoException  if database errors occurs.
     */
    void save(T item) throws DaoException;

    /**
     * Removes specific object in database by id.
     *
     * @param  id  an id value of object to remove.
     *
     * @throws  DaoException  if database errors occurs.
     */
    void removeById(long id) throws DaoException;

    /**
     * Gets rows amount in specific table.
     *
     * @param  additionalCondition  an optional condition to query presented in string.
     *
     * @return  a rows amount in the table.
     *
     * @throws  DaoException  if database errors occurs.
     */
    int getRowsAmount(Optional<String> additionalCondition) throws DaoException;
}
