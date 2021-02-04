package com.epam.web.command.impl.admin;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.command.util.ParameterExtractor;
import com.epam.web.constant.Attribute;
import com.epam.web.constant.Page;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.logic.service.match.MatchService;
import com.epam.web.logic.service.team.TeamService;
import com.epam.web.model.entity.Match;
import com.epam.web.exception.ServiceException;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.model.entity.dto.MatchDto;

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
        MatchDto matchDto = buildMatchDto(match);
        requestContext.addAttribute(Attribute.MATCH_DTO, matchDto);

        return CommandResult.forward(Page.EDIT_MATCH);
    }

    private MatchDto buildMatchDto(Match match) throws ServiceException {
        long firstTeamId = match.getFirstTeamId();
        long secondTeamId = match.getSecondTeamId();
        String firstTeamName = teamService.getTeamNameById(firstTeamId);
        String secondTeamName = teamService.getTeamNameById(secondTeamId);
        MatchDto.MatchDtoBuilder matchDtoBuilder = new MatchDto.MatchDtoBuilder();
        matchDtoBuilder.setGeneralFields(match,
                firstTeamName, secondTeamName);
        return matchDtoBuilder.build();
    }
}
