package com.epam.web.logic.service.match;

import com.epam.web.exception.ServiceException;
import com.epam.web.model.entity.Match;

import java.util.List;

public interface MatchService {
    void saveMatch(Match match) throws ServiceException;

    void removeById(long id) throws ServiceException;

    void editMatch(Match match, long id) throws ServiceException;

    Match findById(long id) throws ServiceException;

    void addCommissionById(float commission, long id) throws ServiceException;

    void cancelMatchById(long id) throws ServiceException;

    boolean isFinishedMatch(long id) throws ServiceException;

    List<Match> getUnclosedMatchesRange(int beginIndex, int endIndex) throws ServiceException;

    List<Match> getClosedMatchesRange(int beginIndex, int endIndex) throws ServiceException;

    List<Match> getAcceptedMatchesRange(int beginIndex, int endIndex) throws ServiceException;

    List<Match> getUnacceptedMatchesRange(int beginIndex, int endIndex) throws ServiceException;

    List<Match> getFinishedMatchesRange(int beginIndex, int endIndex) throws ServiceException;

    List<Match> getUnfinishedMatches() throws ServiceException;
}
