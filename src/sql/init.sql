INSERT INTO teams(name) VALUES ('Sharks');
INSERT INTO teams(name) VALUES ('Heroic');
INSERT INTO teams(name) VALUES ('Navi');
INSERT INTO teams(name) VALUES ('GamerLegion');
INSERT INTO teams(name) VALUES ('VP');
INSERT INTO teams(name) VALUES ('DreamTeam');
INSERT INTO teams(name) VALUES ('VG');
INSERT INTO teams(name) VALUES ('SecretTeam');
INSERT INTO teams(name) VALUES ('Alliance');
INSERT INTO teams(name) VALUES ('EG');
INSERT INTO teams(name) VALUES ('Liquid');
INSERT INTO teams(name) VALUES ('IG');
INSERT INTO teams(name) VALUES ('Fnatic');
INSERT INTO teams(name) VALUES ('Nigma');
INSERT INTO teams(name) VALUES ('Vikings');
INSERT INTO teams(name) VALUES ('Chaos');
INSERT INTO teams(name) VALUES ('Spiders');
INSERT INTO teams(name) VALUES ('Brame');
INSERT INTO teams(name) VALUES ('Unique');
INSERT INTO teams(name) VALUES ('Winstrike');
INSERT INTO teams(name) VALUES ('Empire');
INSERT INTO teams(name) VALUES ('Kings');
INSERT INTO teams(name) VALUES ('Dynasty');

INSERT INTO matches(date, tournament, first_team_id, second_team_id)
VALUES ('2021-01-27 18:09:22', 'Dreamhack Open', 1, 2);
INSERT INTO matches(date, tournament, first_team_id, second_team_id)
VALUES ('2021-01-27 14:09:22', 'CMD368', 3, 4);

INSERT INTO accounts(name, password, role)
VALUES('admin', 'admin', 'ADMIN');
INSERT INTO accounts(name, password, role)
VALUES('bookmaker', 'bookmaker', 'BOOKMAKER');
INSERT INTO accounts(name, password, role, balance)
VALUES('user1', 'user123', 'USER', 400);
INSERT INTO accounts(name, password, role)
VALUES('user2', 'user123', 'USER');
INSERT INTO accounts(name, password, role, balance)
VALUES('user3', 'user123', 'USER', 40);
INSERT INTO accounts(name, password, role)
VALUES('user4', 'user123', 'USER');
INSERT INTO accounts(name, password, role)
VALUES('user5', 'user123', 'USER');
INSERT INTO accounts(name, password, role)
VALUES('user6', 'user123', 'USER');