package com.epam.web.logic.calculator;

import com.epam.web.model.enumeration.MatchTeamNumber;

import java.math.BigDecimal;

/**
 * Interface with description of math operations with bets.
 */
public interface BetCalculator {
    /**
     * Calculates coefficient of given team by first and second team bets amount.
     *
     * @param  team                  a type(left, right) of team.
     * @param  firstTeamBetsAmount   first team bets amount.
     * @param  secondTeamBetsAmount  second team bets amount.
     *
     * @return  coefficient value of given team.
     */
    BigDecimal calculateCoefficient(MatchTeamNumber team, BigDecimal firstTeamBetsAmount,
                                    BigDecimal secondTeamBetsAmount);

    /**
     * Calculates bets amount percent of given team by first and second team bets amount.
     *
     * @param  team                  a type(left, right) of team.
     * @param  firstTeamBetsAmount   first team bets amount.
     * @param  secondTeamBetsAmount  second team bets amount.
     *
     * @return  bets amount percent of given team.
     */
    int calculatePercent(MatchTeamNumber team, float firstTeamBetsAmount, float secondTeamBetsAmount);
}
