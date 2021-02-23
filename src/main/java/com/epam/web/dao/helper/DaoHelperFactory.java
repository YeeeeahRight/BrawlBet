package com.epam.web.dao.helper;

import com.epam.web.connection.ConnectionPool;

public class DaoHelperFactory {

    public DaoHelper create() {
        return new DaoHelper(ConnectionPool.getInstance());
    }
}
