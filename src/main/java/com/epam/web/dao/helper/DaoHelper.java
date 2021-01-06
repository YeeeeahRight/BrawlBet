package com.epam.web.dao.helper;

import com.epam.web.connection.ConnectionPool;
import com.epam.web.connection.ProxyConnection;
import com.epam.web.dao.match.MatchDao;
import com.epam.web.dao.match.MatchDaoImpl;
import com.epam.web.exceptions.DaoException;
import com.epam.web.dao.account.AccountDao;
import com.epam.web.dao.account.AccountDaoImpl;

import java.sql.SQLException;

public class DaoHelper implements AutoCloseable {
    private ProxyConnection connection;

    public DaoHelper(ConnectionPool pool) {
        this.connection = pool.getConnection();
    }

    public AccountDao createUserDao() {
        return new AccountDaoImpl(connection);
    }

    public MatchDao createMatchDao() {
        return new MatchDaoImpl(connection);
    }

    public void startTransaction() throws DaoException {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    @Override
    public void close() {
        connection.close();
    }
}
