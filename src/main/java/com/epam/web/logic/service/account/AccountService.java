package com.epam.web.logic.service.account;

import com.epam.web.exception.ServiceException;
import com.epam.web.model.entity.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    List<Account> getAll() throws ServiceException;

    float getBalance(long id) throws ServiceException;

    void addMoneyById(float money, long id) throws ServiceException;

    void unblockById(long id) throws ServiceException;

    void block(long id) throws ServiceException;

    boolean isAccountExistByLoginPassword(String login, String password) throws ServiceException;

    Account getAccountByLogin(String login) throws ServiceException;
}
