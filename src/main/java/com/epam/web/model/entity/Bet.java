package com.epam.web.model.entity;

import com.epam.web.model.Entity;

public class Bet implements Entity {
    public static final String TABLE = "bets";
    public static final String ID = "id";
    public static final String ACCOUNT_ID = "account_id";
    public static final String MATCH_ID = "match_id";
    public static final String MONEY_BET = "money_bet";
    public static final String TEAM = "team";
    public static final String MONEY_RECEIVED = "money_received";

    private final long accountId;
    private final long matchId;
    private final String team;
    private final int moneyReceived;
    private final int moneyBet;
    private long id;


    public Bet(long accountId, long matchId, int moneyBet, String team, int moneyReceived) {
        this.accountId = accountId;
        this.matchId = matchId;
        this.moneyBet = moneyBet;
        this.team = team;
        this.moneyReceived = moneyReceived;
    }

    public Bet(long id, long accountId, long matchId, int moneyBet, String team, int moneyReceived) {
        this.id = id;
        this.accountId = accountId;
        this.matchId = matchId;
        this.moneyBet = moneyBet;
        this.team = team;
        this.moneyReceived = moneyReceived;
    }

    public long getId() {
        return id;
    }

    public long getAccountId() {
        return accountId;
    }

    public long getMatchId() {
        return matchId;
    }

    public int getMoneyBet() {
        return moneyBet;
    }

    public String getTeam() {
        return team;
    }

    public int getMoneyReceived() {
        return moneyReceived;
    }
}
