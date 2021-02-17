package com.epam.web.logic.validator.impl;

import com.epam.web.logic.validator.Validator;
import com.epam.web.model.entity.Team;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class TeamValidatorTest {
    private static final Team VALID_TEAM = new Team("name", 2, 1);
    private static final Team INVALID_TEAM = new Team("", 2, 1);

    private Validator<Team> teamValidator;

    @Before
    public void initMethod() {
        teamValidator = new TeamValidator();
    }

    @Test
    public void testIsValidShouldReturnTrueWhenBetIsValid() {
        //given
        boolean isValid;
        //when
        isValid = teamValidator.isValid(VALID_TEAM);
        //then
        Assert.assertTrue(isValid);
    }

    @Test
    public void testIsValidShouldReturnTrueWhenBetIsInvalid() {
        //given
        boolean isValid;
        //when
        isValid = teamValidator.isValid(INVALID_TEAM);
        //then
        Assert.assertFalse(isValid);
    }
}
