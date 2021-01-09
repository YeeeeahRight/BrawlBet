package com.epam.web.dao.impl.match;

import com.epam.web.model.entity.Match;
import com.epam.web.exceptions.DaoException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MatchDao {
    List<Match> getAll() throws DaoException;
    List<Match> getUnacceptedMatches() throws DaoException;
    List<Match> getActiveMatches() throws DaoException;
    List<Match> getUnclosedMatches() throws DaoException;
    List<Match> getFinishedMatches() throws DaoException;
    List<Match> getUnfinishedMatches() throws DaoException;
    void removeById(long id) throws DaoException;
    void save(Match match) throws DaoException;
    void close(long id, String winner) throws DaoException;
    Optional<Match> findById(long id) throws DaoException;
    void edit(Match newMatch, long id) throws DaoException;
    void addCommission(float commission, long id) throws DaoException;
}
