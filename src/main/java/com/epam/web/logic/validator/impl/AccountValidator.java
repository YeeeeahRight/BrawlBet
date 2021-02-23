package com.epam.web.logic.validator.impl;

import com.epam.web.logic.validator.Validator;
import com.epam.web.model.entity.Account;
import com.epam.web.model.enumeration.AccountRole;

public class AccountValidator implements Validator<Account> {
    private static final int MAX_LOGIN_LENGTH = 20;
    private static final int MAX_PASSWORD_LENGTH = 25;

    @Override
    public boolean isValid(Account account) {
        String name = account.getName();
        String password = account.getPassword();
        AccountRole role = account.getRole();
        if (name == null || role == null || password == null) {
            return false;
        }
        if (name.isEmpty() || name.length() > MAX_LOGIN_LENGTH) {
            return false;
        }
        if (password.isEmpty() || password.length() > MAX_PASSWORD_LENGTH) {
            return false;
        }
        return true;
    }
}
