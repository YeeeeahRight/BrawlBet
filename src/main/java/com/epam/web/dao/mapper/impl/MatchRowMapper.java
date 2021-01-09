package com.epam.web.dao.mapper.impl;

import com.epam.web.date.DateFormatType;
import com.epam.web.date.DateParser;
import com.epam.web.model.entity.Match;
import com.epam.web.dao.mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

public class MatchRowMapper implements RowMapper<Match> {

    @Override
    public Match map(ResultSet resultSet) throws SQLException {
        String tournament = resultSet.getString(Match.TOURNAMENT);
        String firstTeam = resultSet.getString(Match.FIRST_TEAM);
        String secondTeam = resultSet.getString(Match.SECOND_TEAM);
        long id = resultSet.getLong(Match.ID);
        float commission = resultSet.getFloat(Match.COMMISSION);
        String dateString = resultSet.getString(Match.DATE);
        String winner = resultSet.getString(Match.WINNER);
        boolean isClosed = resultSet.getBoolean(Match.IS_CLOSED);
        DateParser dateParser = new DateParser(dateString);
        Date date;
        try {
            date = dateParser.parse(DateFormatType.MYSQL);
        } catch (ParseException e) {
            throw new SQLException("Invalid date format.");
        }

        return new Match(id, date, tournament, firstTeam, secondTeam, winner, commission, isClosed);
    }
}
