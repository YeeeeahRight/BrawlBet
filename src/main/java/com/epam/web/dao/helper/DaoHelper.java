package com.epam.web.dao.helper;

import com.epam.web.connection.ConnectionPool;
import com.epam.web.connection.ProxyConnection;
import com.epam.web.dao.impl.bet.BetDao;
import com.epam.web.dao.impl.bet.BetDaoImpl;
import com.epam.web.dao.impl.match.MatchDao;
import com.epam.web.dao.impl.match.MatchDaoImpl;
import com.epam.web.exception.DaoException;
import com.epam.web.dao.impl.account.AccountDao;
import com.epam.web.dao.impl.account.AccountDaoImpl;

import java.sql.SQLException;

public class DaoHelper implements AutoCloseable {
    private ProxyConnection connection;

    public DaoHelper(ConnectionPool pool) {
        this.connection = pool.getConnection();
    }

    public AccountDao createAccountDao() {
        return new AccountDaoImpl(connection);
    }

    public MatchDao createMatchDao() {
        return new MatchDaoImpl(connection);
    }

    public BetDao createBetDao() {
        return new BetDaoImpl(connection);
    }

    public void startTransaction() throws DaoException {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    private void rollback() throws DaoException {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void commit() throws DaoException {
        try {
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                throw new DaoException(rollbackException);
            }
        }
    }

    @Override
    public void close() throws DaoException {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
    }
}
