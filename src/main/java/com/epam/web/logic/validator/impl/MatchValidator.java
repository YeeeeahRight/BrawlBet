package com.epam.web.logic.validator.impl;

import com.epam.web.logic.validator.Validator;
import com.epam.web.model.entity.Match;

import java.util.Date;

public class MatchValidator implements Validator<Match> {
    private static final int MAX_STRING_SIZE = 15;

    @Override
    public boolean isValid(Match match) {
        Date date = match.getDate();
        String tournament = match.getTournament();
        String firstTeam = match.getFirstTeam();
        String secondTeam = match.getSecondTeam();
        if (date == null || tournament == null || firstTeam == null || secondTeam == null) {
            return false;
        }
        if (tournament.isEmpty() || firstTeam.isEmpty() || secondTeam.isEmpty()) {
            return false;
        }
        if (tournament.length() > MAX_STRING_SIZE ||
                firstTeam.length() > MAX_STRING_SIZE || secondTeam.length() > MAX_STRING_SIZE) {
            return false;
        }
        return true;
    }
}
