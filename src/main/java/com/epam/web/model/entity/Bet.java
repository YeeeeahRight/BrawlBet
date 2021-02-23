package com.epam.web.model.entity;

import com.epam.web.model.Entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class Bet implements Entity {
    public static final String TABLE = "bets";
    public static final String ID = "id";
    public static final String ACCOUNT_ID = "account_id";
    public static final String MATCH_ID = "match_id";
    public static final String MONEY_BET = "money_bet";
    public static final String TEAM_ID = "team_id";
    public static final String MONEY_RECEIVED = "money_received";
    public static final String BET_DATE = "bet_date";

    private final Long accountId;
    private final Long matchId;
    private final Long teamId;
    private final BigDecimal moneyReceived;
    private final BigDecimal moneyBet;
    private final Date betDate;
    private final Long id;

    public Bet(Long id, Long accountId, Long matchId, BigDecimal moneyBet, Long teamId, BigDecimal moneyReceived,
               Date betDate) {
        this.id = id;
        this.accountId = accountId;
        this.matchId = matchId;
        this.moneyBet = moneyBet;
        this.teamId = teamId;
        this.moneyReceived = moneyReceived;
        this.betDate = betDate;
    }

    public Bet(Long accountId, Long matchId, BigDecimal moneyBet, Long teamId, Date betDate) {
        this.id = null;
        this.accountId = accountId;
        this.matchId = matchId;
        this.moneyBet = moneyBet;
        this.teamId = teamId;
        this.betDate = betDate;
        this.moneyReceived = BigDecimal.ZERO;
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

    public BigDecimal getMoneyBet() {
        return moneyBet;
    }

    public Long getTeamId() {
        return teamId;
    }

    public BigDecimal getMoneyReceived() {
        return moneyReceived;
    }

    public Date getBetDate() {
        return betDate;
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
        if (!teamId.equals(bet.teamId)) {
            return false;
        }
        if (!moneyReceived.equals(bet.moneyReceived)) {
            return false;
        }
        if (!moneyBet.equals(bet.moneyBet)) {
            return false;
        }
        if (!betDate.equals(bet.betDate)) {
            return false;
        }
        return Objects.equals(id, bet.id);
    }

    @Override
    public int hashCode() {
        int result = accountId.hashCode();
        result = 31 * result + matchId.hashCode();
        result = 31 * result + teamId.hashCode();
        result = 31 * result + moneyReceived.hashCode();
        result = 31 * result + moneyBet.hashCode();
        result = 31 * result + betDate.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Bet{" +
                "accountId=" + accountId +
                ", matchId=" + matchId +
                ", teamId=" + teamId +
                ", moneyReceived=" + moneyReceived +
                ", moneyBet=" + moneyBet +
                ", betDate=" + betDate +
                ", id=" + id +
                '}';
    }
}
