package com.epam.web.dao.match;

import com.epam.web.entity.Match;
import com.epam.web.exceptions.DaoException;

import java.util.List;
import java.util.Optional;

public interface MatchDao {
    List<Match> getAll() throws DaoException;
    void removeById(Long id) throws DaoException;
    void save(Match match) throws DaoException;
    Optional<Match> findById(Long id) throws DaoException;
    void editMatch(Match newMatch, Long id) throws DaoException;
}
