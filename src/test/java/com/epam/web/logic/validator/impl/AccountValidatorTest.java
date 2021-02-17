package com.epam.web.logic.validator.impl;

import com.epam.web.logic.validator.Validator;
import com.epam.web.model.entity.Account;
import com.epam.web.model.enumeration.AccountRole;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AccountValidatorTest {
    private static final Account VALID_ACCOUNT = new Account("name", "password", AccountRole.USER);
    private static final Account INVALID_ACCOUNT = new Account("name", "", AccountRole.USER);

    private Validator<Account> accountValidator;

    @Before
    public void initMethod() {
        accountValidator = new AccountValidator();
    }

    @Test
    public void testIsValidShouldReturnTrueWhenAccountIsValid() {
        //given
        boolean isValid;
        //when
        isValid = accountValidator.isValid(VALID_ACCOUNT);
        //then
        Assert.assertTrue(isValid);
    }

    @Test
    public void testIsValidShouldReturnTrueWhenAccountIsInvalid() {
        //given
        boolean isValid;
        //when
        isValid = accountValidator.isValid(INVALID_ACCOUNT);
        //then
        Assert.assertFalse(isValid);
    }
}
