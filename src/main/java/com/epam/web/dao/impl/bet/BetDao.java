package com.epam.web.dao.impl.bet;

import com.epam.web.dao.Dao;
import com.epam.web.exception.DaoException;
import com.epam.web.model.entity.Bet;

import java.math.BigDecimal;
import java.util.List;

public interface BetDao extends Dao<Bet> {
    List<Bet> getBetsByMatchId(long id) throws DaoException;

    void close(float moneyReceived, long id) throws DaoException;
}
