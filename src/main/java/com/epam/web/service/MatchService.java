package com.epam.web.service;

import com.epam.web.dao.impl.account.AccountDao;
import com.epam.web.dao.impl.bet.BetDao;
import com.epam.web.dao.helper.DaoHelper;
import com.epam.web.dao.helper.DaoHelperFactory;
import com.epam.web.dao.impl.match.MatchDao;
import com.epam.web.model.entity.Bet;
import com.epam.web.model.entity.Match;
import com.epam.web.exceptions.DaoException;
import com.epam.web.exceptions.ServiceException;

import java.util.List;
import java.util.Optional;

public class MatchService {
    private final DaoHelperFactory daoHelperFactory;

    public MatchService(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }

    public List<Match> getAll() throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MatchDao matchDao = daoHelper.createMatchDao();
            return matchDao.getAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void removeById(long id) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MatchDao matchDao = daoHelper.createMatchDao();
            matchDao.removeById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void saveMatch(Match match) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MatchDao matchDao = daoHelper.createMatchDao();
            matchDao.save(match);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void editMatch(Match match, long id) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MatchDao matchDao = daoHelper.createMatchDao();
            matchDao.edit(match, id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Match findById(long id) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MatchDao matchDao = daoHelper.createMatchDao();
            Optional<Match> match = matchDao.findById(id);
            if (!match.isPresent()) {
                throw new ServiceException("There is no such match with this id anymore.");
            }
            return match.get();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void addCommission(float commission, long id) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MatchDao matchDao = daoHelper.createMatchDao();
            matchDao.addCommission(commission, id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Match> getActiveMatches() throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MatchDao matchDao = daoHelper.createMatchDao();
            return matchDao.getActiveMatches();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Match> getUnacceptedMatches() throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MatchDao matchDao = daoHelper.createMatchDao();
            return matchDao.getUnacceptedMatches();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void cancelMatch(long id) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MatchDao matchDao = daoHelper.createMatchDao();
            BetDao betDao = daoHelper.createBetDao();
            AccountDao accountDao = daoHelper.createAccountDao();
            List<Bet> bets = betDao.getBetsByMatchId(id);
            daoHelper.startTransaction();
            for (Bet bet : bets) {
                long accountId = bet.getAccountId();
                int money = bet.getMoneyBet();
                int received = bet.getMoneyReceived();
                accountDao.addMoneyToBalance(money - received, accountId);
            }
            matchDao.removeById(id);
            betDao.removeById(id);
            daoHelper.commit();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

}
