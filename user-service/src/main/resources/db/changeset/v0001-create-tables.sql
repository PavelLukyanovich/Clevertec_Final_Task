--create type u_Role as ENUM ('ADMIN', 'JOURNALIST', 'SUBSCRIBER');

create table users
(
    id        uuid        not null
        primary key,
    name      varchar(60),
    login     varchar(30) not null,
    password  varchar(250),
    role varchar
);

create unique index users_login_uindex
    on users (login);
create extension if not exists "uuid-ossp";