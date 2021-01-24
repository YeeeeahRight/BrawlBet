package com.epam.web.command.impl.admin;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.constant.CommandName;
import com.epam.web.constant.Parameter;
import com.epam.web.date.DateFormatType;
import com.epam.web.date.DateParser;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.logic.service.match.MatchService;
import com.epam.web.model.entity.Match;
import com.epam.web.exception.ServiceException;
import com.epam.web.controller.request.RequestContext;

import java.text.ParseException;
import java.util.Date;

public class EditMatchCommand implements Command {
    private static final String MATCHES_COMMAND = "controller?command=" + CommandName.MATCHES +
            "&" + Parameter.PAGE + "=1";
    private final MatchService matchService;

    public EditMatchCommand(MatchService matchService) {
        this.matchService = matchService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException, InvalidParametersException {
        String idStr = requestContext.getRequestParameter(Parameter.ID);
        long id;
        try {
            id = Long.parseLong(idStr);
        } catch (NumberFormatException e) {
            throw new InvalidParametersException("Invalid match id parameter in request.");
        }
        Match match = buildMatch(requestContext);
        String firstTeam = match.getFirstTeam();
        String secondTeam = match.getSecondTeam();
        if (firstTeam.equalsIgnoreCase(secondTeam)) {
            throw new InvalidParametersException("Team names should be different.");
        }
        matchService.editMatch(match, id);

        return CommandResult.redirect(MATCHES_COMMAND);
    }

    private Match buildMatch(RequestContext requestContext) {
        String tournament = requestContext.getRequestParameter(Parameter.TOURNAMENT);
        String firstTeam = requestContext.getRequestParameter(Parameter.FIRST_TEAM);
        String secondTeam = requestContext.getRequestParameter(Parameter.SECOND_TEAM);
        String dateStr = requestContext.getRequestParameter(Parameter.DATE);
        DateParser dateParser = new DateParser(dateStr);
        Date date;
        try {
            date = dateParser.parse(DateFormatType.HTML);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format.");
        }
        return new Match(date, tournament, firstTeam, secondTeam);
    }
}


