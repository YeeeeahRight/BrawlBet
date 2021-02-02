package com.epam.web.dao.mapper.impl;

import com.epam.web.dao.mapper.RowMapper;
import com.epam.web.date.DateFormatType;
import com.epam.web.date.DateParser;
import com.epam.web.model.entity.Bet;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

public class BetRowMapper implements RowMapper<Bet> {

    @Override
    public Bet map(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(Bet.ID);
        Long accountId = resultSet.getLong(Bet.ACCOUNT_ID);
        Long matchId = resultSet.getLong(Bet.MATCH_ID);
        Long teamId = resultSet.getLong(Bet.TEAM_ID);
        BigDecimal moneyBet = resultSet.getBigDecimal(Bet.MONEY_BET);
        BigDecimal moneyReceived = resultSet.getBigDecimal(Bet.MONEY_RECEIVED);
        String dateString = resultSet.getString(Bet.BET_DATE);
        Date betDate = parseDate(dateString);

        return new Bet(id, accountId, matchId, moneyBet, teamId, moneyReceived, betDate);
    }

    private Date parseDate(String dateString) throws SQLException {
        try {
            return DateParser.parse(dateString, DateFormatType.MYSQL);
        } catch (ParseException e) {
            throw new SQLException("Invalid date format.");
        }
    }
}
