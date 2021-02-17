package com.epam.web.command.impl.user;

import com.epam.web.command.CommandResult;
import com.epam.web.constant.Attribute;
import com.epam.web.constant.Parameter;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.exception.ServiceException;
import com.epam.web.logic.service.account.AccountService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

public class DepositCommandTest {
    private static final String REQUEST_HEADER = "controller?command=deposit-page";
    private static final Map<String, Object> SESSION_ATTRIBUTES = new HashMap<>();
    private static final Map<String, Object> REQUEST_ATTRIBUTES = new HashMap<>();
    private static final Map<String, String[]> REQUEST_PARAMETERS = new HashMap<>();
    private static final String DEPOSIT_PARAM_VALUE = "20";
    private static final String INVALID_DEPOSIT_PARAM_VALUE = "200000";
    private static final Long ACCOUNT_ID = 2L;

    static {
        SESSION_ATTRIBUTES.put(Attribute.ACCOUNT_ID, ACCOUNT_ID);
    }

    private AccountService accountService;
    private RequestContext requestContext;
    private DepositCommand depositCommand;

    @Before
    public void initMethod() {
        accountService = Mockito.mock(AccountService.class);

        requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                REQUEST_PARAMETERS, SESSION_ATTRIBUTES, REQUEST_HEADER);
        depositCommand = new DepositCommand(accountService);
    }

    @Test
    public void testExecuteShouldReturnRedirectWhenDepositInRange() throws ServiceException,
            InvalidParametersException {
        //given
        //when
        REQUEST_PARAMETERS.put(Parameter.MONEY, new String[]{DEPOSIT_PARAM_VALUE});
        CommandResult actual = depositCommand.execute(requestContext);
        //then
        CommandResult expected = CommandResult.redirect(REQUEST_HEADER);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testExecuteShouldAddMoneyWhenDepositInRange() throws ServiceException,
            InvalidParametersException {
        //given
        //when
        REQUEST_PARAMETERS.put(Parameter.MONEY, new String[]{DEPOSIT_PARAM_VALUE});
        depositCommand.execute(requestContext);
        //then
        verify(accountService, times(1)).addMoneyById(anyObject(), anyLong());
    }

    @Test(expected = InvalidParametersException.class)
    public void testExecuteShouldThrowInvalidParametersExceptionWhenDepositOutOfRange()
            throws ServiceException, InvalidParametersException {
        //given
        //when
        REQUEST_PARAMETERS.put(Parameter.MONEY, new String[]{INVALID_DEPOSIT_PARAM_VALUE});
        depositCommand.execute(requestContext);
    }
}
