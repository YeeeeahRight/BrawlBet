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
import com.epam.web.model.entity.Match;
import com.epam.web.exception.ServiceException;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.model.entity.dto.MatchBetsDto;
import com.epam.web.model.enumeration.Team;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class HomePageCommand implements Command {
    private static final int MAX_MATCHES_PAGE = 6;
    private static final String NO_WINNER = "NONE";
    private final MatchService matchService;
    private final BetCalculator betCalculator;

    public HomePageCommand(MatchService matchService, BetCalculator betCalculator) {
        this.matchService = matchService;
        this.betCalculator = betCalculator;
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
        sortMatchesByClosing(activeMatches);
        List<MatchBetsDto> matchBetsDtoList = buildMatchBetDtoList(activeMatches);
        requestContext.addAttribute(Attribute.MATCH_BETS_DTO_LIST, matchBetsDtoList);
        requestContext.addAttribute(Attribute.CURRENT_PAGE, page);
        int maxPage = ((matchService.getMatchesTypeAmount(MatchType.ACCEPTED) - 1) / MAX_MATCHES_PAGE) + 1;
        requestContext.addAttribute(Attribute.MAX_PAGE, maxPage);

        return CommandResult.forward(Page.HOME);
    }

    private List<MatchBetsDto> buildMatchBetDtoList(List<Match> activeMatches) {
        List<MatchBetsDto> matchBetsDtoList = new ArrayList<>();
        for (Match match : activeMatches) {
            long id = match.getId();
            Date date = match.getDate();
            String tournament = match.getTournament();
            String firstTeam = match.getFirstTeam();
            String secondTeam = match.getSecondTeam();
            String winner = match.getWinner();
            boolean isClosed = match.isClosed();
            float firstTeamBetsAmount = match.getFirstTeamBets();
            float secondTeamBetsAmount = match.getSecondTeamBets();
            int firstPercent = 0;
            int secondPercent = 0;
            if (firstTeamBetsAmount + secondTeamBetsAmount != 0) {
                firstPercent = betCalculator.calculatePercent(Team.FIRST,
                        firstTeamBetsAmount, secondTeamBetsAmount);
                secondPercent = 100 - firstPercent;
            }
            MatchBetsDto matchBetsDto = new MatchBetsDto(id, date, tournament,
                    firstTeam, secondTeam, firstPercent, secondPercent, winner, isClosed);
            matchBetsDtoList.add(matchBetsDto);
        }
        return matchBetsDtoList;
    }

    private void sortMatchesByClosing(List<Match> matches) {
        List<Match> closedMatches = new ArrayList<>();
        Iterator<Match> matchIterator = matches.iterator();
        while (matchIterator.hasNext()) {
            Match currentMatch = matchIterator.next();
            if (!currentMatch.getWinner().equalsIgnoreCase(NO_WINNER)) {
                closedMatches.add(currentMatch);
                matchIterator.remove();
            }
        }
        matches.addAll(closedMatches);
    }
}
