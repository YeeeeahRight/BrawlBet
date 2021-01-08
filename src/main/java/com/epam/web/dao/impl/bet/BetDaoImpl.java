package com.epam.web.dao.impl.bet;

import com.epam.web.dao.AbstractDao;
import com.epam.web.dao.mapper.impl.BetRowMapper;
import com.epam.web.exceptions.DaoException;
import com.epam.web.model.entity.Bet;

import java.sql.Connection;
import java.util.List;

public class BetDaoImpl extends AbstractDao<Bet> implements BetDao{
    private static final String GET_BETS_BY_MATCH_QUERY = "SELECT * from bets WHERE match_id=?";
    private static final String INSERT_QUERY = "INSERT bets(account_id, match_id, money_bet, team) VALUES(?, ?, ?, ?)";

    public BetDaoImpl(Connection connection) {
        super(connection, new BetRowMapper(), Bet.TABLE);
    }

    @Override
    public void save(Bet item) throws DaoException {
        long accountId = item.getAccountId();
        long matchId = item.getMatchId();
        int money = item.getMoneyBet();
        String team = item.getTeam();
        updateSingle(INSERT_QUERY, accountId, matchId, money, team);
    }

    @Override
    public List<Bet> getBetsByMatchId(long id) throws DaoException {
        return executeQuery(GET_BETS_BY_MATCH_QUERY, id);
    }
}
