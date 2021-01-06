package com.epam.web.model.entity;

import java.math.BigDecimal;

public class Account {
    public static final String ID = "id";
    public static final String ROLE = "role";
    public static final String PASSWORD = "password";
    public static final String TABLE = "accounts";
    public static final String NAME = "name";
    public static final String BALANCE = "balance";
    public static final String STATUS = "isBlocked";

    private final String name;
    private final String password;
    private final String role;

    private Long id;

    private BigDecimal balance;
    private boolean isBlocked;

    public Account(String name, String password, String role, Long id, BigDecimal balance, boolean isBlocked) {
        this.name = name;
        this.password = password;
        this.role = role;
        this.id = id;
        this.balance = balance;
        this.isBlocked = isBlocked;
    }

    public Account(String name, String password, String role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }


    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public Long getId() {
        return id;
    }

    
}
