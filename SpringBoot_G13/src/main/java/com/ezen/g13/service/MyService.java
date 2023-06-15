package com.ezen.g13.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezen.g13.dao.ITransactionDao1;
import com.ezen.g13.dao.ITransactionDao2;

@Service
public class MyService {

	@Autowired
	ITransactionDao1 td1;

	@Autowired
	ITransactionDao2 td2;
  

	
	// @Transactional(rollbackFor) = {RuntimeException.class, Exception.class}}
	
	
	
	
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
	public int buy(String id, int amount, int error) {	   //	@Transactional   // 에러 페이지가 뜨지만,  Transactional에 의해 롤백문이 가동됨.
		td1.buy(id, amount);
		if (error == 0) {
			 int n = 10 / 0; 
			//throw new RuntimeErrorException(null);
		} 
		td2.buy(id, amount);	  
		return error;
	}
	
	
	
	
	/* 두 번째  방법 (구식 2)
	 * @Autowired TransactionTemplate tt;
	 * 
	 * public int buy(String id, int amount, int error) { try {
	 * 
	 * tt.execute(new TransactionCallbackWithoutResult() {
	 * 
	 * @Override protected void doInTransactionWithoutResult(TransactionStatus
	 * status) {
	 * 
	 * td1.buy(id, amount); if (error == 0) { int n = 10 / 0; } td2.buy(id, amount);
	 * System.out.println("Transaction Commit"); } }); } catch (Exception e) {
	 * System.out.println("Transaction RollBack"); return 0; } return 1; }
	 */

	/*
	 * 첫 번 째 방식 (구식)
	 * 
	 * @Autowired PlatformTransactionManager ptm; // 데이터베이스 작업 등의 트랜잭션을 시작하고 커밋 또는
	 * 롤백하는 역할의 인터페이스
	 * 
	 * @Autowired TransactionDefinition td; // 트랜잭션의 속성과 동작을 정의하는 인터페이스
	 * 
	 * 
	 * 
	 * public int buy(String id, int amount, int error) { // transaction1 테이블과
	 * transaction2 테이블에 레코드를 insert 하는 메서드를 각각의 dao에서 따로 호출함.
	 * 
	 * // 하나 이상의 데이터베이스 작업을 한 단위로 묶어서 하나의 실행 단위로 정의된 것을 트랜잭션이라고 함. // 트랜잭션 하나가 모두 다
	 * 실행이 되아 완료되면, commit이라는 명령으로 작업을 완료하고, // 중간에 에러가 발생하여 트랜잭션을 취소하고자 한다면,
	 * rollback 이라는 명령으로 취소함.
	 * 
	 * // 트랜잭션의 시작 TransactionStatus status = ptm.getTransaction(td); // 끝은 commit
	 * 또는 rollback
	 * 
	 * 
	 * try { // 에러 발생 시 프로그램 종류가 아닌 다음 구문으로 넘어가기 위해 사용
	 * 
	 * td1.buy( id, amount);
	 * 
	 * if( error == 0) { int n = 10/0; }
	 * 
	 * td2.buy( id, amount);
	 * 
	 * System.out.println("에러없이 둘 다 실행 되었스므니다."); ptm.commit(status); // 영역 안의 모든
	 * 데이터 베이스 작업의 실행 적용 - 트랜잭션의 끝
	 * 
	 * }catch( Exception e) {
	 * 
	 * System.out.println("중간에 에러나서 둘 다 실행이 안 되었스므니다."); ptm.rollback(status); // //
	 * 영역 안의 모든 데이터 베이스 작업의 취소
	 * 
	 * 
	 * return 0;
	 * 
	 * } return 1; }
	 */

}
