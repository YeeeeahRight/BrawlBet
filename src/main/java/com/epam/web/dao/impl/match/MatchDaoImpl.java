package com.epam.web.dao.impl.match;

import com.epam.web.dao.AbstractDao;
import com.epam.web.date.DateFormatType;
import com.epam.web.date.DateFormatter;
import com.epam.web.logic.service.match.MatchType;
import com.epam.web.model.entity.Match;
import com.epam.web.exception.DaoException;
import com.epam.web.dao.mapper.impl.MatchRowMapper;
import com.epam.web.model.enumeration.MatchTeamNumber;

import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class MatchDaoImpl extends AbstractDao<Match> implements MatchDao {
    private static final String EDIT_QUERY = "UPDATE matches SET date=?, tournament=?, " +
            "first_team_id=?, second_team_id=? WHERE id=?";
    private static final String ADD_QUERY = "INSERT matches (date, tournament, first_team_id, " +
            "second_team_id) VALUES(?, ?, ?, ?)";
    private static final String GET_ACCEPTED_MATCHES_QUERY_RANGE =
            "SELECT * FROM matches WHERE commission > 0 ORDER BY is_closed DESC, date LIMIT ?,?";
    private static final String GET_UNACCEPTED_MATCHES_QUERY_RANGE =
            "SELECT * FROM matches WHERE commission = 0 ORDER BY date LIMIT ?,?";
    private static final String GET_UNCLOSED_MATCHES_QUERY_RANGE =
            "SELECT * FROM matches WHERE is_closed = 0 ORDER BY date LIMIT ?,?";
    private static final String GET_CLOSED_MATCHES_QUERY_RANGE =
            "SELECT * FROM matches WHERE is_closed = 1 ORDER BY date DESC LIMIT ?, ?";
    private static final String GET_FINISHED_MATCHES_QUERY_RANGE =
            "SELECT * FROM matches WHERE date <= ? AND commission > 0 AND is_closed = 0 " +
            "ORDER BY date LIMIT ?,?";
    private static final String ADD_COMMISSION_QUERY = "UPDATE matches SET commission=? WHERE id=?";
    private static final String ADD_FIRST_TEAM_BETS_QUERY = "UPDATE matches SET first_team_bets= " +
            "first_team_bets + ? WHERE id=?";
    private static final String ADD_SECOND_TEAM_BETS_QUERY = "UPDATE matches SET second_team_bets= " +
            "second_team_bets + ? WHERE id=?";
    private static final String CLOSE_QUERY = "UPDATE matches SET is_closed=1, winner_team=? WHERE id=?";

    public MatchDaoImpl(Connection connection) {
        super(connection, new MatchRowMapper(), Match.TABLE);
    }

    @Override
    public void save(Match match) throws DaoException {
        String tournament = match.getTournament();
        Long firstTeamId = match.getFirstTeamId();
        Long secondTeamId = match.getSecondTeamId();
        Date date = match.getDate();
        String dateStr = formatDate(date);
        Long id = match.getId();
        if (id == null) {
            updateSingle(ADD_QUERY, dateStr, tournament, firstTeamId, secondTeamId);
        } else {
            updateSingle(EDIT_QUERY, dateStr, tournament, firstTeamId, secondTeamId, id);
        }
    }

    private String formatDate(Date date) {
        DateFormatter dateFormatter = new DateFormatter(date);
        return dateFormatter.format(DateFormatType.MYSQL);
    }

    @Override
    public void close(long id, MatchTeamNumber matchTeamNumber) throws DaoException {
        updateSingle(CLOSE_QUERY, matchTeamNumber.toString(), id);
    }

    @Override
    public List<Match> getMatchesTypeRange(MatchType matchType, int offset, int amount) throws DaoException {
        switch (matchType) {
            case UNACCEPTED:
                return executeQuery(GET_UNACCEPTED_MATCHES_QUERY_RANGE, offset, amount);
            case ACCEPTED:
                return executeQuery(GET_ACCEPTED_MATCHES_QUERY_RANGE, offset, amount);
            case UNCLOSED:
                return executeQuery(GET_UNCLOSED_MATCHES_QUERY_RANGE, offset, amount);
            case FINISHED:
                String dateStr = formatDate(new Date());
                return executeQuery(GET_FINISHED_MATCHES_QUERY_RANGE, dateStr, offset, amount);
            case CLOSED:
                return executeQuery(GET_CLOSED_MATCHES_QUERY_RANGE, offset, amount);
            default:
                throw new DaoException("Unknown match type.");
        }
    }

    @Override
    public int getMatchesTypeAmount(MatchType matchType) throws DaoException {
        Optional<String> additionalCondition;
        switch (matchType) {
            case UNACCEPTED:
                additionalCondition = Optional.of("WHERE commission = 0");
                break;
            case ACCEPTED:
                additionalCondition = Optional.of("WHERE commission > 0");
                break;
            case UNCLOSED:
                additionalCondition = Optional.of("WHERE is_closed = 0");
                break;
            case FINISHED:
                String dateStr = formatDate(new Date());
                additionalCondition = Optional.of("WHERE date <= '" + dateStr +
                        "' AND commission > 0 AND is_closed = 0");
                break;
            case CLOSED:
                additionalCondition = Optional.of("WHERE is_closed = 1");
                break;
            default:
                throw new DaoException("Unknown match type.");
        }
        return getRowsAmount(additionalCondition);
    }

    public void addCommissionById(float commission, long id) throws DaoException {
        updateSingle(ADD_COMMISSION_QUERY, commission, id);
    }

    @Override
    public void addTeamBetAmount(MatchTeamNumber teamType, float betAmount, long id) throws DaoException {
        String query;
        switch (teamType) {
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
