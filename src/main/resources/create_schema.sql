DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS role_seq;

CREATE SEQUENCE role_seq START WITH 50000;

CREATE TABLE users
(
    login    VARCHAR PRIMARY KEY,
    name     VARCHAR NOT NULL,
    password VARCHAR NOT NULL
);

CREATE TABLE role
(
    id         INT PRIMARY KEY DEFAULT nextval('role_seq'),
    role_name  VARCHAR NOT NULL,
    user_login VARCHAR NOT NULL,
    FOREIGN KEY (user_login) REFERENCES users (login) ON DELETE CASCADE
);
