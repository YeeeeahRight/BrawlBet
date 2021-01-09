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
import com.epam.web.model.entity.dto.MatchBetsDto;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class MatchService {
    private static final String FIRST_TEAM = "FIRST";
    private static final String SECOND_TEAM = "SECOND";
    private final DaoHelperFactory daoHelperFactory;

    public MatchService(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
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

    public void close(long matchId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MatchDao matchDao = daoHelper.createMatchDao();
            BetDao betDao = daoHelper.createBetDao();
            AccountDao accountDao = daoHelper.createAccountDao();
            Optional<Match> matchOptional = matchDao.findById(matchId);
            if (!matchOptional.isPresent()) {
                throw new ServiceException("There is no such match with this id anymore.");
            }
            Match match = matchOptional.get();
            if (match.isClosed()) {
                throw new ServiceException("This match already closed.");
            }
            List<Bet> bets = betDao.getBetsByMatchId(matchId);
            if (bets.size() != 0) {
                MatchBetsDto matchBetsDto = createMatchBetDto(match, bets);
                int firstPercent = matchBetsDto.getFirstPercent();
                String winner = calculateWinner(firstPercent);
                boolean isBetsOnOneTeam = (firstPercent == 0 || firstPercent == 100);
                float coefficient;
                if (isBetsOnOneTeam) {
                    coefficient = 1.0f;
                } else {
                    coefficient = winner.equalsIgnoreCase(FIRST_TEAM) ?
                            matchBetsDto.getFirstCoefficient() : matchBetsDto.getSecondCoefficient();
                    boolean isNotCountCommission = isOneUserBets(bets);
                    if (isNotCountCommission) {
                        coefficient -= matchBetsDto.getCommissionByCoefficient(coefficient);
                    }
                }
                daoHelper.startTransaction();
                for (Bet bet : bets) {
                    String team = bet.getTeam();
                    int moneyReceived;
                    if (team.equalsIgnoreCase(winner)) {
                        moneyReceived = Math.round(bet.getMoneyBet() * coefficient);
                    } else {
                        moneyReceived = bet.getMoneyBet() * -1;
                    }
                    betDao.close(moneyReceived, bet.getId());
                    accountDao.addMoneyToBalance(moneyReceived, bet.getAccountId());
                }
            }
            matchDao.close(matchId);
            daoHelper.commit();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private MatchBetsDto createMatchBetDto(Match match, List<Bet> bets) {
        long id = match.getId();
        Date date = match.getDate();
        String tournament = match.getTournament();
        String firstTeam = match.getFirstTeam();
        String secondTeam = match.getSecondTeam();
        float commission = match.getCommission();
        int firstTeamBetsAmount = 0;
        int secondTeamBetsAmount = 0;
        for (Bet bet : bets) {
            long matchId = bet.getMatchId();
            if (matchId == id) {
                String team = bet.getTeam();
                if (team.equalsIgnoreCase(FIRST_TEAM)) {
                    firstTeamBetsAmount += bet.getMoneyBet();
                } else {
                    secondTeamBetsAmount += bet.getMoneyBet();
                }
            }
        }
        return new MatchBetsDto(id, date, tournament,
                firstTeam, secondTeam, commission, firstTeamBetsAmount, secondTeamBetsAmount);
    }

    private String calculateWinner(int firstTeamPercent) {
        long percent = Math.round((Math.random() * 100) + 1);
        return percent > firstTeamPercent ? SECOND_TEAM : FIRST_TEAM;
    }

    private boolean isOneUserBets(List<Bet> bets) {
        long prevAccountId = bets.get(0).getId();
        for (Bet bet : bets) {
            if (prevAccountId != bet.getId()) {
                return false;
            }
        }
        return true;
    }

    public List<Match> getUnclosedMatches() throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MatchDao matchDao = daoHelper.createMatchDao();
            return matchDao.getUnclosedMatches();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public boolean isFinished(long id) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MatchDao matchDao = daoHelper.createMatchDao();
            Optional<Match> matchOptional = matchDao.findById(id);
            if (!matchOptional.isPresent()) {
                throw new ServiceException("There is no such match with this id anymore.");
            }
            Match match = matchOptional.get();
            long matchTime = match.getDate().getTime();
            long currentTime = new Date().getTime();
            return currentTime >= matchTime;
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

    public List<Match> getFinishedMatches() throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MatchDao matchDao = daoHelper.createMatchDao();
            return matchDao.getFinishedMatches();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Match> getUnfinishedMatches() throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MatchDao matchDao = daoHelper.createMatchDao();
            return matchDao.getFinishedMatches();
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
