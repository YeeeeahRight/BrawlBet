package com.epam.web.command.impl;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.constant.Attribute;
import com.epam.web.constant.Parameter;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.exceptions.InvalidInputException;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.model.entity.Bet;
import com.epam.web.service.BetService;
import com.epam.web.service.MatchService;


public class BetCommand implements Command {
    private static final String FIRST_TEAM = "FIRST";
    private static final String SECOND_TEAM = "SECOND";

    private final MatchService matchService;
    private final BetService betService;

    public BetCommand(MatchService matchService, BetService betService) {
        this.matchService = matchService;
        this.betService = betService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {
        String matchIdStr = requestContext.getRequestParameter(Parameter.ID);
        long matchId = Long.parseLong(matchIdStr);
        if (matchService.isFinished(matchId)) {
            throw new InvalidInputException("This match is already finished.");
        }
        String moneyStr = requestContext.getRequestParameter(Parameter.MONEY);
        int money;
        try {
            money = Integer.parseInt(moneyStr);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Your bet is not a number.");
        }
        if (money <= 0) {
            throw new InvalidInputException("Your bet value is not a positive value.");
        }
        long accountId = (Long) requestContext.getSessionAttribute(Attribute.ACCOUNT_ID);
        if (betService.getUserBalance(accountId) < money) {
            throw new InvalidInputException("You have no money for your bet.");
        }
        String team = requestContext.getRequestParameter(Parameter.BET_ON);
        if (team == null || team.isEmpty() ||
                !(team.equals(FIRST_TEAM) || team.equals(SECOND_TEAM))) {
            throw new InvalidInputException("Invalid team on bet.");
        }
        Bet bet = new Bet(accountId, matchId, money, team, 0);
        betService.bet(bet);
        int balance = (Integer) requestContext.getSessionAttribute(Attribute.USER_BALANCE);
        requestContext.addSession(Attribute.USER_BALANCE, balance - bet.getMoneyBet());

        String prevPage = requestContext.getHeader();
        return CommandResult.redirect(prevPage);
    }
}
