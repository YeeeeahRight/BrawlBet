package com.epam.web.command.impl.admin;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.command.util.MatchDtoCommandHelper;
import com.epam.web.command.util.ParameterExtractor;
import com.epam.web.constant.Attribute;
import com.epam.web.constant.Page;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.logic.service.match.MatchService;
import com.epam.web.logic.service.match.MatchType;
import com.epam.web.logic.service.team.TeamService;
import com.epam.web.model.entity.Match;
import com.epam.web.exception.ServiceException;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.model.entity.dto.MatchDto;

import java.util.Collections;
import java.util.List;

public class EditMatchPageCommand implements Command {
    private static final int FIRST_ELEMENT_INDEX = 0;

    private final MatchService matchService;
    private final MatchDtoCommandHelper matchDtoCommandHelper;

    public EditMatchPageCommand(MatchService matchService, MatchDtoCommandHelper matchDtoCommandHelper) {
        this.matchService = matchService;
        this.matchDtoCommandHelper = matchDtoCommandHelper;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException, InvalidParametersException {
        long id = ParameterExtractor.extractId(requestContext);
        Match match = matchService.getMatchById(id);
        List<Match> matches = Collections.singletonList(match);
        List<MatchDto> matchDtoList = matchDtoCommandHelper.buildMatchDtoList(matches, MatchType.UNCLOSED);
        MatchDto matchDto = matchDtoList.get(FIRST_ELEMENT_INDEX);
        requestContext.addAttribute(Attribute.MATCH_DTO, matchDto);

        return CommandResult.forward(Page.EDIT_MATCH);
    }
}
