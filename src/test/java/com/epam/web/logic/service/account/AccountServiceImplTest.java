package com.epam.web.logic.service.account;

import com.epam.web.dao.helper.DaoHelper;
import com.epam.web.dao.helper.DaoHelperFactory;
import com.epam.web.dao.impl.account.AccountDao;
import com.epam.web.exception.DaoException;
import com.epam.web.exception.ServiceException;
import com.epam.web.model.entity.Account;
import com.epam.web.model.enumeration.AccountRole;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class AccountServiceImplTest {
    private static final Account ACCOUNT = new Account("name", "password", AccountRole.USER);
    private static final int ANY_INT = 2;
    private static final String ANY_STRING = "String";

    private AccountDao accountDao;
    private AccountService accountService;

    @Before
    public void initMethod() {
        accountDao = Mockito.mock(AccountDao.class);
        DaoHelper daoHelper = Mockito.mock(DaoHelper.class);
        when(daoHelper.createAccountDao()).thenReturn(accountDao);
        DaoHelperFactory daoHelperFactory = Mockito.mock(DaoHelperFactory.class);
        when(daoHelperFactory.create()).thenReturn(daoHelper);
        accountService = new AccountServiceImpl(daoHelperFactory);
    }

    @Test
    public void testGetUsersRangeShouldGet() throws ServiceException, DaoException {
        //given
        //when
        accountService.getUsersRange(ANY_INT, ANY_INT);
        //then
        verify(accountDao, times(1)).getUsersRange(ANY_INT, ANY_INT);
    }

    @Test
    public void testGetUsersAmountShouldGet() throws ServiceException, DaoException {
        //given
        //when
        accountService.getUsersAmount();
        //then
        verify(accountDao, times(1)).getUsersAmount();
    }

    @Test
    public void testFindBookmakerShouldFind() throws ServiceException, DaoException {
        //given
        //when
        accountService.findBookmaker();
        //then
        verify(accountDao, times(1)).findBookmaker();
    }

    @Test
    public void testGetBalanceShouldGetWhenAccountExist() throws ServiceException, DaoException {
        //given
        //when
        when(accountDao.findById(ANY_INT)).thenReturn(Optional.of(ACCOUNT));
        accountService.getBalance(ANY_INT);
        //then
        verify(accountDao, times(1)).findById(ANY_INT);
    }

    //then
    @Test(expected = ServiceException.class)
    public void testGetBalanceShouldThrowExceptionWhenAccountIsNotExist() throws ServiceException, DaoException {
        //given
        //when
        when(accountDao.findById(ANY_INT)).thenReturn(Optional.empty());
        accountService.getBalance(ANY_INT);
    }

    @Test
    public void testAddMoneyByIdShouldAdd() throws ServiceException, DaoException {
        //given
        //when
        accountService.addMoneyById(BigDecimal.TEN, ANY_INT);
        //then
        verify(accountDao, times(1)).addMoneyById(BigDecimal.TEN, ANY_INT);
    }

    @Test
    public void testUnlockByIdShouldUnlock() throws ServiceException, DaoException {
        //given
        //when
        accountService.unblockById(ANY_INT);
        //then
        verify(accountDao, times(1)).unblockById(ANY_INT);
    }

    @Test
    public void testBlockByIdShouldBlock() throws ServiceException, DaoException {
        //given
        //when
        accountService.blockById(ANY_INT);
        //then
        verify(accountDao, times(1)).blockById(ANY_INT);
    }

    @Test
    public void testIsBlockedByIdShouldReturnFalseWhenNewAccountExist() throws ServiceException, DaoException {
        //given
        //when
        when(accountDao.findById(ANY_INT)).thenReturn(Optional.of(ACCOUNT));
        boolean isBlocked = accountService.isBlockedById(ANY_INT);
        //then
        Assert.assertFalse(isBlocked);
    }

    //then
    @Test(expected = ServiceException.class)
    public void testIsBlockedByIdShouldThrowExceptionWhenAccountIsNotExist()
            throws ServiceException, DaoException {
        //given
        //when
        when(accountDao.findById(ANY_INT)).thenReturn(Optional.empty());
        accountService.isBlockedById(ANY_INT);
    }

    @Test
    public void testIsAccountExistByLoginPasswordShouldReturnTrueWhenAccountIsExist()
            throws ServiceException, DaoException {
        //given
        //when
        when(accountDao.findAccountByLoginPassword(ANY_STRING, ANY_STRING)).thenReturn(Optional.of(ACCOUNT));
        boolean isExist = accountService.isAccountExistByLoginPassword(ANY_STRING, ANY_STRING);
        //then
        Assert.assertTrue(isExist);
    }

    @Test
    public void testIsAccountExistByLoginPasswordShouldReturnFalseWhenAccountIsNotExist()
            throws ServiceException, DaoException {
        //given
        //when
        when(accountDao.findAccountByLoginPassword(ANY_STRING, ANY_STRING)).thenReturn(Optional.empty());
        boolean isExist = accountService.isAccountExistByLoginPassword(ANY_STRING, ANY_STRING);
        //then
        Assert.assertFalse(isExist);
    }

    @Test
    public void testGetAccountByLoginShouldGetWhenAccountExist() throws ServiceException, DaoException {
        //given
        //when
        when(accountDao.findAccountByLogin(ANY_STRING)).thenReturn(Optional.of(ACCOUNT));
        accountService.getAccountByLogin(ANY_STRING);
        //then
        verify(accountDao, times(1)).findAccountByLogin(ANY_STRING);
    }

    //then
    @Test(expected = ServiceException.class)
    public void testGetAccountByLoginShouldThrowExceptionWhenAccountIsNotExist()
            throws ServiceException, DaoException {
        //given
        //when
        when(accountDao.findAccountByLogin(ANY_STRING)).thenReturn(Optional.empty());
        accountService.getAccountByLogin(ANY_STRING);
    }
}
