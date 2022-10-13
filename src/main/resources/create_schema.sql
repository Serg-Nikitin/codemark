-- Create schema
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    login    VARCHAR PRIMARY KEY,
    name     VARCHAR NOT NULL,
    password VARCHAR NOT NULL
);

CREATE TABLE role
(
    role_name  VARCHAR NOT NULL,
    user_login VARCHAR NOT NULL,
    PRIMARY KEY (role_name, user_login),
    FOREIGN KEY (user_login) REFERENCES users (login) ON DELETE CASCADE
);

-- populate schema

DELETE
FROM users;
DELETE
FROM role;


INSERT INTO users
VALUES ('al', 'Alex', 'A1'),
       ('yu', 'Yuri', 'Y2'),
       ('ve', 'Veronika', 'V4'),
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