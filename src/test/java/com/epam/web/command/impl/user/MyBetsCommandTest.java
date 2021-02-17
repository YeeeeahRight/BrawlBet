package com.epam.web.command.impl.user;

import com.epam.web.command.CommandResult;
import com.epam.web.command.impl.admin.TeamsCommand;
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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.*;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

public class MyBetsCommandTest {
    private static final String VALID_REQUEST_HEADER = "controller?command=home-page&page=1";
    private static final Map<String, Object> SESSION_ATTRIBUTES = new HashMap<>();
    private static final Map<String, Object> REQUEST_ATTRIBUTES = new HashMap<>();
    private static final Map<String, String[]> REQUEST_PARAMETERS = new HashMap<>();
    private static final CommandResult EXPECTED_COMMAND_RESULT = CommandResult.forward(Page.MY_BETS);
    private static final Match MATCH =
            new Match(new Date(), "Cup", 0L, 1L);
    private static final List<Bet> BETS;
    private static final String PAGE_PARAM = "2";
    private static final Long ACCOUNT_ID = 2L;

    static {
        Bet bet = new Bet(2L, 2L, new BigDecimal("20"), 2L, new Date());
        BETS = Collections.singletonList(bet);
        REQUEST_PARAMETERS.put(Parameter.PAGE, new String[]{PAGE_PARAM});
        SESSION_ATTRIBUTES.put(Attribute.ACCOUNT_ID, ACCOUNT_ID);
    }

    private BetService betService;
    private MatchService matchService;
    private TeamService teamService;
    private BetCalculator betCalculator;
    private RequestContext requestContext;
    private MyBetsCommand myBetsCommand;

    @Before
    public void initMethod() {
        teamService = Mockito.mock(TeamService.class);
        betCalculator = Mockito.mock(BetCalculator.class);
        matchService = Mockito.mock(MatchService.class);
        betService = Mockito.mock(BetService.class);

        requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                REQUEST_PARAMETERS, SESSION_ATTRIBUTES, VALID_REQUEST_HEADER);
        myBetsCommand = new MyBetsCommand(betService, matchService, teamService, betCalculator);
    }

    @Test
    public void testExecuteShouldReturnForward() throws ServiceException,
            InvalidParametersException {
        //given
        //when
        when(betService.getBetsByAccountIdRange(anyLong(), anyInt(), anyInt())).thenReturn(BETS);
        when(matchService.getMatchById(anyLong())).thenReturn(MATCH);
        CommandResult actual = myBetsCommand.execute(requestContext);
        //then
        Assert.assertEquals(EXPECTED_COMMAND_RESULT, actual);
    }

    @Test
    public void testExecuteShouldAddBetMatchDtoListAttribute() throws ServiceException,
            InvalidParametersException {
        //given
        //when
        when(betService.getBetsByAccountIdRange(anyLong(), anyInt(), anyInt())).thenReturn(BETS);
        when(matchService.getMatchById(anyLong())).thenReturn(MATCH);
        myBetsCommand.execute(requestContext);
        //then
        boolean isMatchDtoAttributeExist =
                requestContext.getRequestAttribute(Attribute.BET_MATCH_DTO_LIST) != null;
        Assert.assertTrue(isMatchDtoAttributeExist);
    }

    @Test
    public void testExecuteShouldAddCurrentPageAttribute() throws ServiceException,
            InvalidParametersException {
        //given
        //when
        when(betService.getBetsByAccountIdRange(anyLong(), anyInt(), anyInt())).thenReturn(BETS);
        when(matchService.getMatchById(anyLong())).thenReturn(MATCH);
        myBetsCommand.execute(requestContext);
        //then
        boolean isMatchDtoAttributeExist =
                requestContext.getRequestAttribute(Attribute.CURRENT_PAGE) != null;
        Assert.assertTrue(isMatchDtoAttributeExist);
    }

    @Test
    public void testExecuteShouldAddMaxPageAttribute() throws ServiceException,
            InvalidParametersException {
        //given
        //when
        when(betService.getBetsByAccountIdRange(anyLong(), anyInt(), anyInt())).thenReturn(BETS);
        when(matchService.getMatchById(anyLong())).thenReturn(MATCH);
        myBetsCommand.execute(requestContext);
        //then
        boolean isMatchDtoAttributeExist =
                requestContext.getRequestAttribute(Attribute.MAX_PAGE) != null;
        Assert.assertTrue(isMatchDtoAttributeExist);
    }

    @Test(expected = InvalidParametersException.class)
    public void testExecuteShouldThrowInvalidParametersExceptionWhenBetsEmpty()
            throws ServiceException, InvalidParametersException {
        //given
        //when
        when(betService.getBetsByAccountIdRange(anyLong(), anyInt(), anyInt())).thenReturn(Collections.emptyList());
        myBetsCommand.execute(requestContext);
    }
}
