SELECT*FROM BANNER;
DELETE FROM BANNER WHERE BSEQ=25
update banner set image='w7.jpg' where bseq=1

DROP TABLE Member CASCADE CONSTRAINTS;

CREATE TABLE MEMBER
(
  id varchar2(20) NOT NULL,
  pwd varchar2(20),
  name varchar2(20),
  email varchar2(40) NOT NULL,
  zip_num varchar2(10),
  address1 varchar2(50),
  address2 varchar2(50),
  address3 varchar2(50),
  phone varchar2(20),
  indate date DEFAULT SYSDATE,
  useyn char(1) DEFAULT 'Y',
  provider varchar2(30),
  PRIMARY KEY(id)
);

insert into member(id,pwd,name,zip_num,address1,address2,address3,phone,email) values
('one','1111','김나리','133-110','서울시 성동구 성수동 1가', '1번지 21호','1번지 21호','010-777-7777', 'acc@abc.com');
insert into member(id,pwd,name,zip_num,address1,address2,address3,phone,email) values
('two','2222','김길동','130-120','서울시 송파구 잠실2동 ', '리샌츠 아파트 21호','1번지 21호','010-123-7545', 'acc@abc.com');


select* from banner;
select* from product;

insert into banner (bseq, subject, order_seq, useyn, image)
values (banner_seq.nextval, '테스트', 5, 'Y', 'main_img.jpg');

update product  set  IMAGE = 'ddabong.jpg' where

insert into banner (bseq, subject, order_seq, useyn, image)
values (banner_seq.nextval, '테스트', 5, 'Y', 'main_img.jpg');