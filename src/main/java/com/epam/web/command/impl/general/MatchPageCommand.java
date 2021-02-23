package com.epam.web.command.impl.general;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.command.util.MatchDtoCommandHelper;
import com.epam.web.command.util.ParameterExtractor;
import com.epam.web.constant.Attribute;
import com.epam.web.constant.Page;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.exception.ServiceException;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.logic.calculator.BetCalculator;
import com.epam.web.logic.service.account.AccountService;
import com.epam.web.logic.service.match.MatchService;
import com.epam.web.logic.service.match.MatchType;
import com.epam.web.model.entity.Match;
import com.epam.web.model.entity.dto.MatchDto;
import com.epam.web.model.enumeration.AccountRole;
import com.epam.web.model.enumeration.MatchTeamNumber;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MatchPageCommand implements Command {
    private static final float MIN_BET = 0.1f;
    private static final int FIRST_ELEMENT_INDEX = 0;

    private final MatchService matchService;
    private final AccountService accountService;
    private final BetCalculator betCalculator;
    private final MatchDtoCommandHelper matchDtoCommandHelper;

    public MatchPageCommand(MatchService matchService, AccountService accountService,
                            BetCalculator betCalculator, MatchDtoCommandHelper matchDtoCommandHelper) {
        this.matchService = matchService;
        this.accountService = accountService;
        this.betCalculator = betCalculator;
        this.matchDtoCommandHelper = matchDtoCommandHelper;
    }

    @Override
    public CommandResult execute(RequestContext requestContext)
            throws ServiceException, InvalidParametersException {
        long id = ParameterExtractor.extractId(requestContext);
        Match match = matchService.getMatchById(id);
        List<Match> matches = Collections.singletonList(match);
        List<MatchDto> matchDtoList = matchDtoCommandHelper.buildMatchDtoList(matches, MatchType.ANY);
        MatchDto matchDto = matchDtoList.get(FIRST_ELEMENT_INDEX);
        requestContext.addAttribute(Attribute.MATCH_DTO, matchDto);
        addMatchStatusAttribute(requestContext, match);
        addAdditionalAttributes(requestContext, match);

        return CommandResult.forward(Page.MATCH);
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
        float maxBet = accountService.getBalance(accountId).floatValue();
        if (maxBet < MIN_BET) {
            maxBet = 0.0f;
        }
        requestContext.addAttribute(Attribute.MAX_BET, maxBet);
        requestContext.addAttribute(Attribute.MIN_BET, MIN_BET);
        float firstCoefficient = betCalculator.calculateCoefficient(MatchTeamNumber.FIRST,
                match.getFirstTeamBets(), match.getSecondTeamBets()).floatValue();
        float secondCoefficient = betCalculator.calculateCoefficient(MatchTeamNumber.SECOND,
                match.getFirstTeamBets(), match.getSecondTeamBets()).floatValue();
        float commission = match.getCommission();
        firstCoefficient -= firstCoefficient * commission / 100;
        secondCoefficient -= secondCoefficient * commission / 100;
        requestContext.addAttribute(Attribute.FIRST_COEFFICIENT, firstCoefficient + 1);
        requestContext.addAttribute(Attribute.SECOND_COEFFICIENT, secondCoefficient + 1);
    }
}
