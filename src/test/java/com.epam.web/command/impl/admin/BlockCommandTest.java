package com.epam.web.command.impl.admin;

import com.epam.web.command.CommandResult;
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

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class BlockCommandTest {
    private static final String VALID_REQUEST_HEADER = "controller?command=users&page=1";
    private static final Map<String, Object> SESSION_ATTRIBUTES = new HashMap<>();
    private static final Map<String, Object> REQUEST_ATTRIBUTES = new HashMap<>();
    private static final String VALID_ID = "2";
    private static final String INVALID_ID = "invalid";

    private Map<String, String[]> requestParameters;
    private AccountService accountService;

    @Before
    public void initMethod() {
        accountService = Mockito.mock(AccountService.class);
        requestParameters = new HashMap<>();
        requestParameters.put(Parameter.ID, new String[]{VALID_ID});
    }

    @Test
    public void testExecuteShouldReturnRedirectWhenIdIsValid() throws ServiceException,
            InvalidParametersException {
        //given
        BlockCommand blockCommand = new BlockCommand(accountService);
        RequestContext requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                requestParameters, SESSION_ATTRIBUTES, VALID_REQUEST_HEADER);
        //when
        CommandResult actual = blockCommand.execute(requestContext);
        //then
        CommandResult expected = CommandResult.redirect(VALID_REQUEST_HEADER);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testExecuteShouldBlockWhenIdIsValid() throws ServiceException,
            InvalidParametersException {
        //given
        BlockCommand blockCommand = new BlockCommand(accountService);
        RequestContext requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                requestParameters, SESSION_ATTRIBUTES, VALID_REQUEST_HEADER);
        //when
        blockCommand.execute(requestContext);
        //then
        verify(accountService, times(1)).blockById(anyInt());
    }

    //then
    @Test(expected = InvalidParametersException.class)
    public void testExecuteShouldThrowInvalidParametersExceptionRedirectWhenIdIsInvalid()
            throws ServiceException, InvalidParametersException {
        //given
        requestParameters.put(Parameter.ID, new String[]{INVALID_ID});
        BlockCommand blockCommand = new BlockCommand(accountService);
        RequestContext requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                requestParameters, SESSION_ATTRIBUTES, VALID_REQUEST_HEADER);
        //when
        blockCommand.execute(requestContext);
    }
}
