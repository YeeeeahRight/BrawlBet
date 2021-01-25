package com.epam.web.dao.impl.account;

import com.epam.web.dao.Dao;
import com.epam.web.exception.DaoException;
import com.epam.web.model.entity.Account;

import java.util.List;
import java.util.Optional;

/**
 * Interface with description of operations with the Account data.
 */
public interface AccountDao extends Dao<Account> {
    /**
     * Gets list of users in range described as offset and amount of users.
     *
     * @param  offset  an offset from first row of users table.
     * @param  amount  an amount of users to get.
     *
     * @return  a received list of accounts(users).
     *
     * @throws  DaoException  if database errors occurs.
     */
    List<Account> getUsersRange(int offset, int amount) throws DaoException;

    /**
     * Gets users amount in database.
     *
     * @return  an amount of all users.
     *
     * @throws  DaoException  if database errors occurs.
     */
    int getUsersAmount() throws DaoException;

    /**
     * Finds bookmaker in database and returns container of bookmaker
     * or empty container if not found.
     *
     * @return  an optional container of account.
     *
     * @throws  DaoException  if database errors occurs.
     */
    Optional<Account> findBookmaker() throws DaoException;

    /**
     * Finds account in database by login and password and returns container of account
     * or empty container if not found.
     *
     * @param  login     a login(username) of account.
     * @param  password  a password of account.
     *
     * @return  an optional container of account.
     *
     * @throws  DaoException  if database errors occurs.
     */
    Optional<Account> findAccountByLoginPassword(String login, String password) throws DaoException;

    /**
     * Finds account in database by login and returns container of account
     * or empty container if not found.
     *
     * @param  login  a login(username) of account.
     *
     * @return  an optional container of account.
     *
     * @throws  DaoException  if database errors occurs.
     */
    Optional<Account> findAccountByLogin(String login) throws DaoException;

    /**
     * Blocks account by id.
     *
     * @param  id  an id value of account to block.
     *
     * @throws  DaoException  if database errors occurs.
     */
    void blockById(long id) throws DaoException;

    /**
     * Unblock account by id.
     *
     * @param  id  an id value of account to unblock.
     *
     * @throws  DaoException  if database errors occurs.
     */
    void unblockById(long id) throws DaoException;

    /**
     * Adds money value to balance of account by account id.
     *
     * @param  money  a money value to add.
     * @param  id     an id value of account.
     *
     * @throws  DaoException  if database errors occurs.
     */
    void addMoneyById(float money, long id) throws DaoException;
}
