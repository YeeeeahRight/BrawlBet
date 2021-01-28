package com.epam.web.logic.service.match;

import com.epam.web.dao.helper.DaoHelper;
import com.epam.web.dao.helper.DaoHelperFactory;
import com.epam.web.dao.impl.account.AccountDao;
import com.epam.web.dao.impl.bet.BetDao;
import com.epam.web.dao.impl.match.MatchDao;
import com.epam.web.dao.impl.team.TeamDao;
import com.epam.web.exception.DaoException;
import com.epam.web.exception.ServiceException;
import com.epam.web.logic.calculator.BetCalculator;
import com.epam.web.model.entity.Account;
import com.epam.web.model.entity.Bet;
import com.epam.web.model.entity.Match;
import com.epam.web.model.enumeration.MatchTeamNumber;

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
    public void closeMatchById(long matchId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MatchDao matchDao = daoHelper.createMatchDao();
            BetDao betDao = daoHelper.createBetDao();
            AccountDao accountDao = daoHelper.createAccountDao();
            Optional<Match> matchOptional = matchDao.findById(matchId);
            if (!matchOptional.isPresent()) {
                throw new ServiceException("Match with id='" + matchId + "' is not found.");
            }
            Match match = matchOptional.get();
            if (match.isClosed()) {
                throw new ServiceException("This match already closed.");
            }
            List<Bet> matchBets = betDao.getBetsByMatchId(matchId);
            MatchTeamNumber winnerTeam;
            daoHelper.startTransaction();
            if (matchBets.size() > 0) {
                float firstTeamBetsAmount = match.getFirstTeamBets();
                float secondTeamBetsAmount = match.getSecondTeamBets();
                int firstPercent = betCalculator.calculatePercent(MatchTeamNumber.FIRST, firstTeamBetsAmount,
                        secondTeamBetsAmount);
                winnerTeam = calculateWinner(firstPercent);
                float commission = match.getCommission();
                float winCoefficient = calculateCoefficient(commission,
                        firstTeamBetsAmount, secondTeamBetsAmount, matchBets, winnerTeam);
                long firstTeamId = match.getFirstTeamId();
                long secondTeamId = match.getSecondTeamId();
                for (Bet bet : matchBets) {
                    long teamId = bet.getTeamId();
                    MatchTeamNumber teamBetNumber = findTeamNumber(teamId, firstTeamId, secondTeamId);
                    float moneyReceived;
                    if (teamBetNumber == winnerTeam) {
                        moneyReceived = bet.getMoneyBet() * winCoefficient;
                    } else {
                        moneyReceived = 0f;
                    }
                    betDao.close(moneyReceived, bet.getId());
                    accountDao.addMoneyById(moneyReceived, bet.getAccountId());
                }
                Optional<Account> bookmakerOptional = accountDao.findBookmaker();
                if (bookmakerOptional.isPresent()) {
                    Account bookmaker = bookmakerOptional.get();
                    float matchGain = winnerTeam == MatchTeamNumber.FIRST ? secondTeamBetsAmount : firstTeamBetsAmount;
                    float bookmakerGain = matchGain * commission / 100f;
                    accountDao.addMoneyById(bookmakerGain, bookmaker.getId());
                }
            } else {
                winnerTeam = calculateWinner(SAME_WIN_CHANCE);
            }
            TeamDao teamDao = daoHelper.createTeamDao();
            incrementTeamsStatistic(teamDao, winnerTeam, match.getFirstTeamId(), match.getSecondTeamId());
            matchDao.close(matchId, winnerTeam);
            daoHelper.commit();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private MatchTeamNumber calculateWinner(int firstTeamPercent) {
        long percent = Math.round((Math.random() * 100) + 1);
        return percent > firstTeamPercent ? MatchTeamNumber.SECOND : MatchTeamNumber.FIRST;
    }

    private float calculateCoefficient(float commission, float firstTeamBetsAmount,
                                       float secondTeamBetsAmount, List<Bet> matchBets, MatchTeamNumber winner) {
        float coefficient = 0.0f;
        if (isBetsOnTwoTeams(matchBets)) {
            if (winner == MatchTeamNumber.FIRST) {
                coefficient = betCalculator.calculateCoefficient(MatchTeamNumber.FIRST, firstTeamBetsAmount,
                        secondTeamBetsAmount);
            } else {
                coefficient = betCalculator.calculateCoefficient(MatchTeamNumber.SECOND, firstTeamBetsAmount,
                        secondTeamBetsAmount);
            }
            if (!isOneUserBets(matchBets)) {
                coefficient -= coefficient * commission / 100f;
            }
        }
        return coefficient + 1;
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
        long firstBetTeamId = bets.get(0).getTeamId();
        for (int i = 1; i < bets.size(); i++) {
            long currentBetTeamId = bets.get(i).getTeamId();
            if (firstBetTeamId != currentBetTeamId) {
                return true;
            }
        }
        return false;
    }

    private MatchTeamNumber findTeamNumber(long teamId, long firstTeamId, long secondTeamId) throws ServiceException {
        if (teamId == firstTeamId) {
            return MatchTeamNumber.FIRST;
        } else if (teamId == secondTeamId) {
            return MatchTeamNumber.SECOND;
        }
        throw new ServiceException("Team with id='" + teamId + "' is not found.");
    }

    private void incrementTeamsStatistic(TeamDao teamDao, MatchTeamNumber matchTeamNumber,
                                         long firstTeamId, long secondTeamId) throws DaoException {
        if (matchTeamNumber == MatchTeamNumber.SECOND) {
            long tempId = firstTeamId;
            firstTeamId = secondTeamId;
            secondTeamId = tempId;
        }
        teamDao.incrementMatchesWonById(firstTeamId);
        teamDao.incrementMatchesLostById(secondTeamId);
    }
}
