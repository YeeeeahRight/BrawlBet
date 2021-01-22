package com.epam.web.command.impl.bookmaker;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.constant.Attribute;
import com.epam.web.constant.Page;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.exception.ServiceException;
import com.epam.web.logic.service.match.MatchService;
import com.epam.web.model.entity.Match;
import com.epam.web.model.entity.dto.MatchBetsDto;
import com.epam.web.model.enumeration.Team;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookmakerHistory implements Command {
    private final MatchService matchService;

    public BookmakerHistory(MatchService matchService) {
        this.matchService = matchService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException, InvalidParametersException {
        List<Match> matches = matchService.getClosedMatches();
        requestContext.addAttribute(Attribute.MATCHES, matches);

        return CommandResult.forward(Page.BOOKMAKER_HISTORY);
    }
}
