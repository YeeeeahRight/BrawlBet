package com.epam.web.command.impl;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.constant.Attribute;
import com.epam.web.constant.Page;
import com.epam.web.constant.Parameter;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.model.entity.Bet;
import com.epam.web.model.entity.Match;
import com.epam.web.model.entity.dto.MatchBetsDto;
import com.epam.web.service.BetService;
import com.epam.web.service.MatchService;

import java.util.Date;
import java.util.List;

public class BetPageCommand implements Command {
    private final MatchService matchService;
    private final BetService betService;

    public BetPageCommand(MatchService matchService, BetService betService) {
        this.matchService = matchService;
        this.betService = betService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {
        String idStr = requestContext.getRequestParameter(Parameter.ID);
        long id = Long.parseLong(idStr);
        boolean isMatchActive = matchService.isFinished(id);
        requestContext.addAttribute(Attribute.IS_MATCH_FINISHED, isMatchActive);
        Match match = matchService.findById(id);
        List<Bet> bets = betService.getBetsByMatch(id);
        MatchBetsDto matchBetsDto = createMatchBetDto(match, bets);
        requestContext.addAttribute(Attribute.MATCH_BETS_DTO, matchBetsDto);
        Long accountId = (Long) requestContext.getSessionAttribute(Attribute.ACCOUNT_ID);
        //accountId equals null when guest
        if (accountId != null) {
            int balance = betService.getUserBalance(accountId);
            requestContext.addAttribute(Attribute.MAX_BET, balance);
            requestContext.addAttribute(Attribute.MIN_BET, 1);
            requestContext.addSession(Attribute.USER_BALANCE, balance);
        }

        return CommandResult.forward(Page.BET);
    }

    private MatchBetsDto createMatchBetDto(Match match, List<Bet> bets) {
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
        return new MatchBetsDto(id, date, tournament,
                firstTeam, secondTeam, commission, firstTeamBetsAmount, secondTeamBetsAmount);
    }
}
