package com.epam.web.logic.service.account;

import com.epam.web.dao.helper.DaoHelper;
import com.epam.web.dao.helper.DaoHelperFactory;
import com.epam.web.dao.impl.account.AccountDao;
import com.epam.web.exception.DaoException;
import com.epam.web.exception.ServiceException;
import com.epam.web.logic.validator.Validator;
import com.epam.web.model.entity.Account;
import com.epam.web.model.enumeration.AccountRole;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class SignUpServiceImplTest {
    private static final Account ACCOUNT = new Account("name", "password", AccountRole.USER);
    private static final String ANY_STRING = "String";

    private AccountDao accountDao;
    private Validator<Account> accountValidator;
    private SignUpService signUpService;

    @Before
    public void initMethod() {
        accountDao = Mockito.mock(AccountDao.class);
        DaoHelper daoHelper = Mockito.mock(DaoHelper.class);
        when(daoHelper.createAccountDao()).thenReturn(accountDao);
        DaoHelperFactory daoHelperFactory = Mockito.mock(DaoHelperFactory.class);
        when(daoHelperFactory.create()).thenReturn(daoHelper);
        accountValidator = Mockito.mock(Validator.class);
        signUpService = new SignUpServiceImpl(daoHelperFactory, accountValidator);
    }

    @Test
    public void testSignUpShouldSignUpWhenAccountIsValid() throws ServiceException, DaoException {
        //given
        //when
        when(accountValidator.isValid(anyObject())).thenReturn(true);
        signUpService.signUp(ANY_STRING, ANY_STRING);
        //then
        verify(accountDao, times(1)).save(anyObject());
    }

    //then
    @Test(expected = ServiceException.class)
    public void testSignUpShouldThrowExceptionWhenAccountIsInvalid() throws ServiceException, DaoException {
        //given
        //when
        when(accountValidator.isValid(anyObject())).thenReturn(false);
        signUpService.signUp(ANY_STRING, ANY_STRING);
    }

    @Test
    public void testIsUsernameExistShouldReturnTrueWhenAccountIsExist() throws ServiceException, DaoException {
        //given
        //when
        when(accountDao.findAccountByLogin(ANY_STRING)).thenReturn(Optional.of(ACCOUNT));
        boolean isExist = signUpService.isUsernameExist(ANY_STRING);
        //then
        Assert.assertTrue(isExist);
    }

    @Test
    public void testIsUsernameExistShouldReturnFalseWhenAccountIsExist() throws ServiceException, DaoException {
        //given
        //when
        when(accountDao.findAccountByLogin(ANY_STRING)).thenReturn(Optional.empty());
        boolean isExist = signUpService.isUsernameExist(ANY_STRING);
        //then
        Assert.assertFalse(isExist);
    }
}
