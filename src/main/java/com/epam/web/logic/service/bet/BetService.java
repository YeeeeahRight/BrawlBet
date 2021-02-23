package com.epam.web.logic.service.bet;

import com.epam.web.exception.ServiceException;
import com.epam.web.model.entity.Bet;

import java.util.List;

/**
 * Interface with description of operations with the Bet.
 */
public interface BetService {
    /**
     * Creates bet in database or updates if bet id is exist in database.
     *
     * @param  bet  a Bet object to save or update.
     *
     * @throws  ServiceException  if bet is not passed validation and
     *                            also it's a wrapper for lower errors.
     */
    void saveBet(Bet bet) throws ServiceException;

    /**
     * Gets list of bets of match by match id.
     *
     * @param  matchId  match id for searching match bets.
     *
     * @return  a received list of bets.
     *
     * @throws  ServiceException  a wrapper for lower errors.
     */
    List<Bet> getBetsByMatchId(long matchId) throws ServiceException;

    /**
     * Gets list of bets of match by match id
     * in range described as offset and amount of bets.
     *
     * @param  accountId  an id value of match id.
     * @param  offset     an offset from first row of bets table.
     * @param  amount     an amount of bets to get.
     *
     * @return  a received list of bets.
     *
     * @throws  ServiceException  a wrapper for lower errors.
     */
    List<Bet> getBetsByAccountIdRange(long accountId, int offset, int amount) throws ServiceException;

    /**
     * Gets bets amount by account id.
     *
     * @param  accountId  an id value of account id to search.
     *
     * @return  an amount of finding bets;
     *
     * @throws  ServiceException  a wrapper for lower errors.
     */
    int getBetsAmountByAccountId(long accountId) throws ServiceException;
}
