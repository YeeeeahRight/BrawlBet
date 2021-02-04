package com.epam.web.command.impl.admin;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.command.util.ParameterExtractor;
import com.epam.web.constant.CommandName;
import com.epam.web.constant.Parameter;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.exception.ServiceException;
import com.epam.web.logic.service.team.TeamService;
import com.epam.web.model.entity.Team;

public class AddTeamCommand implements Command {
    private static final String TEAMS_COMMAND = "controller?command=" + CommandName.TEAMS +
            "&" + Parameter.PAGE + "=1";
    private final TeamService teamService;

    public AddTeamCommand(TeamService teamService) {
        this.teamService = teamService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException, InvalidParametersException {
        String teamName = ParameterExtractor.extractString(Parameter.TEAM_NAME, requestContext);
        Team newTeam = new Team(teamName, 0, 0);
        teamService.saveTeam(newTeam);

        return CommandResult.redirect(TEAMS_COMMAND);
    }
}
