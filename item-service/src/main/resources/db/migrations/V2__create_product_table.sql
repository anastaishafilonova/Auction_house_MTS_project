CREATE TABLE product
(
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    price INTEGER,
    customer_id BIGINT REFERENCES customer (id),
    start_time TIMESTAMP,
    finish_time TIMESTAMP,
    seller_id BIGINT REFERENCES seller (id) ON DELETE CASCADE,
    status BOOLEAN,
    min_bet INTEGER NOT NULL
);