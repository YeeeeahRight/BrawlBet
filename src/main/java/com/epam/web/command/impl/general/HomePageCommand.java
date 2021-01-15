package com.epam.web.command.impl.general;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.constant.Attribute;
import com.epam.web.constant.Page;
import com.epam.web.logic.calculator.BetCalculator;
import com.epam.web.logic.service.bet.BetService;
import com.epam.web.logic.service.match.MatchService;
import com.epam.web.model.entity.Bet;
import com.epam.web.model.entity.Match;
import com.epam.web.exception.ServiceException;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.model.enumeration.Team;

import java.util.List;

public class HomePageCommand implements Command {
    private final MatchService matchService;
    private final BetService betService;
    private final BetCalculator betCalculator;

    public HomePageCommand(MatchService matchService, BetService betService, BetCalculator betCalculator) {
        this.matchService = matchService;
        this.betService = betService;
        this.betCalculator = betCalculator;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {
        List<Match> activeMatches = matchService.getActiveMatches();
        activeMatches.sort((m1, m2) -> m1.getDate().compareTo(m2.getDate()));
        requestContext.addAttribute(Attribute.MATCHES, activeMatches);

        List<Bet> bets = betService.getAll();
        float firstTeamBetsAmount = betCalculator.calculateBetsAmount(Team.FIRST, bets);
        float secondTeamBetsAmount = betCalculator.calculateBetsAmount(Team.SECOND, bets);
        int firstPercent = betCalculator.calculatePercent(Team.FIRST,
                firstTeamBetsAmount, secondTeamBetsAmount);
        int secondPercent = betCalculator.calculatePercent(Team.SECOND,
                firstTeamBetsAmount, secondTeamBetsAmount);
        requestContext.addAttribute(Attribute.FIRST_PERCENT, firstPercent);
        requestContext.addAttribute(Attribute.SECOND_PERCENT, secondPercent);
        return CommandResult.forward(Page.HOME);
    }
    }
