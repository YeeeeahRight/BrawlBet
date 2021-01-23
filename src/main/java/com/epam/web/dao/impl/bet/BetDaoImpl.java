package com.epam.web.dao.impl.bet;

import com.epam.web.dao.AbstractDao;
import com.epam.web.dao.mapper.impl.BetRowMapper;
import com.epam.web.date.DateFormatType;
import com.epam.web.date.DateFormatter;
import com.epam.web.exception.DaoException;
import com.epam.web.model.entity.Bet;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

public class BetDaoImpl extends AbstractDao<Bet> implements BetDao{
    private static final String GET_BETS_BY_MATCH_QUERY = "SELECT * from bets WHERE match_id=?";
    private static final String GET_BETS_BY_ACCOUNT_QUERY_RANGE =
            "SELECT * from bets WHERE account_id=? ORDER BY bet_date DESC LIMIT ?,?";
    private static final String INSERT_QUERY = "INSERT bets(account_id, match_id, money_bet, team, bet_date) " +
            "VALUES(?, ?, ?, ?, ?)";
    private static final String CLOSE_QUERY = "UPDATE bets SET money_received=? WHERE id=?";

    public BetDaoImpl(Connection connection) {
        super(connection, new BetRowMapper(), Bet.TABLE);
    }

    @Override
    public void save(Bet item) throws DaoException {
        long accountId = item.getAccountId();
        long matchId = item.getMatchId();
        float money = item.getMoneyBet();
        String team = item.getTeam().toString();
        String betDate = formatDate(item.getBetDate());
        updateSingle(INSERT_QUERY, accountId, matchId, money, team, betDate);
    }

    private String formatDate(Date date) {
        DateFormatter dateFormatter = new DateFormatter(date);
        return dateFormatter.format(DateFormatType.MYSQL);
    }

    @Override
    public List<Bet> getBetsByMatchId(long id) throws DaoException {
        return executeQuery(GET_BETS_BY_MATCH_QUERY, id);
    }

    @Override
    public List<Bet> getBetsByAccountIdRange(long id, int beginIndex, int endIndex) throws DaoException {
        return executeQuery(GET_BETS_BY_ACCOUNT_QUERY_RANGE, id, beginIndex, endIndex);
    }

    @Override
    public void close(float moneyReceived, long id) throws DaoException {
        updateSingle(CLOSE_QUERY, moneyReceived, id);
    }
}
