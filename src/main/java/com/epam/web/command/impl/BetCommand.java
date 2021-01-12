package com.epam.web.command.impl;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.constant.Attribute;
import com.epam.web.constant.Parameter;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.exceptions.InvalidInputException;
import com.epam.web.exceptions.InvalidParametersException;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.model.entity.Bet;
import com.epam.web.logic.service.BetService;
import com.epam.web.logic.service.MatchService;
import com.epam.web.logic.service.UserService;
import com.epam.web.model.enumeration.Team;


public class BetCommand implements Command {
    private final MatchService matchService;
    private final BetService betService;
    private final UserService userService;

    public BetCommand(MatchService matchService, BetService betService, UserService userService) {
        this.matchService = matchService;
        this.betService = betService;
        this.userService = userService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException, InvalidParametersException {
        String matchIdStr = requestContext.getRequestParameter(Parameter.ID);
        String moneyStr = requestContext.getRequestParameter(Parameter.MONEY);
        long matchId;
        int money;
        Team team;
        try {
            matchId = Long.parseLong(matchIdStr);
            if (matchService.isFinishedMatch(matchId)) {
                throw new ServiceException("This match is already finished.");
            }
            money = Integer.parseInt(moneyStr);
            String teamStr = requestContext.getRequestParameter(Parameter.BET_ON);
            try {
                team = Team.valueOf(teamStr);
            } catch (IllegalArgumentException e) {
                throw new InvalidInputException("Invalid team on bet.");
            }
        } catch (NumberFormatException e) {
            throw new InvalidParametersException("Invalid parameters in request.");
        }
        Long accountId = (Long)requestContext.getSessionAttribute(Attribute.ACCOUNT_ID);
        if (userService.getBalance(accountId) < money) {
            throw new ServiceException("You have no money for your bet.");
        }
        Bet bet = new Bet(accountId, matchId, money, team);
        betService.createBet(bet);

        String prevPage = requestContext.getHeader();
        return CommandResult.redirect(prevPage);
    }
}
