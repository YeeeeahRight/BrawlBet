package com.epam.web.command.impl;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.entity.Match;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.request.RequestContext;
import com.epam.web.service.MatchService;

public class EditMatchCommand implements Command {
    private static final String MATCHES_COMMAND = "controller?command=matches";
    private static final String ID_ATTRIBUTE = "id";
    private final MatchService matchService;

    public EditMatchCommand(MatchService matchService) {
        this.matchService = matchService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {
        String date = requestContext.getRequestParameter(Match.DATE);
        String tournament = requestContext.getRequestParameter(Match.TOURNAMENT);
        String firstTeam = requestContext.getRequestParameter(Match.FIRST_TEAM);
        String secondTeam = requestContext.getRequestParameter(Match.SECOND_TEAM);
        Match match = new Match(date, tournament, firstTeam, secondTeam);
        String idStr = requestContext.getRequestParameter(ID_ATTRIBUTE);
        Long id = Long.valueOf(idStr);
        matchService.editMatch(match, id);

        return CommandResult.redirect(MATCHES_COMMAND);
    }
}
