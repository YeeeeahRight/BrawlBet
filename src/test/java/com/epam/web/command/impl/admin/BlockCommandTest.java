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

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class BlockCommandTest {
    private static final String VALID_REQUEST_HEADER = "controller?command=users&page=1";
    private static final Map<String, Object> SESSION_ATTRIBUTES = new HashMap<>();
    private static final Map<String, Object> REQUEST_ATTRIBUTES = new HashMap<>();
    private static final Map<String, String[]> REQUEST_PARAMETERS = new HashMap<>();
    private static final String ID_PARAM = "2";

    static {
        REQUEST_PARAMETERS.put(Parameter.ID, new String[]{ID_PARAM});
    }

    private AccountService accountService;
    private RequestContext requestContext;
    private BlockCommand blockCommand;

    @Before
    public void initMethod() {
        accountService = Mockito.mock(AccountService.class);
        requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                REQUEST_PARAMETERS, SESSION_ATTRIBUTES, VALID_REQUEST_HEADER);
        blockCommand = new BlockCommand(accountService);
    }

    @Test
    public void testExecuteShouldReturnRedirect() throws ServiceException,
            InvalidParametersException {
        //given
        //when
        CommandResult actual = blockCommand.execute(requestContext);
        //then
        CommandResult expected = CommandResult.redirect(VALID_REQUEST_HEADER);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testExecuteShouldBlock() throws ServiceException,
            InvalidParametersException {
        //given
        //when
        blockCommand.execute(requestContext);
        //then
        verify(accountService, times(1)).blockById(anyLong());
    }
}
