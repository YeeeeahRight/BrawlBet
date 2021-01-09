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
        long id = resultSet.getLong(Account.ID);
        int balance = resultSet.getInt(Account.BALANCE);
        boolean isBlocked = resultSet.getString(Account.STATUS).equals("1");

        return new Account(id, name, password, role, balance, isBlocked);
    }
}
