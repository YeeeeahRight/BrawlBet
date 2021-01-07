package com.epam.web.model.entity;

import java.math.BigDecimal;

public class Account {
    public static final String ID = "id";
    public static final String ROLE = "role";
    public static final String PASSWORD = "password";
    public static final String TABLE = "accounts";
    public static final String NAME = "name";
    public static final String BALANCE = "balance";
    public static final String STATUS = "is_blocked";

    private final String name;
    private final String password;
    private final String role;

    private long id;

    private int balance;
    private boolean isBlocked;

    public Account(String name, String password, String role, long id, int balance, boolean isBlocked) {
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


    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public int getBalance() {
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

    public long getId() {
        return id;
    }

    
}
