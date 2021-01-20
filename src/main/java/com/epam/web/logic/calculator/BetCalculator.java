package com.epam.web.logic.calculator;

import com.epam.web.model.entity.Bet;
import com.epam.web.model.enumeration.Team;

import java.util.List;

public interface BetCalculator {
    float calculateCoefficient(Team team, float firstTeamBetsAmount, float secondTeamBetsAmount);
    int calculatePercent(Team team, float firstTeamBetsAmount, float secondTeamBetsAmount);
}
