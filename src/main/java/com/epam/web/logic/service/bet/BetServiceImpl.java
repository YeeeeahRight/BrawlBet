package com.epam.web.logic.service.bet;

import com.epam.web.dao.impl.account.AccountDao;
import com.epam.web.dao.impl.bet.BetDao;
import com.epam.web.dao.helper.DaoHelper;
import com.epam.web.dao.helper.DaoHelperFactory;
import com.epam.web.dao.impl.match.MatchDao;
import com.epam.web.exception.DaoException;
import com.epam.web.exception.ServiceException;
import com.epam.web.logic.validator.Validator;
import com.epam.web.model.entity.Bet;
import com.epam.web.model.enumeration.Team;

import java.util.List;

public class BetServiceImpl implements BetService {
    private final DaoHelperFactory daoHelperFactory;
    private final Validator<Bet> betValidator;

    public BetServiceImpl(DaoHelperFactory daoHelperFactory, Validator<Bet> betValidator) {
        this.daoHelperFactory = daoHelperFactory;
        this.betValidator = betValidator;
    }

    @Override
    public void saveBet(Bet bet) throws ServiceException {
        if (!betValidator.isValid(bet)) {
            throw new ServiceException("Invalid bet data.");
        }
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            BetDao betDao = daoHelper.createBetDao();
            AccountDao accountDao = daoHelper.createAccountDao();
            MatchDao matchDao = daoHelper.createMatchDao();
            float money = bet.getMoneyBet();
            long accountId = bet.getAccountId();
            Team team = bet.getTeam();
            daoHelper.startTransaction();
            accountDao.addMoneyById(money * -1, accountId);
            betDao.save(bet);
            matchDao.addTeamBetAmount(team, money, bet.getMatchId());
            daoHelper.commit();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Bet> getBetsByMatchId(long matchId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            BetDao betDao = daoHelper.createBetDao();
            return betDao.getBetsByMatchId(matchId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Bet> getBetsByAccountIdRange(long accountId, int offset, int amount) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            BetDao betDao = daoHelper.createBetDao();
            return betDao.getBetsByAccountIdRange(accountId, offset, amount);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int getBetsAmountByAccountId(long accountId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            BetDao betDao = daoHelper.createBetDao();
            return betDao.getBetsAmountByAccountId(accountId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
