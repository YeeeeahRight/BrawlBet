package com.epam.web.logic.validator.impl;

import com.epam.web.logic.validator.Validator;
import com.epam.web.model.entity.Bet;

import java.util.Date;

public class BetValidator implements Validator<Bet> {
    private static final int MIN_ID_VALUE = 1;
    private static final float MIN_BET_VALUE = 0.1f;

    @Override
    public boolean isValid(Bet bet) {
        Long accountId = bet.getAccountId();
        Long matchId = bet.getMatchId();
        Float moneyBet = bet.getMoneyBet();
        Long teamId = bet.getTeamId();
        Date betDate = bet.getBetDate();
        if (accountId == null || matchId == null || moneyBet == null || teamId == null || betDate == null) {
            return false;
        }
        if (accountId < MIN_ID_VALUE || matchId < MIN_ID_VALUE || teamId < MIN_ID_VALUE) {
            return false;
        }
        if (moneyBet < MIN_BET_VALUE) {
            return false;
        }
        return true;
    }
}
