CREATE TABLE users
(
    username         VARCHAR(50)  NOT NULL,
    password         VARCHAR(100) NOT NULL,
    firstname        VARCHAR(30)  NOT NULL,
    enabled          boolean      NOT NULL,
    PRIMARY KEY (username)
);
INSERT INTO users(username, password, firstname, enabled)
VALUES ('elvira', '$2a$10$5Uz2qq8W.NLKITMFizNJM.QomdCNeiRFpRqhwRvIAmv7AWiZdGMxC', 'Эльвира',  true),
       ('user', '$2a$10$pmSqleuQX0M6hxYDQcd6P.CwHaix8gSxYiTU4pcZaOtDcz4pnb8em', 'Иван',  true);
CREATE TABLE authorities
(
    username  varchar(50) NOT NULL,
    authority varchar(50) NOT NULL,

    CONSTRAINT authorities_idx UNIQUE (username, authority),

    CONSTRAINT authorities_ibfk_1
        FOREIGN KEY (username)
            REFERENCES users (username)
);

INSERT INTO authorities(username, authority)
VALUES ('elvira', 'ROLE_USER'),
       ('user', 'ROLE_MED');
