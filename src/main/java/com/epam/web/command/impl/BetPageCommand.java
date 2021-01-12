package com.epam.web.command.impl;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.constant.Attribute;
import com.epam.web.constant.Page;
import com.epam.web.constant.Parameter;
import com.epam.web.exceptions.InvalidParametersException;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.model.entity.Bet;
import com.epam.web.model.entity.Match;
import com.epam.web.model.entity.dto.MatchBetsDto;
import com.epam.web.logic.service.BetService;
import com.epam.web.logic.service.MatchService;
import com.epam.web.logic.service.UserService;

import java.util.Date;
import java.util.List;

public class BetPageCommand implements Command {
    private final MatchService matchService;
    private final BetService betService;
    private final UserService userService;

    public BetPageCommand(MatchService matchService, BetService betService, UserService userService) {
        this.matchService = matchService;
        this.betService = betService;
        this.userService = userService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException, InvalidParametersException {
        String idStr = requestContext.getRequestParameter(Parameter.ID);
        long id;
        try {
            id = Long.parseLong(idStr);
        } catch (NumberFormatException e) {
            throw new InvalidParametersException("Invalid id match parameter in request.");
        }
        boolean isMatchActive = matchService.isFinishedMatch(id);
        requestContext.addAttribute(Attribute.IS_MATCH_FINISHED, isMatchActive);
        Match match = matchService.findById(id);
        List<Bet> bets = betService.getBetsByMatch(id);
        MatchBetsDto matchBetsDto = createMatchBetDto(match, bets);
        requestContext.addAttribute(Attribute.MATCH_BETS_DTO, matchBetsDto);
        Long accountId = (Long) requestContext.getSessionAttribute(Attribute.ACCOUNT_ID);
        if (accountId != null) {
            String role = (String)requestContext.getSessionAttribute(Attribute.ROLE);
            if (role != null && role.equals("USER")) {
                int balance = userService.getBalance(accountId);
                requestContext.addAttribute(Attribute.MAX_BET, balance);
                requestContext.addAttribute(Attribute.MIN_BET, 1);
            }
        }

        return CommandResult.forward(Page.BET);
    }

    private MatchBetsDto createMatchBetDto(Match match, List<Bet> bets) {
        Long id = match.getId();
        Date date = match.getDate();
        String tournament = match.getTournament();
        String firstTeam = match.getFirstTeam();
        String secondTeam = match.getSecondTeam();
        String winner = match.getWinner();
        Float commission = match.getCommission();
        Integer firstTeamBetsAmount = 0;
        Integer secondTeamBetsAmount = 0;
        for (Bet bet : bets) {
            Long matchId = bet.getMatchId();
            if (matchId.equals(id)) {
                String team = bet.getTeam();
                if (team.equalsIgnoreCase("FIRST")) {
                    firstTeamBetsAmount += bet.getMoneyBet();
                } else {
                    secondTeamBetsAmount += bet.getMoneyBet();
                }
            }
        }
        return new MatchBetsDto(id, date, tournament,
                firstTeam, secondTeam, winner, commission, firstTeamBetsAmount, secondTeamBetsAmount);
    }
}
