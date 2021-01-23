package com.epam.web.logic.service.bet;

import com.epam.web.exception.ServiceException;
import com.epam.web.model.entity.Bet;

import java.util.List;

public interface BetService {
    List<Bet> getAll() throws ServiceException;

    void createBet(Bet bet) throws ServiceException;

    List<Bet> getBetsByMatchId(long matchId) throws ServiceException;

    List<Bet> getBetsByAccountIdRange(long accountId, int beginIndex, int endIndex) throws ServiceException;
}
