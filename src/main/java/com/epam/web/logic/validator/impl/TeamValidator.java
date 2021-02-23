package com.epam.web.logic.validator.impl;

import com.epam.web.logic.validator.Validator;
import com.epam.web.model.entity.Team;

public class TeamValidator implements Validator<Team> {
    private static final int MAX_NAME_LENGTH = 15;
    private static final String NOT_TAG_REGEX = "[^<>]+";

    @Override
    public boolean isValid(Team team) {
        String name = team.getName();
        if (name == null) {
            return false;
        }
        if (name.isEmpty() || name.length() > MAX_NAME_LENGTH || !name.matches(NOT_TAG_REGEX)) {
            return false;
        }
        return true;
    }
}
