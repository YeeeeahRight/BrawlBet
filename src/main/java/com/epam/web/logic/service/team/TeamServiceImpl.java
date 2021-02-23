package com.epam.web.logic.service.team;

import com.epam.web.dao.helper.DaoHelper;
import com.epam.web.dao.helper.DaoHelperFactory;
import com.epam.web.dao.impl.team.TeamDao;
import com.epam.web.exception.DaoException;
import com.epam.web.exception.ServiceException;
import com.epam.web.logic.validator.Validator;
import com.epam.web.model.entity.Team;

import java.util.List;
import java.util.Optional;

public class TeamServiceImpl implements TeamService {
    private final DaoHelperFactory daoHelperFactory;
    private final Validator<Team> teamValidator;

    public TeamServiceImpl(DaoHelperFactory daoHelperFactory, Validator<Team> teamValidator) {
        this.daoHelperFactory = daoHelperFactory;
        this.teamValidator = teamValidator;
    }

    @Override
    public void saveTeam(Team team) throws ServiceException {
        if (!teamValidator.isValid(team)) {
            throw new ServiceException("Invalid team data.");
        }
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            TeamDao teamDao = daoHelper.createTeamDao();
            teamDao.save(team);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Team getTeamById(long id) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            TeamDao teamDao = daoHelper.createTeamDao();
            Optional<Team> teamOptional = teamDao.findById(id);
            if (!teamOptional.isPresent()) {
                throw new ServiceException("Team with id='" + id + "' is not found.");
            }
            return teamOptional.get();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Long getTeamIdByName(String name) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            TeamDao teamDao = daoHelper.createTeamDao();
            Optional<Team> teamOptional = teamDao.findTeamByName(name);
            if (!teamOptional.isPresent()) {
                throw new ServiceException("Team with name='" + name + "' is not found.");
            }
            Team team = teamOptional.get();
            return team.getId();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public String getTeamNameById(long id) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            TeamDao teamDao = daoHelper.createTeamDao();
            Optional<Team> teamOptional = teamDao.findById(id);
            if (!teamOptional.isPresent()) {
                throw new ServiceException("Team with id='" + id + "' is not found.");
            }
            Team team = teamOptional.get();
            return team.getName();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Team> getTeamsRange(int offset, int amount) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            TeamDao teamDao = daoHelper.createTeamDao();
            return teamDao.getTeamsRange(offset, amount);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int getTeamsAmount() throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            TeamDao teamDao = daoHelper.createTeamDao();
            return teamDao.getRowsAmount(Optional.empty());
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
