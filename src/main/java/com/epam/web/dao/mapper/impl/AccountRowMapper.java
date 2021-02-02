package com.epam.web.dao.mapper.impl;

import com.epam.web.model.entity.Account;
import com.epam.web.dao.mapper.RowMapper;
import com.epam.web.model.enumeration.AccountRole;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRowMapper implements RowMapper<Account> {

    @Override
    public Account map(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString(Account.NAME);
        String password = resultSet.getString(Account.PASSWORD);
        String roleStr = resultSet.getString(Account.ROLE);
        AccountRole accountRole = AccountRole.valueOf(roleStr);
        Long id = resultSet.getLong(Account.ID);
        BigDecimal balance = resultSet.getBigDecimal(Account.BALANCE);
        Boolean isBlocked = resultSet.getString(Account.STATUS).equals("1");

        return new Account(id, name, password, accountRole, balance, isBlocked);
    }
}
