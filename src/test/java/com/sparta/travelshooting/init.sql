create database travelshooting;

create table users
(
    id              bigint auto_increment primary key,
    email           varchar(255) not null unique,
    password        varchar(255) not null,
    username        varchar(10)  not null,
    nickname        varchar(50)  not null unique,
    region          varchar(10),
    role            varchar(10),
    recent_password varchar(255)
);

insert into travelshooting.users(email, password, username, nickname, region)
VALUES ('ex1@email.com', 1234, '김XX', 'nickname1', '서울'),
       ('ex2@email.com', 1234, '이XX', 'nickname2', '경기'),
       ('ex3@email.com', 1234, '박XX', 'nickname3', '인천'),
       ('ex4@email.com', 1234, '주XX', 'nickname4', '충남'),
       ('ex5@email.com', 1234, '김OO', 'nickname5', '충북'),
       ('ex6@email.com', 1234, '최XX', 'nickname6', '대전'),
       ('ex7@email.com', 1234, '정XX', 'nickname7', '강원'),
       ('ex8@email.com', 1234, '강XX', 'nickname8', '경북'),
       ('ex9@email.com', 1234, '조XX', 'nickname9', '대구'),
       ('ex10@email.com', 1234, '윤OO', 'nickname10', '울산'),
       ('ex11@email.com', 1234, '장XX', 'nickname11', '경남'),
       ('ex12@email.com', 1234, '임XX', 'nickname12', '부산'),
       ('ex13@email.com', 1234, '한XX', 'nickname13', '전남'),
       ('ex14@email.com', 1234, '오XX', 'nickname14', '광주'),
       ('ex15@email.com', 1234, '서OO', 'nickname15', '제주'),
       ('ex16@email.com', 1234, '신XX', 'nickname16', '서울'),
       ('ex17@email.com', 1234, '권XX', 'nickname17', '경기'),
       ('ex18@email.com', 1234, '황XX', 'nickname18', '인천'),
       ('ex19@email.com', 1234, '안XX', 'nickname19', '충남'),
       ('ex20@email.com', 1234, '송OO', 'nickname20', '충북');


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
       ('제주')