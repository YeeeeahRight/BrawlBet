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

    private final Long accountId;
    private final Long matchId;
    private final String team;
    private final Integer moneyReceived;
    private final Integer moneyBet;
    private final Long id;

    public Bet(Long id, Long accountId, Long matchId, Integer moneyBet, String team, Integer moneyReceived) {
        this.id = id;
        this.accountId = accountId;
        this.matchId = matchId;
        this.moneyBet = moneyBet;
        this.team = team;
        this.moneyReceived = moneyReceived;
    }

    public Bet(Long accountId, Long matchId, Integer moneyBet, String team) {
        this.id = null;
        this.accountId = accountId;
        this.matchId = matchId;
        this.moneyBet = moneyBet;
        this.team = team;
        this.moneyReceived = 0;
    }

    public Long getId() {
        return id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public Long getMatchId() {
        return matchId;
    }

    public Integer getMoneyBet() {
        return moneyBet;
    }

    public String getTeam() {
        return team;
    }

    public Integer getMoneyReceived() {
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

        if (!accountId.equals(bet.accountId)) {
            return false;
        }
        if (!matchId.equals(bet.matchId)) {
            return false;
        }
        if (!team.equals(bet.team)) {
            return false;
        }
        if (!moneyReceived.equals(bet.moneyReceived)) {
            return false;
        }
        if (!moneyBet.equals(bet.moneyBet)) {
            return false;
        }
        return id.equals(bet.id);
    }

    @Override
    public int hashCode() {
        int result = accountId.hashCode();
        result = 31 * result + matchId.hashCode();
        result = 31 * result + team.hashCode();
        result = 31 * result + moneyReceived.hashCode();
        result = 31 * result + moneyBet.hashCode();
        result = 31 * result + id.hashCode();
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
