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
import com.epam.web.model.entity.Bet;

import java.util.Date;

public class BetCommand implements Command {
    private final BetService betService;
    private final AccountService accountService;

    public BetCommand(BetService betService, AccountService accountService) {
        this.betService = betService;
        this.accountService = accountService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException, InvalidParametersException {
        String matchIdStr = requestContext.getRequestParameter(Parameter.ID);
        String teamIdStr = requestContext.getRequestParameter(Parameter.BET_ON);
        String moneyStr = requestContext.getRequestParameter(Parameter.MONEY);
        long matchId;
        long teamId;
        float money;
        try {
            matchId = Long.parseLong(matchIdStr);
            teamId = Long.parseLong(teamIdStr);
            money = Float.parseFloat(moneyStr);
        } catch (NumberFormatException e) {
            throw new InvalidParametersException("Invalid parameters in request.");
        }
        Long accountId = (Long) requestContext.getSessionAttribute(Attribute.ACCOUNT_ID);
        if (accountService.getBalance(accountId) < money) {
            throw new ServiceException("You have no money for your bet.");
        }
        Date betDate = new Date();
        Bet bet = new Bet(accountId, matchId, money, teamId, betDate);
        betService.saveBet(bet);

        String prevPage = requestContext.getHeader();
        return CommandResult.redirect(prevPage);
    }
}
