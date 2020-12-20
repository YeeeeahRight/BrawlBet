package com.epam.web.dao.match;

import com.epam.web.dao.AbstractDao;
import com.epam.web.entity.Match;
import com.epam.web.exceptions.DaoException;
import com.epam.web.mapper.impl.MatchRowMapper;

import java.sql.Connection;

public class MatchDaoImpl extends AbstractDao<Match> implements MatchDao {
    private static final String EDIT_QUERY_FORMAT =
            "UPDATE matches SET date ='%s', tournament='%s', firstTeam ='%s', secondTeam='%s' WHERE id=%d";
    private static final String ADD_QUERY_FORMAT =
            "INSERT matches (date, tournament, firstTeam, secondTeam, firstPercent, secondPercent) VALUES" +
                    "('%s', '%s', '%s', '%s', 0, 0)";

    public MatchDaoImpl(Connection connection) {
        super(connection, new MatchRowMapper(), Match.TABLE);
    }

    @Override
    public void save(Match item) throws DaoException {
        String saveQuery = String.format(ADD_QUERY_FORMAT, item.getDate(), item.getTournament(),
                item.getFirstTeam(), item.getSecondTeam());
        executeUpdate(saveQuery);
    }

    @Override
    public void editMatch(Match newMatch, Long id) throws DaoException {
        String updateQuery = String.format(EDIT_QUERY_FORMAT, newMatch.getDate(), newMatch.getTournament(),
                newMatch.getFirstTeam(), newMatch.getSecondTeam(), id);
        executeUpdate(updateQuery);
    }
}
