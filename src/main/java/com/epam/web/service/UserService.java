package com.epam.web.service;

import com.epam.web.dao.helper.DaoHelper;
import com.epam.web.dao.helper.DaoHelperFactory;
import com.epam.web.dao.impl.account.AccountDao;
import com.epam.web.model.entity.Account;
import com.epam.web.exceptions.DaoException;
import com.epam.web.exceptions.ServiceException;

import java.util.List;

public class UserService {
    private final DaoHelperFactory daoHelperFactory;

    public UserService(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }

    public List<Account> getAll() throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            AccountDao matchDao = daoHelper.createAccountDao();
            return matchDao.getAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void addMoney(int money, long id) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            AccountDao matchDao = daoHelper.createAccountDao();
            matchDao.addMoneyToBalance(money, id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void unblock(long id) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            AccountDao matchDao = daoHelper.createAccountDao();
            matchDao.unblock(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void block(long id) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            AccountDao matchDao = daoHelper.createAccountDao();
            matchDao.block(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
