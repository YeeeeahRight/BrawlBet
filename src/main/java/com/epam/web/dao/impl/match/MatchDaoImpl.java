package com.epam.web.dao.impl.match;

import com.epam.web.dao.AbstractDao;
import com.epam.web.date.DateFormatType;
import com.epam.web.date.DateFormatter;
import com.epam.web.model.entity.Match;
import com.epam.web.exception.DaoException;
import com.epam.web.dao.mapper.impl.MatchRowMapper;
import com.epam.web.model.enumeration.Team;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

public class MatchDaoImpl extends AbstractDao<Match> implements MatchDao {
    private static final String EDIT_QUERY =
            "UPDATE matches SET date=?, tournament=?, first_team=?, second_team=? WHERE id=?";
    private static final String ADD_QUERY =
            "INSERT matches (date, tournament, first_team, second_team) VALUES" +
                    "(?, ?, ?, ?)";
    private static final String GET_ACCEPTED_MATCHES_QUERY = "SELECT * FROM matches WHERE commission > 0";
    private static final String GET_UNACCEPTED_MATCHES_QUERY = "SELECT * FROM matches WHERE commission = 0";
    private static final String GET_UNCLOSED_MATCHES_QUERY = "SELECT * FROM matches WHERE is_closed = 0";
    private static final String GET_CLOSED_MATCHES_QUERY = "SELECT * FROM matches WHERE is_closed = 1";
    private static final String GET_FINISHED_MATCHES_QUERY = "SELECT * FROM matches WHERE date <= ? " +
            "AND commission > 0 AND is_closed = 0";
    private static final String GET_UNFINISHED_MATCHES_QUERY = "SELECT * FROM matches WHERE date > ?";
    private static final String ADD_COMMISSION_QUERY = "UPDATE matches SET commission=? WHERE id=?";
    private static final String ADD_FIRST_TEAM_BETS_QUERY = "UPDATE matches SET first_team_bets= " +
            "first_team_bets + ? WHERE id=?";
    private static final String ADD_SECOND_TEAM_BETS_QUERY = "UPDATE matches SET second_team_bets= " +
            "second_team_bets + ? WHERE id=?";
    private static final String CLOSE_QUERY = "UPDATE matches SET is_closed=1, winner=? WHERE id=?";

    public MatchDaoImpl(Connection connection) {
        super(connection, new MatchRowMapper(), Match.TABLE);
    }

    @Override
    public void save(Match match) throws DaoException {
        String tournament = match.getTournament();
        String firstTeam = match.getFirstTeam();
        String secondTeam = match.getSecondTeam();
        Date date = match.getDate();
        String dateStr = formatDate(date);
        updateSingle(ADD_QUERY, dateStr, tournament, firstTeam, secondTeam);
    }

    @Override
    public void edit(Match match, long id) throws DaoException {
        String tournament = match.getTournament();
        String firstTeam = match.getFirstTeam();
        String secondTeam = match.getSecondTeam();
        Date date = match.getDate();
        String dateStr = formatDate(date);

        updateSingle(EDIT_QUERY, dateStr, tournament, firstTeam, secondTeam, id);
    }

    private String formatDate(Date date) {
        DateFormatter dateFormatter = new DateFormatter(date);
        return dateFormatter.format(DateFormatType.MYSQL);
    }

    @Override
    public void close(long id, String winner) throws DaoException {
        updateSingle(CLOSE_QUERY, winner, id);
    }

    @Override
    public List<Match> getUnacceptedMatches() throws DaoException {
        return executeQuery(GET_UNACCEPTED_MATCHES_QUERY);
    }

    @Override
    public List<Match> getAcceptedMatches() throws DaoException {
        return executeQuery(GET_ACCEPTED_MATCHES_QUERY);
    }

    @Override
    public List<Match> getUnclosedMatches() throws DaoException {
        return executeQuery(GET_UNCLOSED_MATCHES_QUERY);
    }

    @Override
    public List<Match> getClosedMatches() throws DaoException {
        return executeQuery(GET_CLOSED_MATCHES_QUERY);
    }

    @Override
    public List<Match> getFinishedMatches() throws DaoException {
        String dateStr = formatDate(new Date());
        return executeQuery(GET_FINISHED_MATCHES_QUERY, dateStr);
    }

    @Override
    public List<Match> getUnfinishedMatches() throws DaoException {
        String dateStr = formatDate(new Date());
        return executeQuery(GET_UNFINISHED_MATCHES_QUERY, dateStr);
    }

    public void addCommission(float commission, long id) throws DaoException {
        updateSingle(ADD_COMMISSION_QUERY, commission, id);
    }

    @Override
    public void addTeamBetAmount(Team team, float betAmount, long id) throws DaoException {
        String query;
        switch (team) {
            case FIRST:
                query = ADD_FIRST_TEAM_BETS_QUERY;
                break;
            case SECOND:
                query = ADD_SECOND_TEAM_BETS_QUERY;
                break;
            default:
                throw new DaoException("Unknown team.");
        }
        updateSingle(query, betAmount, id);
    }
}
