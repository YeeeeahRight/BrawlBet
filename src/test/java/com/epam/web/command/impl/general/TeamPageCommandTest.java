package com.epam.web.command.impl.general;

import com.epam.web.command.CommandResult;
import com.epam.web.constant.Attribute;
import com.epam.web.constant.Page;
import com.epam.web.constant.Parameter;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.exception.ServiceException;
import com.epam.web.logic.service.team.TeamService;
import com.epam.web.model.entity.Team;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class TeamPageCommandTest {
    private static final String VALID_REQUEST_HEADER = "controller?command=matches&page=1";
    private static final Map<String, Object> SESSION_ATTRIBUTES = new HashMap<>();
    private static final Map<String, Object> REQUEST_ATTRIBUTES = new HashMap<>();
    private static final Map<String, String[]> REQUEST_PARAMETERS = new HashMap<>();
    private static final String ID_PARAM = "2";
    private static final String PAGE_PARAM = "1";
    private static final String NAME_PARAM = "Name";
    private static final Team TEAM = new Team("team", 200, 100);

    static {
        REQUEST_PARAMETERS.put(Parameter.PAGE, new String[]{PAGE_PARAM});
    }

    private RequestContext requestContext;
    private TeamService teamService;
    private TeamPageCommand teamPageCommand;

    @Before
    public void initMethod() {
        requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                REQUEST_PARAMETERS, SESSION_ATTRIBUTES, VALID_REQUEST_HEADER);
        teamService = Mockito.mock(TeamService.class);
        teamPageCommand = new TeamPageCommand(teamService);
        REQUEST_PARAMETERS.put(Parameter.ID, new String[]{ID_PARAM});
    }

    @Test
    public void testExecuteShouldReturnForwardTeamPageWhenIdIsValid()
            throws InvalidParametersException, ServiceException {
        //given
        //when
        when(teamService.getTeamById(anyLong())).thenReturn(TEAM);
        CommandResult actual = teamPageCommand.execute(requestContext);
        //then
        CommandResult expected = CommandResult.forward(Page.TEAM);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testExecuteShouldAddTeamAttribute() throws ServiceException,
            InvalidParametersException {
        //given
        //when
        when(teamService.getTeamById(anyLong())).thenReturn(TEAM);
        teamPageCommand.execute(requestContext);
        //then
        boolean isTeamAttributeExist =
                requestContext.getRequestAttribute(Attribute.TEAM) != null;
        Assert.assertTrue(isTeamAttributeExist);
    }


    @Test
    public void testExecuteShouldReturnForwardTeamPageWhenExistNameInsteadId()
            throws InvalidParametersException, ServiceException {
        //given
        //when
        REQUEST_PARAMETERS.put(Parameter.ID, null);
        REQUEST_PARAMETERS.put(Parameter.TEAM_NAME, new String[]{NAME_PARAM});
        when(teamService.getTeamIdByName(anyString())).thenReturn(2L);
        when(teamService.getTeamById(anyLong())).thenReturn(TEAM);
        CommandResult actual = teamPageCommand.execute(requestContext);
        //then
        CommandResult expected = CommandResult.forward(Page.TEAM);
        Assert.assertEquals(expected, actual);
    }
}
