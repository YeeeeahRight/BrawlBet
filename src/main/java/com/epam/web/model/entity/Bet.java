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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Bet bet = (Bet) o;

        if (accountId != bet.accountId) {
            return false;
        }
        if (matchId != bet.matchId) {
            return false;
        }
        if (moneyReceived != bet.moneyReceived) {
            return false;
        }
        if (moneyBet != bet.moneyBet) {
            return false;
        }
        if (id != bet.id) {
            return false;
        }
        return team.equals(bet.team);
    }

    @Override
    public int hashCode() {
        int result = (int) (accountId ^ (accountId >>> 32));
        result = 31 * result + (int) (matchId ^ (matchId >>> 32));
        result = 31 * result + team.hashCode();
        result = 31 * result + moneyReceived;
        result = 31 * result + moneyBet;
        result = 31 * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Bet{" + "accountId=" + accountId +
                ", matchId=" + matchId +
                ", team='" + team + '\'' +
                ", moneyReceived=" + moneyReceived +
                ", moneyBet=" + moneyBet +
                ", id=" + id +
                '}';
    }
}
