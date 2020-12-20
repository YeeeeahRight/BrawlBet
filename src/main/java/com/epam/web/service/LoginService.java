package com.epam.web.service;

import com.epam.web.dao.helper.DaoHelper;
import com.epam.web.dao.helper.DaoHelperFactory;
import com.epam.web.exceptions.DaoException;
import com.epam.web.dao.user.UserDao;
import com.epam.web.entity.User;
import com.epam.web.exceptions.ServiceException;

import java.util.Optional;

public class LoginService {
    private final DaoHelperFactory daoHelperFactory;

    public LoginService(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }

    public boolean isUserExist(String login, String password) throws ServiceException {
        try(DaoHelper daoHelper = daoHelperFactory.create()) {
            //daoHelper.startTransaction();
            UserDao userDao = daoHelper.createUserDao();
            Optional<User> user = userDao.findUserByLoginPassword(login, password);
            return user.isPresent();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public boolean isBlocked(String login) throws ServiceException {
        try(DaoHelper daoHelper = daoHelperFactory.create()) {
            UserDao userDao = daoHelper.createUserDao();
            Optional<User> user = userDao.findUserByLogin(login);
            if (!user.isPresent()) {
                throw new ServiceException("User not found.");
            }
            return user.get().isBlocked();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
