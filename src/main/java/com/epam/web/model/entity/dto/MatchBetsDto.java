package com.epam.web.model.entity.dto;

import java.util.Date;

public class MatchBetsDto {
    private final Long id;
    private final Date date;
    private final String tournament;
    private final String firstTeam;
    private final String secondTeam;
    private final String winner;
    private final boolean isClosed;
    private final int firstPercent;
    private final int secondPercent;

    public MatchBetsDto(Long id, Date date, String tournament, String firstTeam, String secondTeam,
                        int firstPercent, int secondPercent, String winner, boolean isClosed) {
        this.id = id;
        this.date = date;
        this.tournament = tournament;
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
        this.firstPercent = firstPercent;
        this.secondPercent = secondPercent;
        this.winner = winner;
        this.isClosed = isClosed;
    }

    public Long getId() {
        return id;
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

    public int getFirstPercent() {
        return firstPercent;
    }

    public int getSecondPercent() {
        return secondPercent;
    }

    public String getWinner() {
        return winner;
    }

    public boolean isClosed() {
        return isClosed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MatchBetsDto that = (MatchBetsDto) o;

        if (isClosed != that.isClosed) {
            return false;
        }
        if (firstPercent != that.firstPercent) {
            return false;
        }
        if (secondPercent != that.secondPercent) {
            return false;
        }
        if (!id.equals(that.id)) {
            return false;
        }
        if (!date.equals(that.date)) {
            return false;
        }
        if (!tournament.equals(that.tournament)) {
            return false;
        }
        if (!firstTeam.equals(that.firstTeam)) {
            return false;
        }
        if (!secondTeam.equals(that.secondTeam)) {
            return false;
        }
        return winner.equals(that.winner);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + tournament.hashCode();
        result = 31 * result + firstTeam.hashCode();
        result = 31 * result + secondTeam.hashCode();
        result = 31 * result + winner.hashCode();
        result = 31 * result + (isClosed ? 1 : 0);
        result = 31 * result + firstPercent;
        result = 31 * result + secondPercent;
        return result;
    }

    @Override
    public String toString() {
        return "MatchBetsDto{" +
                "id=" + id +
                ", date=" + date +
                ", tournament='" + tournament + '\'' +
                ", firstTeam='" + firstTeam + '\'' +
                ", secondTeam='" + secondTeam + '\'' +
                ", winner='" + winner + '\'' +
                ", isClosed=" + isClosed +
                ", firstPercent=" + firstPercent +
                ", secondPercent=" + secondPercent +
                '}';
    }
}
