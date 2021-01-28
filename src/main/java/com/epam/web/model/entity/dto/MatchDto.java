package com.epam.web.model.entity.dto;

import com.epam.web.model.entity.Match;

import java.util.Date;
import java.util.Objects;

public class MatchDto {
    private final long id;
    private final Date date;
    private final String tournament;
    private final boolean isClosed;
    private final String firstTeam;
    private final String secondTeam;
    private final String winner;
    private final float firstTeamBets;
    private final float secondTeamBets;

    private final Integer firstPercent;
    private final Integer secondPercent;
    private final Float commission;

    private MatchDto(long id, Date date, String tournament, boolean isClosed, String firstTeam,
                     String secondTeam, String winner, float firstTeamBets, float secondTeamBets,
                     Integer firstPercent, Integer secondPercent, Float commission) {
        this.id = id;
        this.date = date;
        this.tournament = tournament;
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
        this.winner = winner;
        this.firstTeamBets = firstTeamBets;
        this.secondTeamBets = secondTeamBets;
        this.isClosed = isClosed;
        this.firstPercent = firstPercent;
        this.secondPercent = secondPercent;
        this.commission = commission;
    }

    public long getId() {
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

    public String getWinner() {
        return winner;
    }

    public Float getFirstTeamBets() {
        return firstTeamBets;
    }

    public Float getSecondTeamBets() {
        return secondTeamBets;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public Integer getFirstPercent() {
        return firstPercent;
    }

    public Integer getSecondPercent() {
        return secondPercent;
    }

    public Float getCommission() {
        return commission;
    }

    public static class MatchDtoBuilder {
        private long id;
        private boolean isClosed;
        private Date date;
        private String tournament;
        private String firstTeam;
        private String secondTeam;
        private String winner;
        private float firstTeamBets;
        private float secondTeamBets;

        private Integer firstPercent;
        private Integer secondPercent;
        private Float commission;

        public MatchDtoBuilder setGeneralFields(Match match, String firstTeam, String secondTeam) {
            this.id = match.getId();
            this.date = match.getDate();
            this.tournament = match.getTournament();
            this.isClosed = match.isClosed();
            this.firstTeam = firstTeam;
            this.secondTeam = secondTeam;
            this.firstTeamBets = match.getFirstTeamBets();
            this.secondTeamBets = match.getSecondTeamBets();
            return this;
        }

        public MatchDtoBuilder setWinner(String winner) {
            this.winner = winner;
            return this;
        }

        public MatchDtoBuilder setPercents(Integer firstPercent, Integer secondPercent) {
            this.firstPercent = firstPercent;
            this.secondPercent = secondPercent;
            return this;
        }

        public MatchDtoBuilder setCommission(Float commission) {
            this.commission = commission;
            return this;
        }

        public MatchDto build() {
            return new MatchDto(id, date, tournament, isClosed, firstTeam, secondTeam, winner,
                                firstTeamBets, secondTeamBets, firstPercent, secondPercent, commission);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MatchDto matchDto = (MatchDto) o;

        if (id != matchDto.id) {
            return false;
        }
        if (isClosed != matchDto.isClosed) {
            return false;
        }
        if (Float.compare(matchDto.firstTeamBets, firstTeamBets) != 0) {
            return false;
        }
        if (Float.compare(matchDto.secondTeamBets, secondTeamBets) != 0) {
            return false;
        }
        if (Float.compare(matchDto.commission, commission) != 0) {
            return false;
        }
        if (!date.equals(matchDto.date)) {
            return false;
        }
        if (!tournament.equals(matchDto.tournament)) {
            return false;
        }
        if (!firstTeam.equals(matchDto.firstTeam)) {
            return false;
        }
        if (!secondTeam.equals(matchDto.secondTeam)) {
            return false;
        }
        if (!Objects.equals(winner, matchDto.winner)) {
            return false;
        }
        if (!Objects.equals(firstPercent, matchDto.firstPercent)) {
            return false;
        }
        return Objects.equals(secondPercent, matchDto.secondPercent);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + date.hashCode();
        result = 31 * result + tournament.hashCode();
        result = 31 * result + (isClosed ? 1 : 0);
        result = 31 * result + firstTeam.hashCode();
        result = 31 * result + secondTeam.hashCode();
        result = 31 * result + (winner != null ? winner.hashCode() : 0);
        result = 31 * result + (firstTeamBets != +0.0f ? Float.floatToIntBits(firstTeamBets) : 0);
        result = 31 * result + (secondTeamBets != +0.0f ? Float.floatToIntBits(secondTeamBets) : 0);
        result = 31 * result + (commission != +0.0f ? Float.floatToIntBits(commission) : 0);
        result = 31 * result + (firstPercent != null ? firstPercent.hashCode() : 0);
        result = 31 * result + (secondPercent != null ? secondPercent.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MatchDto{" +
                "id=" + id +
                ", date=" + date +
                ", tournament='" + tournament + '\'' +
                ", firstTeam='" + firstTeam + '\'' +
                ", secondTeam='" + secondTeam + '\'' +
                ", winner='" + winner + '\'' +
                ", firstTeamBets=" + firstTeamBets +
                ", secondTeamBets=" + secondTeamBets +
                ", isClosed=" + isClosed +
                ", firstPercent=" + firstPercent +
                ", secondPercent=" + secondPercent +
                ", commission=" + commission +
                '}';
    }
}
