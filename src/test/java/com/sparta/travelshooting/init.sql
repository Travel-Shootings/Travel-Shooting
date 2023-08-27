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

insert into travelshooting.users(email, password, username, nickname, region, role)
VALUES ('ex1@email.com', 1234, '김XX', 'nickname1', '서울', 'USER'),
       ('ex2@email.com', 1234, '이XX', 'nickname2', '경기', 'USER'),
       ('ex3@email.com', 1234, '박XX', 'nickname3', '인천', 'USER'),
       ('ex4@email.com', 1234, '주XX', 'nickname4', '충남', 'USER'),
       ('ex5@email.com', 1234, '김OO', 'nickname5', '충북', 'USER'),
       ('ex6@email.com', 1234, '최XX', 'nickname6', '대전', 'USER'),
       ('ex7@email.com', 1234, '정XX', 'nickname7', '강원', 'USER'),
       ('ex8@email.com', 1234, '강XX', 'nickname8', '경북', 'USER'),
       ('ex9@email.com', 1234, '조XX', 'nickname9', '대구', 'USER'),
       ('ex10@email.com', 1234, '윤OO', 'nickname10', '울산', 'USER'),
       ('ex11@email.com', 1234, '장XX', 'nickname11', '경남', 'USER'),
       ('ex12@email.com', 1234, '임XX', 'nickname12', '부산', 'USER'),
       ('ex13@email.com', 1234, '한XX', 'nickname13', '전남', 'USER'),
       ('ex14@email.com', 1234, '오XX', 'nickname14', '광주', 'USER'),
       ('ex15@email.com', 1234, '서OO', 'nickname15', '제주', 'USER'),
       ('ex16@email.com', 1234, '신XX', 'nickname16', '서울', 'USER'),
       ('ex17@email.com', 1234, '권XX', 'nickname17', '경기', 'USER'),
       ('ex18@email.com', 1234, '황XX', 'nickname18', '인천', 'USER'),
       ('ex19@email.com', 1234, '안XX', 'nickname19', '충남', 'USER'),
       ('ex20@email.com', 1234, '송OO', 'nickname20', '충북', 'USER');


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

# INSERT INTO chat_messages (content, sender_name, time, user_id, chat_room_id)
# SELECT
#     CONCAT('메세지 내용 ', FLOOR(RAND() * 100)), -- 랜덤한 내용 생성
#     CONCAT('보낸이 ', FLOOR(RAND() * 22) + 1), -- 랜덤한 사용자 ID 생성 (1~22)
#     NOW() - INTERVAL 30 DAY + INTERVAL (seq.seq + 1) MINUTE, -- 1분 간격으로 시간 생성
#     FLOOR(RAND() * 22) + 1, -- 랜덤한 사용자 ID 생성 (1~22)
#     FLOOR(RAND() * 16) + 1 -- 랜덤한 채팅방 ID 생성 (1~16)
# FROM information_schema.TABLES,
#      (SELECT a.seq + b.seq AS seq
#       FROM (SELECT 0 AS seq UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) a,
#            (SELECT 0 AS seq UNION SELECT 10 UNION SELECT 20 UNION SELECT 30 UNION SELECT 40) b
#      ) seq
# WHERE seq.seq BETWEEN 1 AND 100 -- 100개의 데이터 생성
# ORDER BY seq.seq;

INSERT INTO chat_messages (content, sender_name, time, user_id, chat_room_id)
VALUES
    ('안녕하세요!', '보낸이 1', '2023-08-01 00:01:00', 1, 1),
    ('안녕하세요~', '보낸이 2', '2023-08-01 00:02:00', 2, 1),
    ('안녕하세요^^', '보낸이 3', '2023-08-01 00:03:00', 3, 1),
    ('안녕하세요~ 반갑습니다!', '보낸이 4', '2023-08-01 00:04:00', 4, 1),
    ('안녕하세요~ 오랜만입니다.', '보낸이 5', '2023-08-01 00:05:00', 5, 1),
    ('안녕하세요! 잘 지내셨나요?', '보낸이 6', '2023-08-01 00:06:00', 6, 1),
    ('안녕하세요~ 신나는 하루 시작이네요!', '보낸이 7', '2023-08-01 00:07:00', 7, 1),
    ('안녕하세요~ 기분이 좋아지는 아침입니다!', '보낸이 8', '2023-08-01 00:08:00', 8, 1),
    ('안녕하세요~ 행복한 하루 되세요!', '보낸이 9', '2023-08-01 00:09:00', 9, 1),
    ('안녕하세요~ 미소가 가득한 하루 되세요!', '보낸이 10', '2023-08-01 00:10:00', 10, 1),
    ('안녕하세요~~ 오늘도 화이팅!', '보낸이 11', '2023-08-01 00:11:00', 11, 1),
    ('안녕하세요~ 행복한 일요일 되세요!', '보낸이 12', '2023-08-01 00:12:00', 12, 1),
    ('안녕하세요~ 즐거운 주말 보내세요!', '보낸이 13', '2023-08-01 00:13:00', 13, 1),
    ('안녕하세요~ 웃음 가득한 하루 되세요!', '보낸이 14', '2023-08-01 00:14:00', 14, 1),
    ('안녕하세요~ 힘찬 하루 시작이에요!', '보낸이 15', '2023-08-01 00:15:00', 15, 1),
    ('안녕하세요~ 편안한 하루 보내세요!', '보낸이 16', '2023-08-01 00:16:00', 16, 1),
    ('안녕하세요~ 즐거운 하루 되세요!', '보낸이 17', '2023-08-01 00:17:00', 17, 1),
    ('안녕하세요~ 스트레스 없는 하루 되세요!', '보낸이 18', '2023-08-01 00:18:00', 18, 1),
    ('안녕하세요~ 행복한 하루 보내세요!', '보낸이 19', '2023-08-01 00:19:00', 19, 1),
    ('안녕하세요~ 마음 편히 쉬세요!', '보낸이 20', '2023-08-01 00:20:00', 20, 1);
