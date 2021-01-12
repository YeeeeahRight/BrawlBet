package com.epam.web.logic.service.account;

import com.epam.web.dao.helper.DaoHelper;
import com.epam.web.dao.helper.DaoHelperFactory;
import com.epam.web.dao.impl.account.AccountDao;
import com.epam.web.logic.validator.Validator;
import com.epam.web.model.entity.Account;
import com.epam.web.exception.DaoException;
import com.epam.web.exception.ServiceException;
import com.epam.web.model.enumeration.AccountRole;

import java.util.Optional;

public class SignUpServiceImpl implements SignUpService {
    private final DaoHelperFactory daoHelperFactory;
    private final Validator<Account> accountValidator;

    public SignUpServiceImpl(DaoHelperFactory daoHelperFactory, Validator<Account> accountValidator) {
        this.daoHelperFactory = daoHelperFactory;
        this.accountValidator = accountValidator;
    }

    @Override
    public void signUp(String login, String password) throws ServiceException {
        Account newAccount = new Account(login, password, AccountRole.USER);
        if (!accountValidator.isValid(newAccount)) {
            throw new ServiceException("Invalid account data.");
        }
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            AccountDao accountDao = daoHelper.createAccountDao();
            accountDao.save(newAccount);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean isUsernameExist(String login) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            AccountDao accountDao = daoHelper.createAccountDao();
            Optional<Account> user = accountDao.findAccountByLogin(login);
            return user.isPresent();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
