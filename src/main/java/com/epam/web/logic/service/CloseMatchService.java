package com.epam.web.logic.service;

import com.epam.web.dao.helper.DaoHelper;
import com.epam.web.dao.helper.DaoHelperFactory;
import com.epam.web.dao.impl.account.AccountDao;
import com.epam.web.dao.impl.bet.BetDao;
import com.epam.web.dao.impl.match.MatchDao;
import com.epam.web.exceptions.DaoException;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.model.entity.Bet;
import com.epam.web.model.entity.Match;
import com.epam.web.model.entity.dto.MatchBetsDto;
import com.epam.web.model.enumeration.Team;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class CloseMatchService {
    private static final int SAME_WIN_CHANCE = 50;
    private final DaoHelperFactory daoHelperFactory;

    public CloseMatchService(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
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
            List<Bet> matchBets = betDao.getBetsByMatchId(matchId);
            Team winner;
            if (matchBets.size() > 0) {
                MatchBetsDto matchBetsDto = createMatchBetDto(match, matchBets);
                int firstPercent = matchBetsDto.getFirstPercent();
                winner = calculateWinner(firstPercent);
                float winCoefficient = calculateCoefficient(matchBetsDto, matchBets, winner);
                daoHelper.startTransaction();
                for (Bet bet : matchBets) {
                    Team team = bet.getTeam();
                    int moneyReceived;
                    if (team == winner) {
                        moneyReceived = Math.round(bet.getMoneyBet() * winCoefficient);
                    } else {
                        moneyReceived = bet.getMoneyBet() * -1;
                    }
                    betDao.close(moneyReceived, bet.getId());
                    accountDao.addMoneyToBalance(moneyReceived, bet.getAccountId());
                }
            } else {
                winner = calculateWinner(SAME_WIN_CHANCE);
            }
            matchDao.close(matchId, winner.toString());
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
        String winner = match.getWinner();
        float commission = match.getCommission();
        int firstTeamBetsAmount = 0;
        int secondTeamBetsAmount = 0;
        for (Bet bet : bets) {
            long matchId = bet.getMatchId();
            if (matchId == id) {
                Team team = bet.getTeam();
                if (team == Team.FIRST) {
                    firstTeamBetsAmount += bet.getMoneyBet();
                } else {
                    secondTeamBetsAmount += bet.getMoneyBet();
                }
            }
        }
        return new MatchBetsDto(id, date, tournament,
                firstTeam, secondTeam, winner, commission, firstTeamBetsAmount, secondTeamBetsAmount);
    }

    private Team calculateWinner(int firstTeamPercent) {
        long percent = Math.round((Math.random() * 100) + 1);
        return percent > firstTeamPercent ? Team.SECOND : Team.FIRST;
    }

    private float calculateCoefficient(MatchBetsDto matchBetsDto, List<Bet> matchBets, Team winner) {
        float coefficient = 1.0f;
        if (isBetsOnTwoTeams(matchBets)) {
            coefficient = winner == Team.FIRST ?
                    matchBetsDto.getFirstCoefficient() : matchBetsDto.getSecondCoefficient();
            if (isOneUserBets(matchBets)) {
                coefficient -= matchBetsDto.getCommissionByCoefficient(coefficient);
            }
        }
        return coefficient;
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
