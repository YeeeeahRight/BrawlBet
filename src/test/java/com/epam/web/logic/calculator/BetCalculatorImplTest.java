package com.epam.web.logic.calculator;

import com.epam.web.model.enumeration.MatchTeamNumber;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class BetCalculatorImplTest {
    private static final float FIRST_TEAM_BETS_AMOUNT = 10f;
    private static final BigDecimal FIRST_TEAM_BETS_DECIMAL = new BigDecimal(FIRST_TEAM_BETS_AMOUNT);
    private static final float SECOND_TEAM_BETS_AMOUNT = 20f;
    private static final BigDecimal SECOND_TEAM_BETS_DECIMAL = new BigDecimal(SECOND_TEAM_BETS_AMOUNT);
    private static final float ZERO_BETS_AMOUNT = 0f;
    private static final int FIRST_TEAM_PERCENT = 33;
    private static final int SECOND_TEAM_PERCENT = 67;
    private static final BigDecimal FIRST_TEAM_COEFFICIENT = new BigDecimal(2);
    private static final BigDecimal SECOND_TEAM_COEFFICIENT = new BigDecimal("0.5");
    private static final int ZERO_PERCENT = 0;
    private BetCalculator betCalculator;


    @Before
    public void initMethod() {
        betCalculator = new BetCalculatorImpl();
    }

    @Test
    public void testCalculatePercentShouldCalculateWhenTeamIsFirst() {
        //given
        int percent;
        //when
        percent = betCalculator.calculatePercent(MatchTeamNumber.FIRST,
                FIRST_TEAM_BETS_AMOUNT, SECOND_TEAM_BETS_AMOUNT);
        //then
        Assert.assertEquals(FIRST_TEAM_PERCENT, percent);
    }

    @Test
    public void testCalculatePercentShouldCalculateWhenTeamIsSecond() {
        //given
        int percent;
        //when
        percent = betCalculator.calculatePercent(MatchTeamNumber.SECOND,
                FIRST_TEAM_BETS_AMOUNT, SECOND_TEAM_BETS_AMOUNT);
        //then
        Assert.assertEquals(SECOND_TEAM_PERCENT, percent);
    }

    @Test
    public void testCalculateShouldReturnZeroWhenSumBetsAreZero() {
        //given
        int percent;
        //when
        percent = betCalculator.calculatePercent(MatchTeamNumber.SECOND,
                ZERO_BETS_AMOUNT, ZERO_BETS_AMOUNT);
        //then
        Assert.assertEquals(ZERO_PERCENT, percent);
    }

    @Test
    public void testCalculateCoefficientShouldCalculateWhenTeamIsFirst() {
        //given
        BigDecimal coefficient;
        //when
        coefficient = betCalculator.calculateCoefficient(MatchTeamNumber.FIRST,
                FIRST_TEAM_BETS_DECIMAL, SECOND_TEAM_BETS_DECIMAL);
        //then
        boolean isEquals = FIRST_TEAM_COEFFICIENT.compareTo(coefficient) == 0;
        Assert.assertTrue(isEquals);
    }

    @Test
    public void testCalculateCoefficientShouldCalculateWhenTeamIsSecond() {
        //given
        BigDecimal coefficient;
        //when
        coefficient = betCalculator.calculateCoefficient(MatchTeamNumber.SECOND,
                FIRST_TEAM_BETS_DECIMAL, SECOND_TEAM_BETS_DECIMAL);
        //then
        boolean isEquals = SECOND_TEAM_COEFFICIENT.compareTo(coefficient) == 0;
        Assert.assertTrue(isEquals);
    }

    @Test
    public void testCalculateCoefficientShouldReturnZeroWhenSumBetsAreZero() {
        //given
        BigDecimal coefficient;
        //when
        coefficient = betCalculator.calculateCoefficient(MatchTeamNumber.SECOND,
                BigDecimal.ZERO, BigDecimal.ZERO);
        //then
        boolean isEquals = BigDecimal.ZERO.compareTo(coefficient) == 0;
        Assert.assertTrue(isEquals);
    }
}
