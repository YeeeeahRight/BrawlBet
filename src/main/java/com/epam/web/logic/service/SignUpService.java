package com.epam.web.logic.service;

import com.epam.web.dao.helper.DaoHelper;
import com.epam.web.dao.helper.DaoHelperFactory;
import com.epam.web.dao.impl.account.AccountDao;
import com.epam.web.model.entity.Account;
import com.epam.web.exceptions.DaoException;
import com.epam.web.exceptions.ServiceException;

import java.util.Optional;

public class SignUpService {
    private static final String USER_ROLE = "user";
    private static final int MAX_LOGIN_LENGTH = 20;
    private static final int MAX_PASSWORD_LENGTH = 30;
    private final DaoHelperFactory daoHelperFactory;

    public SignUpService(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }

    public void signUp(String login, String password) throws ServiceException {
        if (login == null || login.isEmpty() || login.length() > MAX_LOGIN_LENGTH) {
            throw new ServiceException("Incorrect username: " + login);
        }
        if (password == null || password.isEmpty() || password.length() > MAX_PASSWORD_LENGTH) {
            throw new ServiceException("Incorrect password: " + password);
        }
        try(DaoHelper daoHelper = daoHelperFactory.create()) {
            AccountDao accountDao = daoHelper.createAccountDao();
            Account newAccount = new Account(login, password, USER_ROLE);
            accountDao.save(newAccount);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public boolean isUsernameExist(String login) throws ServiceException {
        try(DaoHelper daoHelper = daoHelperFactory.create()) {
            AccountDao accountDao = daoHelper.createAccountDao();
            Optional<Account> user = accountDao.findAccountByLogin(login);
            return user.isPresent();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
