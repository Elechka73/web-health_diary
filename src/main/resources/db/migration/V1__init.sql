CREATE TABLE users
(
    username         VARCHAR(50)  NOT NULL,
    password         VARCHAR(100) NOT NULL,
    firstname        VARCHAR(30)  NOT NULL,
    lastname         VARCHAR(30)  NOT NULL,
    enabled          boolean      NOT NULL,
    patronymic       VARCHAR(30),
    date_of_birth    date        NOT NULL,
    insurance_number varchar(16) NOT NULL,
        PRIMARY KEY (username)
);
INSERT INTO users(username, password, firstname, lastname, patronymic, date_of_birth,insurance_number, enabled )
VALUES ('elvira', '$2a$10$5Uz2qq8W.NLKITMFizNJM.QomdCNeiRFpRqhwRvIAmv7AWiZdGMxC', 'Эльвира','Веккер', 'Викторовна','16.07.2002'::date,'1234123412341234',  true),
       ('user', '$2a$10$pmSqleuQX0M6hxYDQcd6P.CwHaix8gSxYiTU4pcZaOtDcz4pnb8em', 'Иван','Иванов',null, '01.01.2001'::date,'0000000000000000', true);
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

CREATE SEQUENCE diaries_id_seq;
CREATE TABLE diaries
(id INTEGER PRIMARY KEY DEFAULT nextval('diaries_id_seq') ,
username varchar(30) NOT NULL REFERENCES users(username)
);

CREATE SEQUENCE dishes_id_seq;
CREATE SEQUENCE activities_id_seq;

CREATE TABLE dishes
(id INTEGER PRIMARY KEY DEFAULT nextval('dishes_id_seq'),
dish_name VARCHAR(255) NOT NULL,
proteins real NOT NULL,
fats real NOT NULL,
carbohydrates real NOT NULL,
energy_value real NOT NULL
);

CREATE TABLE activities
(id INTEGER PRIMARY KEY DEFAULT nextval('activities_id_seq'),
activity_name VARCHAR(255) NOT NULL,
calories_per_minute real NOT NULL
);
