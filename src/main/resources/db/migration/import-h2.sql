-- password in plaintext: "password"
INSERT INTO USER (password, email, username, first_name, last_name, active) VALUES
 ('$2a$06$OAPObzhRdRXBCbk7Hj/ot.jY3zPwR8n7/mfLtKIgTzdJa4.6TwsIm', 'user@mail.com', 'user', 'Name', 'Surname', 1);
INSERT INTO USER (password, email, username, first_name, last_name, active) VALUES
 ('$2a$06$OAPObzhRdRXBCbk7Hj/ot.jY3zPwR8n7/mfLtKIgTzdJa4.6TwsIm', 'johndoe@gmail.com', 'johndoe', 'John', 'Doe', 1);
INSERT INTO USER (password, email, username, first_name, last_name, active) VALUES
 ('$2a$06$OAPObzhRdRXBCbk7Hj/ot.jY3zPwR8n7/mfLtKIgTzdJa4.6TwsIm', 'name@gmail.com', 'namesurname', 'Name', 'Surname', 1);

INSERT INTO ROLE (role) VALUES ('ROLE_ADMIN');
INSERT INTO ROLE (role) VALUES ('ROLE_USER');

INSERT INTO USER_ROLE (user_id, role_id) VALUES (1, 1);
INSERT INTO USER_ROLE (user_id, role_id) VALUES (1, 2);
INSERT INTO USER_ROLE (user_id, role_id) VALUES (2, 2);
INSERT INTO USER_ROLE (user_id, role_id) VALUES (3, 2);

INSERT INTO ENTRY (DATE, DISTANCE, DURATION, LOCATION, WEATHER, USER_ID) VALUES
(DATEADD(day, -10, CURRENT_TIMESTAMP()), 12341, 561, 'London,uk', 'UNKNOWN', 1);
INSERT INTO ENTRY (DATE, DISTANCE, DURATION, LOCATION, WEATHER, USER_ID) VALUES
(DATEADD(day, -9, CURRENT_TIMESTAMP()), 12342, 562, 'London,uk', 'UNKNOWN', 1);
INSERT INTO ENTRY (DATE, DISTANCE, DURATION, LOCATION, WEATHER, USER_ID) VALUES
(DATEADD(day, -8, CURRENT_TIMESTAMP()), 12343, 563, 'London,uk', 'UNKNOWN', 1);
INSERT INTO ENTRY (DATE, DISTANCE, DURATION, LOCATION, WEATHER, USER_ID) VALUES
(DATEADD(day, -7, CURRENT_TIMESTAMP()), 12344, 564, 'London,uk', 'UNKNOWN', 2);
INSERT INTO ENTRY (DATE, DISTANCE, DURATION, LOCATION, WEATHER, USER_ID) VALUES
(DATEADD(day, -6, CURRENT_TIMESTAMP()), 12345, 565, 'London,uk', 'UNKNOWN', 2);
INSERT INTO ENTRY (DATE, DISTANCE, DURATION, LOCATION, WEATHER, USER_ID) VALUES
(DATEADD(day, -5, CURRENT_TIMESTAMP()), 12346, 566, 'London,uk', 'UNKNOWN', 2);
INSERT INTO ENTRY (DATE, DISTANCE, DURATION, LOCATION, WEATHER, USER_ID) VALUES
(DATEADD(day, -4, CURRENT_TIMESTAMP()), 12347, 567, 'London,uk', 'UNKNOWN', 2);
INSERT INTO ENTRY (DATE, DISTANCE, DURATION, LOCATION, WEATHER, USER_ID) VALUES
(DATEADD(day, -3, CURRENT_TIMESTAMP()), 12348, 568, 'London,uk', 'UNKNOWN', 3);
INSERT INTO ENTRY (DATE, DISTANCE, DURATION, LOCATION, WEATHER, USER_ID) VALUES
(DATEADD(day, -2, CURRENT_TIMESTAMP()), 12349, 569, 'London,uk', 'UNKNOWN', 3);
INSERT INTO ENTRY (DATE, DISTANCE, DURATION, LOCATION, WEATHER, USER_ID) VALUES
(DATEADD(day, -1, CURRENT_TIMESTAMP()), 12347, 561, 'London,uk', 'UNKNOWN', 3);

insert into employee(name) values ('ana');
insert into employee(name) values ('john');
insert into book(author, title) values ('ana', 'how to bake cookies');
insert into book(author, title) values ('john', 'how to mine bitcoins');
insert into GITHUB_PROJECT(ORG_NAME , REPO_NAME ) values ('wangshane3', 'minimum-set-web-jpa');