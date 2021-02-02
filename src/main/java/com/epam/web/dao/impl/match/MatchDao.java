package com.epam.web.dao.impl.match;

import com.epam.web.dao.Dao;
import com.epam.web.logic.service.match.MatchType;
import com.epam.web.model.entity.Match;
import com.epam.web.exception.DaoException;
import com.epam.web.model.enumeration.MatchTeamNumber;

import java.math.BigDecimal;
import java.util.List;

/**
 * Extending DAO interface for processing Matches
 */
public interface MatchDao extends Dao<Match> {

    /**
     * Closes match by match id using match winner.
     *
     * @param  id               an id value of match to close.
     * @param  matchTeamNumber  a serial number of team in match(first, second).
     *
     * @throws  DaoException  if database errors occurs.
     */
    void close(long id, MatchTeamNumber matchTeamNumber) throws DaoException;

    /**
     * Sets commission value to match by match id.
     *
     * @param  commission  a commission value to set.
     * @param  id          an id value of match.
     *
     * @throws  DaoException  if database errors occurs.
     */
    void setCommissionById(float commission, long id) throws DaoException;

    /**
     * Adds bet amount to team by match id and team type.
     *
     * @param  teamType   a type(left, right) of team.
     * @param  betAmount  a bet amount to add.
     * @param  id         an id value of match.
     *
     * @throws  DaoException  if database errors occurs.
     */
    void addTeamBetAmount(MatchTeamNumber teamType, BigDecimal betAmount, long id) throws DaoException;

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
