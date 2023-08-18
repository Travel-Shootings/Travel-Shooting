create database travelshooting;

create table users
(
    id       bigint auto_increment primary key,
    username varchar(50)  not null unique,
    password varchar(255) not null,
    nickname varchar(50)  not null,
    address  varchar(255) not null unique,
    email    varchar(255) not null unique
);

insert into travelshooting.users(username, password, nickname, address, email)
VALUES ('name1', 1234, 'nickname1', 'Seoul', 'ex1@email.com'),
       ('name2', 1234, 'nickname2', 'Seoul', 'ex2@email.com'),
       ('name3', 1234, 'nickname3', 'Busan', 'ex3@email.com'),
       ('name4', 1234, 'nickname4', 'Busan', 'ex4@email.com'),
       ('name5', 1234, 'nickname5', 'Jeju', 'ex5@email.com');