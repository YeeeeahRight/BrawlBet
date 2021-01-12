package com.epam.web.logic.service;

import com.epam.web.dao.impl.account.AccountDao;
import com.epam.web.dao.impl.bet.BetDao;
import com.epam.web.dao.helper.DaoHelper;
import com.epam.web.dao.helper.DaoHelperFactory;
import com.epam.web.exceptions.DaoException;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.logic.validator.Validator;
import com.epam.web.model.entity.Account;
import com.epam.web.model.entity.Bet;

import java.util.List;
import java.util.Optional;

public class BetService {
    private final DaoHelperFactory daoHelperFactory;
    private final Validator<Bet> betValidator;

    public BetService(DaoHelperFactory daoHelperFactory, Validator<Bet> betValidator) {
        this.daoHelperFactory = daoHelperFactory;
        this.betValidator = betValidator;
    }

    public List<Bet> getAll() throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            BetDao betDao = daoHelper.createBetDao();
            return betDao.getAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void createBet(Bet bet) throws ServiceException {
        if (!betValidator.isValid(bet)) {
            throw new ServiceException("Invalid bet data.");
        }
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            BetDao betDao = daoHelper.createBetDao();
            AccountDao accountDao = daoHelper.createAccountDao();
            int money = bet.getMoneyBet();
            long accountId = bet.getAccountId();
            Optional<Account> account = accountDao.findById(accountId);
            if (!account.isPresent()) {
                throw new ServiceException("There is no bet with this id.");
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
