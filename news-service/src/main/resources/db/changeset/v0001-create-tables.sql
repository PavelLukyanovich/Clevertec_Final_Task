create table if not exists news
(
    id    uuid not null primary key,
    time  timestamp,
    title varchar(150),
    text  text,
    user_id uuid not null
    );

create table if not exists comment
(
    id   uuid not null primary key,
    time timestamp,
    text text,
    user_id uuid not null,
    user_name varchar(60),
    news_id uuid not null
);

create extension if not exists "uuid-ossp";