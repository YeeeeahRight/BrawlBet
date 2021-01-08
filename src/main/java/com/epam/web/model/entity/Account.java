package com.epam.web.model.entity;

import com.epam.web.model.Entity;

public class Account implements Entity {
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
    private final int balance;
    private final boolean isBlocked;

    private long id;

    public Account(long id, String name, String password, String role, int balance, boolean isBlocked) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = role;
        this.balance = balance;
        this.isBlocked = isBlocked;
    }

    public Account(String name, String password, String role, int balance, boolean isBlocked) {
        this.name = name;
        this.password = password;
        this.role = role;
        this.balance = balance;
        this.isBlocked = isBlocked;
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
