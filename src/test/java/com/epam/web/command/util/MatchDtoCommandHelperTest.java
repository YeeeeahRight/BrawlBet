package com.epam.web.command.util;

import com.epam.web.constant.Attribute;
import com.epam.web.constant.Parameter;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.exception.ServiceException;
import com.epam.web.logic.service.match.MatchService;
import com.epam.web.logic.service.match.MatchType;
import com.epam.web.logic.service.team.TeamService;
import com.epam.web.model.entity.Match;
import com.epam.web.model.entity.dto.MatchDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

public class MatchDtoCommandHelperTest {
    private static final String REQUEST_HEADER = "controller?command=matches&page=1";
    private static final Map<String, Object> SESSION_ATTRIBUTES = new HashMap<>();
    private static final Map<String, Object> REQUEST_ATTRIBUTES = new HashMap<>();
    private static final Map<String, String[]> REQUEST_PARAMETERS = new HashMap<>();
    private static final String FIRST_TEAM_NAME = "1";
    private static final String SECOND_TEAM_NAME = "2";
    private static final Long FIRST_TEAM_ID = 1L;
    private static final Long SECOND_TEAM_ID = 2L;
    private static final int MAX_MATCHES_PAGE = 4;
    private static final int MATCHES_AMOUNT = 10;
    private static final String PAGE_STR = "1";
    private static final List<Match> MATCHES;
    private static final List<MatchDto> MATCH_DTO_LIST;

    static {
        Match match = new Match(2L, new Date(), "Tournament", FIRST_TEAM_ID, SECOND_TEAM_ID);
        MATCHES = Collections.singletonList(match);
        MatchDto.MatchDtoBuilder matchDtoBuilder = new MatchDto.MatchDtoBuilder();
        matchDtoBuilder.setGeneralFields(match, FIRST_TEAM_NAME, SECOND_TEAM_NAME);
        MATCH_DTO_LIST = Collections.singletonList(matchDtoBuilder.build());
        REQUEST_PARAMETERS.put(Parameter.PAGE, new String[]{PAGE_STR});
    }

    private TeamService teamService;
    private MatchService matchService;
    private MatchDtoCommandHelper matchDtoCommandHelper;
    private RequestContext requestContext;

    @Before
    public void initMethod() {
        teamService = Mockito.mock(TeamService.class);
        matchService = Mockito.mock(MatchService.class);
        requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                REQUEST_PARAMETERS, SESSION_ATTRIBUTES, REQUEST_HEADER);
        matchDtoCommandHelper = new MatchDtoCommandHelper(matchService, teamService);
    }

    @Test
    public void testProcessCommandWithPaginationShouldAddRightMatchDtoList()
            throws InvalidParametersException, ServiceException {
        //given
        //when
        when(matchService.getMatchesTypeRange(anyObject(), anyInt(), anyInt())).thenReturn(MATCHES);
        when(matchService.getMatchesTypeAmount(anyObject())).thenReturn(MATCHES_AMOUNT);
        when(teamService.getTeamNameById(FIRST_TEAM_ID)).thenReturn(FIRST_TEAM_NAME);
        when(teamService.getTeamNameById(SECOND_TEAM_ID)).thenReturn(SECOND_TEAM_NAME);
        matchDtoCommandHelper.processCommandWithPagination(requestContext, MatchType.FINISHED, MAX_MATCHES_PAGE);
        //then
        List<MatchDto> actualMatchDtoList =
                ((List<MatchDto>)requestContext.getRequestAttribute(Attribute.MATCH_DTO_LIST));
        System.out.println(actualMatchDtoList.get(0).getCommission());
        Assert.assertEquals(MATCH_DTO_LIST, actualMatchDtoList);
    }

    @Test
    public void testProcessCommandWithPaginationShouldAddRightCurrentPage()
            throws InvalidParametersException, ServiceException {
        //given
        //when
        when(matchService.getMatchesTypeRange(anyObject(), anyInt(), anyInt())).thenReturn(MATCHES);
        when(matchService.getMatchesTypeAmount(anyObject())).thenReturn(MATCHES_AMOUNT);
        when(teamService.getTeamIdByName(FIRST_TEAM_NAME)).thenReturn(FIRST_TEAM_ID);
        when(teamService.getTeamIdByName(SECOND_TEAM_NAME)).thenReturn(SECOND_TEAM_ID);
        matchDtoCommandHelper.processCommandWithPagination(requestContext, MatchType.FINISHED, MAX_MATCHES_PAGE);
        //then
        Integer expectedCurrentPage = Integer.parseInt(PAGE_STR);
        Integer actualCurrentPage = (Integer) requestContext.getRequestAttribute(Attribute.CURRENT_PAGE);
        Assert.assertEquals(expectedCurrentPage, actualCurrentPage);
    }

    @Test
    public void testProcessCommandWithPaginationShouldAddRightMaxPage()
            throws InvalidParametersException, ServiceException {
        //given
        //when
        when(matchService.getMatchesTypeRange(anyObject(), anyInt(), anyInt())).thenReturn(MATCHES);
        when(matchService.getMatchesTypeAmount(anyObject())).thenReturn(MATCHES_AMOUNT);
        when(teamService.getTeamIdByName(FIRST_TEAM_NAME)).thenReturn(FIRST_TEAM_ID);
        when(teamService.getTeamIdByName(SECOND_TEAM_NAME)).thenReturn(SECOND_TEAM_ID);
        matchDtoCommandHelper.processCommandWithPagination(requestContext, MatchType.FINISHED, MAX_MATCHES_PAGE);
        //then
        Integer expectedMaxPage = ((MATCHES_AMOUNT - 1) / MAX_MATCHES_PAGE) + 1;
        Integer actualMaxPage = (Integer) requestContext.getRequestAttribute(Attribute.MAX_PAGE);
        Assert.assertEquals(expectedMaxPage, actualMaxPage);
    }
}
