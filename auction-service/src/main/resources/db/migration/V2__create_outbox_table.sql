create table outbox
(
    id BIGSERIAL primary key,
    data TEXT not null
)
