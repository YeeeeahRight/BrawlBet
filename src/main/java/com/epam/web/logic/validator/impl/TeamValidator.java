package com.epam.web.logic.validator.impl;

import com.epam.web.logic.validator.Validator;
import com.epam.web.model.entity.Team;

public class TeamValidator implements Validator<Team> {
    private static final int MAX_NAME_LENGTH = 15;

    @Override
    public boolean isValid(Team team) {
        Long id = team.getId();
        String name = team.getName();
        if (id == null || name == null) {
            return false;
        }
        if (name.isEmpty() || name.length() > MAX_NAME_LENGTH) {
            return false;
        }
        return true;
    }
}
