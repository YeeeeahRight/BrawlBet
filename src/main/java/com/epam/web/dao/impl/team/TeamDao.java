package com.epam.web.dao.impl.team;

import com.epam.web.dao.Dao;
import com.epam.web.exception.DaoException;
import com.epam.web.model.entity.Team;

import java.util.Optional;

/**
 * Extending DAO interface for processing Teams
 */
public interface TeamDao extends Dao<Team> {

    /**
     * Finds team in database by name and returns container of team
     * or empty container if not found.
     *
     * @param  name  a name of team.
     *
     * @return  an optional container of team.
     *
     * @throws  DaoException  if database errors occurs.
     */
    Optional<Team> findTeamByName(String name) throws DaoException;

    /**
     * Increments matches won statistic of team by team id
     *
     * @param  id  an id value of team id.
     *
     * @throws  DaoException  if database errors occurs.
     */
    void incrementMatchesWonById(long id) throws DaoException;

    /**
     * Increments matches lost statistic of team by team id
     *
     * @param  id  an id value of team id.
     *
     * @throws  DaoException  if database errors occurs.
     */
    void incrementMatchesLostById(long id) throws DaoException;
}
