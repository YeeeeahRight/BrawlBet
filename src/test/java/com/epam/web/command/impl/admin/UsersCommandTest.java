package com.epam.web.command.impl.admin;

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

import java.util.*;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

public class UsersCommandTest {
    private static final String VALID_REQUEST_HEADER = "controller?command=home-page&page=1";
    private static final Map<String, Object> SESSION_ATTRIBUTES = new HashMap<>();
    private static final Map<String, Object> REQUEST_ATTRIBUTES = new HashMap<>();
    private static final Map<String, String[]> REQUEST_PARAMETERS = new HashMap<>();
    private static final CommandResult EXPECTED_COMMAND_RESULT = CommandResult.forward(Page.USERS);
    private static final Account USER =
            new Account("AnyName", "AnyPassword", AccountRole.USER);
    private static final Account BOOKMAKER =
            new Account("AnyBookmakerName", "AnyBookmakerPassword", AccountRole.BOOKMAKER);
    private static final List<Account> USERS = Collections.singletonList(USER);
    private static final String PAGE_PARAM = "2";
    private static final String FIRST_PAGE_PARAM = "1";

    static {
        REQUEST_PARAMETERS.put(Parameter.PAGE, new String[]{PAGE_PARAM});
    }

    private AccountService accountService;
    private RequestContext requestContext;
    private UsersCommand usersCommand;

    @Before
    public void initMethod() {
        accountService = Mockito.mock(AccountService.class);
        requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                REQUEST_PARAMETERS, SESSION_ATTRIBUTES, VALID_REQUEST_HEADER);
        usersCommand = new UsersCommand(accountService);
    }

    @Test
    public void testExecuteShouldReturnForward() throws ServiceException,
            InvalidParametersException {
        //given
        //when
        when(accountService.findBookmaker()).thenReturn(Optional.empty());
        when(accountService.getUsersRange(anyInt(), anyInt())).thenReturn(USERS);
        CommandResult actual = usersCommand.execute(requestContext);
        //then
        Assert.assertEquals(EXPECTED_COMMAND_RESULT, actual);
    }

    @Test
    public void testExecuteShouldAddTeamsAttribute() throws ServiceException,
            InvalidParametersException {
        //given
        //when
        when(accountService.findBookmaker()).thenReturn(Optional.empty());
        when(accountService.getUsersRange(anyInt(), anyInt())).thenReturn(USERS);
        usersCommand.execute(requestContext);
        //then
        boolean isMatchDtoAttributeExist =
                requestContext.getRequestAttribute(Attribute.USERS) != null;
        Assert.assertTrue(isMatchDtoAttributeExist);
    }

    @Test
    public void testExecuteShouldAddCurrentPageAttribute() throws ServiceException,
            InvalidParametersException {
        //given
        //when
        when(accountService.findBookmaker()).thenReturn(Optional.empty());
        when(accountService.getUsersRange(anyInt(), anyInt())).thenReturn(USERS);
        usersCommand.execute(requestContext);
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
        when(accountService.findBookmaker()).thenReturn(Optional.empty());
        when(accountService.getUsersRange(anyInt(), anyInt())).thenReturn(USERS);
        usersCommand.execute(requestContext);
        //then
        boolean isMatchDtoAttributeExist =
                requestContext.getRequestAttribute(Attribute.MAX_PAGE) != null;
        Assert.assertTrue(isMatchDtoAttributeExist);
    }

    @Test
    public void testExecuteShouldAddBookmakerWhenBookmakerExistAndPageIsFirst()
            throws ServiceException, InvalidParametersException {
        //given
        //when
        REQUEST_PARAMETERS.put(Parameter.PAGE, new String[]{FIRST_PAGE_PARAM});
        when(accountService.findBookmaker()).thenReturn(Optional.of(BOOKMAKER));
        when(accountService.getUsersRange(anyInt(), anyInt())).thenReturn(USERS);
        usersCommand.execute(requestContext);
        //then
        List<Account> users = (List<Account>)requestContext.getRequestAttribute(Attribute.USERS);
        System.out.println(users);
        boolean isBookmakerExist = users.contains(BOOKMAKER);
        Assert.assertTrue(isBookmakerExist);
    }

    @Test(expected = InvalidParametersException.class)
    public void testExecuteShouldThrowInvalidParametersExceptionWhenUsersEmptyAndPageIsNotFirst()
            throws ServiceException, InvalidParametersException {
        //given
        //when
        REQUEST_PARAMETERS.put(Parameter.PAGE, new String[]{PAGE_PARAM});
        when(accountService.findBookmaker()).thenReturn(Optional.empty());
        when(accountService.getUsersRange(anyInt(), anyInt())).thenReturn(Collections.emptyList());
        usersCommand.execute(requestContext);
    }
}
