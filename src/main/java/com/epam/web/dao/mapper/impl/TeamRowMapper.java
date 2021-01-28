package com.epam.web.dao.mapper.impl;

import com.epam.web.dao.mapper.RowMapper;
import com.epam.web.model.entity.Team;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TeamRowMapper implements RowMapper<Team> {

    @Override
    public Team map(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(Team.ID);
        String name = resultSet.getString(Team.NAME);
        Integer matchesWon = resultSet.getInt(Team.MATCHES_WON);
        Integer matchesLost = resultSet.getInt(Team.MATCHES_LOST);

        return new Team(id, name, matchesWon, matchesLost);
    }
}
