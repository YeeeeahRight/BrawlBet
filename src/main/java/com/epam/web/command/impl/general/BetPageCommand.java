package com.epam.web.command.impl.general;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.constant.Attribute;
import com.epam.web.constant.Page;
import com.epam.web.constant.Parameter;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.exception.ServiceException;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.logic.calculator.BetCalculator;
import com.epam.web.logic.service.account.AccountService;
import com.epam.web.logic.service.bet.BetService;
import com.epam.web.logic.service.match.MatchService;
import com.epam.web.model.entity.Bet;
import com.epam.web.model.entity.Match;
import com.epam.web.model.enumeration.AccountRole;
import com.epam.web.model.enumeration.Team;

import java.util.List;

public class BetPageCommand implements Command {
    private final MatchService matchService;
    private final BetService betService;
    private final AccountService accountService;
    private final BetCalculator betCalculator;

    public BetPageCommand(MatchService matchService, BetService betService,
                          AccountService accountService, BetCalculator betCalculator) {
        this.matchService = matchService;
        this.betService = betService;
        this.accountService = accountService;
        this.betCalculator = betCalculator;
    }

    @Override
    public CommandResult execute(RequestContext requestContext)
            throws ServiceException, InvalidParametersException {
        String idStr = requestContext.getRequestParameter(Parameter.ID);
        long id;
        try {
            id = Long.parseLong(idStr);
        } catch (NumberFormatException e) {
            throw new InvalidParametersException("Invalid id match parameter in request.");
        }
        boolean isMatchFinished = matchService.isFinishedMatch(id);
        requestContext.addAttribute(Attribute.IS_MATCH_FINISHED, isMatchFinished);
        Match match = matchService.findById(id);
        requestContext.addAttribute(Attribute.MATCH, match);
        List<Bet> bets = betService.getBetsByMatch(id);
        Long accountId = (Long) requestContext.getSessionAttribute(Attribute.ACCOUNT_ID);
        float firstTeamBetsAmount = betCalculator.calculateBetsAmount(Team.FIRST, bets);
        float secondTeamBetsAmount = betCalculator.calculateBetsAmount(Team.SECOND, bets);
        requestContext.addAttribute(Attribute.FIRST_BETS_AMOUNT, firstTeamBetsAmount);
        requestContext.addAttribute(Attribute.SECOND_BETS_AMOUNT, secondTeamBetsAmount);
        int firstPercent = betCalculator.calculatePercent(Team.FIRST,
                firstTeamBetsAmount, secondTeamBetsAmount);
        int secondPercent = betCalculator.calculatePercent(Team.SECOND,
                firstTeamBetsAmount, secondTeamBetsAmount);
        requestContext.addAttribute(Attribute.FIRST_PERCENT, firstPercent);
        requestContext.addAttribute(Attribute.SECOND_PERCENT, secondPercent);
        if (accountId != null) {
            AccountRole role = (AccountRole) requestContext.getSessionAttribute(Attribute.ROLE);
            if (role == AccountRole.USER) {
                float balance = accountService.getBalance(accountId);
                requestContext.addAttribute(Attribute.MAX_BET, balance);
                requestContext.addAttribute(Attribute.MIN_BET, 0.1f);
                float firstCoefficient = betCalculator.calculateCoefficient(Team.FIRST,
                        firstTeamBetsAmount, secondTeamBetsAmount);
                float secondCoefficient = betCalculator.calculateCoefficient(Team.SECOND,
                        firstTeamBetsAmount, secondTeamBetsAmount);
                if (!isOneUserBets(bets)) {
                    float commission = match.getCommission();
                    firstCoefficient -= firstCoefficient * commission / 100;
                    secondCoefficient -= secondCoefficient * commission / 100;
                }
                requestContext.addAttribute(Attribute.FIRST_COEFFICIENT, firstCoefficient);
                requestContext.addAttribute(Attribute.SECOND_COEFFICIENT, secondCoefficient);
            }
        }

        return CommandResult.forward(Page.BET);
    }

    private boolean isOneUserBets(List<Bet> bets) {
        for (int i = bets.size() - 1; i >= 0; i--) {
            long currentAccountId = bets.get(i).getAccountId();
            if (i > 0 && bets.get(i - 1).getAccountId() != currentAccountId) {
                return false;
            }
        }
        return true;
    }
}
