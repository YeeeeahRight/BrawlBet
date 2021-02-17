package com.epam.web.logic.validator.impl;

import com.epam.web.logic.validator.Validator;
import com.epam.web.model.entity.Bet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

public class BetValidatorTest {
    private static final Bet VALID_BET = new Bet(1L, 2L, BigDecimal.TEN, 3L, new Date());
    private static final Bet INVALID_BET =  new Bet(-1L, 2L, BigDecimal.TEN, 3L, new Date());

    private Validator<Bet> betValidator;

    @Before
    public void initMethod() {
        betValidator = new BetValidator();
    }

    @Test
    public void testIsValidShouldReturnTrueWhenBetIsValid() {
        //given
        boolean isValid;
        //when
        isValid = betValidator.isValid(VALID_BET);
        //then
        Assert.assertTrue(isValid);
    }

    @Test
    public void testIsValidShouldReturnTrueWhenBetIsInvalid() {
        //given
        boolean isValid;
        //when
        isValid = betValidator.isValid(INVALID_BET);
        //then
        Assert.assertFalse(isValid);
    }
}
