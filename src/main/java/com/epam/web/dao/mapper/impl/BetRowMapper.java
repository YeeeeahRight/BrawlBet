package com.epam.web.dao.mapper.impl;

import com.epam.web.dao.mapper.RowMapper;
import com.epam.web.model.entity.Bet;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BetRowMapper implements RowMapper<Bet> {

    @Override
    public Bet map(ResultSet resultSet) throws SQLException {
        long id = Long.parseLong(resultSet.getString(Bet.ID));
        long accountId = Long.parseLong(resultSet.getString(Bet.ACCOUNT_ID));
        long matchId = Long.parseLong(resultSet.getString(Bet.MATCH_ID));
        int moneyBet = Integer.parseInt(resultSet.getString(Bet.MONEY_BET));
        int moneyReceived = Integer.parseInt(resultSet.getString(Bet.MONEY_RECEIVED));
        String team = resultSet.getString(Bet.TEAM);

        return new Bet(id, accountId, matchId, moneyBet, team, moneyReceived);
    }
}
