package com.epam.web.logic.service.team;

import com.epam.web.dao.helper.DaoHelper;
import com.epam.web.dao.helper.DaoHelperFactory;
import com.epam.web.dao.impl.team.TeamDao;
import com.epam.web.exception.DaoException;
import com.epam.web.exception.ServiceException;
import com.epam.web.logic.validator.Validator;
import com.epam.web.model.entity.Team;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

public class TeamServiceImplTest {
    private static final Long ID = 2L;
    private static final String TEAM_NAME = "TeamName";
    private static final Team TEAM = new Team(ID, TEAM_NAME, 2, 3);
    private static final long ANY_LONG = 2L;
    private static final int ANY_INT = 2;
    private static final String ANY_STRING = "String";

    private Validator<Team> teamValidator;
    private TeamDao teamDao;
    private TeamService teamService;

    @Before
    public void initMethod() {
        teamDao = Mockito.mock(TeamDao.class);
        DaoHelper daoHelper = Mockito.mock(DaoHelper.class);
        when(daoHelper.createTeamDao()).thenReturn(teamDao);
        DaoHelperFactory daoHelperFactory = Mockito.mock(DaoHelperFactory.class);
        when(daoHelperFactory.create()).thenReturn(daoHelper);
        teamValidator = Mockito.mock(Validator.class);
        teamService = new TeamServiceImpl(daoHelperFactory, teamValidator);
    }

    @Test
    public void testSaveTeamShouldSaveWhenTeamIsValid()
            throws ServiceException, DaoException {
        //given
        //when
        when(teamValidator.isValid(anyObject())).thenReturn(true);
        teamService.saveTeam(TEAM);
        //then
        verify(teamDao, times(1)).save(anyObject());
    }

    //then
    @Test(expected = ServiceException.class)
    public void testSaveTeamShouldSaveWhenTeamIsInvalid() throws ServiceException {
        //given
        //when
        when(teamValidator.isValid(anyObject())).thenReturn(false);
        teamService.saveTeam(TEAM);
    }

    @Test
    public void testGetTeamIdByIdShouldGetWhenTeamIsExist()
            throws DaoException, ServiceException {
        //given
        //when
        when(teamDao.findById(anyLong())).thenReturn(Optional.of(TEAM));
        Team team = teamService.getTeamById(ANY_LONG);
        //then
        Assert.assertNotNull(team);
    }

    //then
    @Test(expected = ServiceException.class)
    public void testGetTeamIdByIdShouldThrowExceptionWhenTeamIsNotExist()
            throws DaoException, ServiceException {
        //given
        //when
        when(teamDao.findById(anyLong())).thenReturn(Optional.empty());
        teamService.getTeamById(ANY_LONG);
    }

    @Test
    public void testGetTeamIdByNameShouldGetWhenTeamIsExist()
            throws DaoException, ServiceException {
        //given
        //when
        when(teamDao.findTeamByName(anyString())).thenReturn(Optional.of(TEAM));
        Long actualId = teamService.getTeamIdByName(ANY_STRING);
        //then
        Assert.assertEquals(ID, actualId);
    }

    //then
    @Test(expected = ServiceException.class)
    public void testGetTeamIdByNameShouldThrowExceptionWhenTeamIsNotExist()
            throws DaoException, ServiceException {
        //given
        //when
        when(teamDao.findTeamByName(anyString())).thenReturn(Optional.empty());
        teamService.getTeamIdByName(ANY_STRING);
    }

    @Test
    public void testGetTeamNameByIdShouldGetWhenTeamIsExist()
            throws DaoException, ServiceException {
        //given
        //when
        when(teamDao.findById(anyLong())).thenReturn(Optional.of(TEAM));
        String teamName = teamService.getTeamNameById(ANY_LONG);
        //then
        Assert.assertEquals(TEAM_NAME, teamName);
    }

    //then
    @Test(expected = ServiceException.class)
    public void testGetTeamNameByIdShouldThrowExceptionWhenTeamIsNotExist()
            throws DaoException, ServiceException {
        //given
        //when
        when(teamDao.findById(anyLong())).thenReturn(Optional.empty());
        teamService.getTeamNameById(ANY_LONG);
    }

    @Test
    public void testGetTeamsRangeShouldGet() throws ServiceException, DaoException {
        //given
        //when
        teamService.getTeamsRange(ANY_INT, ANY_INT);
        //then
        verify(teamDao, times(1)).getTeamsRange(anyInt(), anyInt());
    }

    @Test
    public void testGetTeamsAmountShouldGet() throws ServiceException, DaoException {
        //given
        //when
        teamService.getTeamsAmount();
        //then
        verify(teamDao, times(1)).getRowsAmount(anyObject());
    }

}
