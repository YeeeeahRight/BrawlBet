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
import com.epam.web.model.enumeration.Team;

import java.util.Date;

public class BetCommand implements Command {
    private final MatchService matchService;
    private final BetService betService;
    private final AccountService accountService;

    public BetCommand(MatchService matchService, BetService betService, AccountService accountService) {
        this.matchService = matchService;
        this.betService = betService;
        this.accountService = accountService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException, InvalidParametersException {
        String matchIdStr = requestContext.getRequestParameter(Parameter.ID);
        String moneyStr = requestContext.getRequestParameter(Parameter.MONEY);
        long matchId;
        float money;
        Team team;
        try {
            matchId = Long.parseLong(matchIdStr);
            if (matchService.isFinishedMatch(matchId)) {
                throw new ServiceException("This match is already finished.");
            }
            money = Float.parseFloat(moneyStr);
            String teamStr = requestContext.getRequestParameter(Parameter.BET_ON);
            try {
                team = Team.valueOf(teamStr);
            } catch (IllegalArgumentException e) {
                throw new InvalidParametersException("Invalid team on bet.");
            }
        } catch (NumberFormatException e) {
            throw new InvalidParametersException("Invalid parameters in request.");
        }
        Long accountId = (Long) requestContext.getSessionAttribute(Attribute.ACCOUNT_ID);
        if (accountService.getBalance(accountId) < money) {
            throw new ServiceException("You have no money for your bet.");
        }
        Date betDate = new Date();
        Bet bet = new Bet(accountId, matchId, money, team, betDate);
        betService.createBet(bet);

        String prevPage = requestContext.getHeader();
        return CommandResult.redirect(prevPage);
    }
}
