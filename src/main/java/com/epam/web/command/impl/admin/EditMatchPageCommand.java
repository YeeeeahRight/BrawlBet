package com.epam.web.command.impl.admin;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.command.util.ParameterExtractor;
import com.epam.web.constant.Attribute;
import com.epam.web.constant.Page;
import com.epam.web.date.DateFormatType;
import com.epam.web.date.DateFormatter;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.logic.service.match.MatchService;
import com.epam.web.logic.service.team.TeamService;
import com.epam.web.model.entity.Match;
import com.epam.web.exception.ServiceException;
import com.epam.web.controller.request.RequestContext;

public class EditMatchPageCommand implements Command {
    private final MatchService matchService;
    private final TeamService teamService;

    public EditMatchPageCommand(MatchService matchService, TeamService teamService) {
        this.matchService = matchService;
        this.teamService = teamService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException, InvalidParametersException {
        long id = ParameterExtractor.extractId(requestContext);
        Match match = matchService.getMatchById(id);
        String firstTeam = teamService.getTeamNameById(match.getFirstTeamId());
        String secondTeam = teamService.getTeamNameById(match.getSecondTeamId());
        String formattedDate = DateFormatter.format(match.getDate(), DateFormatType.HTML);
        requestContext.addAttribute(Attribute.DATE, formattedDate);
        requestContext.addAttribute(Attribute.TOURNAMENT, match.getTournament());
        requestContext.addAttribute(Attribute.FIRST_TEAM, firstTeam);
        requestContext.addAttribute(Attribute.SECOND_TEAM, secondTeam);
        requestContext.addAttribute(Attribute.MATCH_ID, id);

        return CommandResult.forward(Page.EDIT_MATCH);
    }
}
