package com.epam.web.command.impl.general;

import com.epam.web.command.CommandResult;
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
    private static final Map<String, String[]> REQUEST_PARAMETERS = new HashMap<>();
    private static final String VALID_LOGIN = "loginValid";
    private static final String INVALID_LOGIN = "l*oginValid";
    private static final String VALID_PASSWORD = "password1234";
    private static final String INVALID_PASSWORD = "pas";

    static {
        REQUEST_PARAMETERS.put(Parameter.REPEATED_PASSWORD, new String[]{VALID_PASSWORD});
    }

    private SignUpService signUpService;
    private SignUpCommand signUpCommand;
    private RequestContext requestContext;

    @Before
    public void initMethod() {
        requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                REQUEST_PARAMETERS, SESSION_ATTRIBUTES, VALID_REQUEST_HEADER);
        REQUEST_PARAMETERS.put(Parameter.LOGIN, new String[]{VALID_LOGIN});
        REQUEST_PARAMETERS.put(Parameter.PASSWORD, new String[]{VALID_PASSWORD});
        signUpService = Mockito.mock(SignUpService.class);
        signUpCommand = new SignUpCommand(signUpService);
    }

    @Test
    public void testExecuteShouldReturnRedirectWhenParametersAreValid() throws ServiceException,
            InvalidParametersException {
        //given
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
        //when
        signUpCommand.execute(requestContext);
        //then
        verify(signUpService, times(1)).signUp(anyString(), anyString());
    }

    @Test
    public void testExecuteShouldForwardWhenAccountExist()
            throws ServiceException, InvalidParametersException {
        //given
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
        REQUEST_PARAMETERS.put(Parameter.LOGIN, new String[]{INVALID_LOGIN});
        //when
        CommandResult actual = signUpCommand.execute(requestContext);
        //then
        CommandResult expected = CommandResult.forward(Page.SIGN_UP);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testExecuteShouldForwardWhenPasswordIsInvalid()
            throws ServiceException, InvalidParametersException {
        //given
        REQUEST_PARAMETERS.put(Parameter.PASSWORD, new String[]{INVALID_PASSWORD});
        //when
        CommandResult actual = signUpCommand.execute(requestContext);
        //then
        CommandResult expected = CommandResult.forward(Page.SIGN_UP);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = InvalidParametersException.class)
    public void testExecuteShouldForwardWhenLoginIsNull()
            throws ServiceException, InvalidParametersException {
        //given
        REQUEST_PARAMETERS.put(Parameter.LOGIN, null);

        //when
        signUpCommand.execute(requestContext);
    }
}
