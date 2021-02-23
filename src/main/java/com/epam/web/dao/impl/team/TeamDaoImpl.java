package com.epam.web.dao.impl.team;

import com.epam.web.dao.AbstractDao;
import com.epam.web.dao.mapper.impl.TeamRowMapper;
import com.epam.web.exception.DaoException;
import com.epam.web.model.entity.Account;
import com.epam.web.model.entity.Team;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class TeamDaoImpl extends AbstractDao<Team> implements TeamDao {
    private static final String ADD_QUERY = "INSERT INTO teams(name) VALUES (?)";
    private static final String FIND_BY_NAME = "SELECT * FROM teams WHERE name=?";
    private static final String INCREMENT_MATCHES_WIN =
            "UPDATE teams SET matches_won = matches_won + 1 WHERE id=?";
    private static final String INCREMENT_MATCHES_LOST =
            "UPDATE teams SET matches_lost = matches_lost + 1 WHERE id=?";
    private static final String GET_TEAMS_QUERY_RANGE =
            "SELECT * FROM teams LIMIT ?,?";

    public TeamDaoImpl(Connection connection) {
        super(connection, new TeamRowMapper(), Team.TABLE);
    }

    @Override
    public void save(Team team) throws DaoException {
        updateSingle(ADD_QUERY, team.getName());
    }

    @Override
    public Optional<Team> findTeamByName(String name) throws DaoException {
        return executeForSingleResult(FIND_BY_NAME, name);
    }

    @Override
    public void incrementMatchesWonById(long id) throws DaoException {
        updateSingle(INCREMENT_MATCHES_WIN, id);
    }

    @Override
    public void incrementMatchesLostById(long id) throws DaoException {
        updateSingle(INCREMENT_MATCHES_LOST, id);
    }

    @Override
    public List<Team> getTeamsRange(int offset, int amount) throws DaoException {
        return executeQuery(GET_TEAMS_QUERY_RANGE, offset, amount);
    }
}
