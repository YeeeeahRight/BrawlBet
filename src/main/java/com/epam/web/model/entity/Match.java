package com.epam.web.model.entity;

import com.epam.web.date.DateFormatType;
import com.epam.web.date.DateFormatter;

import java.util.Date;

public class Match {
    public static final String TABLE = "matches";
    public static final String ID = "id";
    public static final String DATE = "date";
    public static final String TOURNAMENT = "tournament";
    public static final String FIRST_TEAM = "first_team";
    public static final String SECOND_TEAM = "second_team";
    public static final String FIRST_PERCENT = "first_percent";
    public static final String SECOND_PERCENT = "second_percent";
    public static final String FIRST_COEFFICIENT = "first_coefficient";
    public static final String SECOND_COEFFICIENT = "second_coefficient";
    public static final String COMMISSION = "commission";

    private final Date date;
    private final DateFormatter dateFormatter;
    private final String tournament;
    private final String firstTeam;
    private final String secondTeam;
    private Long id;

    private int firstPercent;
    private int secondPercent;
    private float commission;
    private float firstCoefficient;
    private float secondCoefficient;

    public Match(Date date, String tournament, String firstTeam, String secondTeam, Long id) {
        this.date = date;
        dateFormatter = new DateFormatter(date);
        this.tournament = tournament;
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
        this.id = id;
    }

    public Match(Date date, String tournament, String firstTeam, String secondTeam) {
        this.date = date;
        dateFormatter = new DateFormatter(date);
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

    public float getCommission() {
        return commission;
    }

    public void setCommission(float commission) {
        this.commission = commission;
    }

    public float getFirstCoefficient() {
        return firstCoefficient;
    }

    public void setFirstCoefficient(float firstCoefficient) {
        this.firstCoefficient = firstCoefficient;
    }

    public float getSecondCoefficient() {
        return secondCoefficient;
    }

    public void setSecondCoefficient(float secondCoefficient) {
        this.secondCoefficient = secondCoefficient;
    }

    public Date getDate() {
        return date;
    }

    public String getHtmlFormattedDate() {
        return dateFormatter.format(DateFormatType.HTML);
    }

    public String getMatchFormattedDate() {
        return dateFormatter.format(DateFormatType.MATCH);
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
