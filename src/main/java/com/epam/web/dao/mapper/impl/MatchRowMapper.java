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
        Long id = Long.valueOf(resultSet.getString(Match.ID));
        int firstPercent = Integer.parseInt(resultSet.getString(Match.FIRST_PERCENT));
        int secondPercent = Integer.parseInt(resultSet.getString(Match.SECOND_PERCENT));
        float firstCoefficient = Float.parseFloat(resultSet.getString(Match.FIRST_COEFFICIENT));
        float secondCoefficient = Float.parseFloat(resultSet.getString(Match.SECOND_COEFFICIENT));
        float commission = Float.parseFloat(resultSet.getString(Match.COMMISSION));
        String dateString = resultSet.getString(Match.DATE);
        DateParser dateParser = new DateParser(dateString);
        Date date;
        try {
            date = dateParser.parse(DateFormatType.MYSQL);
        } catch (ParseException e) {
            throw new SQLException("Invalid date format.");
        }

        Match match = new Match(date, tournament, firstTeam, secondTeam, id);
        match.setFirstPercent(firstPercent);
        match.setSecondPercent(secondPercent);
        match.setFirstCoefficient(firstCoefficient);
        match.setSecondCoefficient(secondCoefficient);
        match.setCommission(commission);

        return match;
    }
}
