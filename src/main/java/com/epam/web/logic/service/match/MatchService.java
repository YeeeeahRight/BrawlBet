package com.epam.web.logic.service.match;

import com.epam.web.exception.ServiceException;
import com.epam.web.model.entity.Match;

import java.util.List;

/**
 * Interface with description of operations with the Match.
 */
public interface MatchService {
    /**
     * Saves match or updates if match id is exist.
     *
     * @param  match  a Match object to save or update.
     *
     * @throws  ServiceException  if match is not passed validation
     *                            and also it's a wrapper for lower errors.
     */
    void saveMatch(Match match) throws ServiceException;

    /**
     * Removes match by match id.
     *
     * @param  id  an id value of match to remove.
     *
     * @throws  ServiceException  a wrapper for lower errors.
     */
    void removeById(long id) throws ServiceException;

    /**
     * Gets match by id.
     *
     * @param  id  an id value of match to get.
     *
     * @return  received Match object.
     *
     * @throws  ServiceException  if match is not found and
     *                            also it's a wrapper for lower errors.
     */
    Match getMatchById(long id) throws ServiceException;

    /**
     * Sets commission value to match by match id.
     *
     * @param  commission  a commission value to set.
     * @param  id          an id value of match.
     *
     * @throws  ServiceException  a wrapper for lower errors.
     */
    void setCommissionById(float commission, long id) throws ServiceException;

    /**
     * Cancel match by match id.
     *
     * @param  id  an id value of match to cancel.
     *
     * @throws  ServiceException  a wrapper for lower errors.
     */
    void cancelMatchById(long id) throws ServiceException;

    /**
     * Finds out if match is finished by match id.
     * Returns boolean result.
     *
     * @param  id  id value of match id.
     *
     * @return  a boolean result of finding out.
     *
     * @throws  ServiceException  if match is not found and
     *                            also it's a wrapper for lower errors.
     */
    boolean isFinishedMatch(long id) throws ServiceException;

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
     * @throws  ServiceException  a wrapper for lower errors.
     */
    List<Match> getMatchesTypeRange(MatchType matchType, int offset, int amount) throws ServiceException;

    /**
     * Gets matches amount by match type.
     *
     * @param  matchType  a match type to get.
     *
     * @return  an amount of finding matches.
     *
     * @throws  ServiceException  a wrapper for lower errors.
     */
    int getMatchesTypeAmount(MatchType matchType) throws ServiceException;
}
