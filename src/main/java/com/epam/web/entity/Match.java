package com.epam.web.entity;

public class Match {
    public static final String TABLE = "matches";
    public static final String ID = "id";
    public static final String DATE = "date";
    public static final String TOURNAMENT = "tournament";
    public static final String FIRST_TEAM = "firstTeam";
    public static final String SECOND_TEAM = "secondTeam";
    public static final String FIRST_PERCENT = "firstPercent";
    public static final String SECOND_PERCENT = "secondPercent";

    public final String date;
    private final String tournament;
    private final String firstTeam;
    private final String secondTeam;
    private Long id;

    private int firstPercent;
    private int secondPercent;

    public Match(String date, String tournament, String firstTeam, String secondTeam, Long id) {
        this.date = date;
        this.tournament = tournament;
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
        this.id = id;
    }

    public Match(String date, String tournament, String firstTeam, String secondTeam) {
        this.date = date;
        this.tournament = tournament;
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
    }

    public int getSecondPercent() {
        return secondPercent;
    }

    public void setSecondPercent(int secondPercent) {
        this.secondPercent = secondPercent;
    }

    public int getFirstPercent() {
        return firstPercent;
    }

    public void setFirstPercent(int firstPercent) {
        this.firstPercent = firstPercent;
    }


    public String getDate() {
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

    public Long getId() {
        return id;
    }
}
