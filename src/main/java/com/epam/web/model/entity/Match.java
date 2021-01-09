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

    private final Date date;
    private final String tournament;
    private final String firstTeam;
    private final String secondTeam;
    private final float commission;
    private final boolean isClosed;
    private long id;

    public Match(long id, Date date, String tournament, String firstTeam,
                 String secondTeam, float commission, boolean isClosed) {
        this.id = id;
        this.date = date;
        this.tournament = tournament;
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
        this.commission = commission;
        this.isClosed = isClosed;
    }

    public Match(Date date, String tournament, String firstTeam,
                 String secondTeam, float commission, boolean isClosed) {
        this.date = date;
        this.tournament = tournament;
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
        this.commission = commission;
        this.isClosed = isClosed;
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

    public float getCommission() {
        return commission;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public long getId() {
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

        if (Float.compare(match.commission, commission) != 0) {
            return false;
        }
        if (isClosed != match.isClosed) {
            return false;
        }
        if (id != match.id) {
            return false;
        }
        if (!date.equals(match.date)) {
            return false;
        }
        if (!tournament.equals(match.tournament)) {
            return false;
        }
        if (!firstTeam.equals(match.firstTeam)) {
            return false;
        }
        return secondTeam.equals(match.secondTeam);
    }

    @Override
    public int hashCode() {
        int result = date.hashCode();
        result = 31 * result + tournament.hashCode();
        result = 31 * result + firstTeam.hashCode();
        result = 31 * result + secondTeam.hashCode();
        result = 31 * result + (commission != +0.0f ? Float.floatToIntBits(commission) : 0);
        result = 31 * result + (isClosed ? 1 : 0);
        result = 31 * result + (int) (id ^ (id >>> 32));
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
