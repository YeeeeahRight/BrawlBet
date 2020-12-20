package com.epam.web.dao.user;

import com.epam.web.dao.AbstractDao;
import com.epam.web.exceptions.DaoException;
import com.epam.web.entity.User;
import com.epam.web.mapper.impl.UserRowMapper;

import java.sql.Connection;
import java.util.Optional;

public class UserDaoImpl extends AbstractDao<User> implements UserDao {
    private static final String BLOCK_QUERY = "UPDATE users SET isBanned=1 WHERE ID=";
    private static final String UNBLOCK_QUERY = "UPDATE users SET isBanned=0 WHERE ID=";
    private static final String FIND_BY_LOGIN_AND_PASSWORD =
            "SELECT * FROM USERS WHERE name = ? AND password = ?";
    private static final String FIND_BY_LOGIN =
            "SELECT * FROM USERS WHERE name = ?";
    private static final String INSERT_QUERY_FORMAT = "INSERT users(name, password, role) VALUES('%s','%s','%s')";


    public UserDaoImpl(Connection connection) {
        super(connection, new UserRowMapper(), User.TABLE);
    }

    @Override
    public void save(User item) throws DaoException {
        String saveQuery = String.format(INSERT_QUERY_FORMAT, item.getName(), item.getPassword(), item.getRole());
        executeUpdate(saveQuery);
    }

    @Override
    public Optional<User> findUserByLoginPassword(String login, String password) throws DaoException {
        return executeForSingleResult(FIND_BY_LOGIN_AND_PASSWORD, login, password);
    }

    @Override
    public Optional<User> findUserByLogin(String login) throws DaoException {
        return executeForSingleResult(FIND_BY_LOGIN, login);
    }

    @Override
    public void block(Long id) throws DaoException {
        executeUpdate(BLOCK_QUERY + id);
    }

    @Override
    public void unblock(Long id) throws DaoException {
        executeUpdate(UNBLOCK_QUERY + id);
    }
}
