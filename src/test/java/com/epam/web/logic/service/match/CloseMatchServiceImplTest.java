package com.epam.web.logic.service.match;

import com.epam.web.dao.helper.DaoHelper;
import com.epam.web.dao.helper.DaoHelperFactory;
import com.epam.web.dao.impl.account.AccountDao;
import com.epam.web.dao.impl.bet.BetDao;
import com.epam.web.dao.impl.match.MatchDao;
import com.epam.web.dao.impl.team.TeamDao;
import com.epam.web.exception.DaoException;
import com.epam.web.exception.ServiceException;
import com.epam.web.logic.calculator.BetCalculator;
import com.epam.web.model.entity.Bet;
import com.epam.web.model.entity.Match;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class CloseMatchServiceImplTest {
    private static final Bet BET =
            new Bet(1L, 1L, 2L, BigDecimal.TEN, 2L, BigDecimal.TEN, new Date());
    private static final Match VALID_MATCH =
            new Match(new Date(), "tour", 2L, 1L, false);
    private static final Match CLOSED_MATCH =
            new Match(new Date(), "tour", 2L, 1L, true);
    private static final Long ANY_LONG = 2L;

    private AccountDao accountDao;
    private MatchDao matchDao;
    private TeamDao teamDao;
    private BetDao betDao;
    private CloseMatchService closeMatchService;

    @Before
    public void initMethod() {
        accountDao = Mockito.mock(AccountDao.class);
        matchDao = Mockito.mock(MatchDao.class);
        teamDao = Mockito.mock(TeamDao.class);
        betDao = Mockito.mock(BetDao.class);
        DaoHelper daoHelper = Mockito.mock(DaoHelper.class);
        when(daoHelper.createAccountDao()).thenReturn(accountDao);
        when(daoHelper.createTeamDao()).thenReturn(teamDao);
        when(daoHelper.createMatchDao()).thenReturn(matchDao);
        when(daoHelper.createBetDao()).thenReturn(betDao);
        DaoHelperFactory daoHelperFactory = Mockito.mock(DaoHelperFactory.class);
        when(daoHelperFactory.create()).thenReturn(daoHelper);
        BetCalculator betCalculator = Mockito.mock(BetCalculator.class);
        closeMatchService = new CloseMatchServiceImpl(daoHelperFactory, betCalculator);
    }

    @Test(expected = ServiceException.class)
    public void testCloseMatchByIdShouldThrowExceptionWhenMatchIsNotFound() throws ServiceException, DaoException {
        //given
        //when
        when(matchDao.findById(anyLong())).thenReturn(Optional.empty());
        closeMatchService.closeMatchById(ANY_LONG);
    }

    @Test(expected = ServiceException.class)
    public void testCloseMatchByIdShouldThrowExceptionWhenMatchIsClosed() throws ServiceException, DaoException {
        //given
        //when
        when(matchDao.findById(anyLong())).thenReturn(Optional.of(CLOSED_MATCH));
        closeMatchService.closeMatchById(ANY_LONG);
    }

    @Test
    public void testCloseMatchByIdShouldCloseWhenNoBets() throws ServiceException, DaoException {
        //given
        //when
        when(betDao.getBetsByMatchId(anyLong())).thenReturn(Collections.emptyList());
        when(matchDao.findById(anyLong())).thenReturn(Optional.of(VALID_MATCH));
        closeMatchService.closeMatchById(ANY_LONG);
        //then
        verify(teamDao, times(1)).incrementMatchesWonById(anyLong());
        verify(teamDao, times(1)).incrementMatchesLostById(anyLong());
        verify(matchDao, times(1)).close(anyLong(), anyObject());
    }

    @Test
    public void testCloseMatchByIdShouldCloseWhenBetsExist() throws ServiceException, DaoException {
        //given
        //when
        when(betDao.getBetsByMatchId(anyLong())).thenReturn(Collections.singletonList(BET));
        when(matchDao.findById(anyLong())).thenReturn(Optional.of(VALID_MATCH));
        closeMatchService.closeMatchById(ANY_LONG);
        //then
        verify(teamDao, times(1)).incrementMatchesWonById(anyLong());
        verify(teamDao, times(1)).incrementMatchesLostById(anyLong());
        verify(matchDao, times(1)).close(anyLong(), anyObject());
    }


}
