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
     temp_cur SYS_REFCURSOR;   -- �Խù� ��ȣ�� ��ȸ�� ����� ���� Ŀ������
     v_num NUMBER;     -- �׵��� �Խù� ��ȣ���� �����ư��鼭 ������ ����
     v_rownum NUMBER;  -- �׵��� �� ��ȣ���� �����ư��鼭 ������ ����
     v_cnt NUMBER;    -- �� �Խù� ��ȣ�� ��ȸ�� ��� ������ ������ ����
     
BEGIN
      -- board ���̺��� startNum�� endNum ������ �Խù��� ��ȸ�ϵ�, �Խù� ��ȣ(num) ���� ����( ROWNUM �� ����)
      -- num������ reply ���̺���  boardnum�� num�� ���ڵ尡 �� ������ ������ ����.
      -- num ���� ��� ������ �̿��ؼ� board ���̺��� replycnt �ʵ带 update��.
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
           
           -- ��� ������ ä���� ��� �Խù��� ��ȸ�ؼ� p_cursor�� ����.
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








