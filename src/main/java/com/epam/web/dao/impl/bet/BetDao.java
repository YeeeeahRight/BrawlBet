package com.epam.web.dao.impl.bet;

import com.epam.web.dao.Dao;
import com.epam.web.exception.DaoException;
import com.epam.web.model.entity.Bet;

import java.util.List;

public interface BetDao extends Dao<Bet> {
    List<Bet> getBetsByMatchId(long id) throws DaoException;

    void close(int moneyReceived, long id) throws DaoException;
}
