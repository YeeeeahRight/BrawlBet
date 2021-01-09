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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Account account = (Account) o;

        if (balance != account.balance) {
            return false;
        }
        if (isBlocked != account.isBlocked) {
            return false;
        }
        if (id != account.id) {
            return false;
        }
        if (!name.equals(account.name)) {
            return false;
        }
        if (!password.equals(account.password)) {
            return false;
        }
        return role.equals(account.role);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + role.hashCode();
        result = 31 * result + balance;
        result = 31 * result + (isBlocked ? 1 : 0);
        result = 31 * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Account{" + "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", balance=" + balance +
                ", isBlocked=" + isBlocked +
                ", id=" + id +
                '}';
    }
}
