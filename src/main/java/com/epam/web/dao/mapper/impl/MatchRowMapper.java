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
        String winner = resultSet.getString(Match.WINNER);
        boolean isClosed = resultSet.getBoolean(Match.IS_CLOSED);
        float firstTeamBets = resultSet.getFloat(Match.FIRST_TEAM_BETS);
        float secondTeamBets = resultSet.getFloat(Match.SECOND_TEAM_BETS);
        String dateString = resultSet.getString(Match.DATE);
        Date date = parseDate(dateString);
        return new Match(id, date, tournament, firstTeam, secondTeam, winner, commission,
                isClosed, firstTeamBets, secondTeamBets);
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
