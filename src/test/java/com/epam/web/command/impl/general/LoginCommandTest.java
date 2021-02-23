package com.epam.web.command.impl.general;

import com.epam.web.command.CommandResult;
import com.epam.web.constant.Attribute;
import com.epam.web.constant.Page;
import com.epam.web.constant.Parameter;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.exception.ServiceException;
import com.epam.web.logic.service.account.AccountService;
import com.epam.web.model.entity.Account;
import com.epam.web.model.enumeration.AccountRole;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;

public class LoginCommandTest {
    private static final String VALID_REQUEST_HEADER = "controller?command=sign-up-page";
    private static final String HOME_PAGE_COMMAND = "controller?command=home-page&page=1";
    private static final Map<String, Object> SESSION_ATTRIBUTES = new HashMap<>();
    private static final Map<String, Object> REQUEST_ATTRIBUTES = new HashMap<>();
    private static final Map<String, String[]> REQUEST_PARAMETERS = new HashMap<>();
    private static final String LOGIN = "loginValid";
    private static final String PASSWORD = "password1234";
    private static final Long ID = 2L;
    private static final Account ACCOUNT =
            new Account(ID, LOGIN, PASSWORD, AccountRole.USER, null, false);

    private AccountService accountService;
    private LoginCommand loginCommand;
    private RequestContext requestContext;

    @Before
    public void initMethod() {
        requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                REQUEST_PARAMETERS, SESSION_ATTRIBUTES, VALID_REQUEST_HEADER);
        REQUEST_PARAMETERS.put(Parameter.LOGIN, new String[]{LOGIN});
        REQUEST_PARAMETERS.put(Parameter.PASSWORD, new String[]{PASSWORD});
        accountService = Mockito.mock(AccountService.class);
        loginCommand = new LoginCommand(accountService);
    }

    @Test
    public void testExecuteShouldReturnRedirectHomePageWhenParametersAreValid() throws ServiceException,
            InvalidParametersException {
        //given
        //when
        when(accountService.getAccountByLogin(LOGIN)).thenReturn(ACCOUNT);
        when(accountService.isAccountExistByLoginPassword(LOGIN, PASSWORD)).thenReturn(true);
        CommandResult actual = loginCommand.execute(requestContext);
        //then
        CommandResult expected = CommandResult.redirect(HOME_PAGE_COMMAND);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testExecuteShouldSetIdAndRoleSessionWhenParametersAreValid() throws ServiceException,
            InvalidParametersException {
        //given
        //when
        when(accountService.getAccountByLogin(LOGIN)).thenReturn(ACCOUNT);
        when(accountService.isAccountExistByLoginPassword(LOGIN, PASSWORD)).thenReturn(true);
        loginCommand.execute(requestContext);
        //then
        boolean isRoleExist = requestContext.getSessionAttribute(Attribute.ROLE) != null;
        boolean isIdExist = requestContext.getSessionAttribute(Attribute.ACCOUNT_ID) != null;
        Assert.assertTrue(isRoleExist && isIdExist);
    }

    @Test
    public void testExecuteShouldReturnForwardLoginPageWhenAccountIsNotExist() throws ServiceException,
            InvalidParametersException {
        //given
        //when
        when(accountService.isAccountExistByLoginPassword(LOGIN, PASSWORD)).thenReturn(false);
        CommandResult actual = loginCommand.execute(requestContext);
        //then
        CommandResult expected = CommandResult.forward(Page.LOGIN);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testExecuteShouldReturnForwardLoginPageWhenAccountIsBlocked() throws ServiceException,
            InvalidParametersException {
        //given
        Account blockedAccount = new Account(ID, LOGIN, PASSWORD, null, null, true);
        //when
        when(accountService.getAccountByLogin(LOGIN)).thenReturn(blockedAccount);
        when(accountService.isAccountExistByLoginPassword(LOGIN, PASSWORD)).thenReturn(true);
        CommandResult actual = loginCommand.execute(requestContext);
        //then
        CommandResult expected = CommandResult.forward(Page.LOGIN);
        Assert.assertEquals(expected, actual);
    }
}
