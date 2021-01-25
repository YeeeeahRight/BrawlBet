package com.epam.web.dao.impl.bet;

import com.epam.web.dao.Dao;
import com.epam.web.exception.DaoException;
import com.epam.web.model.entity.Bet;

import java.util.List;

/**
 * Interface with description of operations with the Bet data.
 */
public interface BetDao extends Dao<Bet> {
    /**
     * Gets list of bets of match by match id.
     *
     * @param  id  match id for searching match bets.
     *
     * @return  a received list of bets.
     *
     * @throws  DaoException  if database errors occurs.
     */
    List<Bet> getBetsByMatchId(long id) throws DaoException;

    /**
     * Gets list of bets of match by match id
     * in range described as offset and amount of bets.
     *
     * @param  id      an id value of match id.
     * @param  offset  an offset from first row of bets table.
     * @param  amount  an amount of bets to get.
     *
     * @return  a received list of bets.
     *
     * @throws  DaoException  if database errors occurs.
     */
    List<Bet> getBetsByAccountIdRange(long id, int offset, int amount) throws DaoException;

    /**
     * Closes bet by bet id using account money received.
     *
     * @param  moneyReceived  a money value of money received in bet.
     * @param  id             an id value of bet to close.
     *
     * @throws  DaoException  if database errors occurs.
     */
    void close(float moneyReceived, long id) throws DaoException;

    /**
     * Gets bets amount by account id.
     *
     * @param  accountId  an id value of account id to search.
     *
     * @return  an amount of finding bets;
     *
     * @throws  DaoException  if database errors occurs.
     */
    int getBetsAmountByAccountId(long accountId) throws DaoException;
}
