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
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

public class BetCommandTest {
    private static final String REQUEST_HEADER = "controller?command=match&id=1";
    private static final Map<String, Object> SESSION_ATTRIBUTES = new HashMap<>();
    private static final Map<String, Object> REQUEST_ATTRIBUTES = new HashMap<>();
    private static final Map<String, String[]> REQUEST_PARAMETERS = new HashMap<>();
    private static final BigDecimal BALANCE = new BigDecimal("200");
    private static final BigDecimal ZERO_BALANCE = new BigDecimal("0");
    private static final Match MATCH
            = new Match(new Date(), "cup", 0L, 1L);
    private static final String BET_ON_PARAM_VALUE = "FIRST";
    private static final String MONEY_PARAM_VALUE = "20";
    private static final String MATCH_ID = "2";
    private static final Long ACCOUNT_ID = 2L;

    static {
        REQUEST_PARAMETERS.put(Parameter.BET_ON, new String[]{BET_ON_PARAM_VALUE});
        REQUEST_PARAMETERS.put(Parameter.MONEY, new String[]{MONEY_PARAM_VALUE});
        REQUEST_PARAMETERS.put(Parameter.ID, new String[]{MATCH_ID});
        SESSION_ATTRIBUTES.put(Attribute.ACCOUNT_ID, ACCOUNT_ID);
    }

    private MatchService matchService;
    private BetService betService;
    private AccountService accountService;
    private RequestContext requestContext;
    private BetCommand betCommand;

    @Before
    public void initMethod() {
        matchService = Mockito.mock(MatchService.class);
        betService = Mockito.mock(BetService.class);
        accountService = Mockito.mock(AccountService.class);

        requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                REQUEST_PARAMETERS, SESSION_ATTRIBUTES, REQUEST_HEADER);
        betCommand = new BetCommand(betService, accountService, matchService);
    }

    @Test
    public void testExecuteShouldReturnRedirectWhenBalanceIsMoreThanBet() throws ServiceException,
            InvalidParametersException {
        //given
        //when
        when(accountService.getBalance(anyLong())).thenReturn(BALANCE);
        when(matchService.getMatchById(anyLong())).thenReturn(MATCH);
        CommandResult actual = betCommand.execute(requestContext);
        //then
        CommandResult expected = CommandResult.redirect(REQUEST_HEADER);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testExecuteShouldSaveBetWhenBalanceIsMoreThanBet() throws ServiceException,
            InvalidParametersException {
        //given
        //when
        when(accountService.getBalance(anyLong())).thenReturn(BALANCE);
        when(matchService.getMatchById(anyLong())).thenReturn(MATCH);
        betCommand.execute(requestContext);
        //then
        verify(betService, times(1)).saveBet(Matchers.any());
    }

    @Test(expected = InvalidParametersException.class)
    public void testExecuteShouldThrowInvalidParametersExceptionWhenZeroBalance()
            throws ServiceException, InvalidParametersException {
        //given
        //when
        when(accountService.getBalance(anyLong())).thenReturn(ZERO_BALANCE);
        when(matchService.getMatchById(anyLong())).thenReturn(MATCH);
        betCommand.execute(requestContext);
    }
}
