--liquibase formatted sql
--changeset pavel:insert-table;
create extension if not exists "uuid-ossp";
insert into users (id, name, login, password, user_role)
values
(uuid_generate_v4(), 'Ivan', 'ivan-BANAN_22', '1rC#pswrd', 'ADMIN'),
(uuid_generate_v4(), 'Alex', 'alexs-SHMALEX.13', '2vM~pswrd', 'JOURNALIST'),
(uuid_generate_v4(), 'Maria', 'masha-KASHA-43', '3qP$pswrd', 'SUBSCRIBER');


