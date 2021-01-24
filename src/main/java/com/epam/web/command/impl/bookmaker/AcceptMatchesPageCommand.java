package com.epam.web.command.impl.bookmaker;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.constant.Attribute;
import com.epam.web.constant.Page;
import com.epam.web.constant.Parameter;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.exception.ServiceException;
import com.epam.web.logic.service.match.MatchService;
import com.epam.web.logic.service.match.MatchType;
import com.epam.web.model.entity.Match;

import java.util.List;

public class AcceptMatchesPageCommand implements Command {
    private static final int MAX_MATCHES_PAGE = 4;
    private final MatchService matchService;

    public AcceptMatchesPageCommand(MatchService matchService) {
        this.matchService = matchService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException,
            InvalidParametersException {
        String pageStr = requestContext.getRequestParameter(Parameter.PAGE);
        int page;
        try {
            page = Integer.parseInt(pageStr);
        } catch (NumberFormatException e) {
            throw new InvalidParametersException("Invalid page number in request.");
        }
        if (page < 1) {
            throw new InvalidParametersException("Not positive page number in request.");
        }
        int firstMatchIndex = MAX_MATCHES_PAGE * (page - 1);
        List<Match> matches = matchService.getMatchesTypeRange(MatchType.UNACCEPTED,
                firstMatchIndex, MAX_MATCHES_PAGE);
        if (matches.size() == 0 && page > 1) {
            throw new InvalidParametersException("No matches on this page");
        }
        requestContext.addAttribute(Attribute.MATCHES, matches);
        requestContext.addAttribute(Attribute.CURRENT_PAGE, page);
        int maxPage = ((matchService.getMatchesTypeAmount(MatchType.UNACCEPTED) - 1) / MAX_MATCHES_PAGE) + 1;
        requestContext.addAttribute(Attribute.MAX_PAGE, maxPage);

        return CommandResult.forward(Page.ACCEPT_MATCHES);
    }
}
