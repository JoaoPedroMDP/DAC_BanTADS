CREATE DATABASE IF NOT EXISTS auth;

CREATE TABLE IF NOT EXISTS auth.login (
    id serial PRIMARY KEY,
    password TEXT NOT NULL,
    email VARCHAR(255) NOT NULL,
    user INT NOT NULL
);

INSERT INTO login VALUES (
    1,
    "admin",
    "admin@admin.com",
    1
);