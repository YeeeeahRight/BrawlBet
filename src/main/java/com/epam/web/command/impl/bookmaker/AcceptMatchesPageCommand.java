package com.epam.web.command.impl.bookmaker;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.command.util.MatchDtoCommandHelper;
import com.epam.web.constant.Page;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.exception.ServiceException;
import com.epam.web.logic.service.match.MatchService;
import com.epam.web.logic.service.match.MatchType;
import com.epam.web.logic.service.team.TeamService;

public class AcceptMatchesPageCommand implements Command {
    private static final int MAX_MATCHES_PAGE = 4;

    private final MatchService matchService;
    private final TeamService teamService;

    public AcceptMatchesPageCommand(MatchService matchService, TeamService teamService) {
        this.matchService = matchService;
        this.teamService = teamService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException,
            InvalidParametersException {
        MatchDtoCommandHelper matchDtoCommandHelper =
                new MatchDtoCommandHelper(requestContext, matchService, teamService);
        matchDtoCommandHelper.processCommandPagination(MatchType.UNACCEPTED, MAX_MATCHES_PAGE);

        return CommandResult.forward(Page.ACCEPT_MATCHES);
    }
}
