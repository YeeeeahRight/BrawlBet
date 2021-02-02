package com.epam.web.command.impl.user;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.constant.Attribute;
import com.epam.web.constant.Page;
import com.epam.web.constant.Parameter;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.exception.ServiceException;
import com.epam.web.logic.calculator.BetCalculator;
import com.epam.web.logic.service.bet.BetService;
import com.epam.web.logic.service.match.MatchService;
import com.epam.web.logic.service.team.TeamService;
import com.epam.web.model.entity.Bet;
import com.epam.web.model.entity.Match;
import com.epam.web.model.entity.dto.BetMatchDto;
import com.epam.web.model.enumeration.MatchTeamNumber;

import java.util.ArrayList;
import java.util.List;

public class MyBetsCommand implements Command {
    private static final int MAX_BETS_PAGE = 6;

    private final BetService betService;
    private final MatchService matchService;
    private final TeamService teamService;
    private final BetCalculator betCalculator;

    public MyBetsCommand(BetService betService, MatchService matchService,
                         TeamService teamService, BetCalculator betCalculator) {
        this.betService = betService;
        this.matchService = matchService;
        this.teamService = teamService;
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
        int firstMatchIndex = MAX_BETS_PAGE * (page - 1);
        long accountId = (Long) requestContext.getSessionAttribute(Attribute.ACCOUNT_ID);
        List<Bet> accountBets = betService.getBetsByAccountIdRange(accountId, firstMatchIndex, MAX_BETS_PAGE);
        if (accountBets.size() == 0 && page > 1) {
            throw new InvalidParametersException("No bets on this page");
        }
        List<BetMatchDto> betMatchDtoList = buildBetMatchDtoList(accountBets);
        sortBetMatchDtoList(betMatchDtoList);
        requestContext.addAttribute(Attribute.BET_MATCH_DTO_LIST, betMatchDtoList);
        requestContext.addAttribute(Attribute.CURRENT_PAGE, page);
        int maxPage = ((betService.getBetsAmountByAccountId(accountId) - 1) / MAX_BETS_PAGE) + 1;
        requestContext.addAttribute(Attribute.MAX_PAGE, maxPage);

        return CommandResult.forward(Page.MY_BETS);
    }

    private List<BetMatchDto> buildBetMatchDtoList(List<Bet> accountBets) throws ServiceException {
        List<BetMatchDto> betMatchDtoList = new ArrayList<>();
        BetMatchDto.BetMatchDtoBuilder betMatchDtoBuilder = new BetMatchDto.BetMatchDtoBuilder();
        for (Bet bet : accountBets) {
            Long matchId = bet.getMatchId();
            Match match = matchService.getMatchById(matchId);
            String tournament = match.getTournament();
            betMatchDtoBuilder = betMatchDtoBuilder.setGeneralAttributes(bet, tournament);
            betMatchDtoBuilder = setTeams(betMatchDtoBuilder, match, bet.getTeamId());
            betMatchDtoBuilder = setPercents(betMatchDtoBuilder,
                    match.getFirstTeamBets().floatValue(), match.getSecondTeamBets().floatValue());
            BetMatchDto betMatchDto = betMatchDtoBuilder.build();
            betMatchDtoList.add(betMatchDto);
        }
        return betMatchDtoList;
    }

    private BetMatchDto.BetMatchDtoBuilder setTeams(BetMatchDto.BetMatchDtoBuilder betMatchDtoBuilder,
                                         Match match, long teamOnBetId) throws ServiceException {
        long firstTeamId = match.getFirstTeamId();
        long secondTeamId = match.getSecondTeamId();
        String firstTeamName = teamService.getTeamNameById(firstTeamId);
        String secondTeamName = teamService.getTeamNameById(secondTeamId);
        MatchTeamNumber winnerTeam = match.getWinnerTeam();
        String winnerTeamName = findWinnerTeamName(winnerTeam, firstTeamName, secondTeamName);
        String teamOnBet = teamService.getTeamNameById(teamOnBetId);

        return betMatchDtoBuilder.setTeams(firstTeamName, secondTeamName, teamOnBet, winnerTeamName);
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

    private BetMatchDto.BetMatchDtoBuilder setPercents(BetMatchDto.BetMatchDtoBuilder betMatchDtoBuilder,
                                                       float firstTeamBets, float secondTeamBets) {
        int firstPercent = 0;
        int secondPercent = 0;
        if (firstTeamBets + secondTeamBets != 0) {
            firstPercent = betCalculator.calculatePercent(MatchTeamNumber.FIRST,
                    firstTeamBets, secondTeamBets);
            secondPercent = 100 - firstPercent;
        }
        return betMatchDtoBuilder.setPercents(firstPercent, secondPercent);
    }

    private void sortBetMatchDtoList(List<BetMatchDto> betMatchDtoList) {
        betMatchDtoList.sort((o1, o2) -> {
            boolean isFirstClosed = !o1.getWinner().equals("NONE");
            boolean isSecondClosed = !o2.getWinner().equals("NONE");
            return isFirstClosed == isSecondClosed ? 0 : (isFirstClosed ? 1 : -1);
        });
    }
}
