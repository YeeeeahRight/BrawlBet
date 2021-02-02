package com.epam.web.dao.mapper.impl;

import com.epam.web.date.DateFormatType;
import com.epam.web.date.DateParser;
import com.epam.web.model.entity.Match;
import com.epam.web.dao.mapper.RowMapper;
import com.epam.web.model.enumeration.MatchTeamNumber;

import java.math.BigDecimal;
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
        Long id = resultSet.getLong(Match.ID);
        Float commission = resultSet.getFloat(Match.COMMISSION);
        Boolean isClosed = resultSet.getBoolean(Match.IS_CLOSED);
        BigDecimal firstTeamBets = resultSet.getBigDecimal(Match.FIRST_TEAM_BETS);
        BigDecimal secondTeamBets = resultSet.getBigDecimal(Match.SECOND_TEAM_BETS);
        String dateString = resultSet.getString(Match.DATE);
        Date date = parseDate(dateString);
        String winnerTeamStr = resultSet.getString(Match.WINNER_TEAM);
        MatchTeamNumber winnerTeam = MatchTeamNumber.valueOf(winnerTeamStr);

        return new Match(id, date, tournament, firstTeamId, secondTeamId, winnerTeam, commission,
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
