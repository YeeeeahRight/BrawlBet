package com.epam.web.logic.service.match;

import com.epam.web.exception.ServiceException;

/**
 * Interface with description of operation on close match command.
 */
public interface CloseMatchService {
    /**
     * Closing match by match id.
     *
     * @param  matchId  an id value of match id.
     *
     * @throws  ServiceException  if match is not found or match closed
     *                            and also it's a wrapper for lower errors.
     */
    void closeMatchById(long matchId) throws ServiceException;
}