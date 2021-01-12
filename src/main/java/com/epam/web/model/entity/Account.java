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
    private final Integer balance;
    private final Boolean isBlocked;
    private final Long id;

    public Account(Long id, String name, String password, String role, Integer balance, Boolean isBlocked) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = role;
        this.balance = balance;
        this.isBlocked = isBlocked;
    }

    public Account(String name, String password, String role) {
        this.id = null;
        this.name = name;
        this.password = password;
        this.role = role;
        this.balance = 0;
        this.isBlocked = false;
    }

    public Integer getBalance() {
        return balance;
    }

    public Boolean isBlocked() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Account account = (Account) o;

        if (!name.equals(account.name)) {
            return false;
        }
        if (!password.equals(account.password)) {
            return false;
        }
        if (!role.equals(account.role)) {
            return false;
        }
        if (!balance.equals(account.balance)) {
            return false;
        }
        if (!isBlocked.equals(account.isBlocked)) {
            return false;
        }
        return id.equals(account.id);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + role.hashCode();
        result = 31 * result + balance.hashCode();
        result = 31 * result + isBlocked.hashCode();
        result = 31 * result + id.hashCode();
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
