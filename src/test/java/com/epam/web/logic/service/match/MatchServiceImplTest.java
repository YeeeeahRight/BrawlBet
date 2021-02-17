package com.epam.web.logic.service.match;

import com.epam.web.dao.helper.DaoHelper;
import com.epam.web.dao.helper.DaoHelperFactory;
import com.epam.web.dao.impl.account.AccountDao;
import com.epam.web.dao.impl.bet.BetDao;
import com.epam.web.dao.impl.match.MatchDao;
import com.epam.web.exception.DaoException;
import com.epam.web.exception.ServiceException;
import com.epam.web.logic.validator.Validator;
import com.epam.web.model.entity.Bet;
import com.epam.web.model.entity.Match;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

public class MatchServiceImplTest {
    private static final Match MATCH =
            new Match(new Date(), "tour", 2L, 1L);
    private static final Bet BET =
            new Bet(1L, 2L, BigDecimal.TEN, 2L, new Date());
    private static final long ANY_LONG = 2L;
    private static final float ANY_FLOAT = 2f;
    private static final int ANY_INT = 2;

    private Validator<Match> matchValidator;
    private MatchDao matchDao;
    private BetDao betDao;
    private AccountDao accountDao;
    private MatchService matchService;

    @Before
    public void initMethod() {
        matchDao = Mockito.mock(MatchDao.class);
        betDao = Mockito.mock(BetDao.class);
        accountDao = Mockito.mock(AccountDao.class);
        DaoHelper daoHelper = Mockito.mock(DaoHelper.class);
        when(daoHelper.createMatchDao()).thenReturn(matchDao);
        when(daoHelper.createBetDao()).thenReturn(betDao);
        when(daoHelper.createAccountDao()).thenReturn(accountDao);
        DaoHelperFactory daoHelperFactory = Mockito.mock(DaoHelperFactory.class);
        when(daoHelperFactory.create()).thenReturn(daoHelper);
        matchValidator = Mockito.mock(Validator.class);
        matchService = new MatchServiceImpl(daoHelperFactory, matchValidator);
    }

    @Test
    public void testSaveMatchShouldSaveWhenMatchIsValid()
            throws ServiceException, DaoException {
        //given
        //when
        when(matchValidator.isValid(anyObject())).thenReturn(true);
        matchService.saveMatch(MATCH);
        //then
        verify(matchDao, times(1)).save(anyObject());
    }

    //then
    @Test(expected = ServiceException.class)
    public void testSaveMatchShouldSaveWhenMathIsInvalid()
            throws ServiceException {
        //given
        //when
        when(matchValidator.isValid(anyObject())).thenReturn(false);
        matchService.saveMatch(MATCH);
    }

    @Test
    public void testRemoveByIdShouldRemove() throws ServiceException, DaoException {
        //given
        //when
        matchService.removeById(ANY_LONG);
        //then
        verify(matchDao, times(1)).removeById(anyLong());
    }

    @Test
    public void testGetMatchByIdShouldGetWhenMatchIsExist()
            throws ServiceException, DaoException {
        //given
        //when
        when(matchDao.findById(anyLong())).thenReturn(Optional.of(MATCH));
        Match actualMatch = matchService.getMatchById(ANY_INT);
        //then
        Assert.assertEquals(MATCH, actualMatch);
    }

    //then
    @Test(expected = ServiceException.class)
    public void testGetMatchByIdShouldThrowExceptionWhenMatchIsNotExist()
            throws ServiceException, DaoException {
        //given
        //when
        when(matchDao.findById(anyLong())).thenReturn(Optional.empty());
        matchService.saveMatch(MATCH);
    }

    @Test
    public void testGetMatchesTypeRangeShouldGet() throws ServiceException, DaoException {
        //given
        //when
        matchService.getMatchesTypeRange(MatchType.ANY, ANY_INT, ANY_INT);
        //then
        verify(matchDao, times(1)).getMatchesTypeRange(anyObject(), anyInt(), anyInt());
    }

    @Test
    public void testGetMatchesTypeAmountShouldGet() throws ServiceException, DaoException {
        //given
        //when
        matchService.getMatchesTypeAmount(MatchType.ANY);
        //then
        verify(matchDao, times(1)).getMatchesTypeAmount(anyObject());
    }

    @Test
    public void testSetCommissionByIdShouldSet() throws ServiceException, DaoException {
        //given
        //when
        matchService.setCommissionById(ANY_FLOAT, ANY_LONG);
        //then
        verify(matchDao, times(1)).setCommissionById(anyFloat(), anyLong());
    }

    @Test
    public void testCancelMatchByIdShouldCancel() throws ServiceException, DaoException {
        //given
        //when
        when(betDao.getBetsByMatchId(anyLong())).thenReturn(Collections.singletonList(BET));
        matchService.cancelMatchById(ANY_LONG);
        //then
        verify(accountDao).addMoneyById(anyObject(), anyLong());
        verify(betDao).removeById(anyLong());
        verify(matchDao, times(1)).removeById(anyLong());
    }


}
