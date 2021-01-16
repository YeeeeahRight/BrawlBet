package com.epam.web.command.impl.general;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.constant.Attribute;
import com.epam.web.constant.Page;
import com.epam.web.logic.calculator.BetCalculator;
import com.epam.web.logic.service.bet.BetService;
import com.epam.web.logic.service.match.MatchService;
import com.epam.web.model.entity.Bet;
import com.epam.web.model.entity.Match;
import com.epam.web.exception.ServiceException;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.model.entity.dto.MatchBetsDto;
import com.epam.web.model.enumeration.Team;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomePageCommand implements Command {
    private final MatchService matchService;
    private final BetService betService;
    private final BetCalculator betCalculator;

    public HomePageCommand(MatchService matchService, BetService betService, BetCalculator betCalculator) {
        this.matchService = matchService;
        this.betService = betService;
        this.betCalculator = betCalculator;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {
        List<Match> activeMatches = matchService.getActiveMatches();
        List<Bet> bets = betService.getAll();
        List<MatchBetsDto> matchBetsDtoList = buildMatchBetDtoList(activeMatches, bets);
        matchBetsDtoList.sort((m1, m2) -> m1.getDate().compareTo(m2.getDate()));
        requestContext.addAttribute(Attribute.MATCHES_DTO, matchBetsDtoList);

        return CommandResult.forward(Page.HOME);
    }

    private List<MatchBetsDto> buildMatchBetDtoList(List<Match> activeMatches, List<Bet> bets) {
        List<MatchBetsDto> matchBetsDtoList = new ArrayList<>();
        for (Match match : activeMatches) {
            long id = match.getId();
            Date date = match.getDate();
            String tournament = match.getTournament();
            String firstTeam = match.getFirstTeam();
            String secondTeam = match.getSecondTeam();
            float firstTeamBetsAmount = 0.0f;
            float secondTeamBetsAmount = 0.0f;
            for (Bet bet : bets) {
                long matchId = bet.getMatchId();
                if (matchId == id) {
                    Team team = bet.getTeam();
                    if (team == Team.FIRST) {
                        firstTeamBetsAmount += bet.getMoneyBet();
                    } else {
                        secondTeamBetsAmount += bet.getMoneyBet();
                    }
                }
            }
            int firstPercent = betCalculator.calculatePercent(Team.FIRST, firstTeamBetsAmount, secondTeamBetsAmount);
            int secondPercent = betCalculator.calculatePercent(Team.SECOND, firstTeamBetsAmount, secondTeamBetsAmount);
            MatchBetsDto matchBetsDto = new MatchBetsDto(id, date, tournament,
                    firstTeam, secondTeam, firstPercent, secondPercent);
            matchBetsDtoList.add(matchBetsDto);
        }
        return matchBetsDtoList;
    }
}
