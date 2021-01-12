package com.epam.web.model.entity.dto;

import com.epam.web.model.Entity;

import java.util.Date;

public class MatchBetsDto implements Entity {
    private final Long id;
    private final Date date;
    private final String tournament;
    private final String firstTeam;
    private final String secondTeam;
    private final String winner;
    private final Float commission;
    private final Integer firstTeamBetsAmount;
    private final Integer secondTeamBetsAmount;

    public MatchBetsDto(Long id, Date date, String tournament, String firstTeam,
                        String secondTeam, String winner, Float commission,
                        Integer firstTeamBetsAmount, Integer secondTeamBetsAmount) {
        this.id = id;
        this.date = date;
        this.tournament = tournament;
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
        this.winner = winner;
        this.commission = commission;
        this.firstTeamBetsAmount = firstTeamBetsAmount;
        this.secondTeamBetsAmount = secondTeamBetsAmount;
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

    public Integer getFirstTeamBetsAmount() {
        return firstTeamBetsAmount;
    }

    public Integer getSecondTeamBetsAmount() {
        return secondTeamBetsAmount;
    }

    public int getFirstPercent() {
        if (firstTeamBetsAmount + secondTeamBetsAmount == 0) {
            return 0;
        }
        float percent = firstTeamBetsAmount * 1.0f / (firstTeamBetsAmount + secondTeamBetsAmount);
        return Math.round(percent * 100);
    }

    public int getSecondPercent() {
        if (firstTeamBetsAmount + secondTeamBetsAmount == 0) {
            return 0;
        }
        float percent = secondTeamBetsAmount * 1.0f / (firstTeamBetsAmount + secondTeamBetsAmount);
        return Math.round(percent * 100);
    }

    public float getFirstCoefficient() {
        if (firstTeamBetsAmount * secondTeamBetsAmount == 0) {
            return 1.0f;
        }
        float coefficient = secondTeamBetsAmount * 1.0f / firstTeamBetsAmount;
        return coefficient - (coefficient * commission / 100) + 1.0f;
    }

    public float getSecondCoefficient() {
        if (firstTeamBetsAmount * secondTeamBetsAmount == 0) {
            return 1.0f;
        }
        float coefficient = firstTeamBetsAmount * 1.0f / secondTeamBetsAmount;
        return coefficient - (coefficient * commission / 100) + 1.0f;
    }

    public float getCommissionByCoefficient(float coefficient) {
        return (coefficient * commission / 100);
    }

    @Override
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

        MatchBetsDto that = (MatchBetsDto) o;

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
        if (!winner.equals(that.winner)) {
            return false;
        }
        if (!commission.equals(that.commission)) {
            return false;
        }
        if (!firstTeamBetsAmount.equals(that.firstTeamBetsAmount)) {
            return false;
        }
        return secondTeamBetsAmount.equals(that.secondTeamBetsAmount);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + tournament.hashCode();
        result = 31 * result + firstTeam.hashCode();
        result = 31 * result + secondTeam.hashCode();
        result = 31 * result + winner.hashCode();
        result = 31 * result + commission.hashCode();
        result = 31 * result + firstTeamBetsAmount.hashCode();
        result = 31 * result + secondTeamBetsAmount.hashCode();
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
                ", commission=" + commission +
                ", firstTeamBetsAmount=" + firstTeamBetsAmount +
                ", secondTeamBetsAmount=" + secondTeamBetsAmount +
                '}';
    }
}
