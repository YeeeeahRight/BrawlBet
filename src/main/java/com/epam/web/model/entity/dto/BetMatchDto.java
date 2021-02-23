package com.epam.web.model.entity.dto;

import com.epam.web.model.entity.Bet;

import java.util.Date;

public class BetMatchDto {
    private final Long id;
    private final String teamOnBet;
    private final float moneyBet;
    private final float moneyReceived;
    private final Date date;
    private final String tournament;
    private final String firstTeam;
    private final String secondTeam;
    private final String winner;
    private final Integer firstPercent;
    private final Integer secondPercent;

    private BetMatchDto(Long id, String teamOnBet, float moneyBet, float moneyReceived, Date date, String tournament,
                       String firstTeam, String secondTeam, Integer firstPercent, Integer secondPercent, String winner) {
        this.id = id;
        this.teamOnBet = teamOnBet;
        this.moneyBet = moneyBet;
        this.moneyReceived = moneyReceived;
        this.date = date;
        this.tournament = tournament;
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
        this.firstPercent = firstPercent;
        this.secondPercent = secondPercent;
        this.winner = winner;
    }

    public Long getId() {
        return id;
    }

    public String getTeamOnBet() {
        return teamOnBet;
    }

    public float getMoneyBet() {
        return moneyBet;
    }

    public float getMoneyReceived() {
        return moneyReceived;
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

    public Integer getFirstPercent() {
        return firstPercent;
    }

    public Integer getSecondPercent() {
        return secondPercent;
    }

    public String getWinner() {
        return winner;
    }

    public static class BetMatchDtoBuilder {
        private Long id;
        private float moneyBet;
        private float moneyReceived;
        private Date date;
        private String tournament;
        private String firstTeam;
        private String secondTeam;
        private String teamOnBet;
        private String winner;
        private Integer firstPercent;
        private Integer secondPercent;

        public BetMatchDtoBuilder setGeneralAttributes(Bet bet, String tournament) {
            this.id = bet.getMatchId();
            this.moneyBet = bet.getMoneyBet().floatValue();
            this.moneyReceived = bet.getMoneyReceived().floatValue();
            this.date = bet.getBetDate();
            this.tournament = tournament;
            return this;
        }

        public BetMatchDtoBuilder setTeams(String firstTeam, String secondTeam, String teamOnBet, String winner) {
            this.firstTeam = firstTeam;
            this.secondTeam = secondTeam;
            this.teamOnBet = teamOnBet;
            this.winner = winner;
            return this;
        }

        public BetMatchDtoBuilder setPercents(Integer firstPercent, Integer secondPercent) {
            this.firstPercent = firstPercent;
            this.secondPercent = secondPercent;
            return this;
        }

        public BetMatchDto build() {
            return new BetMatchDto(id, teamOnBet, moneyBet, moneyReceived, date, tournament, firstTeam, secondTeam,
                    firstPercent, secondPercent, winner);
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

        BetMatchDto that = (BetMatchDto) o;

        if (Float.compare(that.moneyBet, moneyBet) != 0) {
            return false;
        }
        if (Float.compare(that.moneyReceived, moneyReceived) != 0) {
            return false;
        }
        if (!firstPercent.equals(that.firstPercent)) {
            return false;
        }
        if (!secondPercent.equals(that.secondPercent)) {
            return false;
        }
        if (!id.equals(that.id)) {
            return false;
        }
        if (!teamOnBet.equals(that.teamOnBet)) {
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
        result = 31 * result + teamOnBet.hashCode();
        result = 31 * result + (moneyBet != +0.0f ? Float.floatToIntBits(moneyBet) : 0);
        result = 31 * result + (moneyReceived != +0.0f ? Float.floatToIntBits(moneyReceived) : 0);
        result = 31 * result + date.hashCode();
        result = 31 * result + tournament.hashCode();
        result = 31 * result + firstTeam.hashCode();
        result = 31 * result + secondTeam.hashCode();
        result = 31 * result + firstPercent;
        result = 31 * result + secondPercent;
        result = 31 * result + winner.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "BetMatchDto{" +
                "id=" + id +
                ", teamOnBet='" + teamOnBet + '\'' +
                ", moneyOnBet=" + moneyBet +
                ", moneyReceived=" + moneyReceived +
                ", date=" + date +
                ", tournament='" + tournament + '\'' +
                ", firstTeam='" + firstTeam + '\'' +
                ", secondTeam='" + secondTeam + '\'' +
                ", firstPercent=" + firstPercent +
                ", secondPercent=" + secondPercent +
                ", winner='" + winner + '\'' +
                '}';
    }
}
