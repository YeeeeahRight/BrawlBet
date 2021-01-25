package com.epam.web.command.impl.general;

import com.epam.web.command.CommandResult;
import com.epam.web.command.impl.general.ForwardPageCommand;
import com.epam.web.constant.CommandName;
import com.epam.web.constant.Page;
import com.epam.web.controller.request.RequestContext;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ForwardPageCommandTest {
    private static final String VALID_COMMAND_PAGE = CommandName.LOGIN_PAGE;
    private static final String EXPECTED_PAGE = Page.LOGIN;
    private static final String NULL_PAGE = null;
    private static final String UNKNOWN_PAGE = "unknown command";
    private static final String VALID_REQUEST_HEADER = "controller?command=deposit&money=20";
    private static final Map<String, Object> SESSION_ATTRIBUTES = new HashMap<>();
    private static final Map<String, Object> REQUEST_ATTRIBUTES = new HashMap<>();
    private static final Map<String, String[]> REQUEST_PARAMETERS = new HashMap<>();

    @Test
    public void testExecuteShouldReturnPage() {
        //given
        RequestContext requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                REQUEST_PARAMETERS, SESSION_ATTRIBUTES, VALID_REQUEST_HEADER);
        ForwardPageCommand forwardPageCommand = new ForwardPageCommand(VALID_COMMAND_PAGE);
        //when
        CommandResult actual = forwardPageCommand.execute(requestContext);
        //then
        CommandResult expected = CommandResult.forward(EXPECTED_PAGE);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExecuteShouldThrowIllegalArgumentExceptionWhenPageIsNull() {
        //given
        RequestContext requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                REQUEST_PARAMETERS, SESSION_ATTRIBUTES, VALID_REQUEST_HEADER);
        ForwardPageCommand forwardPageCommand = new ForwardPageCommand(NULL_PAGE);
        //when
        forwardPageCommand.execute(requestContext);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExecuteShouldThrowIllegalArgumentExceptionWhenPageIsUnknown() {
        //given
        RequestContext requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                REQUEST_PARAMETERS, SESSION_ATTRIBUTES, VALID_REQUEST_HEADER);
        ForwardPageCommand forwardPageCommand = new ForwardPageCommand(UNKNOWN_PAGE);
        //when
        forwardPageCommand.execute(requestContext);
    }
}
