package com.epam.web.command.util;

import com.epam.web.constant.Parameter;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.exception.ServiceException;
import com.epam.web.logic.service.team.TeamService;
import com.epam.web.model.entity.Match;

import java.util.Date;

public class MatchRequestCreator {

    public Match createMatch(RequestContext requestContext, TeamService teamService, boolean isIdExist)
            throws InvalidParametersException {
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
        Date date = ParameterExtractor.extractDate(requestContext);
        if (isIdExist) {
            long id = ParameterExtractor.extractId(requestContext);
            return new Match(id, date, tournament, firstTeamId, secondTeamId);
        }
        return new Match(date, tournament, firstTeamId, secondTeamId);
    }
}
