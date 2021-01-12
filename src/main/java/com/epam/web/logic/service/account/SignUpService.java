package com.epam.web.logic.service.account;

import com.epam.web.exception.ServiceException;

public interface SignUpService {
    void signUp(String login, String password) throws ServiceException;

    boolean isUsernameExist(String login) throws ServiceException;
}
