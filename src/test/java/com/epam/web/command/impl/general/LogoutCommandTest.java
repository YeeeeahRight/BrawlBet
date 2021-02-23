package com.epam.web.command.impl.general;

import com.epam.web.command.CommandResult;
import com.epam.web.constant.Attribute;
import com.epam.web.controller.request.RequestContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class LogoutCommandTest {
    private static final String VALID_REQUEST_HEADER = "controller?command=matches&page=1";
    private static final String HOME_PAGE_COMMAND = "controller?command=home-page&page=1";
    private static final Map<String, Object> SESSION_ATTRIBUTES = new HashMap<>();
    private static final Map<String, Object> REQUEST_ATTRIBUTES = new HashMap<>();
    private static final Map<String, String[]> REQUEST_PARAMETERS = new HashMap<>();

    private RequestContext requestContext;
    private LogoutCommand logoutCommand;

    @Before
    public void initMethod() {
        requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                REQUEST_PARAMETERS, SESSION_ATTRIBUTES, VALID_REQUEST_HEADER);
        logoutCommand = new LogoutCommand();
    }

    @Test
    public void testExecuteShouldReturnRedirectHomePage() {
        //given
        //when
        CommandResult actual = logoutCommand.execute(requestContext);
        //then
        CommandResult expected = CommandResult.redirect(HOME_PAGE_COMMAND);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testExecuteShouldSetInvalidateAttributeSession() {
        //given
        //when
        logoutCommand.execute(requestContext);
        //then
        boolean isInvalidateExist =
                requestContext.getSessionAttribute(Attribute.INVALIDATE_ATTRIBUTE) != null;
        Assert.assertTrue(isInvalidateExist);
    }
}
