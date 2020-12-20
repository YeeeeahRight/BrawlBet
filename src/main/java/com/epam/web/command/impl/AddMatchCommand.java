package com.epam.web.command.impl;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.entity.Match;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.request.RequestContext;
import com.epam.web.service.MatchService;

public class AddMatchCommand implements Command {
    private static final String MATCHES_COMMAND = "controller?command=matches";
    private final MatchService matchService;

    public AddMatchCommand(MatchService matchService) {
        this.matchService = matchService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {
        String date = requestContext.getRequestParameter(Match.DATE);
        String tournament = requestContext.getRequestParameter(Match.TOURNAMENT);
        String firstTeam = requestContext.getRequestParameter(Match.FIRST_TEAM);
        String secondTeam = requestContext.getRequestParameter(Match.SECOND_TEAM);
        Match match = new Match(date, tournament, firstTeam, secondTeam);
        match.setFirstPercent(0);
        match.setSecondPercent(0);
        matchService.addMatch(match);

        return CommandResult.redirect(MATCHES_COMMAND);
    }
}
