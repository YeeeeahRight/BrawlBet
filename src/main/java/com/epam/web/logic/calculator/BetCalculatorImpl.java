package com.epam.web.logic.calculator;

import com.epam.web.model.enumeration.MatchTeamNumber;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BetCalculatorImpl implements BetCalculator {

    @Override
    public int calculatePercent(MatchTeamNumber team, float firstTeamBetsAmount,
                                float secondTeamBetsAmount) {
        if (firstTeamBetsAmount + secondTeamBetsAmount == 0.0f) {
            return 0;
        }
        if (team == MatchTeamNumber.SECOND) {
            float tempBetsAmount = firstTeamBetsAmount;
            firstTeamBetsAmount = secondTeamBetsAmount;
            secondTeamBetsAmount = tempBetsAmount;
        }
        float percent = firstTeamBetsAmount * 100.0f / (firstTeamBetsAmount + secondTeamBetsAmount);
        return Math.round(percent);
    }

    @Override
    public BigDecimal calculateCoefficient(MatchTeamNumber team, BigDecimal firstTeamBetsAmount,
                                      BigDecimal secondTeamBetsAmount) {
        if (firstTeamBetsAmount.multiply(secondTeamBetsAmount).compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        if (team == MatchTeamNumber.SECOND) {
            BigDecimal tempBetsAmount = firstTeamBetsAmount;
            firstTeamBetsAmount = secondTeamBetsAmount;
            secondTeamBetsAmount = tempBetsAmount;
        }
        return (secondTeamBetsAmount.divide(firstTeamBetsAmount, 12, RoundingMode.HALF_UP));
    }
}
