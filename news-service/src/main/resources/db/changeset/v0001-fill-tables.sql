--liquibase formatted sql
--changeset pavel:insert-multiple-tables splitStatements:true endDelimiter:;
insert into news (id, time, title, text, user_id)
values
(uuid_generate_v4(), '2023-06-22 19:10:25-07', 'news1', 'text1', uuid_generate_v4()),
(uuid_generate_v4(), '2023-06-22 11:10:25-07', 'news2', 'text2', uuid_generate_v4()),
(uuid_generate_v4(), '2023-06-22 13:10:25-07', 'news3', 'text3', uuid_generate_v4()),
(uuid_generate_v4(), '2023-06-22 16:10:25-07', 'news4', 'text4', uuid_generate_v4()),
(uuid_generate_v4(), '2023-06-22 10:10:25-07', 'news5', 'text5', uuid_generate_v4());

insert into comment (id, time, text,user_id, user_name, news_id)
values
(uuid_generate_v4(), '2023-06-23 22:10:25-07', 'text1', uuid_generate_v4(), 'user1', uuid_generate_v4()),
(uuid_generate_v4(), '2023-01-11 14:10:25-07', 'text2', uuid_generate_v4(),'user2', uuid_generate_v4()),
(uuid_generate_v4(), '2023-02-12 11:10:25-07', 'text3', uuid_generate_v4(), 'user3', uuid_generate_v4()),
(uuid_generate_v4(), '2023-10-20 10:10:25-07', 'text4', uuid_generate_v4(), 'user4', uuid_generate_v4()),
(uuid_generate_v4(), '2023-06-28 01:10:25-07', 'text5', uuid_generate_v4(),'user5', uuid_generate_v4());

