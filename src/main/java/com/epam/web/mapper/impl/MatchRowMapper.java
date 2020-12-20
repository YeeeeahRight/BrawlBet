package com.epam.web.mapper.impl;

import com.epam.web.entity.Match;
import com.epam.web.mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MatchRowMapper implements RowMapper<Match> {
    @Override
    public Match map(ResultSet resultSet) throws SQLException {
        String date = resultSet.getString(Match.DATE);
        String tournament = resultSet.getString(Match.TOURNAMENT);
        String firstTeam = resultSet.getString(Match.FIRST_TEAM);
        String secondTeam = resultSet.getString(Match.SECOND_TEAM);
        Long id = Long.valueOf(resultSet.getString(Match.ID));
        int firstPercent = Integer.parseInt(resultSet.getString(Match.FIRST_PERCENT));
        int secondPercent = Integer.parseInt(resultSet.getString(Match.SECOND_PERCENT));
        Match match = new Match(date, tournament, firstTeam, secondTeam, id);
        match.setFirstPercent(firstPercent);
        match.setSecondPercent(secondPercent);

        return match;
    }
}
