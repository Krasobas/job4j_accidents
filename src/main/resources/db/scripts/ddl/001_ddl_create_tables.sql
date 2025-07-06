create table if not exists accident_type (
    id serial primary key,
    name varchar not null
);

create table if not exists rule (
    id serial primary key,
    name varchar not null
);

create table if not exists accident (
    id serial primary key,
    name varchar not null,
    text text,
    address varchar,
    type_id bigint not null references accident_type(id)
);

create table if not exists accident_rule(
    accident_id bigint not null references accident(id),
    rule_id bigint not null references rule(id),
    primary key (accident_id, rule_id)
);