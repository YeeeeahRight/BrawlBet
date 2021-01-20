package com.epam.web.logic.service.match;

import com.epam.web.dao.helper.DaoHelper;
import com.epam.web.dao.helper.DaoHelperFactory;
import com.epam.web.dao.impl.account.AccountDao;
import com.epam.web.dao.impl.bet.BetDao;
import com.epam.web.dao.impl.match.MatchDao;
import com.epam.web.exception.DaoException;
import com.epam.web.exception.ServiceException;
import com.epam.web.logic.calculator.BetCalculator;
import com.epam.web.model.entity.Bet;
import com.epam.web.model.entity.Match;
import com.epam.web.model.enumeration.Team;

import java.util.List;
import java.util.Optional;

public class CloseMatchServiceImpl implements CloseMatchService {
    private static final int SAME_WIN_CHANCE = 50;

    private final DaoHelperFactory daoHelperFactory;
    private final BetCalculator betCalculator;

    public CloseMatchServiceImpl(DaoHelperFactory daoHelperFactory, BetCalculator betCalculator) {
        this.daoHelperFactory = daoHelperFactory;
        this.betCalculator = betCalculator;
    }

    @Override
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
            List<Bet> matchBets = betDao.getBetsByMatchId(matchId);
            Team winner;
            daoHelper.startTransaction();
            if (matchBets.size() > 0) {
                float firstTeamBetsAmount = match.getFirstTeamBets();
                float secondTeamBetsAmount = match.getSecondTeamBets();
                int firstPercent = betCalculator.calculatePercent(Team.FIRST, firstTeamBetsAmount,
                        secondTeamBetsAmount);
                winner = calculateWinner(firstPercent);
                float commission = match.getCommission();
                float winCoefficient = calculateCoefficient(commission,
                        firstTeamBetsAmount, secondTeamBetsAmount, matchBets, winner);
                for (Bet bet : matchBets) {
                    Team team = bet.getTeam();
                    float moneyReceived;
                    if (team == winner) {
                        moneyReceived = bet.getMoneyBet() * winCoefficient;
                    } else {
                        moneyReceived = 0;
                    }
                    betDao.close(moneyReceived, bet.getId());
                    accountDao.addMoneyToBalance(moneyReceived, bet.getAccountId());
                }
            } else {
                winner = calculateWinner(SAME_WIN_CHANCE);
            }
            String winnerTeam = winner == Team.FIRST ? match.getFirstTeam() : match.getSecondTeam();
            matchDao.close(matchId, winnerTeam);
            daoHelper.commit();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private Team calculateWinner(int firstTeamPercent) {
        long percent = Math.round((Math.random() * 100) + 1);
        return percent > firstTeamPercent ? Team.SECOND : Team.FIRST;
    }

    private float calculateCoefficient(float commission, float firstTeamBetsAmount,
                                       float secondTeamBetsAmount, List<Bet> matchBets, Team winner) {
        float coefficient = 1.0f;
        if (isBetsOnTwoTeams(matchBets)) {
            if (winner == Team.FIRST) {
                coefficient = betCalculator.calculateCoefficient(Team.FIRST, firstTeamBetsAmount,
                        secondTeamBetsAmount);
            } else {
                coefficient = betCalculator.calculateCoefficient(Team.SECOND, firstTeamBetsAmount,
                        secondTeamBetsAmount);
            }
            if (!isOneUserBets(matchBets)) {
                coefficient -= coefficient * commission / 100;
            }
        }
        return coefficient;
    }

    private boolean isOneUserBets(List<Bet> bets) {
        for (int i = bets.size() - 1; i >= 0; i--) {
            long currentAccountId = bets.get(i).getAccountId();
            if (i > 0 && bets.get(i - 1).getAccountId() != currentAccountId) {
                return false;
            }
        }
        return true;
    }

    private boolean isBetsOnTwoTeams(List<Bet> bets) {
        Team firstBetTeam = bets.get(0).getTeam();
        for (int i = 1; i < bets.size(); i++) {
            Team currentBetTeam = bets.get(i).getTeam();
            if (firstBetTeam != currentBetTeam) {
                return true;
            }
        }
        return false;
    }
}
