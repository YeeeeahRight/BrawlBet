package com.epam.web.logic.calculator;

import com.epam.web.model.entity.Bet;
import com.epam.web.model.enumeration.Team;

import java.util.List;

public class BetCalculatorImpl implements BetCalculator {

    @Override
    public int calculatePercent(Team team, float firstTeamBetsAmount, float secondTeamBetsAmount) {
        if (team == Team.SECOND) {
            float tempBetsAmount = firstTeamBetsAmount;
            firstTeamBetsAmount = secondTeamBetsAmount;
            secondTeamBetsAmount = tempBetsAmount;
        }
        if (firstTeamBetsAmount + secondTeamBetsAmount == 0.0f) {
            return 0;
        }
        float percent = firstTeamBetsAmount * 100.0f / (firstTeamBetsAmount + secondTeamBetsAmount);
        return Math.round(percent);
    }

    @Override
    public float calculateCoefficient(Team team, float firstTeamBetsAmount, float secondTeamBetsAmount) {
        if (team == Team.SECOND) {
            float tempBetsAmount = firstTeamBetsAmount;
            firstTeamBetsAmount = secondTeamBetsAmount;
            secondTeamBetsAmount = tempBetsAmount;
        }
        if (firstTeamBetsAmount * secondTeamBetsAmount == 0) {
            return 1.0f;
        }
        return (secondTeamBetsAmount / firstTeamBetsAmount) + 1;
    }

    @Override
    public float calculateBetsAmount(Team team, List<Bet> bets) {
        float betsAmount = 0.0f;
        for (Bet bet : bets) {
            if (team == bet.getTeam()) {
                betsAmount += bet.getMoneyBet();
            }
        }
        return betsAmount;
    }
}
