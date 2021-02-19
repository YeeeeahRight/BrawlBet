package com.epam.web.command.impl.admin;

import com.epam.web.command.CommandResult;
import com.epam.web.command.util.MatchRequestCreator;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.exception.ServiceException;
import com.epam.web.logic.service.match.MatchService;
import com.epam.web.logic.service.team.TeamService;
import com.epam.web.model.entity.Match;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

public class AddMatchCommandTest {
    private static final Match MATCH =
            new Match(new Date(), "tour", 2L, 1L, false);
    private static final String REQUEST_HEADER = "controller?command=matches&page=1";
    private static final Map<String, Object> SESSION_ATTRIBUTES = new HashMap<>();
    private static final Map<String, Object> REQUEST_ATTRIBUTES = new HashMap<>();
    private static final Map<String, String[]> REQUEST_PARAMETERS = new HashMap<>();

    private MatchService matchService;
    private RequestContext requestContext;
    private AddMatchCommand addMatchCommand;
    private MatchRequestCreator matchRequestCreator;

    @Before
    public void initMethod() {
        matchRequestCreator = Mockito.mock(MatchRequestCreator.class);
        matchService = Mockito.mock(MatchService.class);
        TeamService teamService = Mockito.mock(TeamService.class);

        requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                REQUEST_PARAMETERS, SESSION_ATTRIBUTES, REQUEST_HEADER);
        addMatchCommand = new AddMatchCommand(matchService, teamService, matchRequestCreator);
    }

    @Test
    public void testExecuteShouldReturnRedirect() throws ServiceException,
            InvalidParametersException {
        //given
        //when
        when(matchRequestCreator.createMatch(anyObject(), anyObject(), anyBoolean()))
                .thenReturn(MATCH);
        CommandResult actual = addMatchCommand.execute(requestContext);
        //then
        CommandResult expected = CommandResult.redirect(REQUEST_HEADER);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testExecuteShouldAddMatch() throws ServiceException,
            InvalidParametersException {
        //given
        //when
        when(matchRequestCreator.createMatch(anyObject(), anyObject(), anyBoolean()))
                .thenReturn(MATCH);
        addMatchCommand.execute(requestContext);
        //then
        verify(matchService, times(1)).saveMatch(Matchers.any());
    }
}
