create database travelshooting;

create table users
(
    id              bigint auto_increment primary key,
    email           varchar(255) not null unique,
    password        varchar(255) not null,
    username        varchar(10)  not null,
    nickname        varchar(50)  not null unique,
    region          varchar(10),
    role            ENUM ('USER', 'ADMIN'),
    recent_password varchar(255)
);

insert into travelshooting.chat_rooms(chat_room_name)
VALUES ('서울'),
       ('경기'),
       ('인천'),
       ('충남'),
       ('충북'),
       ('대전'),
       ('강원'),
       ('경북'),
       ('대구'),
       ('울산'),
       ('경남'),
       ('부산'),
       ('전북'),
       ('전남'),
       ('광주'),
       ('제주');

-- 비밀번호는 1234입니다.
INSERT INTO users (email, password, username, nickname, region, role)
values ('admin@email.com', '$2a$10$oUcs64mK8NfUJphC91QpfeC/Ujsyx6fMqEMypWmTktLQGX0tEsvTS', 'admin', '관리자', '서울', 'ADMIN')