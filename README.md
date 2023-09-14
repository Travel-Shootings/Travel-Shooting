# Travel-Shooting
![상단 이미지 후보2](https://github.com/Travel-Shootings/Travel-Shooting/assets/131871197/b063a562-d6c2-41dd-b778-11f89db73d9a)

목차

[1. 프로젝트 소개](#1.-프로젝트-소개)  

[2. 프로젝트 이용 방법](#2.-프로젝트-이용-방법)

[3. 아키텍처](#3.-아키텍처)

[4. ERD](#4.-erd)  

[5. 주요기능](#5.-주요기능)  

[6. 기술적 의사결정](#6.-기술적-의사결정)  

[7. 트러블 슈팅](#7.-트러블-슈팅)  

[8. 사용자 피드백 및 추후 개발 계획](#8.-사용자-피드백-및-추후-개발-계획)  

[9. 팀원 소개](#9.-팀원-소개)


## 1. 프로젝트 소개

프로젝트명 : 트래블 슈팅(Travel Shooting)
- 여행을 뜻하는 단어인 'Travel'과 문제 해결을 뜻하는 'Trouble Shooting'을 합쳐서 만든 이름으로, 여행에 대한 고민 해결을 뜻함
  
- 여행을 계획하면서 생기는 여러가지 고민이나 문제들을 여러 사람이 모여서 함께 고민하고 해결해보면 좋을 것 같다는 생각으로 프로젝트를 기획을 시작함

- 지도 기능 있는 여행 계획 게시판, 여행 후기와 사진을 올릴 수 있는 후기 게시판, 여행지 별 채팅방 등의 기능을 구현하여 여행을 주제로 한 커뮤니티 활성화를 목표로 함


## 2. 프로젝트 이용 방법
https://travel-shooting.site/view/home

- 해당 url로 접속하여 프로젝트를 이용해 볼 수 있습니다.
  
- PC 환경에서 접속해야 원활하게 이용하실 수 있습니다.
  
- 추가적인 기능들을 이용해보려면 로그인 후 이용하시길 추천드립니다.

## 3. 아키텍처 

![image](https://github.com/Travel-Shootings/Travel-Shooting/assets/131871197/1136e945-a80e-41bd-ada5-c2fe763a7eba)


## 4. ERD 

![erd 수정 PNG](https://github.com/Travel-Shootings/Travel-Shooting/assets/131871197/78e20508-dd0f-47b7-9add-a10aa70926b8)

https://www.erdcloud.com/d/syQQP4TuXNnECdBvA

## 5. 주요기능  

- 로그인 및 회원가입
  
 ![회원가입](https://github.com/Travel-Shootings/Travel-Shooting/assets/131871197/b96b284f-ea93-4aaf-8742-c5956dd5c748)

- 마이페이지(회원정보 수정, 비밀번호 변경)
  
![마이페이지](https://github.com/Travel-Shootings/Travel-Shooting/assets/131871197/2d4c397b-0d19-4fcb-b24b-f4907fa7941b)  

- 여행 지역별 채팅방
  
![채팅방](https://github.com/Travel-Shootings/Travel-Shooting/assets/131871197/0c61d7e7-6241-441a-b7f6-5577fc6bc9e2)

- 여행 계획 게시판(지도 API, 날짜별 여행 일정 계획, 여행 경비 계산)
  
![여행 계획 게시판](https://github.com/Travel-Shootings/Travel-Shooting/assets/131871197/44db36d1-8909-4cbd-8006-1f13cf1e503b)

- 여행 후기 게시판(이미지 첨부 기능)
  
![여행 후기 게시판](https://github.com/Travel-Shootings/Travel-Shooting/assets/131871197/cddb8e5f-7c1c-4f9b-93c9-f36970aa5a22)

- 게시판 부가 기능(댓글, 대댓글, 좋아요)
  
![부가기능](https://github.com/Travel-Shootings/Travel-Shooting/assets/131871197/7113aaf6-c81b-445d-8743-18fc3dff8e50)

- 백오피스(회원 관리, 게시글 및 댓글 관리)
  
![백오피스](https://github.com/Travel-Shootings/Travel-Shooting/assets/131871197/ac2bb0f6-4781-4fda-b228-f6a222375e22)

- 알림 기능

## 6. 기술적 의사결정  

![기술적 의사결정](https://github.com/Travel-Shootings/Travel-Shooting/assets/131871197/d0426c18-1eb1-44c4-a2a2-f2b7d56d3365)

## 7. 트러블 슈팅  

<h3>- 네이버 지도 API </h3>

**문제** 
1. 네이버 open API 를 이용해서 지도를 구현하긴 하였으나, 도로명 주소를 정확히 입력 또는 x좌표 & y좌표를 입력해야 지도에 표시가 되는 상황
→ 사용자 관점에서 도로명 주소를 정확히 입력하는 건 매우 불편하다 생각

2. 장소를 여러개 등록할 때 지도상에 표시가 한개 밖에 핀이 안찍히는 문제
→ 장소를 여러군데 등록할 때 & 게시글을 조회할 때 한번에 모든 동선이 보이는게 사용자 관점에서 좋다고 판단

**시도**
1. 네이버 openAPI 를 확인하던 중 네이버 지역 검색 API를 사용하면 다음과 같이 상호명, 장소 이름, 주소, 도로명 주소 를 모두 받아낼 수 있다는 것을 알게 되어 응용을 시도
   
2. 네이버 지도 API 예시 중 폴리라인을 이용하면 여러 장소를 한눈에 볼 수 있을것 같아 이를 적용해보기를 시도

**해결**
1. 프론트에서 유저가 장소 명을 검색 → 백엔드 코드에 네이버 지역 검색 요청을 받은 후 그 값을 다시 프론트에 전달하여 5개의 검색값 중 하나를 선택 → 주소 값을 그대로 지도 API 전달하여 지도에 핀이 찍히게 하여 문제 해결
   
2. 프론트엔드 코드에서 poly라인을 전역에 선언한 후 배열에 지도의 좌표 값을 넣어서 이를 잇게 하여 한눈에 여러 동선이 있더라도 순서에 맞게 볼 수 있도록 구현

<h3>- S3 버킷을 활용한 이미지 저장 기능 </h3>
  
**문제**
1. 이미지 첨부 기능이 있는 게시판 구현을 위해서 기존에 사용하던 MYSQL 이외의 온라인 스토리지를 활용해야 했음. S3 버킷을 환경에 맞게 설정하고 프로젝트 내에서 MYSQL,S3의 연결관계를 만드는 것이 까다로웠음
   
2. MYSQL에 데이터를 저장하는 로직과 S3에 업로드하는 로직을 구현한 후에도 DB에 데이터만 저장되고 S3에 이미지가 올라가지 않거나, 반대로 DB에 데이터 저장없이 S3에 이미지만 올라가버리는 문제가 발생

**시도**
1. @Transactional을 활용해서 메서드에서 오류가 발생 하였을 때 롤백하도록 하여 MYSQL 혹은 S3 한 쪽에서만 데이터 저장이 일어나지 않도록 함

<h3>- 로그아웃 이후 AccessToken 관리 </h3>

**문제**
1. 로그아웃을 진행하면 쿠키를 삭제하고, Redis에 저장 되어있던 RefreshToken을 삭제한다. 그러나 쿠키를 삭제해도 만약 이전에 AccessToken이 탈취가 되어있는 상태이면 이 AccessToken이 만료되기 전에 충분히 활용될 위험성이 있다.
  
2. 로그아웃과 동시에 AccessToken을 BlackList 테이블에  저장하는 방법을 사용하려 했는데, RDBMS는 만료 시간에 따른 데이터 처리를 하기 까다로웠음

**시도**
1. Spring Scheduler를 사용하여 일정 시간마다 만료된 토큰을 DB에서 삭제하려 했으나 효율적이지 못하다고 판단
   
2. Redis를 활용하게 된다면 AccessToken의 만료시간이 지나면 자동적으로 삭제되고, 조회 속도도 훨씬 개선되는 장점이 있어서 Redis에 BlackList를 저장하기로 시도

<h3>- https 에서의 WebSocket 연결 오류 </h3>

**문제**  

1. nginx 로 80 포트(http)로의 요청을 전부 https로 리다이렉트 시켰었는데,  웹소켓 연결 자체가 정상적으로 작동하지 않았던 오류  

2. port 문제인지, https 자체의 문제인지 원인을 찾기 어려웠으나 다른 서비스는 정상 동작하는 것으로 보아 brokerURL 혹은 라우팅 설정 둘 중에 원인이 있을 것이라고 추측

**시도**  

1. 우선 brokerURL을 https에 맞춰서 wss:// 로 변경, 기존의 연결 포트 번호(8080)를 nginx 라우팅에서 관리하도록 수정    

2. 웹소켓 서브도메인(brokerURL)에 대해서도 따로 라우팅 설정

**해결**  

1. 위 설정 후 해결이 되었다. 다른 url, 혹은 API 요청은 정상 동작하고 리다이렉트 또한 제대로 되었었는데, 웹소켓 url에 대해서만 오류가 발생했고 이를 라우팅 설정에 따로 작성을 해 주어야 하는지 처음 알게 되었다.   
이후 http, 8080 포트로의 서버 접근을 막기 위해 EC2 inbound 규칙을 수정함으로써 https 관련 이슈는 해결하고, 설정을 완료할 수 있었다.

## 8. 사용자 피드백 및 추후 개발 계획

## 9. 팀원 소개

![역할](https://github.com/Travel-Shootings/Travel-Shooting/assets/131871197/e88fdfc5-07d6-400b-8515-e31dfd3395b2)



