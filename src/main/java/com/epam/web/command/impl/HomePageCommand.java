package com.epam.web.command.impl;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.entity.Match;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.request.RequestContext;
import com.epam.web.service.MatchService;

import java.util.List;

public class HomePageCommand implements Command {
    private static final String START_PARAM = "start-page";
    private static final String ADMIN_PARAM = "admin-page";
    private static final String START_PAGE = "WEB-INF/view/pages/start-page.jsp";
    private static final String ADMIN_PAGE = "WEB-INF/view/pages/admin-page.jsp";
    private static final String MATCHES_ATTRIBUTE = "matches";
    private final MatchService matchService;
    private final String command;

    public HomePageCommand(MatchService matchService, String command) {
        this.command = command;
        this.matchService = matchService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {
        List<Match> matches = matchService.getAll();
        requestContext.addAttribute(MATCHES_ATTRIBUTE, matches);
        switch (command) {
            case START_PARAM:
                return CommandResult.forward(START_PAGE);
            case ADMIN_PARAM:
                return CommandResult.forward(ADMIN_PAGE);
            default:
                throw new IllegalArgumentException("Unknown page: " + command);
        }
    }
}
