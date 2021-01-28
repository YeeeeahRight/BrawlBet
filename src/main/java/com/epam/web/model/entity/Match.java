package com.epam.web.model.entity;

import com.epam.web.model.Entity;
import com.epam.web.model.enumeration.MatchTeamNumber;

import java.util.Date;
import java.util.Objects;

public class Match implements Entity {
    public static final String TABLE = "matches";
    public static final String ID = "id";
    public static final String DATE = "date";
    public static final String TOURNAMENT = "tournament";
    public static final String FIRST_TEAM_ID = "first_team_id";
    public static final String SECOND_TEAM_ID = "second_team_id";
    public static final String WINNER_TEAM = "winner_team";
    public static final String COMMISSION = "commission";
    public static final String IS_CLOSED = "is_closed";
    public static final String FIRST_TEAM_BETS = "first_team_bets";
    public static final String SECOND_TEAM_BETS = "second_team_bets";

    private final Long id;
    private final Long firstTeamId;
    private final Long secondTeamId;
    private final MatchTeamNumber winnerTeam;
    private final Date date;
    private final String tournament;
    private final Float commission;
    private final Boolean isClosed;
    private final Float firstTeamBets;
    private final Float secondTeamBets;

    public Match(Long id, Date date, String tournament, Long firstTeamId, Long secondTeamId,
                 MatchTeamNumber winnerTeam, Float commission, Boolean isClosed, Float firstTeamBets,
                 Float secondTeamBets) {
        this.id = id;
        this.date = date;
        this.tournament = tournament;
        this.firstTeamId = firstTeamId;
        this.secondTeamId = secondTeamId;
        this.winnerTeam = winnerTeam;
        this.commission = commission;
        this.isClosed = isClosed;
        this.firstTeamBets = firstTeamBets;
        this.secondTeamBets = secondTeamBets;
    }

    public Match(Long id, Date date, String tournament, Long firstTeamId, Long secondTeamId) {
        this.id = id;
        this.date = date;
        this.tournament = tournament;
        this.firstTeamId = firstTeamId;
        this.secondTeamId = secondTeamId;
        this.winnerTeam = MatchTeamNumber.NONE;
        this.commission = 0.0f;
        this.isClosed = false;
        this.firstTeamBets = 0.0f;
        this.secondTeamBets = 0.0f;
    }

    public Match(Date date, String tournament, Long firstTeamId, Long secondTeamId) {
        this.id = null;
        this.date = date;
        this.tournament = tournament;
        this.firstTeamId = firstTeamId;
        this.secondTeamId = secondTeamId;
        this.winnerTeam = MatchTeamNumber.NONE;
        this.commission = 0.0f;
        this.isClosed = false;
        this.firstTeamBets = 0.0f;
        this.secondTeamBets = 0.0f;
    }

    public Date getDate() {
        return date;
    }

    public String getTournament() {
        return tournament;
    }

    public Long getFirstTeamId() {
        return firstTeamId;
    }

    public Long getSecondTeamId() {
        return secondTeamId;
    }

    public Float getCommission() {
        return commission;
    }

    public Boolean isClosed() {
        return isClosed;
    }

    public MatchTeamNumber getWinnerTeam() {
        return winnerTeam;
    }

    public Float getFirstTeamBets() {
        return firstTeamBets;
    }

    public Float getSecondTeamBets() {
        return secondTeamBets;
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

        if (!Objects.equals(id, match.id)) {
            return false;
        }
        if (!firstTeamId.equals(match.firstTeamId)) {
            return false;
        }
        if (!secondTeamId.equals(match.secondTeamId)) {
            return false;
        }
        if (winnerTeam != match.winnerTeam) {
            return false;
        }
        if (!date.equals(match.date)) {
            return false;
        }
        if (!tournament.equals(match.tournament)) {
            return false;
        }
        if (!commission.equals(match.commission)) {
            return false;
        }
        if (!Objects.equals(isClosed, match.isClosed)) {
            return false;
        }
        if (!firstTeamBets.equals(match.firstTeamBets)) {
            return false;
        }
        return secondTeamBets.equals(match.secondTeamBets);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + firstTeamId.hashCode();
        result = 31 * result + secondTeamId.hashCode();
        result = 31 * result + winnerTeam.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + tournament.hashCode();
        result = 31 * result + commission.hashCode();
        result = 31 * result + (isClosed != null ? isClosed.hashCode() : 0);
        result = 31 * result + firstTeamBets.hashCode();
        result = 31 * result + secondTeamBets.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Match{" +
                "date=" + date +
                ", tournament='" + tournament + '\'' +
                ", firstTeamId='" + firstTeamId + '\'' +
                ", secondTeamId='" + secondTeamId + '\'' +
                ", winnerTeam='" + winnerTeam + '\'' +
                ", commission=" + commission +
                ", isClosed=" + isClosed +
                ", firstTeamBets=" + firstTeamBets +
                ", secondTeamBets=" + secondTeamBets +
                ", id=" + id +
                '}';
    }
}
