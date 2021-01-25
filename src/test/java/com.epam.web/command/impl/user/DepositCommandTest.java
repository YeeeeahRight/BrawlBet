package com.epam.web.command.impl.user;

import com.epam.web.command.CommandResult;
import com.epam.web.command.impl.user.DepositCommand;
import com.epam.web.constant.Attribute;
import com.epam.web.constant.Parameter;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.exception.ServiceException;
import com.epam.web.logic.service.account.AccountService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class DepositCommandTest {
    private static final String VALID_REQUEST_HEADER = "controller?command=deposit-page";
    private static final Map<String, Object> SESSION_ATTRIBUTES = new HashMap<>();
    private static final Map<String, Object> REQUEST_ATTRIBUTES = new HashMap<>();
    private static final Long VALID_ID = 2L;
    private static final String VALID_MONEY = "50.0";
    private static final String MONEY_OUT_OF_RANGE = "200000.0";
    private static final String MONEY_NOT_NUMBER = "abcd";

    private Map<String, String[]> requestParameters = new HashMap<>();
    private AccountService accountService;

    @BeforeClass
    public static void init() {
        SESSION_ATTRIBUTES.put(Attribute.ACCOUNT_ID, VALID_ID);
    }

    @Before
    public void initMethod() {
        requestParameters = new HashMap<>();
        requestParameters.put(Parameter.MONEY, new String[]{VALID_MONEY});

        accountService = Mockito.mock(AccountService.class);
    }

    @Test
    public void testExecuteShouldReturnRedirectWhenMoneyIsValid() throws ServiceException,
            InvalidParametersException {
        //given
        DepositCommand depositCommand = new DepositCommand(accountService);
        RequestContext requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                requestParameters, SESSION_ATTRIBUTES, VALID_REQUEST_HEADER);
        //when
        CommandResult actual = depositCommand.execute(requestContext);
        //then
        CommandResult expected = CommandResult.redirect(VALID_REQUEST_HEADER);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testExecuteShouldDepositWhenMoneyIsValid() throws ServiceException,
            InvalidParametersException {
        //given
        DepositCommand depositCommand = new DepositCommand(accountService);
        RequestContext requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                requestParameters, SESSION_ATTRIBUTES, VALID_REQUEST_HEADER);
        //when
        depositCommand.execute(requestContext);
        //then
        verify(accountService, times(1)).addMoneyById(anyFloat(), anyInt());
    }

    //then
    @Test(expected = InvalidParametersException.class)
    public void testExecuteShouldThrowInvalidParametersExceptionRedirectWhenMoneyOutOfRange()
            throws ServiceException, InvalidParametersException {
        //given
        requestParameters.put(Parameter.MONEY, new String[]{MONEY_OUT_OF_RANGE});
        DepositCommand depositCommand = new DepositCommand(accountService);
        RequestContext requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                requestParameters, SESSION_ATTRIBUTES, VALID_REQUEST_HEADER);
        //when
        depositCommand.execute(requestContext);
    }

    //then
    @Test(expected = InvalidParametersException.class)
    public void testExecuteShouldThrowInvalidParametersExceptionRedirectWhenMoneyIsNotNumber()
            throws ServiceException, InvalidParametersException {
        //given
        requestParameters.put(Parameter.MONEY, new String[]{MONEY_NOT_NUMBER});
        DepositCommand depositCommand = new DepositCommand(accountService);
        RequestContext requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                requestParameters, SESSION_ATTRIBUTES, VALID_REQUEST_HEADER);
        //when
        depositCommand.execute(requestContext);
    }
}
