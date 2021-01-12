package com.epam.web.dao.impl.account;

import com.epam.web.dao.Dao;
import com.epam.web.exception.DaoException;
import com.epam.web.model.entity.Account;

import java.util.Optional;

public interface AccountDao extends Dao<Account> {
    Optional<Account> findAccountByLoginPassword(String login, String password) throws DaoException;

    Optional<Account> findAccountByLogin(String login) throws DaoException;

    void block(long id) throws DaoException;

    void unblock(long id) throws DaoException;

    void addMoneyToBalance(int money, long id) throws DaoException;
}
