package com.epam.web.connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    //TODO: Change to atomic boolean
    private static volatile ConnectionPool instance = null;
    private final Queue<ProxyConnection> availableConnections;
    private final Queue<ProxyConnection> usingConnections;
    private final ReentrantLock connectionLocker = new ReentrantLock();
    private final ConnectionFactory connectionFactory = new ConnectionFactory();
    private static final ReentrantLock INSTANCE_LOCKER = new ReentrantLock();
    private static final int POOL_SIZE = 10;

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
        ConnectionPool localInstance = instance;
        if (localInstance == null) {
            INSTANCE_LOCKER.lock();
            try {
                localInstance = instance;
                if (localInstance == null) {
                    instance = new ConnectionPool();
                }
            } finally {
                INSTANCE_LOCKER.unlock();
            }
        }
        return instance;
    }

    public void returnConnection(ProxyConnection proxyConnection) {
        connectionLocker.lock();
        try {
            if (usingConnections.contains(proxyConnection)) {
                availableConnections.offer(proxyConnection);
            }
        } finally {
            connectionLocker.unlock();
        }
    }

    public ProxyConnection getConnection() {
        connectionLocker.lock();
        try {
            ProxyConnection proxyConnection = availableConnections.poll();
            usingConnections.offer(proxyConnection);
            return proxyConnection;
        } finally {
            connectionLocker.unlock();
        }
    }

    public void closeAll() {
        usingConnections.forEach(this::returnConnection);
        for (ProxyConnection proxyConnection : availableConnections) {
            try {
                proxyConnection.finalCloseConnection();
            } catch (SQLException e) {
               // LOGGER.error(e.getMessage(), e);
            }
        }
    }
}
