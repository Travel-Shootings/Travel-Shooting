# Travel-Shooting
![상단 이미지 후보2](https://github.com/Travel-Shootings/Travel-Shooting/assets/131871197/b063a562-d6c2-41dd-b778-11f89db73d9a)

<h2>목차</h2>
<h3>  

[1. 프로젝트 소개](#1-프로젝트-소개)  

[2. 프로젝트 이용 방법](#2-프로젝트-이용-방법)

[3. 아키텍처](#3-아키텍처)

[4. ERD](#4-erd)  

[5. 주요기능](#5-주요기능)  

[6. 기술적 의사결정](#6-기술적-의사결정)  

[7. 트러블 슈팅](#7-트러블-슈팅)  

[8. 사용자 피드백 및 추후 개발 계획](#8-사용자-피드백-및-추후-개발-계획)  

[9. 팀원 소개](#9-팀원-소개)</h3>


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

<h3>네이버 지도 API </h3>

![네이버 API](https://github.com/Travel-Shootings/Travel-Shooting/assets/131871197/f18dfe13-ec5b-454d-bc9a-405e405dd027)



<h3>S3 버킷을 활용한 이미지 저장 기능 </h3>  

  ![S3](https://github.com/Travel-Shootings/Travel-Shooting/assets/131871197/2d5aba3c-b487-4675-8955-dfcd265b3404)

  

<h3> 로그아웃 이후 AccessToken 관리 </h3>  

  
![AccessToken관리](https://github.com/Travel-Shootings/Travel-Shooting/assets/131871197/02625165-b249-4389-924e-a565656f46fd)


<h3> https 에서의 WebSocket 연결 오류 </h3>  

  ![websocket 연결 오류](https://github.com/Travel-Shootings/Travel-Shooting/assets/131871197/777e9418-0be8-440d-9360-9eccce1d8bb2)




## 8. 사용자 피드백 및 추후 개발 계획

## 9. 팀원 소개

![역할](https://github.com/Travel-Shootings/Travel-Shooting/assets/131871197/e88fdfc5-07d6-400b-8515-e31dfd3395b2)



