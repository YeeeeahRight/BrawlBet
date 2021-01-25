package com.epam.web.dao.impl.match;

import com.epam.web.logic.service.match.MatchType;
import com.epam.web.model.entity.Match;
import com.epam.web.exception.DaoException;
import com.epam.web.model.enumeration.Team;

import java.util.List;
import java.util.Optional;

/**
 * Interface with description of operations with the Match data.
 */
public interface MatchDao {
    /**
     * Removes match in database by match id.
     *
     * @param  id  an id value of match to remove.
     *
     * @throws  DaoException  if database errors occurs.
     */
    void removeById(long id) throws DaoException;

    /**
     * Saves match to database or updates if match id is exist in database.
     *
     * @param  match  a match object to save or update.
     *
     * @throws  DaoException  if database errors occurs.
     */
    void save(Match match) throws DaoException;

    /**
     * Closes match by match id using match winner.
     *
     * @param  id      an id value of match to close.
     * @param  winner  a winner of match.
     *
     * @throws  DaoException  if database errors occurs.
     */
    void close(long id, String winner) throws DaoException;

    /**
     * Finds match by id and returns container of match if exist
     * or empty container if not.
     *
     * @param  id  an id value of match to search.
     *
     * @return  an optional container of Match object.
     *
     * @throws  DaoException  if database errors occurs.
     */
    Optional<Match> findById(long id) throws DaoException;

    /**
     * Adds commission value to match by match id.
     *
     * @param  commission  a commission value to add.
     * @param  id          an id value of match.
     *
     * @throws  DaoException  if database errors occurs.
     */
    void addCommissionById(float commission, long id) throws DaoException;

    /**
     * Adds bet amount to team by match id and team type.
     *
     * @param  team       a type(left, right) of team.
     * @param  betAmount  a bet amount to add.
     * @param  id         an id value of match.
     *
     * @throws  DaoException  if database errors occurs.
     */
    void addTeamBetAmount(Team team, float betAmount, long id) throws DaoException;

    /**
     * Gets list of matches by match type
     * in range described as offset and amount of matches.
     *
     * @param  matchType  a match type to get.
     * @param  offset     an offset from first row of matches table.
     * @param  amount     an amount of matches to get.
     *
     * @return  a received list of matches.
     *
     * @throws  DaoException  if database errors occurs.
     */
    List<Match> getMatchesTypeRange(MatchType matchType, int offset, int amount) throws DaoException;

    /**
     * Gets matches amount by match type.
     *
     * @param  matchType  a match type to get.
     *
     * @return  an amount of finding matches.
     *
     * @throws  DaoException  if database errors occurs.
     */
    int getMatchesTypeAmount(MatchType matchType) throws DaoException;

}
