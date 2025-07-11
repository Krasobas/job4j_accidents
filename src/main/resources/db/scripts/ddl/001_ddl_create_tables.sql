create table if not exists role (
    id serial primary key,
    name varchar(50) not null
);

create table if not exists account (
    id serial primary key,
    email varchar not null unique,
    name varchar(50) not null,
    password varchar(100) not null,
    enabled boolean default true
);

create table if not exists account_role (
    account_id bigint not null references account(id),
    role_id bigint not null references role(id),
    primary key (account_id, role_id)
);

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