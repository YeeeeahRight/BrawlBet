package com.epam.web.logic.validator.impl;

import com.epam.web.logic.validator.Validator;
import com.epam.web.model.entity.Match;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class MatchValidatorTest {
    private static final Match VALID_MATCH = new Match(new Date(), "tournament", 2L, 1L);
    private static final Match INVALID_MATCH = new Match(new Date(), "tournament", 1L, 1L);

    private Validator<Match> matchValidator;

    @Before
    public void initMethod() {
        matchValidator = new MatchValidator();
    }

    @Test
    public void testIsValidShouldReturnTrueWhenBetIsValid() {
        //given
        boolean isValid;
        //when
        isValid = matchValidator.isValid(VALID_MATCH);
        //then
        Assert.assertTrue(isValid);
    }

    @Test
    public void testIsValidShouldReturnTrueWhenBetIsInvalid() {
        //given
        boolean isValid;
        //when
        isValid = matchValidator.isValid(INVALID_MATCH);
        //then
        Assert.assertFalse(isValid);
    }
}
