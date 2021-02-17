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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

public class CloseMatchServiceImpl implements CloseMatchService {
    private static final BigDecimal HUNDRED = new BigDecimal("100.0");
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
                BigDecimal firstTeamBetsAmount = match.getFirstTeamBets();
                BigDecimal secondTeamBetsAmount = match.getSecondTeamBets();
                int firstPercent = betCalculator.calculatePercent(MatchTeamNumber.FIRST,
                        firstTeamBetsAmount.floatValue(), secondTeamBetsAmount.floatValue());
                winnerTeam = calculateWinner(firstPercent);
                float commission = match.getCommission();
                BigDecimal winCoefficient = calculateCoefficient(commission,
                        firstTeamBetsAmount, secondTeamBetsAmount, matchBets, winnerTeam);
                long firstTeamId = match.getFirstTeamId();
                long secondTeamId = match.getSecondTeamId();
                for (Bet bet : matchBets) {
                    long teamId = bet.getTeamId();
                    MatchTeamNumber teamBetNumber = findTeamNumber(teamId, firstTeamId, secondTeamId);
                    BigDecimal moneyReceived;
                    if (teamBetNumber == winnerTeam) {
                        System.out.print(bet.getMoneyBet() + " and then ");
                        moneyReceived = bet.getMoneyBet().multiply(winCoefficient);
                        System.out.println(moneyReceived);
                    } else {
                        moneyReceived = BigDecimal.ZERO;
                    }
                    betDao.close(moneyReceived, bet.getId());
                    accountDao.addMoneyById(moneyReceived, bet.getAccountId());
                }
                if (isNotOneUserBets(matchBets)) {
                    Optional<Account> bookmakerOptional = accountDao.findBookmaker();
                    if (bookmakerOptional.isPresent()) {
                        Account bookmaker = bookmakerOptional.get();
                        BigDecimal matchGain = winnerTeam == MatchTeamNumber.FIRST ?
                                secondTeamBetsAmount : firstTeamBetsAmount;
                        BigDecimal commissionDecimal = BigDecimal.valueOf(commission);
                        BigDecimal bookmakerGain = matchGain
                                .multiply(commissionDecimal)
                                .divide(HUNDRED, 12, RoundingMode.HALF_UP);
                        accountDao.addMoneyById(bookmakerGain, bookmaker.getId());
                    }
                } else {
                    matchDao.setCommissionById(commission, matchId);
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

    private BigDecimal calculateCoefficient(float commission, BigDecimal firstTeamBetsAmount,
                                       BigDecimal secondTeamBetsAmount, List<Bet> matchBets, MatchTeamNumber winner) {
        BigDecimal coefficient = BigDecimal.ZERO;
        if (isBetsOnTwoTeams(matchBets)) {
            if (winner == MatchTeamNumber.FIRST) {
                coefficient = betCalculator.calculateCoefficient(MatchTeamNumber.FIRST, firstTeamBetsAmount,
                        secondTeamBetsAmount);
            } else {
                coefficient = betCalculator.calculateCoefficient(MatchTeamNumber.SECOND, firstTeamBetsAmount,
                        secondTeamBetsAmount);
            }
            if (isNotOneUserBets(matchBets)) {
                BigDecimal commissionDecimal = BigDecimal.valueOf(commission);
                BigDecimal commissionPart = coefficient
                        .multiply(commissionDecimal)
                        .divide(HUNDRED, 10, RoundingMode.HALF_UP);
                coefficient = coefficient.subtract(commissionPart);
            }
        }
        return coefficient.add(BigDecimal.ONE);
    }

    private boolean isNotOneUserBets(List<Bet> bets) {
        for (int i = bets.size() - 1; i >= 0; i--) {
            long currentAccountId = bets.get(i).getAccountId();
            if (i > 0 && bets.get(i - 1).getAccountId() != currentAccountId) {
                return true;
            }
        }
        return false;
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
