package com.epam.web.service;

import com.epam.web.dao.impl.account.AccountDao;
import com.epam.web.dao.impl.bet.BetDao;
import com.epam.web.dao.helper.DaoHelper;
import com.epam.web.dao.helper.DaoHelperFactory;
import com.epam.web.exceptions.DaoException;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.model.entity.Account;
import com.epam.web.model.entity.Bet;

import java.util.List;
import java.util.Optional;

public class BetService {
    private final DaoHelperFactory daoHelperFactory;

    public BetService(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }

    public List<Bet> getAll() throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            BetDao betDao = daoHelper.createBetDao();
            return betDao.getAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public int getUserBalance(long userId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            AccountDao accountDao = daoHelper.createAccountDao();
            Optional<Account> user = accountDao.findById(userId);
            if (!user.isPresent()) {
                throw new ServiceException("There is no user with this id anymore.");
            }
            return user.get().getBalance();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void bet(Bet bet) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            BetDao betDao = daoHelper.createBetDao();
            AccountDao accountDao = daoHelper.createAccountDao();
            int money = bet.getMoneyBet();
            long accountId = bet.getAccountId();
            Optional<Account> account = accountDao.findById(accountId);
            if (!account.isPresent()) {
                throw new ServiceException("There is no user with this id anymore.");
            }
            daoHelper.startTransaction();
            accountDao.addMoneyToBalance(money * -1, accountId);
            betDao.save(bet);
            daoHelper.commit();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Bet> getBetsByMatch(long matchId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            BetDao betDao = daoHelper.createBetDao();
            return betDao.getBetsByMatchId(matchId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
