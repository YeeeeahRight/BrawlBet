package com.epam.web.dao.user;

import com.epam.web.dao.Dao;
import com.epam.web.exceptions.DaoException;
import com.epam.web.entity.User;

import java.util.Optional;

public interface UserDao extends Dao<User> {
    Optional<User> findUserByLoginPassword(String login, String password) throws DaoException;
    Optional<User> findUserByLogin(String login) throws DaoException;
    void block(Long id) throws DaoException;
    void unblock(Long id) throws DaoException;
}
