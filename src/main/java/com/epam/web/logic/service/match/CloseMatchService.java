package com.epam.web.logic.service.match;

import com.epam.web.exception.ServiceException;

public interface CloseMatchService {
    void closeMatchById(long matchId) throws ServiceException;
}
