create table user_role (
    user_id bigint references users (id) on delete cascade,
    roles text not null
)