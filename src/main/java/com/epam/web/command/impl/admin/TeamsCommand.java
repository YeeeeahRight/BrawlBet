package com.epam.web.command.impl.admin;

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

import java.util.List;

public class TeamsCommand implements Command {
    private static final int MAX_TEAMS_PAGE = 10;
    private final TeamService teamService;

    public TeamsCommand(TeamService teamService) {
        this.teamService = teamService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException, InvalidParametersException {
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
        int firstMatchIndex = MAX_TEAMS_PAGE * (page - 1);
        List<Team> teams = teamService.getTeamsRange(firstMatchIndex, MAX_TEAMS_PAGE);
        requestContext.addAttribute(Attribute.TEAMS, teams);
        requestContext.addAttribute(Attribute.CURRENT_PAGE, page);
        int maxPage = ((teamService.getTeamsAmount() - 1) / MAX_TEAMS_PAGE) + 1;
        requestContext.addAttribute(Attribute.MAX_PAGE, maxPage);

        return CommandResult.forward(Page.TEAMS);
    }
}
