package com.epam.web.logic.calculator;

import com.epam.web.model.enumeration.Team;

public class BetCalculatorImpl implements BetCalculator {

    @Override
    public int calculatePercent(Team team, float firstTeamBetsAmount, float secondTeamBetsAmount) {
        if (firstTeamBetsAmount + secondTeamBetsAmount == 0.0f) {
            return 0;
        }
        if (team == Team.SECOND) {
            float tempBetsAmount = firstTeamBetsAmount;
            firstTeamBetsAmount = secondTeamBetsAmount;
            secondTeamBetsAmount = tempBetsAmount;
        }
        float percent = firstTeamBetsAmount * 100.0f / (firstTeamBetsAmount + secondTeamBetsAmount);
        return Math.round(percent);
    }

    @Override
    public float calculateCoefficient(Team team, float firstTeamBetsAmount, float secondTeamBetsAmount) {
        if (firstTeamBetsAmount * secondTeamBetsAmount == 0) {
            return 0.0f;
        }
        if (team == Team.SECOND) {
            float tempBetsAmount = firstTeamBetsAmount;
            firstTeamBetsAmount = secondTeamBetsAmount;
            secondTeamBetsAmount = tempBetsAmount;
        }
        return (secondTeamBetsAmount / firstTeamBetsAmount);
    }
}
