package com.epam.web.service;

import com.epam.web.dao.helper.DaoHelper;
import com.epam.web.dao.helper.DaoHelperFactory;
import com.epam.web.dao.user.UserDao;
import com.epam.web.entity.User;
import com.epam.web.exceptions.DaoException;
import com.epam.web.exceptions.ServiceException;

import java.util.List;

public class UserService {
    private final DaoHelperFactory daoHelperFactory;

    public UserService(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }

    public List<User> getAll() throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()){
            UserDao matchDao = daoHelper.createUserDao();
            return matchDao.getAll();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public void unblock(Long id) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()){
            UserDao matchDao = daoHelper.createUserDao();
            matchDao.unblock(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public void block(Long id) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()){
            UserDao matchDao = daoHelper.createUserDao();
            matchDao.block(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
