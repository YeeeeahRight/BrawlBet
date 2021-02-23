package com.epam.web.command.util;

import com.epam.web.constant.Parameter;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.date.DateFormatType;
import com.epam.web.date.DateParser;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.exception.ServiceException;
import com.epam.web.logic.service.team.TeamService;
import com.epam.web.model.entity.Match;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;

public class MatchRequestCreatorTest {
    private static final String REQUEST_HEADER = "controller?command=matches&page=1";
    private static final Map<String, Object> SESSION_ATTRIBUTES = new HashMap<>();
    private static final Map<String, Object> REQUEST_ATTRIBUTES = new HashMap<>();
    private static final Map<String, String[]> REQUEST_PARAMETERS = new HashMap<>();
    private static final String FIRST_TEAM_NAME = "1";
    private static final String SECOND_TEAM_NAME = "2";
    private static final Long FIRST_TEAM_ID = 1L;
    private static final Long SECOND_TEAM_ID = 2L;
    private static final String TOURNAMENT = "Tournament";
    private static final String DATE_STR = "2020-10-11T10:11";
    private static final Match EXPECTED_MATCH;

    static {
        Date date = null;
        try {
            date = DateParser.parse(DATE_STR, DateFormatType.HTML);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        EXPECTED_MATCH = new Match(date, TOURNAMENT, FIRST_TEAM_ID, SECOND_TEAM_ID);
    }

    private TeamService teamService;
    private MatchRequestCreator matchRequestCreator;
    private RequestContext requestContext;

    @Before
    public void initMethod() {
        REQUEST_PARAMETERS.put(Parameter.FIRST_TEAM, new String[]{FIRST_TEAM_NAME});
        REQUEST_PARAMETERS.put(Parameter.SECOND_TEAM, new String[]{SECOND_TEAM_NAME});
        REQUEST_PARAMETERS.put(Parameter.TOURNAMENT, new String[]{TOURNAMENT});
        REQUEST_PARAMETERS.put(Parameter.DATE, new String[]{DATE_STR});
        teamService = Mockito.mock(TeamService.class);
        requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                REQUEST_PARAMETERS, SESSION_ATTRIBUTES, REQUEST_HEADER);
        matchRequestCreator = new MatchRequestCreator();
    }

    @Test
    public void testCreateMatchShouldCreateMatch() throws InvalidParametersException, ServiceException {
        //given
        //when
        when(teamService.getTeamIdByName(FIRST_TEAM_NAME)).thenReturn(FIRST_TEAM_ID);
        when(teamService.getTeamIdByName(SECOND_TEAM_NAME)).thenReturn(SECOND_TEAM_ID);
        Match actualMatch = matchRequestCreator.createMatch(requestContext, teamService, false);
        //then
        Assert.assertEquals(EXPECTED_MATCH, actualMatch);
    }
}
