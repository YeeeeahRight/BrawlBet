package com.epam.web.connection;

import com.epam.web.exception.ConnectionPoolException;
import com.epam.web.exception.DaoException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final int POOL_SIZE = 10;
    private static final AtomicBoolean IS_POOL_CREATED = new AtomicBoolean(false);
    private static final Semaphore SEMAPHORE = new Semaphore(POOL_SIZE, true);
    private static final ReentrantLock INSTANCE_LOCKER = new ReentrantLock();
    private static final ReentrantLock CONNECTION_LOCKER = new ReentrantLock();
    private static ConnectionPool instance = null;
    private final Queue<ProxyConnection> availableConnections;
    private final Queue<ProxyConnection> usingConnections;
    private final ConnectionFactory connectionFactory = new ConnectionFactory();

    private ConnectionPool() {
        availableConnections = new ArrayDeque<>();
        usingConnections = new ArrayDeque<>();
        for (int i = 0; i < POOL_SIZE; i++) {
            Connection connection = connectionFactory.create();
            ProxyConnection proxyConnection = new ProxyConnection(connection, this);
            availableConnections.add(proxyConnection);
        }
    }

    public static ConnectionPool getInstance() {
        if (!IS_POOL_CREATED.get()) {
            INSTANCE_LOCKER.lock();
            try {
                if (!IS_POOL_CREATED.get()) {
                    instance = new ConnectionPool();
                    IS_POOL_CREATED.set(true);
                }
            } finally {
                INSTANCE_LOCKER.unlock();
            }

        }
        return instance;
    }

    public void returnConnection(ProxyConnection proxyConnection) {
        CONNECTION_LOCKER.lock();
        try {
            if (usingConnections.contains(proxyConnection)) {
                availableConnections.offer(proxyConnection);
                usingConnections.poll();
                SEMAPHORE.release();
            }
        } finally {
            CONNECTION_LOCKER.unlock();
        }
    }

    public ProxyConnection getConnection() {
        try {
            SEMAPHORE.acquire();
            CONNECTION_LOCKER.lock();
            ProxyConnection currentConnection = availableConnections.poll();
            usingConnections.offer(currentConnection);
            return currentConnection;
        } catch (InterruptedException e) {
            throw new ConnectionPoolException(e);
        } finally {
            CONNECTION_LOCKER.unlock();
        }
    }

    public void closeAll() throws DaoException {
        availableConnections.addAll(usingConnections);
        usingConnections.clear();
        for (ProxyConnection proxyConnection : availableConnections) {
            try {
                proxyConnection.finalCloseConnection();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
    }
}
