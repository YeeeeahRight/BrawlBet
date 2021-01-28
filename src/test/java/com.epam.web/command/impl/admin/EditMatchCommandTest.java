package com.epam.web.command.impl.admin;

import com.epam.web.command.CommandResult;
import com.epam.web.command.impl.admin.EditMatchCommand;
import com.epam.web.constant.Parameter;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.exception.ServiceException;
import com.epam.web.logic.service.match.MatchService;
import com.epam.web.logic.service.team.TeamService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class EditMatchCommandTest {
    private static final String VALID_REQUEST_HEADER = "controller?command=matches&page=1";
    private static final Map<String, Object> SESSION_ATTRIBUTES = new HashMap<>();
    private static final Map<String, Object> REQUEST_ATTRIBUTES = new HashMap<>();
    private static final String DATE = "2021-10-20T2:32";
    private static final String TOURNAMENT = "Cup";
    private static final String FIRST_TEAM = "First";
    private static final String SECOND_TEAM = "Second";
    private static final long FIRST_TEAM_ID = 2L;
    private static final long SECOND_TEAM_ID = 4L;
    private static final String VALID_ID = "2";
    private static final String INVALID_ID = "invalid";
    private static final String INVALID_DATE = "dsadasdadasa";

    private Map<String, String[]> requestParameters;
    private MatchService matchService;
    private TeamService teamService;

    @Before
    public void initMethod() {
        requestParameters = new HashMap<>();
        requestParameters.put(Parameter.DATE, new String[]{DATE});
        requestParameters.put(Parameter.TOURNAMENT, new String[]{TOURNAMENT});
        requestParameters.put(Parameter.FIRST_TEAM, new String[]{FIRST_TEAM});
        requestParameters.put(Parameter.SECOND_TEAM, new String[]{SECOND_TEAM});
        requestParameters.put(Parameter.ID, new String[]{VALID_ID});

        matchService = Mockito.mock(MatchService.class);
        teamService = Mockito.mock(TeamService.class);
    }

    @Test
    public void testExecuteShouldReturnRedirectWhenParametersAreValid() throws ServiceException,
            InvalidParametersException {
        //given
        EditMatchCommand editMatchCommand = new EditMatchCommand(matchService, teamService);
        RequestContext requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                requestParameters, SESSION_ATTRIBUTES, VALID_REQUEST_HEADER);
        //when
        when(teamService.getTeamIdByName(FIRST_TEAM)).thenReturn(FIRST_TEAM_ID);
        when(teamService.getTeamIdByName(SECOND_TEAM)).thenReturn(SECOND_TEAM_ID);
        CommandResult actual = editMatchCommand.execute(requestContext);
        //then
        CommandResult expected = CommandResult.redirect(VALID_REQUEST_HEADER);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testExecuteShouldEditWhenParametersAreValid()
            throws InvalidParametersException, ServiceException {
        //given
        EditMatchCommand editMatchCommand = new EditMatchCommand(matchService, teamService);
        RequestContext requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                requestParameters, SESSION_ATTRIBUTES, VALID_REQUEST_HEADER);
        //when
        when(teamService.getTeamIdByName(FIRST_TEAM)).thenReturn(FIRST_TEAM_ID);
        when(teamService.getTeamIdByName(SECOND_TEAM)).thenReturn(SECOND_TEAM_ID);
        editMatchCommand.execute(requestContext);
        //then
        verify(matchService, times(1)).saveMatch(Matchers.any());
    }

    //then
    @Test(expected = InvalidParametersException.class)
    public void testExecuteShouldThrowInvalidParametersExceptionRedirectWhenIdIsInvalid()
            throws ServiceException, InvalidParametersException {
        //given
        requestParameters.put(Parameter.ID, new String[]{INVALID_ID});
        EditMatchCommand editMatchCommand = new EditMatchCommand(matchService, teamService);
        RequestContext requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                requestParameters, SESSION_ATTRIBUTES, VALID_REQUEST_HEADER);
        //when
        editMatchCommand.execute(requestContext);
    }

    //then
    @Test(expected = InvalidParametersException.class)
    public void testExecuteShouldThrowInvalidParametersExceptionRedirectWhenDateIsInvalid()
            throws ServiceException, InvalidParametersException {
        //given
        requestParameters.put(Parameter.DATE, new String[]{INVALID_DATE});
        EditMatchCommand editMatchCommand = new EditMatchCommand(matchService, teamService);
        RequestContext requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                requestParameters, SESSION_ATTRIBUTES, VALID_REQUEST_HEADER);
        //when
        editMatchCommand.execute(requestContext);
    }
}
