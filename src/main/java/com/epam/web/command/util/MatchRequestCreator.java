package com.epam.web.command.util;

import com.epam.web.constant.Attribute;
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
            String teamName = e.getMessage().contains(firstTeam) ? firstTeam : secondTeam;
            requestContext.addAttribute(Attribute.NO_TEAM, teamName);
            requestContext.addAttribute(Attribute.TOURNAMENT, tournament);
            requestContext.addAttribute(Attribute.FIRST_TEAM, firstTeam);
            requestContext.addAttribute(Attribute.SECOND_TEAM, secondTeam);
            String dateStr = requestContext.getRequestParameter(Parameter.DATE);
            requestContext.addAttribute(Attribute.DATE, dateStr);
            return null;
        }
        Date date = ParameterExtractor.extractDate(requestContext);
        if (isIdExist) {
            long id = ParameterExtractor.extractId(requestContext);
            return new Match(id, date, tournament, firstTeamId, secondTeamId);
        }
        return new Match(date, tournament, firstTeamId, secondTeamId);
    }
}
