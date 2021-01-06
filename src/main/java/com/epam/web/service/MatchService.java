package com.epam.web.service;

import com.epam.web.dao.helper.DaoHelper;
import com.epam.web.dao.helper.DaoHelperFactory;
import com.epam.web.dao.match.MatchDao;
import com.epam.web.model.entity.Match;
import com.epam.web.exceptions.DaoException;
import com.epam.web.exceptions.ServiceException;

import java.util.List;
import java.util.Optional;

public class MatchService {
    private final DaoHelperFactory daoHelperFactory;

    public MatchService(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }

    public List<Match> getAll() throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MatchDao matchDao = daoHelper.createMatchDao();
            return matchDao.getAll();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public void removeById(Long id) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MatchDao matchDao = daoHelper.createMatchDao();
            matchDao.removeById(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public void saveMatch(Match match) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MatchDao matchDao = daoHelper.createMatchDao();
            matchDao.save(match);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public void editMatch(Match match, Long id) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MatchDao matchDao = daoHelper.createMatchDao();
            matchDao.edit(match, id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public Match findById(Long id) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MatchDao matchDao = daoHelper.createMatchDao();
            Optional<Match> match = matchDao.findById(id);
            if (!match.isPresent()) {
                throw new ServiceException("There is no such match with this id anymore.");
            }
            return match.get();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public void addCommission(Float commission, Long id) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MatchDao matchDao = daoHelper.createMatchDao();
            Optional<Match> matchOptional = matchDao.findById(id);
            if (!matchOptional.isPresent()) {
                throw new ServiceException("There is no such match with this id anymore.");
            }
            Match match = matchOptional.get();
            System.out.println(match);
            match.setCommission(commission);
            System.out.println(match);
            matchDao.edit(match, id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
