package com.epam.web.mapper.impl;

import com.epam.web.entity.User;
import com.epam.web.mapper.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User map(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString(User.NAME);
        String password = resultSet.getString(User.PASSWORD);
        String role = resultSet.getString(User.ROLE);
        Long id = Long.valueOf(resultSet.getString(User.ID));
        BigDecimal balance = BigDecimal.valueOf(Long.parseLong(resultSet.getString(User.BALANCE)));
        boolean isBanned = resultSet.getString(User.STATUS).equals("1");

        return new User(name, password, role, id, balance, isBanned);
    }
}
