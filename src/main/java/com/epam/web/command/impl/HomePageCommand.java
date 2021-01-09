package com.epam.web.command.impl;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.constant.Attribute;
import com.epam.web.constant.Page;
import com.epam.web.model.entity.Bet;
import com.epam.web.model.entity.Match;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.model.entity.dto.MatchBetsDto;
import com.epam.web.service.BetService;
import com.epam.web.service.MatchService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomePageCommand implements Command {
    private final MatchService matchService;
    private final BetService betService;

    public HomePageCommand(MatchService matchService, BetService betService) {
        this.matchService = matchService;
        this.betService = betService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {
        List<Match> activeMatches = matchService.getActiveMatches();
        List<Bet> bets = betService.getAll();
        List<MatchBetsDto> matchBetsDtoList = buildMatchBetDtoList(activeMatches, bets);
        requestContext.addAttribute(Attribute.MATCHES_BETS_DTO, matchBetsDtoList);
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
            float commission = match.getCommission();
            int firstTeamBetsAmount = 0;
            int secondTeamBetsAmount = 0;
            for (Bet bet : bets) {
                long matchId = bet.getMatchId();
                if (matchId == id) {
                    String team = bet.getTeam();
                    if (team.equalsIgnoreCase("FIRST")) {
                        firstTeamBetsAmount += bet.getMoneyBet();
                    } else {
                        secondTeamBetsAmount += bet.getMoneyBet();
                    }
                }
            }
            MatchBetsDto matchBetsDto = new MatchBetsDto(id, date, tournament,
                    firstTeam, secondTeam, commission, firstTeamBetsAmount, secondTeamBetsAmount);
            matchBetsDtoList.add(matchBetsDto);
        }
        return matchBetsDtoList;
    }
}
