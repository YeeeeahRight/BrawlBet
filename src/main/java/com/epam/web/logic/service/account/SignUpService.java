package com.epam.web.logic.service.account;

import com.epam.web.exception.ServiceException;

/**
 * Interface with description of operations on sign up command.
 */
public interface SignUpService {
    /**
     * Authorizes account by login and password.
     *
     * @param  login     a login(username) of account.
     * @param  password  a login(username) of account.
     *
     * @throws  ServiceException  if login and password are not passed validation
     *                            and also it's a wrapper for lower errors.
     */
    void signUp(String login, String password) throws ServiceException;

    /**
     * Finds out if account is exist by login.
     * Returns boolean result.
     *
     * @param  login  a login(username) of account.
     *
     * @return  a boolean result of finding.
     *
     * @throws  ServiceException  a wrapper for lower errors.
     */
    boolean isUsernameExist(String login) throws ServiceException;
}
