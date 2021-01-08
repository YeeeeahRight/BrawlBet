package com.epam.web.dao.mapper;

import com.epam.web.model.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper<T extends Entity> {
    T map(ResultSet resultSet) throws SQLException;
}
