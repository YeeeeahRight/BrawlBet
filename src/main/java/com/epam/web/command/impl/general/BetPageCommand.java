package com.epam.web.command.impl.general;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.constant.Attribute;
import com.epam.web.constant.Page;
import com.epam.web.constant.Parameter;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.exception.ServiceException;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.logic.service.account.AccountService;
import com.epam.web.logic.service.bet.BetService;
import com.epam.web.logic.service.match.MatchService;
import com.epam.web.model.entity.Bet;
import com.epam.web.model.entity.Match;
import com.epam.web.model.entity.dto.MatchBetsDto;
import com.epam.web.model.enumeration.AccountRole;
import com.epam.web.model.enumeration.Team;

import java.util.Date;
import java.util.List;

public class BetPageCommand implements Command {
    private final MatchService matchService;
    private final BetService betService;
    private final AccountService accountService;

    public BetPageCommand(MatchService matchService, BetService betService,
                          AccountService accountService) {
        this.matchService = matchService;
        this.betService = betService;
        this.accountService = accountService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext)
            throws ServiceException, InvalidParametersException {
        String idStr = requestContext.getRequestParameter(Parameter.ID);
        long id;
        try {
            id = Long.parseLong(idStr);
        } catch (NumberFormatException e) {
            throw new InvalidParametersException("Invalid id match parameter in request.");
        }
        boolean isMatchFinished = matchService.isFinishedMatch(id);
        requestContext.addAttribute(Attribute.IS_MATCH_FINISHED, isMatchFinished);
        Match match = matchService.findById(id);
        List<Bet> bets = betService.getBetsByMatch(id);
        MatchBetsDto matchBetsDto = createMatchBetDto(match, bets);
        requestContext.addAttribute(Attribute.MATCH_BETS_DTO, matchBetsDto);
        Long accountId = (Long) requestContext.getSessionAttribute(Attribute.ACCOUNT_ID);
        if (accountId != null) {
            AccountRole role = (AccountRole) requestContext.getSessionAttribute(Attribute.ROLE);
            if (role == AccountRole.USER) {
                float balance = accountService.getBalance(accountId);
                requestContext.addAttribute(Attribute.MAX_BET, balance);
                requestContext.addAttribute(Attribute.MIN_BET, 0.1);
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
        Float firstTeamBetsAmount = 0.0f;
        Float secondTeamBetsAmount = 0.0f;
        for (Bet bet : bets) {
            Long matchId = bet.getMatchId();
            if (matchId.equals(id)) {
                Team team = bet.getTeam();
                Float moneyBet = bet.getMoneyBet();
                if (team == Team.FIRST) {
                    firstTeamBetsAmount += moneyBet;
                } else {
                    secondTeamBetsAmount += moneyBet;
                }
            }
        }
        return new MatchBetsDto(id, date, tournament,
                firstTeam, secondTeam, winner, commission, firstTeamBetsAmount, secondTeamBetsAmount);
    }
}
