package com.epam.web.command.impl.admin;

import com.epam.web.command.CommandResult;
import com.epam.web.constant.CommandName;
import com.epam.web.constant.Parameter;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.exception.ServiceException;
import com.epam.web.logic.service.match.CloseMatchService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CloseMatchCommandTest {
    private static final String MATCHES_PAGE_COMMAND = "controller?command=" + CommandName.CLOSE_MATCHES_PAGE +
            "&" + Parameter.PAGE + "=1";
    private static final Map<String, Object> SESSION_ATTRIBUTES = new HashMap<>();
    private static final Map<String, Object> REQUEST_ATTRIBUTES = new HashMap<>();
    private static final Map<String, String[]> REQUEST_PARAMETERS = new HashMap<>();
    private static final String ID_PARAM = "2";

    static {
        REQUEST_PARAMETERS.put(Parameter.ID, new String[]{ID_PARAM});
    }

    private CloseMatchService closeMatchService;
    private RequestContext requestContext;
    private CloseMatchCommand closeMatchCommand;

    @Before
    public void initMethod() {
        closeMatchService = Mockito.mock(CloseMatchService.class);
        requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                REQUEST_PARAMETERS, SESSION_ATTRIBUTES, MATCHES_PAGE_COMMAND);
        closeMatchCommand = new CloseMatchCommand(closeMatchService);
    }

    @Test
    public void testExecuteShouldReturnRedirect() throws ServiceException,
            InvalidParametersException {
        //given
        //when
        CommandResult actual = closeMatchCommand.execute(requestContext);
        //then
        CommandResult expected = CommandResult.redirect(MATCHES_PAGE_COMMAND);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testExecuteShouldClose() throws ServiceException,
            InvalidParametersException {
        //given
        //when
        closeMatchCommand.execute(requestContext);
        //then
        verify(closeMatchService, times(1)).closeMatchById(anyLong());
    }
}
