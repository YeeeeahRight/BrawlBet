INSERT INTO teams(name) VALUES ('Sharks');
INSERT INTO teams(name) VALUES ('Heroic');
INSERT INTO teams(name) VALUES ('Navi');
INSERT INTO teams(name) VALUES ('GamerLegion');

INSERT INTO matches(date, tournament, first_team_id, second_team_id)
VALUES ('2021-01-27 18:09:22', 'Dreamhack Open', 1, 2);
INSERT INTO matches(date, tournament, first_team_id, second_team_id)
VALUES ('2021-01-27 14:09:22', 'CMD368', 3, 4);

INSERT INTO accounts(name, password, role)
VALUES('admin', 'admin123', 'ADMIN');
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