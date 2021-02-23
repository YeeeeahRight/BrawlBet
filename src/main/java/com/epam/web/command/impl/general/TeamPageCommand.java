package com.epam.web.command.impl.general;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.command.util.ParameterExtractor;
import com.epam.web.constant.Attribute;
import com.epam.web.constant.Page;
import com.epam.web.constant.Parameter;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.exception.ServiceException;
import com.epam.web.logic.service.team.TeamService;
import com.epam.web.model.entity.Team;

public class TeamPageCommand implements Command {
    private final TeamService teamService;

    public TeamPageCommand(TeamService teamService) {
        this.teamService = teamService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext)
            throws ServiceException, InvalidParametersException {
        long id;
        try {
            id = ParameterExtractor.extractId(requestContext);
        } catch (InvalidParametersException e) {
            String teamName = ParameterExtractor.extractString(Parameter.TEAM_NAME, requestContext);
            id = teamService.getTeamIdByName(teamName);
        }
        Team team = teamService.getTeamById(id);
        float winRate = calculateWinRate(team);
        requestContext.addAttribute(Attribute.TEAM, team);
        requestContext.addAttribute(Attribute.WIN_RATE, winRate);

        return CommandResult.forward(Page.TEAM);
    }

    private float calculateWinRate(Team team) {
        int totalMatches = team.getMatchesLost() + team.getMatchesWon();
        if (totalMatches == 0) {
            return -1f;
        }
        return (team.getMatchesWon() * 1.0f / totalMatches) * 100f;
    }
}
