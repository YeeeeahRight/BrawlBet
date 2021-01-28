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
        Long firstTeamId = resultSet.getLong(Match.FIRST_TEAM_ID);
        Long secondTeamId = resultSet.getLong(Match.SECOND_TEAM_ID);
        Long winnerTeamId = resultSet.getLong(Match.WINNER_TEAM_ID);
        Long id = resultSet.getLong(Match.ID);
        Float commission = resultSet.getFloat(Match.COMMISSION);
        Boolean isClosed = resultSet.getBoolean(Match.IS_CLOSED);
        Float firstTeamBets = resultSet.getFloat(Match.FIRST_TEAM_BETS);
        Float secondTeamBets = resultSet.getFloat(Match.SECOND_TEAM_BETS);
        String dateString = resultSet.getString(Match.DATE);
        Date date = parseDate(dateString);

        return new Match(id, date, tournament, firstTeamId, secondTeamId, winnerTeamId, commission,
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
