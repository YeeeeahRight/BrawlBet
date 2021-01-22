package com.epam.web.logic.calculator;

import com.epam.web.model.enumeration.Team;

public interface BetCalculator {
    float calculateCoefficient(Team team, float firstTeamBetsAmount, float secondTeamBetsAmount);

    int calculatePercent(Team team, float firstTeamBetsAmount, float secondTeamBetsAmount);
}
