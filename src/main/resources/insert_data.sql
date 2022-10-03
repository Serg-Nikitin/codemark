DELETE
FROM users;
DELETE
FROM role;


INSERT INTO users
VALUES ('al', 'Alex', 'A1'),
       ('yu', 'Yuri', 'Y2'),
       ('ru', 'Ruslan', 'R3')
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
