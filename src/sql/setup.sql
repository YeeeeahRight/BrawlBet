CREATE DATABASE totalizator;

USE totalizator;

CREATE TABLE accounts
(
	id BIGINT AUTO_INCREMENT,
    name VARCHAR(20) NOT NULL,
    password VARCHAR(25) NOT NULL,
    role ENUM('USER','BOOKMAKER', 'ADMIN') NOT NULL,
    balance float DEFAULT 0.0 NOT NULL,
    is_blocked BOOLEAN DEFAULT 0 NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE matches
(
	id BIGINT AUTO_INCREMENT,
    date DATETIME NOT NULL,
    tournament VARCHAR(15) NOT NULL,
    first_team VARCHAR(15) NOT NULL,
    second_team VARCHAR(15) NOT NULL,
    commission FLOAT DEFAULT 0.0 NOT NULL,
    is_closed BOOLEAN DEFAULT 0 NOT NULL,
    first_team_bets FLOAT DEFAULT 0.0 NOT NULL,
    second_team_bets FLOAT DEFAULT 0.0 NOT NULL,
    winner VARCHAR(15) DEFAULT 'NONE' NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE bets
(
	id BIGINT AUTO_INCREMENT,
	match_id   BIGINT       NOT NULL,
    account_id BIGINT       NOT NULL,
    money_bet float NOT NULL,
    team ENUM('FIRST', 'SECOND') NOT NULL,
    money_received float DEFAULT 0.0 NOT NULL,
	bet_date DATETIME NOT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY (match_id) REFERENCES matches (id) ON DELETE CASCADE,
    FOREIGN KEY (account_id) REFERENCES accounts (id) ON DELETE CASCADE
);