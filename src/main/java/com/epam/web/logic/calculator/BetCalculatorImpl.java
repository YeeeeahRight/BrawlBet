package com.epam.web.logic.calculator;

import com.epam.web.model.enumeration.MatchTeamNumber;

public class BetCalculatorImpl implements BetCalculator {

    @Override
    public int calculatePercent(MatchTeamNumber team, float firstTeamBetsAmount, float secondTeamBetsAmount) {
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
    public float calculateCoefficient(MatchTeamNumber team, float firstTeamBetsAmount, float secondTeamBetsAmount) {
        if (firstTeamBetsAmount * secondTeamBetsAmount == 0) {
            return 0.0f;
        }
        if (team == MatchTeamNumber.SECOND) {
            float tempBetsAmount = firstTeamBetsAmount;
            firstTeamBetsAmount = secondTeamBetsAmount;
            secondTeamBetsAmount = tempBetsAmount;
        }
        return (secondTeamBetsAmount / firstTeamBetsAmount);
    }
}
