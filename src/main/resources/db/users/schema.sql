CREATE TABLE IF NOT EXISTS USERS
(
    ID uuid NOT NULL DEFAULT random_uuid() PRIMARY KEY,
    FIRST_NAME varchar(50),
    LAST_NAME varchar(50),
    EMAIL varchar(50),
    BIRTH_DATE DATE,
    ADDRESS varchar(50),
    PHONE varchar(50)
);