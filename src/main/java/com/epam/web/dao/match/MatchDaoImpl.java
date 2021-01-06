package com.epam.web.dao.match;

import com.epam.web.dao.AbstractDao;
import com.epam.web.date.DateFormatType;
import com.epam.web.date.DateFormatter;
import com.epam.web.model.entity.Match;
import com.epam.web.exceptions.DaoException;
import com.epam.web.dao.mapper.impl.MatchRowMapper;

import java.sql.Connection;
import java.util.Date;

public class MatchDaoImpl extends AbstractDao<Match> implements MatchDao {
    private static final String EDIT_QUERY =
            "UPDATE matches SET date=?, tournament=?, first_team=?, second_team=? WHERE id=?";
    private static final String ADD_QUERY =
            "INSERT matches (date, tournament, first_team, second_team, first_percent, second_percent) VALUES" +
                    "(?, ?, ?, ?, 0, 0)";

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
    public void editMatch(Match match, Long id) throws DaoException {
        String tournament = match.getTournament();
        String firstTeam = match.getFirstTeam();
        String secondTeam = match.getSecondTeam();
        Date date = match.getDate();
        String dateStr = formatDate(date);
        updateSingle(EDIT_QUERY, dateStr, tournament, firstTeam, secondTeam, id);
    }

    private String formatDate(Date date) {
        DateFormatter dateFormatter = new DateFormatter(date);
        return dateFormatter.format(DateFormatType.MATCH);
    }
}
