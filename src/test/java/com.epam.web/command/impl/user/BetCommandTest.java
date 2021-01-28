package com.epam.web.command.impl.user;

import com.epam.web.command.CommandResult;
import com.epam.web.constant.Attribute;
import com.epam.web.constant.Parameter;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.exception.ServiceException;
import com.epam.web.logic.service.account.AccountService;
import com.epam.web.logic.service.bet.BetService;
import com.epam.web.logic.service.match.MatchService;
import com.epam.web.model.entity.Match;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

public class BetCommandTest {
    private static final String VALID_REQUEST_HEADER = "controller?command=match-page";
    private static final Map<String, Object> SESSION_ATTRIBUTES = new HashMap<>();
    private static final Map<String, Object> REQUEST_ATTRIBUTES = new HashMap<>();
    private static final Long MATCH_TEAM_ID = 2L;
    private static final Match MATCH_WITH_TEAMS_IDS =
            new Match(null, null, MATCH_TEAM_ID, MATCH_TEAM_ID);
    private static final Long VALID_ACCOUNT_ID = 3L;
    private static final String VALID_MATCH_ID_STR = "4";
    private static final Long VALID_MATCH_ID = 4L;
    private static final String VALID_TEAM = "FIRST";
    private static final String INVALID_TEAM = "INVALIDDDDD";
    private static final String VALID_MONEY = "50.0";
    private static final String MONEY_NOT_NUMBER = "in.valid";

    private Map<String, String[]> requestParameters;
    private BetService betService;
    private AccountService accountService;
    private MatchService matchService;

    @BeforeClass
    public static void init() {
        SESSION_ATTRIBUTES.put(Attribute.ACCOUNT_ID, VALID_ACCOUNT_ID);
    }

    @Before
    public void initMethod() {
        requestParameters = new HashMap<>();
        requestParameters.put(Parameter.BET_ON, new String[]{VALID_TEAM});
        requestParameters.put(Parameter.ID, new String[]{VALID_MATCH_ID_STR});
        requestParameters.put(Parameter.MONEY, new String[]{VALID_MONEY});

        betService = Mockito.mock(BetService.class);
        accountService = Mockito.mock(AccountService.class);
        matchService = Mockito.mock(MatchService.class);
    }

    @Test
    public void testExecuteShouldReturnRedirectWhenParametersAreValid() throws ServiceException,
            InvalidParametersException {
        //given
        BetCommand betCommand = new BetCommand(betService, accountService, matchService);
        RequestContext requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                requestParameters, SESSION_ATTRIBUTES, VALID_REQUEST_HEADER);
        //when
        when(accountService.getBalance(VALID_ACCOUNT_ID)).thenReturn(Float.MAX_VALUE);
        when(matchService.getMatchById(VALID_MATCH_ID)).thenReturn(MATCH_WITH_TEAMS_IDS);
        CommandResult actual = betCommand.execute(requestContext);
        //then
        CommandResult expected = CommandResult.redirect(VALID_REQUEST_HEADER);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testExecuteShouldBetWhenMatchParametersAreValid() throws ServiceException,
            InvalidParametersException {
        //given
        BetCommand betCommand = new BetCommand(betService, accountService, matchService);
        RequestContext requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                requestParameters, SESSION_ATTRIBUTES, VALID_REQUEST_HEADER);
        //when
        when(accountService.getBalance(VALID_ACCOUNT_ID)).thenReturn(Float.MAX_VALUE);
        when(matchService.getMatchById(VALID_MATCH_ID)).thenReturn(MATCH_WITH_TEAMS_IDS);
        betCommand.execute(requestContext);
        //then
        verify(betService, times(1)).saveBet(anyObject());
    }

    //then
    @Test(expected = InvalidParametersException.class)
    public void testExecuteShouldThrowInvalidParametersExceptionWhenMoneyIsNotNumber()
            throws ServiceException, InvalidParametersException {
        //given
        requestParameters.put(Parameter.MONEY, new String[]{MONEY_NOT_NUMBER});
        BetCommand betCommand = new BetCommand(betService, accountService, matchService);
        RequestContext requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                requestParameters, SESSION_ATTRIBUTES, VALID_REQUEST_HEADER);
        //when
        betCommand.execute(requestContext);
    }

    //then
    @Test(expected = InvalidParametersException.class)
    public void testExecuteShouldThrowInvalidParametersExceptionWhenTeamIsInvalid()
            throws ServiceException, InvalidParametersException {
        //given
        requestParameters.put(Parameter.MONEY, new String[]{VALID_MONEY});
        requestParameters.put(Parameter.BET_ON, new String[]{INVALID_TEAM});
        BetCommand betCommand = new BetCommand(betService, accountService, matchService);
        RequestContext requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                requestParameters, SESSION_ATTRIBUTES, VALID_REQUEST_HEADER);
        //when
        betCommand.execute(requestContext);
    }
}
