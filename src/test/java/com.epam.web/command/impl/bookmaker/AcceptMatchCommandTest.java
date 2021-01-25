package com.epam.web.command.impl.bookmaker;

import com.epam.web.command.CommandResult;
import com.epam.web.command.impl.bookmaker.AcceptMatchCommand;
import com.epam.web.constant.Parameter;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.exception.ServiceException;
import com.epam.web.logic.service.match.MatchService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class AcceptMatchCommandTest {
    private static final String VALID_REQUEST_HEADER = "controller?command=matches&page=1";
    private static final Map<String, Object> SESSION_ATTRIBUTES = new HashMap<>();
    private static final Map<String, Object> REQUEST_ATTRIBUTES = new HashMap<>();
    private static final String VALID_COMMISSION = "5.0";
    private static final String VALID_ID = "1";
    private static final String INVALID_ID = "1invalidddddd";
    private static final String INVALID_COMMISSION = "invalid";

    private Map<String, String[]> requestParameters;
    private MatchService matchService;

    @Before
    public void initMethod() {
        requestParameters = new HashMap<>();
        requestParameters.put(Parameter.ID, new String[]{VALID_ID});
        requestParameters.put(Parameter.COMMISSION, new String[]{VALID_COMMISSION});

        matchService = Mockito.mock(MatchService.class);
    }

    @Test
    public void testExecuteShouldReturnRedirectWhenParametersAreValid() throws ServiceException,
            InvalidParametersException {
        //given
        AcceptMatchCommand acceptMatchCommand  = new AcceptMatchCommand(matchService);
        RequestContext requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                requestParameters, SESSION_ATTRIBUTES, VALID_REQUEST_HEADER);
        //when
        CommandResult actual = acceptMatchCommand.execute(requestContext);
        //then
        CommandResult expected = CommandResult.redirect(VALID_REQUEST_HEADER);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testExecuteShouldAcceptWhenMatchParametersAreValid() throws ServiceException,
            InvalidParametersException {
        //given
        AcceptMatchCommand acceptMatchCommand = new AcceptMatchCommand(matchService);
        RequestContext requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                requestParameters, SESSION_ATTRIBUTES, VALID_REQUEST_HEADER);
        //when
        acceptMatchCommand.execute(requestContext);
        //then
        verify(matchService, times(1)).addCommissionById(anyFloat(), anyInt());
    }

    //then
    @Test(expected = InvalidParametersException.class)
    public void testExecuteShouldThrowInvalidParametersExceptionRedirectWhenCommissionIsInvalid()
            throws ServiceException, InvalidParametersException {
        //given
        requestParameters.put(Parameter.COMMISSION, new String[]{INVALID_COMMISSION});
        AcceptMatchCommand acceptMatchCommand = new AcceptMatchCommand(matchService);
        RequestContext requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                requestParameters, SESSION_ATTRIBUTES, VALID_REQUEST_HEADER);
        //when
        acceptMatchCommand.execute(requestContext);
    }

    //then
    @Test(expected = InvalidParametersException.class)
    public void testExecuteShouldThrowInvalidParametersExceptionRedirectWhenIdIsInvalid()
            throws ServiceException, InvalidParametersException {
        //given
        requestParameters.put(Parameter.ID, new String[]{INVALID_ID});
        AcceptMatchCommand acceptMatchCommand = new AcceptMatchCommand(matchService);
        RequestContext requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                requestParameters, SESSION_ATTRIBUTES, VALID_REQUEST_HEADER);
        //when
        acceptMatchCommand.execute(requestContext);
    }
}
