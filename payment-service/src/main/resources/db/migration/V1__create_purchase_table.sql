create table purchase (
    user_id BIGINT primary key,
    balance int default 0 check (balance >= 0),
    type_of_user text not null
)