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
