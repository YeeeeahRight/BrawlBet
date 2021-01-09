package com.epam.web.model.entity.dto;

import com.epam.web.model.Entity;

import java.util.Date;

public class MatchBetsDto implements Entity {
    private final long id;
    private final Date date;
    private final String tournament;
    private final String firstTeam;
    private final String secondTeam;
    private final float commission;
    private final int firstTeamBetsAmount;
    private final int secondTeamBetsAmount;

    public MatchBetsDto(long id, Date date, String tournament, String firstTeam,
                        String secondTeam, float commission, int firstTeamBetsAmount, int secondTeamBetsAmount) {
        this.id = id;
        this.date = date;
        this.tournament = tournament;
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
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

    public float getCommission() {
        return commission;
    }

    public int getFirstTeamBetsAmount() {
        return firstTeamBetsAmount;
    }

    public int getSecondTeamBetsAmount() {
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

        MatchBetsDto that = (MatchBetsDto) o;

        if (id != that.id) {
            return false;
        }
        if (Float.compare(that.commission, commission) != 0) {
            return false;
        }
        if (firstTeamBetsAmount != that.firstTeamBetsAmount) {
            return false;
        }
        if (secondTeamBetsAmount != that.secondTeamBetsAmount) {
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
        return secondTeam.equals(that.secondTeam);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + date.hashCode();
        result = 31 * result + tournament.hashCode();
        result = 31 * result + firstTeam.hashCode();
        result = 31 * result + secondTeam.hashCode();
        result = 31 * result + (commission != +0.0f ? Float.floatToIntBits(commission) : 0);
        result = 31 * result + firstTeamBetsAmount;
        result = 31 * result + secondTeamBetsAmount;
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
                ", commission=" + commission +
                ", firstTeamBetsAmount=" + firstTeamBetsAmount +
                ", secondTeamBetsAmount=" + secondTeamBetsAmount +
                '}';
    }
}
