package com.epam.web.dao;

import com.epam.web.exceptions.DaoException;
import com.epam.web.dao.mapper.RowMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<T> implements Dao<T> {
    private final Connection connection;
    private final RowMapper<T> mapper;
    private final String tableName;

    protected AbstractDao(Connection connection, RowMapper<T> mapper, String tableName) {
        this.connection = connection;
        this.mapper = mapper;
        this.tableName = tableName;
    }

    @Override
    public List<T> getAll() throws DaoException{
        return executeQuery("SELECT * FROM " + tableName);
    }

    @Override
    public void removeById(Long id) throws DaoException {
        executeUpdate("DELETE FROM " + tableName + " WHERE id=" + id);
    }

    @Override
    public Optional<T> findById(Long id) throws DaoException{
        T item = executeQuery("SELECT * FROM " + tableName + " WHERE id=" + id).get(0);
        return Optional.of(item);
    }

    protected List<T> executeQuery(String query, Object... params) throws DaoException {
        try (PreparedStatement statement = createStatement(query, params)){
            ResultSet resultSet = statement.executeQuery();
            List<T> entities = new ArrayList<>();
            while (resultSet.next()) {
                T entity = mapper.map(resultSet);
                entities.add(entity);
            }
            return entities;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private PreparedStatement createStatement(String query, Object... params) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        for (int i = 1; i <= params.length; i++) {
            statement.setObject(i, params[i-1]);
        }
        return statement;
    }

    protected Optional<T> executeForSingleResult(String query, Object... params) throws DaoException {
        List<T> items = executeQuery(query, params);
        if (items.size() == 1) {
            return Optional.of(items.get(0));
        } else if (items.size() > 1) {
            throw new IllegalArgumentException("More than one record found.");
        } else {
            return Optional.empty();
        }
    }

    protected void executeUpdate(String query) throws DaoException {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    protected void updateSingle(String query, Object ...params) throws DaoException {
        try (PreparedStatement preparedStatement = createStatement(query, params)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
