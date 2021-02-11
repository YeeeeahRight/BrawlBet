package com.epam.web.command.impl.general;

import com.epam.web.command.CommandResult;
import com.epam.web.constant.CommandName;
import com.epam.web.constant.Page;
import com.epam.web.controller.request.RequestContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ForwardPageCommandTest {
    private static final String REQUEST_HEADER = "controller?command=deposit&money=20";
    private static final Map<String, Object> SESSION_ATTRIBUTES = new HashMap<>();
    private static final Map<String, Object> REQUEST_ATTRIBUTES = new HashMap<>();
    private static final Map<String, String[]> REQUEST_PARAMETERS = new HashMap<>();
    private static final String VALID_COMMAND_PAGE = CommandName.LOGIN_PAGE;
    private static final CommandResult EXPECTED_VALID_RESULT = CommandResult.forward(Page.LOGIN);
    private static final String NULL_PAGE = null;
    private static final String UNKNOWN_PAGE = "unknown command";


    private RequestContext requestContext;

    @Before
    public void initMethod() {
        requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                REQUEST_PARAMETERS, SESSION_ATTRIBUTES, REQUEST_HEADER);
    }


    @Test
    public void testExecuteShouldReturnValidPage() {
        //given
        ForwardPageCommand forwardPageCommand = new ForwardPageCommand(VALID_COMMAND_PAGE);
        //when
        CommandResult actual = forwardPageCommand.execute(requestContext);
        //then
        Assert.assertEquals(EXPECTED_VALID_RESULT, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExecuteShouldThrowIllegalArgumentExceptionWhenPageIsNull() {
        //given
        ForwardPageCommand forwardPageCommand = new ForwardPageCommand(NULL_PAGE);
        //when
        forwardPageCommand.execute(requestContext);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExecuteShouldThrowIllegalArgumentExceptionWhenPageIsUnknown() {
        //given
        ForwardPageCommand forwardPageCommand = new ForwardPageCommand(UNKNOWN_PAGE);
        //when
        forwardPageCommand.execute(requestContext);
    }
}
