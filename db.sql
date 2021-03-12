# 데이터베이스 생성
DROP DATABASE IF EXISTS util;
CREATE DATABASE util;
USE util;

# 회원 테이블 생성
CREATE TABLE `member` (
  id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  regDate DATETIME NOT NULL,
  updateDate DATETIME NOT NULL,
  loginId CHAR(30) NOT NULL,
  loginPw VARCHAR(100) NOT NULL,
  authKey CHAR(80) NOT NULL,
  `name` CHAR(30) NOT NULL,
  `nickname` CHAR(30) NOT NULL,
  `email` CHAR(100) NOT NULL,
  `cellphoneNo` CHAR(20) NOT NULL,
  loginProviderTypeCode CHAR(30) NOT NULL COMMENT 'common=일반가입,kakaoRest=카카오REST가입',
  `authLevel` SMALLINT(2) UNSIGNED DEFAULT 3 NOT NULL COMMENT '3=일반,7=관리자'
);
