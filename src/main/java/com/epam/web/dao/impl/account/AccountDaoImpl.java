package com.epam.web.dao.impl.account;

import com.epam.web.dao.AbstractDao;
import com.epam.web.model.entity.Account;
import com.epam.web.exception.DaoException;
import com.epam.web.dao.mapper.impl.AccountRowMapper;

import java.sql.Connection;
import java.util.Optional;

public class AccountDaoImpl extends AbstractDao<Account> implements AccountDao {
    private static final String BLOCK_QUERY = "UPDATE accounts SET is_blocked=1 WHERE id=";
    private static final String UNBLOCK_QUERY = "UPDATE accounts SET is_blocked=0 WHERE id=";
    private static final String ADD_MONEY_BALANCE_QUERY = "UPDATE accounts SET balance=balance+? WHERE id=?";
    private static final String FIND_BY_LOGIN_AND_PASSWORD =
            "SELECT * FROM accounts WHERE name = ? AND password = ?";
    private static final String FIND_BY_LOGIN =
            "SELECT * FROM accounts WHERE name = ?";
    private static final String INSERT_QUERY = "INSERT accounts(name, password, role) VALUES(?, ?, ?)";

    public AccountDaoImpl(Connection connection) {
        super(connection, new AccountRowMapper(), Account.TABLE);
    }

    @Override
    public void save(Account item) throws DaoException {
        String name = item.getName();
        String password = item.getPassword();
        String role = item.getRole().toString();
        updateSingle(INSERT_QUERY, name, password, role);
    }

    @Override
    public Optional<Account> findAccountByLoginPassword(String login, String password) throws DaoException {
        return executeForSingleResult(FIND_BY_LOGIN_AND_PASSWORD, login, password);
    }

    @Override
    public Optional<Account> findAccountByLogin(String login) throws DaoException {
        return executeForSingleResult(FIND_BY_LOGIN, login);
    }

    @Override
    public void block(long id) throws DaoException {
        executeUpdate(BLOCK_QUERY + id);
    }

    @Override
    public void unblock(long id) throws DaoException {
        executeUpdate(UNBLOCK_QUERY + id);
    }

    @Override
    public void addMoneyToBalance(int money, long id) throws DaoException {
        updateSingle(ADD_MONEY_BALANCE_QUERY, money, id);
    }
}
