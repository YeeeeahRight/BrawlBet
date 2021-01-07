package com.epam.web.command.impl;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.constant.Attribute;
import com.epam.web.constant.Page;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.model.entity.Match;
import com.epam.web.service.MatchService;

import java.util.List;

public class AcceptMatchesPageCommand implements Command {
    private final MatchService matchService;

    public AcceptMatchesPageCommand(MatchService matchService) {
        this.matchService = matchService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {
        List<Match> matches = matchService.getUnacceptedMatches();
        requestContext.addAttribute(Attribute.MATCHES, matches);

        return CommandResult.forward(Page.ACCEPT_MATCHES);
    }
}
