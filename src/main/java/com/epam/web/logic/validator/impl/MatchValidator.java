package com.epam.web.logic.validator.impl;

import com.epam.web.logic.validator.Validator;
import com.epam.web.model.entity.Match;

import java.util.Date;

public class MatchValidator implements Validator<Match> {
    private static final int MAX_TOURNAMENT_SIZE = 15;

    @Override
    public boolean isValid(Match match) {
        Date date = match.getDate();
        String tournament = match.getTournament();
        Long firstTeamId = match.getFirstTeamId();
        Long secondTeamId = match.getSecondTeamId();
        if (date == null || tournament == null || firstTeamId == null || secondTeamId == null) {
            return false;
        }
        if (tournament.isEmpty() || tournament.length() > MAX_TOURNAMENT_SIZE) {
            return false;
        }
        return true;
    }
}
