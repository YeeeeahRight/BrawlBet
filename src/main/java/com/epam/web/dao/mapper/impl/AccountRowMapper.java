package com.epam.web.dao.mapper.impl;

import com.epam.web.model.entity.Account;
import com.epam.web.dao.mapper.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRowMapper implements RowMapper<Account> {

    @Override
    public Account map(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString(Account.NAME);
        String password = resultSet.getString(Account.PASSWORD);
        String role = resultSet.getString(Account.ROLE);
        Long id = Long.valueOf(resultSet.getString(Account.ID));
        BigDecimal balance = BigDecimal.valueOf(Long.parseLong(resultSet.getString(Account.BALANCE)));
        boolean isBanned = resultSet.getString(Account.STATUS).equals("1");

        return new Account(name, password, role, id, balance, isBanned);
    }
}
