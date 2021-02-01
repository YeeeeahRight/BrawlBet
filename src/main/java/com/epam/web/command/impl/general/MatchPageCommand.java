package com.epam.web.command.impl.general;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.constant.Attribute;
import com.epam.web.constant.Page;
import com.epam.web.constant.Parameter;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.exception.ServiceException;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.logic.calculator.BetCalculator;
import com.epam.web.logic.service.account.AccountService;
import com.epam.web.logic.service.match.MatchService;
import com.epam.web.logic.service.team.TeamService;
import com.epam.web.model.entity.Match;
import com.epam.web.model.entity.dto.MatchDto;
import com.epam.web.model.enumeration.AccountRole;
import com.epam.web.model.enumeration.MatchTeamNumber;

import java.util.Date;

public class MatchPageCommand implements Command {
    private static final float MIN_BET = 0.1f;

    private final MatchService matchService;
    private final AccountService accountService;
    private final TeamService teamService;
    private final BetCalculator betCalculator;

    public MatchPageCommand(MatchService matchService, AccountService accountService,
                            TeamService teamService, BetCalculator betCalculator) {
        this.matchService = matchService;
        this.accountService = accountService;
        this.teamService = teamService;
        this.betCalculator = betCalculator;
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
        Match match = matchService.getMatchById(id);
        MatchDto matchDto = buildMatchDto(match);
        requestContext.addAttribute(Attribute.MATCH_DTO, matchDto);
        addMatchStatusAttribute(requestContext, match);
        addAdditionalAttributes(requestContext, match);

        return CommandResult.forward(Page.MATCH);
    }

    private MatchDto buildMatchDto(Match match) throws ServiceException {
        long firstTeamId = match.getFirstTeamId();
        long secondTeamId = match.getSecondTeamId();
        String firstTeamName = teamService.getTeamNameById(firstTeamId);
        String secondTeamName = teamService.getTeamNameById(secondTeamId);
        MatchTeamNumber winnerTeam = match.getWinnerTeam();
        String winnerTeamName = findWinnerTeamName(winnerTeam, firstTeamName, secondTeamName);
        MatchDto.MatchDtoBuilder matchDtoBuilder = new MatchDto.MatchDtoBuilder();
        matchDtoBuilder = matchDtoBuilder.setGeneralFields(match,
                firstTeamName, secondTeamName).setWinner(winnerTeamName);
        matchDtoBuilder = setPercents(matchDtoBuilder,
                match.getFirstTeamBets(), match.getSecondTeamBets())
                .setCommission(match.getCommission());
        return matchDtoBuilder.build();
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

    private MatchDto.MatchDtoBuilder setPercents(MatchDto.MatchDtoBuilder matchDtoBuilder,
                                                       float firstTeamBets, float secondTeamBets) {
        int firstPercent = 0;
        int secondPercent = 0;
        if (firstTeamBets + secondTeamBets != 0) {
            firstPercent = betCalculator.calculatePercent(MatchTeamNumber.FIRST,
                    firstTeamBets, secondTeamBets);
            secondPercent = 100 - firstPercent;
        }
        return matchDtoBuilder.setPercents(firstPercent, secondPercent);
    }

    private void addMatchStatusAttribute(RequestContext requestContext, Match match) {
        boolean isMatchClosed = match.isClosed();
        if (isMatchClosed) {
            requestContext.addAttribute(Attribute.IS_MATCH_CLOSED, true);
        } else {
            boolean isMatchUnaccepted = match.getCommission() == 0.0f;
            if (isMatchUnaccepted) {
                requestContext.addAttribute(Attribute.IS_MATCH_UNACCEPTED, true);
            } else {
                boolean isMatchFinished = isFinishedMatch(match);
                requestContext.addAttribute(Attribute.IS_MATCH_FINISHED, isMatchFinished);
            }
        }
    }

    private boolean isFinishedMatch(Match match) {
        long matchTime = match.getDate().getTime();
        long currentTime = new Date().getTime();
        return currentTime >= matchTime;
    }

    private void addAdditionalAttributes(RequestContext requestContext, Match match) throws ServiceException {
        Long accountId = (Long) requestContext.getSessionAttribute(Attribute.ACCOUNT_ID);
        if (accountId != null) {
            AccountRole role = (AccountRole) requestContext.getSessionAttribute(Attribute.ROLE);
            if (role == AccountRole.USER) {
                addBetAttributes(requestContext, accountId, match);
            }
        }
    }

    private void addBetAttributes(RequestContext requestContext, long accountId,
                                  Match match) throws ServiceException {
        float maxBet = accountService.getBalance(accountId);
        if (maxBet < MIN_BET) {
            maxBet = 0f;
        }
        requestContext.addAttribute(Attribute.MAX_BET, maxBet);
        requestContext.addAttribute(Attribute.MIN_BET, MIN_BET);
        float firstCoefficient = betCalculator.calculateCoefficient(MatchTeamNumber.FIRST,
                match.getFirstTeamBets(), match.getSecondTeamBets());
        float secondCoefficient = betCalculator.calculateCoefficient(MatchTeamNumber.SECOND,
                match.getFirstTeamBets(), match.getSecondTeamBets());
        float commission = match.getCommission();
        firstCoefficient -= firstCoefficient * commission / 100;
        secondCoefficient -= secondCoefficient * commission / 100;
        requestContext.addAttribute(Attribute.FIRST_COEFFICIENT, firstCoefficient + 1);
        requestContext.addAttribute(Attribute.SECOND_COEFFICIENT, secondCoefficient + 1);
    }
}
