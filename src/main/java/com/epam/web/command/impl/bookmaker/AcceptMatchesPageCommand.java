package com.epam.web.command.impl.bookmaker;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.command.util.MatchDtoCommandHelper;
import com.epam.web.constant.Page;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.exception.ServiceException;
import com.epam.web.logic.service.match.MatchType;

public class AcceptMatchesPageCommand implements Command {
    private static final int MAX_MATCHES_PAGE = 4;

    private final MatchDtoCommandHelper matchDtoCommandHelper;

    public AcceptMatchesPageCommand(MatchDtoCommandHelper matchDtoCommandHelper) {
        this.matchDtoCommandHelper = matchDtoCommandHelper;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException,
            InvalidParametersException {
        matchDtoCommandHelper.processCommandWithPagination(requestContext, MatchType.UNACCEPTED, MAX_MATCHES_PAGE);

        return CommandResult.forward(Page.ACCEPT_MATCHES);
    }
}
