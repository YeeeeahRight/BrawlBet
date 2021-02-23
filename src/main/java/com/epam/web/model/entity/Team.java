package com.epam.web.model.entity;

import com.epam.web.model.Entity;

import java.util.Objects;

public class Team implements Entity {
    public static final String TABLE = "teams";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String MATCHES_WON = "matches_won";
    public static final String MATCHES_LOST = "matches_lost";

    private final Long id;
    private final String name;
    private final Integer matchesWon;
    private final Integer matchesLost;

    public Team(Long id, String name, Integer matchesWon, Integer matchesLost) {
        this.id = id;
        this.name = name;
        this.matchesWon = matchesWon;
        this.matchesLost = matchesLost;
    }

    public Team(String name, Integer matchesWon, Integer matchesLost) {
        this.id = null;
        this.name = name;
        this.matchesWon = matchesWon;
        this.matchesLost = matchesLost;
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getMatchesWon() {
        return matchesWon;
    }

    public Integer getMatchesLost() {
        return matchesLost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Team team = (Team) o;

        if (!Objects.equals(id, team.id)) {
            return false;
        }
        if (!name.equals(team.name)) {
            return false;
        }
        if (!matchesWon.equals(team.matchesWon)) {
            return false;
        }
        return matchesLost.equals(team.matchesLost);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + name.hashCode();
        result = 31 * result + matchesWon.hashCode();
        result = 31 * result + matchesLost.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", matchesWon=" + matchesWon +
                ", matchesLost=" + matchesLost +
                '}';
    }
}
