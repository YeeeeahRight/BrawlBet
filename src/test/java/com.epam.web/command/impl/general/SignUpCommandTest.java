package com.epam.web.command.impl.general;

import com.epam.web.command.CommandResult;
import com.epam.web.command.impl.general.SignUpCommand;
import com.epam.web.constant.CommandName;
import com.epam.web.constant.Page;
import com.epam.web.constant.Parameter;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.exception.ServiceException;
import com.epam.web.logic.service.account.SignUpService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

public class SignUpCommandTest {
    private static final String VALID_REQUEST_HEADER = "controller?command=sign-up-page";
    private static final String LOGIN_PAGE_COMMAND = "controller?command=" + CommandName.LOGIN_PAGE;
    private static final Map<String, Object> SESSION_ATTRIBUTES = new HashMap<>();
    private static final Map<String, Object> REQUEST_ATTRIBUTES = new HashMap<>();
    private static final String VALID_LOGIN = "loginValid";
    private static final String INVALID_LOGIN = "l*oginValid";
    private static final String VALID_PASSWORD = "password1234";
    private static final String INVALID_PASSWORD = "pas";

    private Map<String, String[]> requestParameters;
    private SignUpService signUpService;

    @Before
    public void initMethod() {
        requestParameters = new HashMap<>();
        requestParameters.put(Parameter.LOGIN, new String[]{VALID_LOGIN});
        requestParameters.put(Parameter.PASSWORD, new String[]{VALID_PASSWORD});
        requestParameters.put(Parameter.REPEATED_PASSWORD, new String[]{VALID_PASSWORD});

        signUpService = Mockito.mock(SignUpService.class);
    }

    @Test
    public void testExecuteShouldReturnRedirectWhenParametersAreValid() throws ServiceException,
            InvalidParametersException {
        //given
        SignUpCommand signUpCommand = new SignUpCommand(signUpService);
        RequestContext requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                requestParameters, SESSION_ATTRIBUTES, VALID_REQUEST_HEADER);
        //when
        CommandResult actual = signUpCommand.execute(requestContext);
        //then
        CommandResult expected = CommandResult.redirect(LOGIN_PAGE_COMMAND);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testExecuteShouldSignUpWhenParametersAreValid() throws ServiceException,
            InvalidParametersException {
        //given
        SignUpCommand signUpCommand = new SignUpCommand(signUpService);
        RequestContext requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                requestParameters, SESSION_ATTRIBUTES, VALID_REQUEST_HEADER);
        //when
        signUpCommand.execute(requestContext);
        //then
        verify(signUpService, times(1)).signUp(anyString(), anyString());
    }

    @Test
    public void testExecuteShouldForwardWhenAccountExist()
            throws ServiceException, InvalidParametersException {
        //given
        SignUpCommand signUpCommand = new SignUpCommand(signUpService);
        RequestContext requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                requestParameters, SESSION_ATTRIBUTES, VALID_REQUEST_HEADER);
        //when
        when(signUpService.isUsernameExist(VALID_LOGIN)).thenReturn(true);
        CommandResult actual = signUpCommand.execute(requestContext);
        //then
        CommandResult expected = CommandResult.forward(Page.SIGN_UP);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testExecuteShouldForwardWhenLoginIsInvalid()
            throws ServiceException, InvalidParametersException {
        //given
        requestParameters.put(Parameter.LOGIN, new String[]{INVALID_LOGIN});
        SignUpCommand signUpCommand = new SignUpCommand(signUpService);
        RequestContext requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                requestParameters, SESSION_ATTRIBUTES, VALID_REQUEST_HEADER);
        //when
        when(signUpService.isUsernameExist(VALID_LOGIN)).thenReturn(true);
        CommandResult actual = signUpCommand.execute(requestContext);
        //then
        CommandResult expected = CommandResult.forward(Page.SIGN_UP);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testExecuteShouldForwardWhenPasswordIsInvalid()
            throws ServiceException, InvalidParametersException {
        //given
        requestParameters.put(Parameter.PASSWORD, new String[]{INVALID_PASSWORD});
        SignUpCommand signUpCommand = new SignUpCommand(signUpService);
        RequestContext requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                requestParameters, SESSION_ATTRIBUTES, VALID_REQUEST_HEADER);
        //when
        when(signUpService.isUsernameExist(VALID_LOGIN)).thenReturn(true);
        CommandResult actual = signUpCommand.execute(requestContext);
        //then
        CommandResult expected = CommandResult.forward(Page.SIGN_UP);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = InvalidParametersException.class)
    public void testExecuteShouldForwardWhenLoginIsNull()
            throws ServiceException, InvalidParametersException {
        //given
        requestParameters.put(Parameter.LOGIN, null);
        SignUpCommand signUpCommand = new SignUpCommand(signUpService);
        RequestContext requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                requestParameters, SESSION_ATTRIBUTES, VALID_REQUEST_HEADER);
        //when
        signUpCommand.execute(requestContext);
    }
}
