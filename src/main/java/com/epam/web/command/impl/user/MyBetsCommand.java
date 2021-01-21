package com.epam.web.command.impl.user;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.constant.Attribute;
import com.epam.web.constant.Page;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.exception.ServiceException;
import com.epam.web.logic.calculator.BetCalculator;
import com.epam.web.logic.service.bet.BetService;
import com.epam.web.logic.service.match.MatchService;
import com.epam.web.model.entity.Bet;
import com.epam.web.model.entity.Match;
import com.epam.web.model.entity.dto.BetMatchDto;
import com.epam.web.model.enumeration.Team;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class MyBetsCommand implements Command {
    private static final String NO_WINNER = "NONE";
    private final BetService betService;
    private final MatchService matchService;
    private final BetCalculator betCalculator;

    public MyBetsCommand(BetService betService, MatchService matchService, BetCalculator betCalculator) {
        this.betService = betService;
        this.matchService = matchService;
        this.betCalculator = betCalculator;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException, InvalidParametersException {
        long accountId = (Long) requestContext.getSessionAttribute(Attribute.ACCOUNT_ID);
        List<Bet> accountBets = betService.getBetsByAccountId(accountId);
        List<BetMatchDto> betMatchDtoList = buildBetMatchDtoList(accountBets);
        sortBetMatchDtoList(betMatchDtoList);
        requestContext.addAttribute(Attribute.BET_MATCH_DTO_LIST, betMatchDtoList);

        return CommandResult.forward(Page.MY_BETS);
    }

    private List<BetMatchDto> buildBetMatchDtoList(List<Bet> accountBets) throws ServiceException {
        List<BetMatchDto> betMatchDtoList = new ArrayList<>();
        for (Bet bet : accountBets) {
            float moneyBet = bet.getMoneyBet();
            float moneyReceived = bet.getMoneyReceived();
            Long matchId = bet.getMatchId();
            Match match = matchService.findById(matchId);
            Date betDate = bet.getBetDate();
            String tournament = match.getTournament();
            float firstTeamBets = match.getFirstTeamBets();
            float secondTeamBets = match.getSecondTeamBets();
            int firstPercent = betCalculator.calculatePercent(Team.FIRST, firstTeamBets, secondTeamBets);
            int secondPercent = betCalculator.calculatePercent(Team.SECOND, firstTeamBets, secondTeamBets);
            int sumPercents = firstPercent + secondPercent;
            if (sumPercents == 101) {
                firstPercent--;
            }
            String winner = match.getWinner();
            String firstTeam = match.getFirstTeam();
            String secondTeam = match.getSecondTeam();
            String winnerTeam = bet.getTeam() == Team.FIRST ? firstTeam : secondTeam;
            BetMatchDto betMatchDto = new BetMatchDto(matchId, winnerTeam, moneyBet, moneyReceived, betDate,
                    tournament, firstTeam, secondTeam, firstPercent, secondPercent, winner);
            betMatchDtoList.add(betMatchDto);
        }
        return betMatchDtoList;
    }

    private void sortBetMatchDtoList(List<BetMatchDto> betMatchDtoList) {
        betMatchDtoList.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));
        List<BetMatchDto> closedBetMatchDtoList = new ArrayList<>();
        Iterator<BetMatchDto> betMatchDtoIterator = betMatchDtoList.iterator();
        while (betMatchDtoIterator.hasNext()) {
            BetMatchDto betMatchDto = betMatchDtoIterator.next();
            if (!betMatchDto.getWinner().equalsIgnoreCase(NO_WINNER)) {
                closedBetMatchDtoList.add(betMatchDto);
                betMatchDtoIterator.remove();
            }
        }
        betMatchDtoList.addAll(closedBetMatchDtoList);
    }
}
