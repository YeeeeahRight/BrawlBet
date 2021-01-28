package com.epam.web.command.impl.bookmaker;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.constant.Attribute;
import com.epam.web.constant.Page;
import com.epam.web.constant.Parameter;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.exception.ServiceException;
import com.epam.web.logic.service.match.MatchService;
import com.epam.web.logic.service.match.MatchType;
import com.epam.web.logic.service.team.TeamService;
import com.epam.web.model.entity.Match;
import com.epam.web.model.entity.dto.MatchDto;

import java.util.ArrayList;
import java.util.List;

public class BookmakerHistory implements Command {
    private static final int MAX_MATCHES_PAGE = 6;

    private final MatchService matchService;
    private final TeamService teamService;

    public BookmakerHistory(MatchService matchService, TeamService teamService) {
        this.matchService = matchService;
        this.teamService = teamService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException,
            InvalidParametersException {
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
        int firstMatchIndex = MAX_MATCHES_PAGE * (page - 1);
        List<Match> matches = matchService.getMatchesTypeRange(MatchType.CLOSED,
                firstMatchIndex, MAX_MATCHES_PAGE);
        if (matches.size() == 0 && page > 1) {
            throw new InvalidParametersException("No history on this page");
        }
        List<MatchDto> matchDtoList = buildMatchDtoList(matches);
        requestContext.addAttribute(Attribute.MATCH_DTO_LIST, matchDtoList);
        requestContext.addAttribute(Attribute.CURRENT_PAGE, page);
        int maxPage = ((matchService.getMatchesTypeAmount(MatchType.CLOSED) - 1) / MAX_MATCHES_PAGE) + 1;
        requestContext.addAttribute(Attribute.MAX_PAGE, maxPage);

        return CommandResult.forward(Page.BOOKMAKER_HISTORY);
    }

    private List<MatchDto> buildMatchDtoList(List<Match> matches) throws ServiceException {
        List<MatchDto> matchDtoList = new ArrayList<>();
        MatchDto.MatchDtoBuilder matchDtoBuilder = new MatchDto.MatchDtoBuilder();
        for (Match match : matches) {
            long firstTeamId = match.getFirstTeamId();
            long secondTeamId = match.getSecondTeamId();
            String firstTeamName = teamService.getTeamNameById(firstTeamId);
            String secondTeamName = teamService.getTeamNameById(secondTeamId);
            long winnerTeamId = match.getWinnerTeamId();
            String winnerTeamName = findWinnerTeamName(winnerTeamId, firstTeamId, secondTeamId,
                    firstTeamName, secondTeamName);
            Float commission = match.getCommission();
            MatchDto matchDto = matchDtoBuilder.setGeneralFields(match, firstTeamName, secondTeamName)
                                               .setWinner(winnerTeamName)
                                               .setCommission(commission).build();
            matchDtoList.add(matchDto);
        }
        return matchDtoList;
    }

    private String findWinnerTeamName(long winnerTeamId, long firstTeamId, long secondTeamId,
                                      String firstTeamName, String secondTeamName) {
        if (winnerTeamId == firstTeamId) {
            return firstTeamName;
        } else if (winnerTeamId == secondTeamId) {
            return secondTeamName;
        }
        return null;
    }
}
