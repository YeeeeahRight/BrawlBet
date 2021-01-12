package com.epam.web.logic.service.account;

import com.epam.web.dao.helper.DaoHelper;
import com.epam.web.dao.helper.DaoHelperFactory;
import com.epam.web.dao.impl.account.AccountDao;
import com.epam.web.model.entity.Account;
import com.epam.web.exception.DaoException;
import com.epam.web.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public class AccountServiceImpl implements AccountService {
    private final DaoHelperFactory daoHelperFactory;

    public AccountServiceImpl(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }

    @Override
    public List<Account> getAll() throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            AccountDao matchDao = daoHelper.createAccountDao();
            return matchDao.getAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int getBalance(long id) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            AccountDao matchDao = daoHelper.createAccountDao();
            Optional<Account> user = matchDao.findById(id);
            if (!user.isPresent()) {
                throw new ServiceException("There is no user with this id.");
            }
            return user.get().getBalance();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addMoneyById(int money, long id) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            AccountDao matchDao = daoHelper.createAccountDao();
            matchDao.addMoneyToBalance(money, id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void unblockById(long id) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            AccountDao matchDao = daoHelper.createAccountDao();
            matchDao.unblock(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void block(long id) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            AccountDao matchDao = daoHelper.createAccountDao();
            matchDao.block(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean isAccountExistByLoginPassword(String login, String password) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            AccountDao accountDao = daoHelper.createAccountDao();
            Optional<Account> user = accountDao.findAccountByLoginPassword(login, password);
            return user.isPresent();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Account getAccountByLogin(String login) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            AccountDao accountDao = daoHelper.createAccountDao();
            Optional<Account> user = accountDao.findAccountByLogin(login);
            if (!user.isPresent()) {
                throw new ServiceException("Account is not found.");
            }
            return user.get();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
