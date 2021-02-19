package com.epam.web.command.impl.admin;

import com.epam.web.command.CommandResult;
import com.epam.web.constant.Attribute;
import com.epam.web.constant.Page;
import com.epam.web.constant.Parameter;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.exception.ServiceException;
import com.epam.web.logic.service.match.MatchService;
import com.epam.web.logic.service.team.TeamService;
import com.epam.web.model.entity.Match;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

public class EditMatchPageCommandTest {
    private static final String REQUEST_HEADER = "controller?command=matches&page=1";
    private static final Map<String, Object> SESSION_ATTRIBUTES = new HashMap<>();
    private static final Map<String, Object> REQUEST_ATTRIBUTES = new HashMap<>();
    private static final Map<String, String[]> REQUEST_PARAMETERS = new HashMap<>();
    private static final CommandResult EXPECTED_COMMAND_RESULT = CommandResult.forward(Page.EDIT_MATCH);
    private static final String ID_PARAM = "2";
    private static final Match MATCH =
            new Match(new Date(), "tour", 2L, 1L, false);

    static {
        REQUEST_PARAMETERS.put(Parameter.ID, new String[]{ID_PARAM});
    }


    private TeamService teamService;
    private MatchService matchService;
    private RequestContext requestContext;
    private EditMatchPageCommand editMatchPageCommand;

    @Before
    public void initMethod() {
        teamService = Mockito.mock(TeamService.class);
        matchService = Mockito.mock(MatchService.class);

        requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                REQUEST_PARAMETERS, SESSION_ATTRIBUTES, REQUEST_HEADER);
        editMatchPageCommand =
                new EditMatchPageCommand(matchService, teamService);
    }

    @Test
    public void testExecuteShouldReturnForward() throws ServiceException,
            InvalidParametersException {
        //given
        //when
        when(matchService.getMatchById(anyLong())).thenReturn(MATCH);
        CommandResult actual = editMatchPageCommand.execute(requestContext);
        //then
        Assert.assertEquals(EXPECTED_COMMAND_RESULT, actual);
    }

    @Test
    public void testExecuteShouldAddMatchAttributes() throws ServiceException,
            InvalidParametersException {
        //given
        //when
        when(matchService.getMatchById(anyLong())).thenReturn(MATCH);
        editMatchPageCommand.execute(requestContext);
        //then
        Set<String> attributes = requestContext.getRequestAttributeNames();
        boolean isMatchDtoAttributeExist =
                attributes.contains(Attribute.DATE)
                && attributes.contains(Attribute.FIRST_TEAM)
                && attributes.contains(Attribute.SECOND_TEAM)
                && attributes.contains(Attribute.TOURNAMENT);
        Assert.assertTrue(isMatchDtoAttributeExist);
    }
}
