package com.epam.web.service;

import com.epam.web.dao.helper.DaoHelper;
import com.epam.web.dao.helper.DaoHelperFactory;
import com.epam.web.model.entity.Account;
import com.epam.web.exceptions.DaoException;
import com.epam.web.dao.account.AccountDao;
import com.epam.web.exceptions.ServiceException;

import java.util.Optional;

public class LoginService {
    private final DaoHelperFactory daoHelperFactory;

    public LoginService(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }

    public boolean isUserExist(String login, String password) throws ServiceException {
        if (login == null || login.isEmpty()) {
            throw new ServiceException("Incorrect username: " + login);
        }
        if (password == null || password.isEmpty()) {
            throw new ServiceException("Incorrect password: " + password);
        }
        try(DaoHelper daoHelper = daoHelperFactory.create()) {
            //daoHelper.startTransaction();
            AccountDao accountDao = daoHelper.createUserDao();
            Optional<Account> user = accountDao.findAccountByLoginPassword(login, password);
            return user.isPresent();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public boolean isBlocked(String login) throws ServiceException {
        try(DaoHelper daoHelper = daoHelperFactory.create()) {
            AccountDao accountDao = daoHelper.createUserDao();
            Optional<Account> user = accountDao.findAccountByLogin(login);
            if (!user.isPresent()) {
                throw new ServiceException("User not found.");
            }
            return user.get().isBlocked();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public Account getUserByLogin(String login) throws ServiceException {
        try(DaoHelper daoHelper = daoHelperFactory.create()) {
            AccountDao accountDao = daoHelper.createUserDao();
            Optional<Account> user = accountDao.findAccountByLogin(login);
            if (!user.isPresent()) {
                throw new ServiceException("User not found.");
            }
            return user.get();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
