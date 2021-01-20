package com.epam.web.dao.mapper.impl;

import com.epam.web.dao.mapper.RowMapper;
import com.epam.web.date.DateFormatType;
import com.epam.web.date.DateParser;
import com.epam.web.model.entity.Bet;
import com.epam.web.model.enumeration.Team;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

public class BetRowMapper implements RowMapper<Bet> {

    @Override
    public Bet map(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(Bet.ID);
        long accountId = resultSet.getLong(Bet.ACCOUNT_ID);
        long matchId = resultSet.getLong(Bet.MATCH_ID);
        float moneyBet = resultSet.getFloat(Bet.MONEY_BET);
        float moneyReceived = resultSet.getFloat(Bet.MONEY_RECEIVED);
        String teamStr = resultSet.getString(Bet.TEAM);
        String dateString = resultSet.getString(Bet.BET_DATE);
        Date betDate = parseDate(dateString);
        Team team = Team.valueOf(teamStr);

        return new Bet(id, accountId, matchId, moneyBet, team, moneyReceived, betDate);
    }

    private Date parseDate(String dateString) throws SQLException {
        DateParser dateParser = new DateParser(dateString);
        try {
            return dateParser.parse(DateFormatType.MYSQL);
        } catch (ParseException e) {
            throw new SQLException("Invalid date format.");
        }
    }
}
