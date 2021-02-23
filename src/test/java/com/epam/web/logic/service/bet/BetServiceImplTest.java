package com.epam.web.logic.service.bet;

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
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

public class BetServiceImplTest {
    private static final Bet BET =
            new Bet(1L, 2L, BigDecimal.TEN, 2L, new Date());
    private static final Match MATCH =
            new Match(new Date(), "tour", 2L, 1L);
    private static final int ANY_INT = 2;
    private static final long ANY_LONG = 2L;

    private BetDao betDao;
    private MatchDao matchDao;
    private AccountDao accountDao;
    private Validator<Bet> betValidator;
    private BetService betService;
    private DaoHelper daoHelper;

    @Before
    public void initMethod() {
        betDao = Mockito.mock(BetDao.class);
        matchDao = Mockito.mock(MatchDao.class);
        accountDao = Mockito.mock(AccountDao.class);
        daoHelper = Mockito.mock(DaoHelper.class);
        when(daoHelper.createBetDao()).thenReturn(betDao);
        DaoHelperFactory daoHelperFactory = Mockito.mock(DaoHelperFactory.class);
        when(daoHelperFactory.create()).thenReturn(daoHelper);
        betValidator = Mockito.mock(Validator.class);
        betService = new BetServiceImpl(daoHelperFactory, betValidator);
    }

    @Test
    public void testSaveBetShouldSaveWhenBetIsValid() throws ServiceException, DaoException {
        //given
        //when
        when(daoHelper.createMatchDao()).thenReturn(matchDao);
        when(matchDao.findById(ANY_INT)).thenReturn(Optional.of(MATCH));
        when(daoHelper.createAccountDao()).thenReturn(accountDao);
        when(betValidator.isValid(anyObject())).thenReturn(true);
        betService.saveBet(BET);
        //then
        verify(betDao, times(1)).save(anyObject());
        verify(accountDao, times(1)).addMoneyById(anyObject(), anyLong());
        verify(matchDao, times(1)).addTeamBetAmount(anyObject(), anyObject(), anyLong());
    }

    //then
    @Test(expected = ServiceException.class)
    public void testSaveBetShouldThrowExceptionWhenBetIsInvalid() throws ServiceException, DaoException {
        //given
        //when
        when(betValidator.isValid(anyObject())).thenReturn(false);
        betService.saveBet(BET);
    }

    @Test
    public void testGetBetsByMatchIdShouldGet() throws ServiceException, DaoException {
        //given
        //when
        betService.getBetsByMatchId(ANY_LONG);
        //then
        verify(betDao, times(1)).getBetsByMatchId(anyInt());
    }

    @Test
    public void testBetsByAccountIdRangeShouldGet() throws ServiceException, DaoException {
        //given
        //when
        betService.getBetsByAccountIdRange(ANY_LONG, ANY_INT, ANY_INT);
        //then
        verify(betDao, times(1)).getBetsByAccountIdRange(anyLong(), anyInt(), anyInt());
    }

    @Test
    public void testGetBetsAmountByAccountIdShouldGet() throws ServiceException, DaoException {
        //given
        //when
        betService.getBetsAmountByAccountId(ANY_LONG);
        //then
        verify(betDao, times(1)).getBetsAmountByAccountId(anyLong());
    }
}
