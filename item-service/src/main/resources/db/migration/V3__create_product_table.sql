CREATE TABLE product
(
    product_id BIGSERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    price INTEGER,
    customer_id BIGINT REFERENCES customer (customer_id),
    start_time TIMESTAMP,
    finish_time TIMESTAMP,
    seller_id BIGINT REFERENCES seller (seller_id) ON DELETE CASCADE,
    status BOOLEAN,
    min_bet INTEGER NOT NULL
);