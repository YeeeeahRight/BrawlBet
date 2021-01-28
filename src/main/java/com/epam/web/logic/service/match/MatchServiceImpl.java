package com.epam.web.logic.service.match;

import com.epam.web.dao.impl.account.AccountDao;
import com.epam.web.dao.impl.bet.BetDao;
import com.epam.web.dao.helper.DaoHelper;
import com.epam.web.dao.helper.DaoHelperFactory;
import com.epam.web.dao.impl.match.MatchDao;
import com.epam.web.logic.validator.Validator;
import com.epam.web.model.entity.Bet;
import com.epam.web.model.entity.Match;
import com.epam.web.exception.DaoException;
import com.epam.web.exception.ServiceException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class MatchServiceImpl implements MatchService {
    private final DaoHelperFactory daoHelperFactory;
    private final Validator<Match> matchValidator;

    public MatchServiceImpl(DaoHelperFactory daoHelperFactory, Validator<Match> matchValidator) {
        this.daoHelperFactory = daoHelperFactory;
        this.matchValidator = matchValidator;
    }

    @Override
    public void saveMatch(Match match) throws ServiceException {
        if (!matchValidator.isValid(match)) {
            throw new ServiceException("Invalid match data.");
        }
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MatchDao matchDao = daoHelper.createMatchDao();
            matchDao.save(match);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void removeById(long id) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MatchDao matchDao = daoHelper.createMatchDao();
            matchDao.removeById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Match getMatchById(long id) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MatchDao matchDao = daoHelper.createMatchDao();
            Optional<Match> match = matchDao.findById(id);
            if (!match.isPresent()) {
                throw new ServiceException("Match with id='" + id + "' is not found.");
            }
            return match.get();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Match> getMatchesTypeRange(MatchType matchType, int offset, int amount) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MatchDao matchDao = daoHelper.createMatchDao();
            return matchDao.getMatchesTypeRange(matchType, offset, amount);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int getMatchesTypeAmount(MatchType matchType) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MatchDao matchDao = daoHelper.createMatchDao();
            return matchDao.getMatchesTypeAmount(matchType);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addCommissionById(float commission, long id) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MatchDao matchDao = daoHelper.createMatchDao();
            matchDao.addCommissionById(commission, id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void cancelMatchById(long id) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MatchDao matchDao = daoHelper.createMatchDao();
            BetDao betDao = daoHelper.createBetDao();
            AccountDao accountDao = daoHelper.createAccountDao();
            List<Bet> bets = betDao.getBetsByMatchId(id);
            daoHelper.startTransaction();
            for (Bet bet : bets) {
                long accountId = bet.getAccountId();
                float money = bet.getMoneyBet();
                float received = bet.getMoneyReceived();
                accountDao.addMoneyById(money - received, accountId);
                long matchId = bet.getMatchId();
                betDao.removeById(matchId);
            }
            matchDao.removeById(id);
            daoHelper.commit();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean isFinishedMatch(long id) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MatchDao matchDao = daoHelper.createMatchDao();
            Optional<Match> matchOptional = matchDao.findById(id);
            if (!matchOptional.isPresent()) {
                throw new ServiceException("Match with id='" + id + "' is not found.");
            }
            Match match = matchOptional.get();
            long matchTime = match.getDate().getTime();
            long currentTime = new Date().getTime();
            return currentTime >= matchTime;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

}
