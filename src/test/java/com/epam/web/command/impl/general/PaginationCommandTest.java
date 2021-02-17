package com.epam.web.command.impl.general;

import com.epam.web.command.CommandResult;
import com.epam.web.constant.Parameter;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.exception.ServiceException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class PaginationCommandTest {
    private static final String VALID_REQUEST_HEADER = "controller?command=matches&page=1";
    private static final String REQUEST_HEADER_NEXT_PAGE = "controller?command=matches&page=2";
    private static final Map<String, Object> SESSION_ATTRIBUTES = new HashMap<>();
    private static final Map<String, Object> REQUEST_ATTRIBUTES = new HashMap<>();
    private static final Map<String, String[]> REQUEST_PARAMETERS = new HashMap<>();

    private static final String NEXT_PAGE = "2";

    private RequestContext requestContext;
    private PaginationCommand paginationCommand;

    @Before
    public void initMethod() {
        requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                REQUEST_PARAMETERS, SESSION_ATTRIBUTES, VALID_REQUEST_HEADER);
        paginationCommand = new PaginationCommand();
    }

    @Test
    public void testExecuteShouldReturnRedirectNextPage()
            throws InvalidParametersException, ServiceException {
        //given
        REQUEST_PARAMETERS.put(Parameter.PAGE, new String[]{NEXT_PAGE});
        //when
        CommandResult actual = paginationCommand.execute(requestContext);
        //then
        CommandResult expected = CommandResult.redirect(REQUEST_HEADER_NEXT_PAGE);
        Assert.assertEquals(expected, actual);
    }
}
