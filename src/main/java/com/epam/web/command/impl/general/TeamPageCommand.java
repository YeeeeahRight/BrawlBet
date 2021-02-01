package com.epam.web.command.impl.general;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
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
    public CommandResult execute(RequestContext requestContext) throws ServiceException, InvalidParametersException {
        String idStr = requestContext.getRequestParameter(Parameter.ID);
        String teamName;
        long id;
        if (idStr != null) {
            try {
                id = Long.parseLong(idStr);
            } catch (NumberFormatException e) {
                throw new InvalidParametersException("Invalid id parameter in request.");
            }
        } else {
            teamName = requestContext.getRequestParameter(Parameter.NAME);
            if (teamName != null) {
                id = teamService.getTeamIdByName(teamName);
            } else {
                throw new InvalidParametersException("Invalid team name parameter in request.");
            }
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
