package com.epam.web.command.impl.general;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.constant.Attribute;
import com.epam.web.constant.Page;
import com.epam.web.logic.calculator.BetCalculator;
import com.epam.web.logic.service.match.MatchService;
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
    private static final String NO_WINNER = "NONE";
    private final MatchService matchService;
    private final BetCalculator betCalculator;

    public HomePageCommand(MatchService matchService, BetCalculator betCalculator) {
        this.matchService = matchService;
        this.betCalculator = betCalculator;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {
        List<Match> activeMatches = matchService.getAcceptedMatchesRange(0, 300);
        sortMatchesByClosing(activeMatches);
        List<MatchBetsDto> matchBetsDtoList = buildMatchBetDtoList(activeMatches);
        requestContext.addAttribute(Attribute.MATCH_BETS_DTO_LIST, matchBetsDtoList);

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
