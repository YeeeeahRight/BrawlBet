package com.epam.web.service;

import com.epam.web.dao.helper.DaoHelper;
import com.epam.web.dao.helper.DaoHelperFactory;
import com.epam.web.dao.user.UserDao;
import com.epam.web.entity.User;
import com.epam.web.exceptions.DaoException;
import com.epam.web.exceptions.ServiceException;

import java.util.Optional;

public class SignUpService {
    private static final String ROLE = "user";
    private final DaoHelperFactory daoHelperFactory;

    public SignUpService(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }

    public void sign(String login, String password) throws ServiceException {
        try(DaoHelper daoHelper = daoHelperFactory.create()) {
            UserDao userDao = daoHelper.createUserDao();
            User newUser = new User(login, password, ROLE);
            userDao.save(newUser);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public boolean isUsernameExist(String login) throws ServiceException {
        try(DaoHelper daoHelper = daoHelperFactory.create()) {
            UserDao userDao = daoHelper.createUserDao();
            Optional<User> user = userDao.findUserByLogin(login);
            return user.isPresent();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
