package com.epam.web.dao;

import com.epam.web.exceptions.DaoException;
import com.epam.web.model.Entity;

import java.util.List;
import java.util.Optional;

//CRUD
public interface Dao<T extends Entity> {
    Optional<T> findById(long id) throws DaoException;
    List<T> getAll() throws DaoException;
    void save(T item) throws DaoException;
    void removeById(long id) throws DaoException;
}
