package com.epam.web.command.util;

import com.epam.web.constant.Attribute;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.exception.ServiceException;
import com.epam.web.logic.service.match.MatchService;
import com.epam.web.logic.service.match.MatchType;
import com.epam.web.logic.service.team.TeamService;
import com.epam.web.model.entity.Match;
import com.epam.web.model.entity.dto.MatchDto;
import com.epam.web.model.enumeration.MatchTeamNumber;

import java.util.ArrayList;
import java.util.List;

public class MatchDtoCommandHelper {
    private final MatchService matchService;
    private final TeamService teamService;

    public MatchDtoCommandHelper(MatchService matchService, TeamService teamService) {
        this.matchService = matchService;
        this.teamService = teamService;
    }

    public void processCommandWithPagination(RequestContext requestContext,
                                             MatchType matchType, int maxMatchesPage)
            throws InvalidParametersException, ServiceException {
        int page = ParameterExtractor.extractPageNumber(requestContext);
        int firstMatchIndex = maxMatchesPage * (page - 1);
        List<Match> matches = matchService.getMatchesTypeRange(matchType, firstMatchIndex, maxMatchesPage);
        if (matches.size() == 0 && page > 1) {
            throw new InvalidParametersException("No matches on this page");
        }
        List<MatchDto> matchDtoList = buildMatchDtoList(matches, matchType);
        requestContext.addAttribute(Attribute.MATCH_DTO_LIST, matchDtoList);
        requestContext.addAttribute(Attribute.CURRENT_PAGE, page);
        int maxPage = ((matchService.getMatchesTypeAmount(matchType) - 1) / maxMatchesPage) + 1;
        requestContext.addAttribute(Attribute.MAX_PAGE, maxPage);
    }

    public List<MatchDto> buildMatchDtoList(List<Match> matches, MatchType matchType)
            throws ServiceException {
        List<MatchDto> matchDtoList = new ArrayList<>();
        MatchDto.MatchDtoBuilder matchDtoBuilder = new MatchDto.MatchDtoBuilder();
        for (Match match : matches) {
            long firstTeamId = match.getFirstTeamId();
            long secondTeamId = match.getSecondTeamId();
            String firstTeamName = teamService.getTeamNameById(firstTeamId);
            String secondTeamName = teamService.getTeamNameById(secondTeamId);
            matchDtoBuilder.setGeneralFields(match,
                    firstTeamName, secondTeamName);
            switch (matchType) {
                case ACCEPTED:
                case ANY:
                    setWinner(matchDtoBuilder, match, firstTeamName, secondTeamName);
                    setPercents(matchDtoBuilder, match);
                case CLOSED:
                    setWinner(matchDtoBuilder, match, firstTeamName, secondTeamName);
                    setCommission(matchDtoBuilder, match);
            }

            MatchDto matchDto = matchDtoBuilder.build();
            matchDtoList.add(matchDto);
        }
        return matchDtoList;
    }

    private void setCommission(MatchDto.MatchDtoBuilder matchDtoBuilder, Match match) {
        float commission = match.getCommission();
        matchDtoBuilder.setCommission(commission);
    }

    private void setWinner(MatchDto.MatchDtoBuilder matchDtoBuilder, Match match,
                           String firstTeamName, String secondTeamName) {
        MatchTeamNumber winnerTeam = match.getWinnerTeam();
        String winnerTeamName = findWinnerTeamName(winnerTeam, firstTeamName, secondTeamName);
        matchDtoBuilder.setWinner(winnerTeamName);
    }

    private String findWinnerTeamName(MatchTeamNumber winnerTeam, String firstTeamName, String secondTeamName) {
        switch (winnerTeam) {
            case FIRST:
                return firstTeamName;
            case SECOND:
                return secondTeamName;
            default:
                return winnerTeam.toString();
        }
    }

    private void setPercents(MatchDto.MatchDtoBuilder matchDtoBuilder, Match match) {
        float firstTeamBets = match.getFirstTeamBets().floatValue();
        float secondTeamBets = match.getSecondTeamBets().floatValue();
        int firstPercent = 0;
        int secondPercent = 0;
        if (firstTeamBets + secondTeamBets != 0) {
            firstPercent = Math.round(firstTeamBets * 100.0f / (firstTeamBets + secondTeamBets));
            secondPercent = 100 - firstPercent;
        }
        matchDtoBuilder.setPercents(firstPercent, secondPercent);
    }
}
