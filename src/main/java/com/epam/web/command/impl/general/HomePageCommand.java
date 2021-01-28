package com.epam.web.command.impl.general;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.constant.Attribute;
import com.epam.web.constant.Page;
import com.epam.web.constant.Parameter;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.logic.calculator.BetCalculator;
import com.epam.web.logic.service.match.MatchService;
import com.epam.web.logic.service.match.MatchType;
import com.epam.web.logic.service.team.TeamService;
import com.epam.web.model.entity.Match;
import com.epam.web.exception.ServiceException;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.model.entity.dto.MatchDto;
import com.epam.web.model.enumeration.MatchTeamNumber;

import java.util.*;

public class HomePageCommand implements Command {
    private static final int MAX_MATCHES_PAGE = 6;
    private final MatchService matchService;
    private final BetCalculator betCalculator;
    private final TeamService teamService;

    public HomePageCommand(MatchService matchService, TeamService teamService, BetCalculator betCalculator) {
        this.matchService = matchService;
        this.betCalculator = betCalculator;
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
        List<Match> activeMatches = matchService.getMatchesTypeRange(MatchType.ACCEPTED,
                firstMatchIndex, MAX_MATCHES_PAGE);
        if (activeMatches.size() == 0 && page > 1) {
            throw new InvalidParametersException("No matches on this page");
        }
        List<MatchDto> matchDtoList = buildMatchDtoList(activeMatches);
        requestContext.addAttribute(Attribute.MATCH_DTO_LIST, matchDtoList);
        requestContext.addAttribute(Attribute.CURRENT_PAGE, page);
        int maxPage = ((matchService.getMatchesTypeAmount(MatchType.ACCEPTED) - 1) / MAX_MATCHES_PAGE) + 1;
        requestContext.addAttribute(Attribute.MAX_PAGE, maxPage);

        return CommandResult.forward(Page.HOME);
    }

    private List<MatchDto> buildMatchDtoList(List<Match> activeMatches) throws ServiceException {
        List<MatchDto> matchDtoList = new ArrayList<>();
        MatchDto.MatchDtoBuilder matchDtoBuilder = new MatchDto.MatchDtoBuilder();
        for (Match match : activeMatches) {
            long firstTeamId = match.getFirstTeamId();
            long secondTeamId = match.getSecondTeamId();
            String firstTeamName = teamService.getTeamNameById(firstTeamId);
            String secondTeamName = teamService.getTeamNameById(secondTeamId);
            MatchTeamNumber winnerTeam = match.getWinnerTeam();
            String winnerTeamName = findWinnerTeamName(winnerTeam, firstTeamName, secondTeamName);
            matchDtoBuilder = matchDtoBuilder.setGeneralFields(match,
                    firstTeamName, secondTeamName).setWinner(winnerTeamName);
            matchDtoBuilder = setPercents(matchDtoBuilder,
                    match.getFirstTeamBets(), match.getSecondTeamBets());
            MatchDto matchDto = matchDtoBuilder.build();
            matchDtoList.add(matchDto);
        }
        return matchDtoList;
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

    private MatchDto.MatchDtoBuilder setPercents(MatchDto.MatchDtoBuilder matchDtoBuilder, float firstTeamBets,
                               float secondTeamBets) {
        int firstPercent = 0;
        int secondPercent = 0;
        if (firstTeamBets + secondTeamBets != 0) {
            firstPercent = betCalculator.calculatePercent(MatchTeamNumber.FIRST,
                    firstTeamBets, secondTeamBets);
            secondPercent = 100 - firstPercent;
        }
        return matchDtoBuilder.setPercents(firstPercent, secondPercent);
    }
}
