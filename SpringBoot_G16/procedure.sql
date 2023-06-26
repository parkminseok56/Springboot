CREATE OR REPLACE PROCEDURE getMember(
       p_userid IN member.userid%TYPE,
       P_cursor OUT SYS_REFCURSOR 
)
IS 
BEGIN
      OPEN P_cursor FOR
          SELECT * FROM member WHERE userid=p_userid;

END;




CREATE OR REPLACE PROCEDURE getAllCount(
       p_cnt OUT NUMBER
       
)
IS

        v_cnt NUMBER;
BEGIN
         SELECT count(*)  INTO v_cnt FROM board;
         
         p_cnt := v_cnt;
        
         
END;



CREATE OR REPLACE PROCEDURE selectBoard(
       p_startNum IN NUMBER,
       p_endNum IN NUMER,
       p_cursor OUT SYS_REFCURSOR
)
IS
     temp_cur SYS_REFCURSOR;   -- 게시물 번호만 조회한 결과를 담을 커서변수
     v_num NUMBER;     -- 그들의 게시물 번호들을 번갈아가면서 저장할 변수
     v_rownum NUMBER;  -- 그들의 행 번호들을 번갈아가면서 저장할 변수
     v_cnt NUMBER;    -- 각 게시물 번호로 조회한 댓글 개수를 저장할 변수
     
BEGIN
      -- board 테이블에서 startNum과 endNum 사이의 게시물을 조회하되, 게시물 번호(num) 값만 취함( ROWNUM 도 같이)
      -- num값으로 reply 테이블에서  boardnum이 num인 레코드가 몇 개인지 개수를 구함.
      -- num 값과 댓글 개수를 이용해서 board 테이블의 replycnt 필드를 update함.
      OPEN temp_cur FOR
           SELECT * FROM (
                 SELECT *FROM (
                     SELECT rownum as rn,  b.num FROM (  (SELECT * FROM board ORDER BY num DESC) b )
                     ) WHERE rn>= v_startNum
                )WHERE rn<=v_endNum;
        LOOP
                 FETCH temp_cur INTO v_rownum, v_num;
                 EXIT WHEN temp_cur%NOTFOUND;
                 SELECT count(*) FROM reply WHERE boardnum=v_num;
                 UPDATE board SET replycnt = v_cnt WHERE num=v_num;
         END LOOP;        
         COMMIT;    
           
           -- 댓글 개수가 채워진 대상 게시물을 조회해서 p_cursor에 담음.
          OPEN p_cursor FOR
              SELECT * FROM (
                    SELECT *FROM (
                        SELECT rownum as rn,  b.num FROM (  (SELECT * FROM board ORDER BY num DESC) b )
                     ) WHERE rn>= p_startNum
                )WHERE rn<=p_endNum;  
         
       
              
END;

SELECT * FROM BOARD;
ALTER TABLE BOARD ADD REPLYCNT NUMBER(5);



CREATE OR REPLACE PROCEDURE joinKakao(
       p_userid member. userid%type,
       p_name member. name%type,
       p_email member.email%type,
       p_provider member.provider%type
)

IS

BEGIN
       INSERT INTO member ( userid,name,email,provider)
       VALUES(p_userid, p_name, p_email, p_provider);
       COMMIT;

END;


CREATE OR REPLACE PROCEDURE insertMember(
       p_userid member. userid%type,  
       p_name member. name%type,
       p_pwd member. pwd%type,
       p_email member.email%type,
       p_phone member.phone%type
)

IS

BEGIN
       INSERT INTO member ( userid,name,pwd,email,phone)
       VALUES(p_userid, p_name,p_pwd, p_email, p_phone);
       COMMIT;

END;


CREATE OR REPLACE PROCEDURE updatetMember(
       p_userid member. userid%type,
       p_pwd member. pwd%type,
       p_name member. name%type,
       p_email member.email%type,
       p_phone member.phone%type
)

IS

BEGIN
       UPDATE member SET  pwd=p_pwd, name=p_name, email=p_email, phone=p_phone
       WHERE userid=p_userid;
       COMMIT;

END;







create or replace procedure plusOneReadCount(
    p_num IN board.num%type
)
is
begin
    update board set readcount = readcount + 1 where num = p_num;
    commit;
end;


create or replace procedure getBoard(
    p_num board.num%type,
    p_cur1 out sys_refcursor,
    p_cur2 out sys_refcursor
)
is
begin
    open p_cur1 for
        select * from board where num=p_num;
    open p_cur2 for
        select * from reply where boardnum = p_num order by replynum desc;
end;




CREATE OR REPLACE PROCEDURE insertReply(
       p_userid In reply. userid%type,  
      p_boardnum In reply. boardnum%type,  
      p_content In reply. content%type
)

IS

BEGIN
       INSERT INTO Reply ( replynum, boardnum, userid,content)
       VALUES(reply_seq.nextVal, p_boardnum, p_userid, p_content);
       COMMIT;

END;




CREATE OR REPLACE PROCEDURE deleteReply(
      p_replynum in reply.replynum%type  
   
)

IS

BEGIN
      delete  from  reply where  replynum=p_replynum;
    
       COMMIT;

END;

CREATE OR REPLACE PROCEDURE insertBoard(
       p_userid In Board.USERID%type,  
       p_pass In Board.PASS%type,  
       p_emai In Board.EMAIL%type,  
       p_title In Board.TITLE%type,  
       p_content In Board.CONTENT%type,  
       p_imgfilename In Board.IMGFILENAME%type  
)
IS
BEGIN
       INSERT INTO board ( num,  pass,userid,email,title,content,imgfilename)
       VALUES(board_seq.nextVal,  p_pass,  p_userid,  p_email,  p_title,  p_content, p_imgfilename);
       COMMIT;
END;


CREATE OR REPLACE PROCEDURE updateBoard(
       p_num IN BOARD.num%TYPE,
       p_userid IN BOARD.userid%TYPE,
       p_pass IN BOARD.pass%TYPE,
       p_email IN BOARD.email%TYPE,
       p_title IN BOARD.title%TYPE,
       p_content IN BOARD.content%TYPE,
       p_imgfilename IN BOARD.imgfilename%TYPE
)
IS
BEGIN
       UPDATE board
       SET pass = p_pass,
           title = p_title,
           content = p_content,
           imgfilename = p_imgfilename,
           email = p_email,
           userid = p_userid
       WHERE num = p_num;
       COMMIT;
END;
