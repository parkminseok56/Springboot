package com.ezen.g13.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.g13.dao.ITransactionDao1;
import com.ezen.g13.dao.ITransactionDao2;

@Service
public class MyService {
    
	@Autowired
	ITransactionDao1 td1;
	
	@Autowired
	ITransactionDao2 td2;
	
	public int buy(String id, int amount, int error) {
		// transaction1 테이블과  transaction2 테이블에 레코드를 insert 하는 메서드를 각각의 dao에서 따로 호출함.		
		try {   // 에러 발생 시 프로그램 종류가 아닌 다음 구문으로 넘어가기 위해 사용
			
			td1.buy( id, amount);
					
		if( error == 0) {
			int n = 10/0;			
		   }
		
	    	td2.buy( id, amount);
	    	
		}catch( Exception e) {
			System.out.println("예외 발생");
			return 0;
		}			
		return 1;
	}

}
