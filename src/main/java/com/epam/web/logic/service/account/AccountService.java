package com.epam.web.logic.service.account;

import com.epam.web.exception.ServiceException;
import com.epam.web.model.entity.Account;

import java.util.List;
import java.util.Optional;

/**
 * Interface with description of operations with the Account.
 */
public interface AccountService {
    /**
     * Gets list of users in range described as offset and amount of users.
     *
     * @param  offset  an offset from first row of users table.
     * @param  amount  an amount of users to get.
     *
     * @return  a received list of accounts(users).
     *
     * @throws  ServiceException  a wrapper for lower errors.
     */
    List<Account> getUsersRange(int offset, int amount) throws ServiceException;

    /**
     * Gets users amount.
     *
     * @return  an amount of all users.
     *
     * @throws  ServiceException  a wrapper for lower errors.
     */
    int getUsersAmount() throws ServiceException;

    /**
     * Finds bookmaker in database and returns container of bookmaker
     * or empty container if not found.
     *
     * @return  an optional container of account.
     *
     * @throws  ServiceException  a wrapper for lower errors.
     */
    Optional<Account> findBookmaker() throws ServiceException;

    /**
     * Gets balance value of account by account id.
     *
     * @param  id  an id value of account id.
     *
     * @return  balance value.
     *
     * @throws  ServiceException  if account is not found
     *                            also it's a wrapper for lower errors.
     */
    float getBalance(long id) throws ServiceException;

    /**
     * Adds money value to balance of account by account id.
     *
     * @param  money  a money value to add.
     * @param  id     an id value of account.
     *
     * @throws  ServiceException  a wrapper for lower errors.
     */
    void addMoneyById(float money, long id) throws ServiceException;

    /**
     * Unblock account by id.
     *
     * @param  id  an id value of account to unblock.
     *
     * @throws  ServiceException  a wrapper for lower errors.
     */
    void unblockById(long id) throws ServiceException;

    /**
     * Blocks account by id.
     *
     * @param  id  an id value of account to block.
     *
     * @throws  ServiceException  a wrapper for lower errors.
     */
    void blockById(long id) throws ServiceException;

    /**
     * Finds out if account is exist by login and password.
     * Returns boolean result.
     *
     * @param  login     a login(username) of account.
     * @param  password  a login(username) of account.
     *
     * @return  a boolean result of finding.
     *
     * @throws  ServiceException  a wrapper for lower errors.
     */
    boolean isAccountExistByLoginPassword(String login, String password) throws ServiceException;

    /**
     * Gets account by login.
     *
     * @param  login  a login(username) of account.
     *
     * @return  an object of account.
     *
     * @throws  ServiceException  if account is not exist
     *                            also it's a wrapper for lower errors.
     */
    Account getAccountByLogin(String login) throws ServiceException;
}
