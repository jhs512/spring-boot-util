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
  `email` CHAR(100) DEFAULT "" NOT NULL,
  `cellphoneNo` CHAR(20) DEFAULT "" NOT NULL,
  loginProviderTypeCode CHAR(30) NOT NULL COMMENT 'common=일반가입,kakaoRest=카카오REST가입',
  `authLevel` SMALLINT(2) UNSIGNED DEFAULT 3 NOT NULL COMMENT '3=일반,7=관리자'
);

ALTER TABLE `member` ADD COLUMN `onLoginProviderMemberId` CHAR(50) NOT NULL COMMENT '로그인 프로바이더에서의 회원 번호' AFTER `loginProviderTypeCode`; 
ALTER TABLE `member` ADD INDEX (`loginProviderTypeCode`, `onLoginProviderMemberId`);

# 카테고리관련 시작
CREATE TABLE cateItem (
  id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  regDate DATETIME NOT NULL,
  updateDate DATETIME NOT NULL,
  typeCode CHAR(20) NULL, # EX : 대분류
  type2Code CHAR(20) NULL, # EX : 중분류
  type3Code CHAR(20) NULL, # EX : 소분류
  type4Code CHAR(20) NULL, # EX : NULL
  typeCodeValue CHAR(20) NULL, # EX : 여성의류
  type2CodeValue CHAR(20) NULL, # EX : 상의
  type3CodeValue CHAR(20) NULL, # EX : 티셔츠
  type4CodeValue CHAR(20) NULL # EX : NULL
);
# 카테고리관련 끝

# 상품관련 시작
# 상품
CREATE TABLE product (
  id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  regDate DATETIME NOT NULL,
  updateDate DATETIME NOT NULL,
  `name` CHAR(100) NOT NULL,
  price INT(10) UNSIGNED DEFAULT 0 NOT NULL,
  salePrice INT(10) UNSIGNED DEFAULT 0 NOT NULL
);

CREATE TABLE productCateItem (
  id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  regDate DATETIME NOT NULL,
  updateDate DATETIME NOT NULL,
  productId INT(10) UNSIGNED NOT NULL,
  cateItemId INT(10) UNSIGNED NOT NULL
);

# 상품옵션
CREATE TABLE productOpt (
  id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  regDate DATETIME NOT NULL,
  updateDate DATETIME NOT NULL,
  productId INT(10) UNSIGNED NOT NULL,
  typeCode CHAR(20) NULL, # EX : 색상
  type2Code CHAR(20) NULL, # EX : 크기
  type3Code CHAR(20) NULL, # EX : ''
  typeCodeValue CHAR(20) NULL, # EX : RED
  type2CodeValue CHAR(20) NULL, # EX : 100
  type3CodeValue CHAR(20) NULL, # EX : ''
  quantity INT(10) DEFAULT 0 NOT NULL, # 현재재고
  notiQuantity INT(10) DEFAULT 0 NOT NULL, # 알림재고
  addiPrice INT(10) DEFAULT 0 NOT NULL # 추가가격
);
# 상품관련 끝

# 장바구니관련 시작
# 장바구니 아이템
CREATE TABLE cartItem (
  id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  regDate DATETIME NOT NULL,
  updateDate DATETIME NOT NULL,
  productId INT(10) UNSIGNED NOT NULL,
  productOpt INT(10) UNSIGNED NOT NULL,
  quantity INT(10) DEFAULT 0 NOT NULL,
  memberId INT(10) NOT NULL
);
# 장바구니관련 끝

# 결제관련 시작
# 결제
# 추가할일 : 결제방법, 정산여부, 취소여부
CREATE TABLE payment (
  id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  regDate DATETIME NOT NULL,
  updateDate DATETIME NOT NULL,
  relTypeCode CHAR(30) NOT NULL,
  memberId INT(10) NOT NULL,
  relId INT(10) DEFAULT 0 NOT NULL
);
# 결제관련 끝

# 주문관련 시작
# 주문
CREATE TABLE zenOrder (
  id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  regDate DATETIME NOT NULL,
  updateDate DATETIME NOT NULL,
  payStatus TINYINT(1) UNSIGNED NOT NULL,
  payDate DATETIME,
  cancelStatus TINYINT(1) UNSIGNED NOT NULL,
  cancelDate DATETIME,
  sendStatus TINYINT(1) UNSIGNED NOT NULL,
  sendDate DATETIME,
  receiveStatus TINYINT(1) UNSIGNED NOT NULL,
  receiveDate DATETIME,
  refundStatus TINYINT(1) UNSIGNED NOT NULL,
  refundDate DATETIME,
  sellerMemberId INT(10) UNSIGNED NOT NULL,
  sellerPlacePersonId INT(10) UNSIGNED NOT NULL,
  buyerMemberId INT(10) UNSIGNED NOT NULL,
  buyerPlacePersonId INT(10) UNSIGNED NOT NULL,
  receiverPlacePersonId INT(10) UNSIGNED NOT NULL,
  initPrice INT(10) UNSIGNED DEFAULT 0 NOT NULL,
  initSalePrice INT(10) UNSIGNED DEFAULT 0 NOT NULL,
  price INT(10) UNSIGNED DEFAULT 0 NOT NULL,
  salePrice INT(10) UNSIGNED DEFAULT 0 NOT NULL,
  etc VARCHAR(100) NOT NULL,
  etc2 VARCHAR(100) NOT NULL
);

