package com.epam.web.dao.impl.match;

import com.epam.web.model.entity.Match;
import com.epam.web.exception.DaoException;
import com.epam.web.model.enumeration.Team;

import java.util.List;
import java.util.Optional;

public interface MatchDao {
    void removeById(long id) throws DaoException;

    void save(Match match) throws DaoException;

    void close(long id, String winner) throws DaoException;

    Optional<Match> findById(long id) throws DaoException;

    void edit(Match newMatch, long id) throws DaoException;

    void addCommission(float commission, long id) throws DaoException;

    void addTeamBetAmount(Team team, float betAmount, long id) throws DaoException;

    List<Match> getUnacceptedMatchesRange(int beginIndex, int endIndex) throws DaoException;

    List<Match> getAcceptedMatchesRange(int beginIndex, int endIndex) throws DaoException;

    List<Match> getUnclosedMatchesRange(int beginIndex, int endIndex) throws DaoException;

    List<Match> getClosedMatchesRange(int beginIndex, int endIndex) throws DaoException;

    List<Match> getFinishedMatchesRange(int beginIndex, int endIndex) throws DaoException;

    List<Match> getUnfinishedMatches() throws DaoException;

}
