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

    private final Date date;
    private final String tournament;
    private final String firstTeam;
    private final String secondTeam;
    private final float commission;
    private long id;

    public Match(long id, Date date, String tournament, String firstTeam,
                 String secondTeam, float commission) {
        this.id = id;
        this.date = date;
        this.tournament = tournament;
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
        this.commission = commission;
    }

    public Match(Date date, String tournament, String firstTeam,
                 String secondTeam, float commission) {
        this.date = date;
        this.tournament = tournament;
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
        this.commission = commission;
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

    public long getId() {
        return id;
    }
}
