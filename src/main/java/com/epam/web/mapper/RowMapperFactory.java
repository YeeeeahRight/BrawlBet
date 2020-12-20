package com.epam.web.mapper;


import com.epam.web.entity.Match;
import com.epam.web.entity.User;
import com.epam.web.mapper.impl.MatchRowMapper;
import com.epam.web.mapper.impl.UserRowMapper;

//useless?
public class RowMapperFactory {

    public RowMapper create(String table) {
        switch (table) {
            case User.TABLE:
                return new UserRowMapper();
            case Match.TABLE:
                return new MatchRowMapper();
            default:
                throw new IllegalArgumentException("Unknown table: " + table);
        }
    }
}
