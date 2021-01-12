package com.epam.web.model.entity;

import com.epam.web.model.Entity;

import java.util.Date;

public class Match implements Entity {
    public static final String TABLE = "matches";
    public static final String ID = "id";
    public static final String DATE = "date";
    public static final String TOURNAMENT = "tournament";
    public static final String FIRST_TEAM = "first_team";
    public static final String SECOND_TEAM = "second_team";
    public static final String COMMISSION = "commission";
    public static final String IS_CLOSED = "is_closed";
    public static final String WINNER = "winner";

    private final Date date;
    private final String tournament;
    private final String firstTeam;
    private final String secondTeam;
    private final String winner;
    private final Float commission;
    private final Boolean isClosed;
    private final Long id;

    public Match(Long id, Date date, String tournament, String firstTeam,
                 String secondTeam, String winner, Float commission, Boolean isClosed) {
        this.id = id;
        this.date = date;
        this.tournament = tournament;
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
        this.winner = winner;
        this.commission = commission;
        this.isClosed = isClosed;
    }

    public Match(Date date, String tournament, String firstTeam,
                 String secondTeam) {
        this.id = null;
        this.date = date;
        this.tournament = tournament;
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
        this.winner = "NONE";
        this.commission = 0.0f;
        this.isClosed = false;
    }

    public Date getDate() {
        return date;
    }

    public String getTournament() {
        return tournament;
    }

    public String getFirstTeam() {
        return firstTeam;
    }

    public String getSecondTeam() {
        return secondTeam;
    }

    public Float getCommission() {
        return commission;
    }

    public Boolean isClosed() {
        return isClosed;
    }

    public String getWinner() {
        return winner;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Match match = (Match) o;

        if (!date.equals(match.date)) {
            return false;
        }
        if (!tournament.equals(match.tournament)) {
            return false;
        }
        if (!firstTeam.equals(match.firstTeam)) {
            return false;
        }
        if (!secondTeam.equals(match.secondTeam)) {
            return false;
        }
        if (!winner.equals(match.winner)) {
            return false;
        }
        if (!commission.equals(match.commission)) {
            return false;
        }
        if (!isClosed.equals(match.isClosed)) {
            return false;
        }
        return id.equals(match.id);
    }

    @Override
    public int hashCode() {
        int result = date.hashCode();
        result = 31 * result + tournament.hashCode();
        result = 31 * result + firstTeam.hashCode();
        result = 31 * result + secondTeam.hashCode();
        result = 31 * result + winner.hashCode();
        result = 31 * result + commission.hashCode();
        result = 31 * result + isClosed.hashCode();
        result = 31 * result + id.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Match{" + "date=" + date +
                ", tournament='" + tournament + '\'' +
                ", firstTeam='" + firstTeam + '\'' +
                ", secondTeam='" + secondTeam + '\'' +
                ", commission=" + commission +
                ", isClosed=" + isClosed +
                ", id=" + id +
                '}';
    }
}
