package com.epam.web.command.impl.admin;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.constant.CommandName;
import com.epam.web.constant.Parameter;
import com.epam.web.date.DateFormatType;
import com.epam.web.date.DateParser;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.logic.service.match.MatchService;
import com.epam.web.logic.service.team.TeamService;
import com.epam.web.model.entity.Match;
import com.epam.web.exception.ServiceException;
import com.epam.web.controller.request.RequestContext;

import java.text.ParseException;
import java.util.Date;

public class EditMatchCommand implements Command {
    private static final String MATCHES_COMMAND = "controller?command=" + CommandName.MATCHES +
            "&" + Parameter.PAGE + "=1";

    private final MatchService matchService;
    private final TeamService teamService;

    public EditMatchCommand(MatchService matchService, TeamService teamService) {
        this.matchService = matchService;
        this.teamService = teamService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException, InvalidParametersException {
        Match match = buildMatch(requestContext);
        Long firstTeamId = match.getFirstTeamId();
        Long secondTeamId = match.getSecondTeamId();
        if (firstTeamId.equals(secondTeamId)) {
            throw new InvalidParametersException("Teams should be different.");
        }
        matchService.saveMatch(match);

        return CommandResult.redirect(MATCHES_COMMAND);
    }

    private Match buildMatch(RequestContext requestContext) throws InvalidParametersException {
        String idStr = requestContext.getRequestParameter(Parameter.ID);
        long id;
        try {
            id = Long.parseLong(idStr);
        } catch (NumberFormatException e) {
            throw new InvalidParametersException("Invalid match id parameter in request.");
        }
        String tournament = requestContext.getRequestParameter(Parameter.TOURNAMENT);
        String firstTeam = requestContext.getRequestParameter(Parameter.FIRST_TEAM);
        String secondTeam = requestContext.getRequestParameter(Parameter.SECOND_TEAM);
        long firstTeamId;
        long secondTeamId;
        try {
            firstTeamId = teamService.getTeamIdByName(firstTeam);
            secondTeamId = teamService.getTeamIdByName(secondTeam);
        } catch (ServiceException e) {
            throw new InvalidParametersException(e.getMessage());
        }
        String dateStr = requestContext.getRequestParameter(Parameter.DATE);
        DateParser dateParser = new DateParser(dateStr);
        Date date;
        try {
            date = dateParser.parse(DateFormatType.HTML);
        } catch (ParseException e) {
            throw new InvalidParametersException("Invalid date format.");
        }
        return new Match(id, date, tournament, firstTeamId, secondTeamId);
    }
}


