# Simple Notice Board

자바 스윙을 사용한 게시판 어플리케이션.

## Dev Environment

- Language : java 8
- DataBase : MySQL, ORACLE
  - interface 를 통한 Connector 구현으로 두가지 데이터 베이스 모두 사용 가능
- Framework : java swing, Spring Structure ( 스프링 구조 )
- Design Pattern : Singleton Pattern, MVC Pattern(Model)


## How to Use

- 로그인 또는 회원가입을 한다.
  - You can sign in or sign up.
- 글을 등록할 수 있다.
  - You can write your own post.
- 다른 사람의 글에 댓글을 달 수 있다.
  - You can write comment to other's post.
- 키워드를 사용한 검색이 가능하다.
  - You can search using keyword that you want to find.
- 자신의 글을 볼수 있다.
  - You can read your all post.


## Data Base

- 유저 테이블을 생성한다.
- create member table.

```sql
-- ORACLE
CREATE TABLE MEMBER (
  ID VARCHAR2(50)     PRIMARY KEY,
  PWD VARCHAR2(50)    NOT NULL,
  NAME VARCHAR2(50)   NOT NULL,
  EMAIL VARCHAR2(50)  UNIQUE,
  AUTH NUMBER(1)      NOT NULL
);
```

- 게시판 테이블을 생성한다.
- create bbs table.

```sql
-- ORACLE
CREATE TABLE BBS(
  SEQ NUMERIC(8)        PRIMARY KEY,
  ID VARCHAR(50)        NOT NULL,
  TITLE VARCHAR(200)    NOT NULL,
  CONTENT VARCHAR(4000) NOT NULL,
  WDATE DATE            NOT NULL,
  DEL NUMERIC(1)        NOT NULL,
  READCOUNT NUMERIC(8)  NOT NULL
);
CONSTRAINT FK_ID FOREIGN KEY(ID) REFERENCES MEMBER(ID);
CREATE SEQUENCE SEQ_BBS INCREMENT BY 1 START WITH 1;
```


## Introduce Program

- Delegator( Singleton Pattern ) has Controller. So Can access Controller everywhere.
- DBConnection interface has two child.
  - MySqlConnection : it make we can access local MySQL server
  - OracleConnection : it make we can access local ORACLE server
- Every User can sign in and sing up, and write something and write comment to not only other's post but also their own post.


## Todo

- Add comment controller and actions
