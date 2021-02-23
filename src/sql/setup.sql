CREATE DATABASE totalizator;

USE totalizator;

CREATE TABLE accounts
(
	id BIGINT AUTO_INCREMENT,
    name     VARCHAR(20) NOT NULL UNIQUE,
    password VARCHAR(25) NOT NULL,
    role ENUM('USER','BOOKMAKER', 'ADMIN') NOT NULL,
    balance DECIMAL(12,4) DEFAULT 0.0 NOT NULL,
    is_blocked BOOLEAN DEFAULT 0 NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE teams
(
	id BIGINT AUTO_INCREMENT,
    name VARCHAR(15) NOT NULL UNIQUE,
    matches_won  INT DEFAULT 0 NOT NULL,
    matches_lost INT DEFAULT 0 NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE matches
(
	id BIGINT AUTO_INCREMENT,
	first_team_id  BIGINT NOT NULL,
    second_team_id BIGINT NOT NULL,
    winner_team ENUM('FIRST', 'SECOND', 'NONE') DEFAULT 'NONE' NOT NULL,
    date DATETIME NOT NULL,
    tournament VARCHAR(15) NOT NULL,
    commission FLOAT DEFAULT 0.0 NOT NULL,
    is_closed BOOLEAN DEFAULT 0 NOT NULL,
    first_team_bets  DECIMAL(12,4) DEFAULT 0.0 NOT NULL,
    second_team_bets DECIMAL(12,4) DEFAULT 0.0 NOT NULL,
    FOREIGN KEY (first_team_id)  REFERENCES teams (id) ON DELETE CASCADE,
	FOREIGN KEY (second_team_id) REFERENCES teams (id) ON DELETE CASCADE,
    PRIMARY KEY(id)
);

CREATE TABLE bets
(
	id BIGINT AUTO_INCREMENT,
	match_id   BIGINT       NOT NULL,
    account_id BIGINT       NOT NULL,
    team_id    BIGINT       NOT NULL,
    money_bet      DECIMAL(12,4) NOT NULL,
    money_received DECIMAL(12,4) DEFAULT 0.0 NOT NULL,
	bet_date DATETIME NOT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY (match_id) REFERENCES matches (id) ON DELETE CASCADE,
	FOREIGN KEY (team_id) REFERENCES teams (id) ON DELETE CASCADE,
    FOREIGN KEY (account_id) REFERENCES accounts (id) ON DELETE CASCADE
);