package com.epam.web.dao.account;

import com.epam.web.dao.Dao;
import com.epam.web.exceptions.DaoException;
import com.epam.web.model.entity.Account;

import java.util.Optional;

public interface AccountDao extends Dao<Account> {
    Optional<Account> findAccountByLoginPassword(String login, String password) throws DaoException;
    Optional<Account> findAccountByLogin(String login) throws DaoException;
    void block(Long id) throws DaoException;
    void unblock(Long id) throws DaoException;
}
