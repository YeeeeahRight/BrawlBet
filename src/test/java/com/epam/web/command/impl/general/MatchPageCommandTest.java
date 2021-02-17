package com.epam.web.command.impl.general;

import com.epam.web.command.CommandResult;
import com.epam.web.command.util.MatchDtoCommandHelper;
import com.epam.web.constant.Attribute;
import com.epam.web.constant.Page;
import com.epam.web.constant.Parameter;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.exception.ServiceException;
import com.epam.web.logic.calculator.BetCalculator;
import com.epam.web.logic.service.account.AccountService;
import com.epam.web.logic.service.match.MatchService;
import com.epam.web.model.entity.Match;
import com.epam.web.model.entity.dto.MatchDto;
import com.epam.web.model.enumeration.AccountRole;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.*;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

public class MatchPageCommandTest {
    private static final String REQUEST_HEADER = "controller?command=matches&page=1";
    private static final Map<String, Object> SESSION_ATTRIBUTES = new HashMap<>();
    private static final Map<String, Object> REQUEST_ATTRIBUTES = new HashMap<>();
    private static final Map<String, String[]> REQUEST_PARAMETERS = new HashMap<>();
    private static final CommandResult EXPECTED_COMMAND_RESULT = CommandResult.forward(Page.MATCH);
    private static final String ID_PARAM_STR = "2";
    private static final Long ID_PARAM = 2L;
    private static final MatchDto MATCH_DTO;
    private static final List<MatchDto> MATCH_DTO_LIST;

    private static final Match MATCH_BY_ID =
            new Match(new Date(), "cup", ID_PARAM, ID_PARAM);

    static {
        MATCH_DTO = new MatchDto.MatchDtoBuilder().build();
        MATCH_DTO_LIST = Collections.singletonList(MATCH_DTO);
        REQUEST_PARAMETERS.put(Parameter.ID, new String[]{ID_PARAM_STR});
        SESSION_ATTRIBUTES.put(Attribute.ACCOUNT_ID, ID_PARAM);
    }


    private MatchDtoCommandHelper matchDtoCommandHelper;
    private MatchService matchService;
    private AccountService accountService;
    private BetCalculator betCalculator;
    private RequestContext requestContext;
    private MatchPageCommand matchPageCommand;

    @Before
    public void initMethod() {
        SESSION_ATTRIBUTES.put(Attribute.ROLE, null);
        requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                REQUEST_PARAMETERS, SESSION_ATTRIBUTES, REQUEST_HEADER);
        matchDtoCommandHelper = Mockito.mock(MatchDtoCommandHelper.class);
        matchService = Mockito.mock(MatchService.class);
        accountService = Mockito.mock(AccountService.class);
        betCalculator = Mockito.mock(BetCalculator.class);
        matchPageCommand =
                new MatchPageCommand(matchService, accountService, betCalculator, matchDtoCommandHelper);
    }

    @Test
    public void testExecuteShouldReturnForward() throws ServiceException,
            InvalidParametersException {
        //given
        //when
        when(matchService.getMatchById(anyLong())).thenReturn(MATCH_BY_ID);
        when(matchDtoCommandHelper.buildMatchDtoList(anyObject(), anyObject()))
                .thenReturn(MATCH_DTO_LIST);
        CommandResult actual = matchPageCommand.execute(requestContext);
        //then
        Assert.assertEquals(EXPECTED_COMMAND_RESULT, actual);
    }

    @Test
    public void testExecuteShouldAddMatchDtoAttribute() throws ServiceException,
            InvalidParametersException {
        //given
        //when
        when(matchService.getMatchById(anyLong())).thenReturn(MATCH_BY_ID);
        when(matchDtoCommandHelper.buildMatchDtoList(anyObject(), anyObject()))
                .thenReturn(MATCH_DTO_LIST);
        matchPageCommand.execute(requestContext);
        //then
        boolean isMatchDtoAttributeExist =
                requestContext.getRequestAttribute(Attribute.MATCH_DTO) != null;
        Assert.assertTrue(isMatchDtoAttributeExist);
    }

    @Test
    public void testExecuteShouldAddIsMatchUnacceptedAttributeWhenMatchUnaccepted()
            throws ServiceException, InvalidParametersException {
        //given
        //when
        when(matchService.getMatchById(anyLong())).thenReturn(MATCH_BY_ID);
        when(matchDtoCommandHelper.buildMatchDtoList(anyObject(), anyObject()))
                .thenReturn(MATCH_DTO_LIST);
        matchPageCommand.execute(requestContext);
        //then
        boolean isMatchUnacceptedAttributeExist =
                requestContext.getRequestAttribute(Attribute.IS_MATCH_UNACCEPTED) != null;
        Assert.assertTrue(isMatchUnacceptedAttributeExist);
    }

    @Test
    public void testExecuteShouldAddBetAttributesWhenRoleIsUser() throws ServiceException,
            InvalidParametersException {
        //given
        //when
        SESSION_ATTRIBUTES.put(Attribute.ROLE, AccountRole.USER);
        when(accountService.getBalance(anyLong())).thenReturn(BigDecimal.TEN);
        when(matchService.getMatchById(anyLong())).thenReturn(MATCH_BY_ID);
        when(matchDtoCommandHelper.buildMatchDtoList(anyObject(), anyObject()))
                .thenReturn(MATCH_DTO_LIST);
        when(betCalculator.calculateCoefficient(anyObject(),anyObject(), anyObject()))
                .thenReturn(BigDecimal.TEN);
        matchPageCommand.execute(requestContext);
        //then
        Set<String> sessionAttributesNames = requestContext.getRequestAttributeNames();
        boolean isMaxMinBetAttributesExists =
                sessionAttributesNames.contains(Attribute.MAX_BET) &&
                sessionAttributesNames.contains(Attribute.MIN_BET);
        boolean isCoefficientAttributesExists =
                sessionAttributesNames.contains(Attribute.FIRST_COEFFICIENT) &&
                sessionAttributesNames.contains(Attribute.SECOND_COEFFICIENT);
        Assert.assertTrue(isMaxMinBetAttributesExists && isCoefficientAttributesExists);
    }
}