# 주문아이템
CREATE TABLE zenOrderItem (
  id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  regDate DATETIME NOT NULL,
  updateDate DATETIME NOT NULL,
  zenOrderId INT(10) UNSIGNED NOT NULL,
  productId INT(10) UNSIGNED NOT NULL,
  productOpt INT(10) UNSIGNED NOT NULL,
  payStatus TINYINT(1) UNSIGNED NOT NULL,
  payDate DATETIME,
  cancelStatus TINYINT(1) UNSIGNED NOT NULL,
  cancelDate DATETIME,
  sendStatus TINYINT(1) UNSIGNED NOT NULL,
  sendDate DATETIME,
  receiveStatus TINYINT(1) UNSIGNED NOT NULL,
  receiveDate DATETIME,
  refundStatus TINYINT(1) UNSIGNED NOT NULL,
  refundDate DATETIME,
  initQuantity INT(10) DEFAULT 0 NOT NULL,
  quantity INT(10) DEFAULT 0 NOT NULL,
  refundQuantity INT(10) DEFAULT 0 NOT NULL,
  cancelQuantity INT(10) DEFAULT 0 NOT NULL,
  initPrice INT(10) UNSIGNED DEFAULT 0 NOT NULL,
  price INT(10) UNSIGNED DEFAULT 0 NOT NULL,
  intSalePrice INT(10) UNSIGNED DEFAULT 0 NOT NULL,
  salePrice INT(10) UNSIGNED DEFAULT 0 NOT NULL,
  buyerMemberId INT(10) UNSIGNED NOT NULL
);

# 구매취소
CREATE TABLE canceledItem (
  id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  regDate DATETIME NOT NULL,
  updateDate DATETIME NOT NULL,
  relTypeCode CHAR(30) NOT NULL, # zenOrder
  relId INT(10) UNSIGNED NOT NULL, # 1
  typeCode CHAR(30) NOT NULL,
  type2Code CHAR(30) NOT NULL,
  beforePayStatus TINYINT(1) UNSIGNED NOT NULL,
  refundPrice TINYINT(1) UNSIGNED NOT NULL,
  cancelQuantity TINYINT(1) UNSIGNED NOT NULL,
  completeStatus TINYINT(1) UNSIGNED NOT NULL,
  completeDate DATETIME,
  cancelStatus TINYINT(1) UNSIGNED NOT NULL,
  cancelDate DATETIME,
  senderPlacePersonId INT(10) UNSIGNED NOT NULL,
  receiverPlacePersonId INT(10) UNSIGNED NOT NULL,
  etc VARCHAR(100) NOT NULL,
  etc2 VARCHAR(100) NOT NULL
);
# 주문관련 끝

# 배송관련 시작
# 배송
CREATE TABLE delivery (
  id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  regDate DATETIME NOT NULL,
  updateDate DATETIME NOT NULL,
  relTypeCode CHAR(30) NOT NULL, # zenOrder
  relId INT(10) UNSIGNED NOT NULL, # 1
  senderPlacePersonId INT(10) UNSIGNED NOT NULL,
  buyerPlacePersonId INT(10) UNSIGNED NOT NULL,
  receiverPlacePersonId INT(10) UNSIGNED NOT NULL,
  noticeStatus TINYINT(1) UNSIGNED NOT NULL,
  noticeDate DATETIME,
  startStatus TINYINT(1) UNSIGNED NOT NULL,
  startDate DATETIME,
  sendStatus TINYINT(1) UNSIGNED NOT NULL,
  sendDate DATETIME,
  receiveStatus TINYINT(1) UNSIGNED NOT NULL,
  receiveDate DATETIME,
  cancelStatus TINYINT(1) UNSIGNED NOT NULL,
  cancelDate DATETIME,
  deliveryPrice INT(10) UNSIGNED NOT NULL,
  prepaymentStatus TINYINT(1) UNSIGNED NOT NULL,
  agencyTypeCode CHAR(20) NOT NULL,
  invoiceNo CHAR(50) NOT NULL,
  etc VARCHAR(100) NOT NULL,
  etc2 VARCHAR(100) NOT NULL,
  etc3 VARCHAR(100) NOT NULL
);

# 배송 아이템
CREATE TABLE deliveryItem (
  id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  regDate DATETIME NOT NULL,
  updateDate DATETIME NOT NULL,
  deliveryId INT(10) UNSIGNED NOT NULL,
  relTypeCode CHAR(30) NOT NULL, # zenOrderItem
  relId INT(10) UNSIGNED NOT NULL # 1
);
# 배송관련 끝

# PP 관련 시작
# PP
CREATE TABLE placePerson (
  id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `name` CHAR(50) NOT NULL,
  `personName` CHAR(50) NOT NULL,
  regDate DATETIME NOT NULL,
  zipCode CHAR(100) NOT NULL,
  address CHAR(100) NOT NULL,
  addressDetail CHAR(100) NOT NULL,
  email CHAR(100) NOT NULL,
  cellphoneNo CHAR(100) NOT NULL,
  etc VARCHAR(100) NOT NULL,
  etc2 VARCHAR(100) NOT NULL
);
# PP 관련 끝



