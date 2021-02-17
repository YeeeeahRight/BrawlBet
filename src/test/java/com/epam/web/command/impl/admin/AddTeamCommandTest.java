package com.epam.web.command.impl.admin;

import com.epam.web.command.CommandResult;
import com.epam.web.constant.Parameter;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.exception.ServiceException;
import com.epam.web.logic.service.team.TeamService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class AddTeamCommandTest {
    private static final String REQUEST_HEADER = "controller?command=teams&page=1";
    private static final Map<String, Object> SESSION_ATTRIBUTES = new HashMap<>();
    private static final Map<String, Object> REQUEST_ATTRIBUTES = new HashMap<>();
    private static final Map<String, String[]> REQUEST_PARAMETERS = new HashMap<>();
    private static final String TEAM_NAME_PARAM = "VP";

    static {
        REQUEST_PARAMETERS.put(Parameter.TEAM_NAME, new String[]{TEAM_NAME_PARAM});
    }

    private TeamService teamService;
    private RequestContext requestContext;
    private AddTeamCommand addTeamCommand;

    @Before
    public void initMethod() {
        teamService = Mockito.mock(TeamService.class);

        requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                REQUEST_PARAMETERS, SESSION_ATTRIBUTES, REQUEST_HEADER);
        addTeamCommand = new AddTeamCommand(teamService);
    }

    @Test
    public void testExecuteShouldReturnRedirect() throws ServiceException,
            InvalidParametersException {
        //given
        //when
        CommandResult actual = addTeamCommand.execute(requestContext);
        //then
        CommandResult expected = CommandResult.redirect(REQUEST_HEADER);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testExecuteShouldAddTeam() throws ServiceException,
            InvalidParametersException {
        //given
        //when
        addTeamCommand.execute(requestContext);
        //then
        verify(teamService, times(1)).saveTeam(Matchers.any());
    }
}
