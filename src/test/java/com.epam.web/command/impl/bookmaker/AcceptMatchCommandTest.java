package com.epam.web.command.impl.bookmaker;

import com.epam.web.command.CommandResult;
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

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class AcceptMatchCommandTest {
    private static final String REQUEST_HEADER = "controller?command=accept-matches-page&page=1";
    private static final Map<String, Object> SESSION_ATTRIBUTES = new HashMap<>();
    private static final Map<String, Object> REQUEST_ATTRIBUTES = new HashMap<>();
    private static final Map<String, String[]> REQUEST_PARAMETERS = new HashMap<>();
    private static final String ID_PARAM = "2";
    private static final String COMMISSION = "2";
    private static final String INVALID_COMMISSION = "-1";

    static {
        REQUEST_PARAMETERS.put(Parameter.ID, new String[]{ID_PARAM});
    }

    private MatchService matchService;
    private RequestContext requestContext;
    private AcceptMatchCommand acceptMatchCommand;

    @Before
    public void initMethod() {
        matchService = Mockito.mock(MatchService.class);

        requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                REQUEST_PARAMETERS, SESSION_ATTRIBUTES, REQUEST_HEADER);
        acceptMatchCommand = new AcceptMatchCommand(matchService);
    }

    @Test
    public void testExecuteShouldReturnRedirect() throws ServiceException,
            InvalidParametersException {
        //given
        REQUEST_PARAMETERS.put(Parameter.COMMISSION, new String[]{COMMISSION});
        //when
        CommandResult actual = acceptMatchCommand.execute(requestContext);
        //then
        CommandResult expected = CommandResult.redirect(REQUEST_HEADER);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testExecuteShouldSetCommission() throws ServiceException,
            InvalidParametersException {
        //given
        REQUEST_PARAMETERS.put(Parameter.COMMISSION, new String[]{COMMISSION});
        //when
        acceptMatchCommand.execute(requestContext);
        //then
        verify(matchService, times(1)).setCommissionById(anyFloat(), anyLong());
    }

    @Test(expected = InvalidParametersException.class)
    public void testExecuteShouldThrowInvalidParametersExceptionWhenCommissionIsOutOfRange()
            throws ServiceException, InvalidParametersException {
        //given
        //when
        REQUEST_PARAMETERS.put(Parameter.COMMISSION, new String[]{INVALID_COMMISSION});
        acceptMatchCommand.execute(requestContext);
        //then
        verify(matchService, times(1)).setCommissionById(anyFloat(), anyLong());
    }
}
