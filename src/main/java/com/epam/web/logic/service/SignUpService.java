package com.epam.web.logic.service;

import com.epam.web.dao.helper.DaoHelper;
import com.epam.web.dao.helper.DaoHelperFactory;
import com.epam.web.dao.impl.account.AccountDao;
import com.epam.web.model.entity.Account;
import com.epam.web.exceptions.DaoException;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.model.enumeration.AccountRole;

import java.util.Optional;

public class SignUpService {
    private final DaoHelperFactory daoHelperFactory;

    public SignUpService(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }

    public void signUp(String login, String password) throws ServiceException {
        Account newAccount = new Account(login, password, AccountRole.USER);
        try(DaoHelper daoHelper = daoHelperFactory.create()) {
            AccountDao accountDao = daoHelper.createAccountDao();
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
