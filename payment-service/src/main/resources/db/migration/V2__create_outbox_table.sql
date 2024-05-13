create table outbox (
    id BIGSERIAL primary key,
    data text not null
)