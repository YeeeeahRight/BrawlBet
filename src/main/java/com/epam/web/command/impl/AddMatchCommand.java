package com.epam.web.command.impl;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.constant.CommandName;
import com.epam.web.date.DateFormatType;
import com.epam.web.date.DateParser;
import com.epam.web.exceptions.InvalidParametersException;
import com.epam.web.model.entity.Match;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.logic.service.MatchService;

import java.text.ParseException;
import java.util.Date;

public class AddMatchCommand implements Command {
    private static final String MATCHES_COMMAND = "controller?command=" + CommandName.MATCHES;

    private final MatchService matchService;

    public AddMatchCommand(MatchService matchService) {
        this.matchService = matchService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException, InvalidParametersException {
        Match match = buildMatch(requestContext);
        matchService.saveMatch(match);

        return CommandResult.redirect(MATCHES_COMMAND);
    }

    private Match buildMatch(RequestContext requestContext) throws InvalidParametersException {
        String tournament = requestContext.getRequestParameter(Match.TOURNAMENT);
        String firstTeam = requestContext.getRequestParameter(Match.FIRST_TEAM);
        String secondTeam = requestContext.getRequestParameter(Match.SECOND_TEAM);
        String dateStr = requestContext.getRequestParameter(Match.DATE);
        DateParser dateParser = new DateParser(dateStr);
        Date date;
        try {
            date = dateParser.parse(DateFormatType.HTML);
        } catch (ParseException e) {
            throw new InvalidParametersException("Invalid date format.");
        }
        return new Match(date, tournament, firstTeam, secondTeam);
    }
}
