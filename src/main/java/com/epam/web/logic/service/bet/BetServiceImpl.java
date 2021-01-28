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
import com.epam.web.model.entity.Match;
import com.epam.web.model.enumeration.MatchTeamNumber;

import java.util.List;
import java.util.Optional;

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
            long matchId = bet.getMatchId();
            Optional<Match> matchOptional = matchDao.findById(matchId);
            if (!matchOptional.isPresent()) {
                throw new ServiceException("Match with id='" + matchId + "' is not found.");
            }
            Match match = matchOptional.get();
            long teamId = bet.getTeamId();
            MatchTeamNumber teamType = findTeamType(teamId, match);
            float money = bet.getMoneyBet();
            long accountId = bet.getAccountId();
            daoHelper.startTransaction();
            accountDao.addMoneyById(money * -1, accountId);
            betDao.save(bet);
            matchDao.addTeamBetAmount(teamType, money, matchId);
            daoHelper.commit();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private MatchTeamNumber findTeamType(long teamId, Match match) throws ServiceException {
        if (teamId == match.getFirstTeamId()) {
            return MatchTeamNumber.FIRST;
        } else if (teamId == match.getSecondTeamId()) {
            return MatchTeamNumber.SECOND;
        }
        throw new ServiceException("No such team id in this match.");
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
