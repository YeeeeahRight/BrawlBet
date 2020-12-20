package com.epam.web.command.impl;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.entity.Match;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.request.RequestContext;
import com.epam.web.service.MatchService;

import java.util.List;

public class MatchesCommand implements Command {
    private static final String MATCHES_PAGE = "WEB-INF/view/pages/matches.jsp";
    private static final String MATCHES_ATTRIBUTE = "matches";
    private final MatchService matchService;

    public MatchesCommand(MatchService matchService) {
        this.matchService = matchService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {
        List<Match> matches = matchService.getAll();
        requestContext.addAttribute(MATCHES_ATTRIBUTE, matches);
        return CommandResult.forward(MATCHES_PAGE);
    }
}
