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

public class BetCommand implements Command {
    private final BetService betService;

    public BetCommand(BetService betService) {
        this.betService = betService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {
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
        String matchIdStr = requestContext.getRequestParameter(Parameter.ID);
        long accountId = (Long) requestContext.getSessionAttribute(Attribute.ACCOUNT_ID);
        if (betService.getUserBalance(accountId) >= money) {
            long matchId = Long.parseLong(matchIdStr);
            String team = requestContext.getRequestParameter(Parameter.BET_ON);
            if (team == null || team.isEmpty() || !(team.equals("SECOND") || team.equals("FIRST"))) {
                System.out.println(team);
                throw new InvalidInputException("Invalid team on bet.");
            }
            Bet bet = new Bet(accountId, matchId, money, team, 0);
            betService.bet(bet);

            int balance = (Integer) requestContext.getSessionAttribute(Attribute.USER_BALANCE);
            requestContext.addSession(Attribute.USER_BALANCE, balance - bet.getMoneyBet());
        } else {
            throw new InvalidInputException("You have no money for your bet.");
        }
        return CommandResult.redirect(requestContext.getHeader());
    }
}
