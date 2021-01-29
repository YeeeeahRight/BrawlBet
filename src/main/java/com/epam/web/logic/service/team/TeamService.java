package com.epam.web.logic.service.team;

import com.epam.web.exception.ServiceException;
import com.epam.web.model.entity.Team;

import java.util.List;

/**
 * Interface with description of operations with the Team.
 */
public interface TeamService {
    /**
     * Saves team in database.
     *
     * @param  team  a Team object to save.
     *
     * @throws ServiceException  if team is not passed validation
     *                            and also it's a wrapper for lower errors.
     */
    void saveTeam(Team team) throws ServiceException;

    /**
     * Gets team by id.
     *
     * @param  id  an id value of team to get.
     *
     * @return  received Team object.
     *
     * @throws  ServiceException  if team is not found and
     *                            also it's a wrapper for lower errors.
     */
    Team getTeamById(long id) throws ServiceException;

    /**
     * Gets team id by name.
     *
     * @param  name a name of team to find.
     *
     * @return  team id from found Team object.
     *
     * @throws  ServiceException  if team is not found and
     *                            also it's a wrapper for lower errors.
     */
    long getTeamIdByName(String name) throws ServiceException;

    /**
     * Gets team name by team id.
     *
     * @param  id  an id value of team to find.
     *
     * @return  team name from found Team object.
     *
     * @throws  ServiceException  if team is not found and
     *                            also it's a wrapper for lower errors.
     */
    String getTeamNameById(long id) throws ServiceException;

    /**
     * Gets list of teams in range described as offset and amount of teams.
     *
     * @param  offset  an offset from first row of teams table.
     * @param  amount  an amount of teams to get.
     *
     * @return  a received list of teams.
     *
     * @throws  ServiceException  a wrapper for lower errors.
     */
    List<Team> getTeamsRange(int offset, int amount) throws ServiceException;

    /**
     * Gets teams amount.
     *
     * @return  an amount of all teams.
     *
     * @throws  ServiceException  a wrapper for lower errors.
     */
    int getTeamsAmount() throws ServiceException;
}
