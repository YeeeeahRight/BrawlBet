package com.epam.web.command.impl.admin;

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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

public class TeamsCommandTest {
    private static final String VALID_REQUEST_HEADER = "controller?command=home-page&page=1";
    private static final Map<String, Object> SESSION_ATTRIBUTES = new HashMap<>();
    private static final Map<String, Object> REQUEST_ATTRIBUTES = new HashMap<>();
    private static final Map<String, String[]> REQUEST_PARAMETERS = new HashMap<>();
    private static final CommandResult EXPECTED_COMMAND_RESULT = CommandResult.forward(Page.TEAMS);
    private static final List<Team> TEAMS;
    private static final String PAGE_PARAM = "2";

    static {
        TEAMS = Collections.singletonList(new Team("AnyName", 0,0));
        REQUEST_PARAMETERS.put(Parameter.PAGE, new String[]{PAGE_PARAM});
    }

    private TeamService teamService;
    private RequestContext requestContext;
    private TeamsCommand teamsCommand;

    @Before
    public void initMethod() {
        teamService = Mockito.mock(TeamService.class);
        requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                REQUEST_PARAMETERS, SESSION_ATTRIBUTES, VALID_REQUEST_HEADER);
        teamsCommand = new TeamsCommand(teamService);
    }

    @Test
    public void testExecuteShouldReturnForward() throws ServiceException,
            InvalidParametersException {
        //given
        //when
        when(teamService.getTeamsRange(anyInt(), anyInt())).thenReturn(TEAMS);
        CommandResult actual = teamsCommand.execute(requestContext);
        //then
        Assert.assertEquals(EXPECTED_COMMAND_RESULT, actual);
    }

    @Test
    public void testExecuteShouldAddTeamsAttribute() throws ServiceException,
            InvalidParametersException {
        //given
        //when
        when(teamService.getTeamsRange(anyInt(), anyInt())).thenReturn(TEAMS);
        teamsCommand.execute(requestContext);
        //then
        boolean isMatchDtoAttributeExist =
                requestContext.getRequestAttribute(Attribute.TEAMS) != null;
        Assert.assertTrue(isMatchDtoAttributeExist);
    }

    @Test
    public void testExecuteShouldAddCurrentPageAttribute() throws ServiceException,
            InvalidParametersException {
        //given
        //when
        when(teamService.getTeamsRange(anyInt(), anyInt())).thenReturn(TEAMS);
        teamsCommand.execute(requestContext);
        //then
        boolean isMatchDtoAttributeExist =
                requestContext.getRequestAttribute(Attribute.CURRENT_PAGE) != null;
        Assert.assertTrue(isMatchDtoAttributeExist);
    }

    @Test
    public void testExecuteShouldAddMaxPageAttribute() throws ServiceException,
            InvalidParametersException {
        //given
        //when
        when(teamService.getTeamsRange(anyInt(), anyInt())).thenReturn(TEAMS);
        teamsCommand.execute(requestContext);
        //then
        boolean isMatchDtoAttributeExist =
                requestContext.getRequestAttribute(Attribute.MAX_PAGE) != null;
        Assert.assertTrue(isMatchDtoAttributeExist);
    }

    @Test(expected = InvalidParametersException.class)
    public void testExecuteShouldThrowInvalidParametersExceptionWhenTeamsEmpty()
            throws ServiceException, InvalidParametersException {
        //given
        //when
        when(teamService.getTeamsRange(anyInt(), anyInt())).thenReturn(Collections.emptyList());
        teamsCommand.execute(requestContext);
    }
}
