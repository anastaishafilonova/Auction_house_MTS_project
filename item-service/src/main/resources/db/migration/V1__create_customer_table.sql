CREATE TABLE customer
(
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR NOT NULL,
    last_name VARCHAR NOT NULL,
    balance INTEGER,
    bet INTEGER
);