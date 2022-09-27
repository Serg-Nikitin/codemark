DELETE
FROM users;
DELETE
FROM role;

ALTER SEQUENCE role_seq RESTART WITH 50000;


INSERT INTO users
VALUES ('al', 'Alex', 'alex'),
       ('yu', 'Yuri', 'yuri'),
       ('ru', 'Ruslan', 'rus')
;

INSERT INTO role(role_name, user_login)
VALUES ('EMPLOYEE', 'al'),
       ('EMPLOYEE', 'yu'),
       ('EMPLOYEE', 'ru'),
       ('LEAD', 'al'),
       ('OPERATOR', 'yu'),
       ('ADMIN', 'yu'),
       ('ANALYST', 'al'),
       ('DEVELOPER', 'ru')
;
