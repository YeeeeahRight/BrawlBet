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

public class CloseMatchesPageCommand implements Command {
    private final MatchService matchService;

    public CloseMatchesPageCommand(MatchService matchService) {
        this.matchService = matchService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {
        List<Match> finishedMatches = matchService.getFinishedMatches();
        requestContext.addAttribute(Attribute.MATCHES, finishedMatches);
        return CommandResult.forward(Page.FINISH_MATCHES);
    }
}
