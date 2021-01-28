package com.epam.web.command.impl.user;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.constant.Attribute;
import com.epam.web.constant.Parameter;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.exception.ServiceException;
import com.epam.web.logic.service.account.AccountService;
import com.epam.web.logic.service.bet.BetService;
import com.epam.web.logic.service.match.MatchService;
import com.epam.web.model.entity.Bet;
import com.epam.web.model.entity.Match;
import com.epam.web.model.enumeration.MatchTeamNumber;

import java.util.Date;

public class BetCommand implements Command {
    private final BetService betService;
    private final AccountService accountService;
    private final MatchService matchService;

    public BetCommand(BetService betService, AccountService accountService,
                      MatchService matchService) {
        this.betService = betService;
        this.accountService = accountService;
        this.matchService = matchService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException, InvalidParametersException {
        String matchIdStr = requestContext.getRequestParameter(Parameter.ID);
        String teamBetName = requestContext.getRequestParameter(Parameter.BET_ON);
        String moneyStr = requestContext.getRequestParameter(Parameter.MONEY);
        long matchId;
        float money;
        MatchTeamNumber teamBet;
        try {
            matchId = Long.parseLong(matchIdStr);
            teamBet = MatchTeamNumber.valueOf(teamBetName);
            money = Float.parseFloat(moneyStr);
        } catch (IllegalArgumentException e) {
            throw new InvalidParametersException("Invalid parameters in request.");
        }
        Long accountId = (Long) requestContext.getSessionAttribute(Attribute.ACCOUNT_ID);
        if (accountService.getBalance(accountId) < money) {
            throw new InvalidParametersException("You have no money for your bet.");
        }
        long teamId = getTeamIdByMatchId(teamBet, matchId);
        Date betDate = new Date();
        Bet bet = new Bet(accountId, matchId, money, teamId, betDate);
        betService.saveBet(bet);

        String prevPage = requestContext.getHeader();
        return CommandResult.redirect(prevPage);
    }

    private long getTeamIdByMatchId(MatchTeamNumber matchTeamNumber, long matchId)
            throws ServiceException, InvalidParametersException {
        Match match = matchService.getMatchById(matchId);
        switch (matchTeamNumber) {
            case FIRST:
                return match.getFirstTeamId();
            case SECOND:
                return match.getSecondTeamId();
            default:
                throw new InvalidParametersException("Unknown team on bet");
        }
    }
}
