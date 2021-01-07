package com.epam.web.dao.match;

import com.epam.web.dao.AbstractDao;
import com.epam.web.date.DateFormatType;
import com.epam.web.date.DateFormatter;
import com.epam.web.model.entity.Match;
import com.epam.web.exceptions.DaoException;
import com.epam.web.dao.mapper.impl.MatchRowMapper;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

public class MatchDaoImpl extends AbstractDao<Match> implements MatchDao {
    private static final String EDIT_QUERY =
            "UPDATE matches SET date=?, tournament=?, first_team=?, second_team=?, commission=?," +
                    "first_percent=?, second_percent=?, first_coefficient=?, second_coefficient=? WHERE id=?";
    private static final String ADD_QUERY =
            "INSERT matches (date, tournament, first_team, second_team, first_percent, second_percent) VALUES" +
                    "(?, ?, ?, ?, 0, 0)";
    private static final String GET_ACTIVE_MATCHES_QUERY = "SELECT * FROM matches WHERE commission > 0";
    private static final String GET_UNACCEPTED_MATCHES_QUERY = "SELECT * FROM matches WHERE commission = 0";
    private static final String ADD_COMMISSION_QUERY = "UPDATE matches SET commission=? WHERE id=?";

    public MatchDaoImpl(Connection connection) {
        super(connection, new MatchRowMapper(), Match.TABLE);
    }

    @Override
    public void save(Match item) throws DaoException {
        String tournament = item.getTournament();
        String firstTeam = item.getFirstTeam();
        String secondTeam = item.getSecondTeam();
        Date date = item.getDate();
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
        float commission = match.getCommission();
        int firstPercent = match.getFirstPercent();
        int secondPercent = match.getFirstPercent();
        float firstCoefficient = match.getFirstCoefficient();
        float secondCoefficient = match.getSecondCoefficient();

        updateSingle(EDIT_QUERY, dateStr, tournament, firstTeam, secondTeam, commission,
                firstPercent, secondPercent, firstCoefficient, secondCoefficient, id);
    }


    private String formatDate(Date date) {
        DateFormatter dateFormatter = new DateFormatter(date);
        return dateFormatter.format(DateFormatType.MATCH);
    }

    @Override
    public List<Match> getUnacceptedMatches() throws DaoException {
        return executeQuery(GET_UNACCEPTED_MATCHES_QUERY);
    }

    @Override
    public List<Match> getActiveMatches() throws DaoException {
        return executeQuery(GET_ACTIVE_MATCHES_QUERY);
    }

    public void addCommission(float commission, long id) throws DaoException {
        updateSingle(ADD_COMMISSION_QUERY, commission, id);
    }
}
