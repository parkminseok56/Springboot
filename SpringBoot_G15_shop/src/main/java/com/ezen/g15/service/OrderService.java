package com.ezen.g15.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezen.g15.dao.ICartDao;
import com.ezen.g15.dao.IOrderDao;
import com.ezen.g15.dto.CartVO;
import com.ezen.g15.dto.OrderVO;

@Service
public class OrderService {

	@Autowired
	IOrderDao odao;

	@Autowired
	ICartDao cdao;
	
	@Transactional
	public HashMap<String, Object> insertOrder(String id) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		int oseq = 0;
		// 여러 데이터 베이스 작업
		// 1. Orders 테이블에 현재 아이디로 레코드를 추가
		odao.insertOrders(id);
		// 2. 방금 추가된 Orders 테이블의 레코드의 주문번호 ( oseq) 를 조회함 ( 가장 큰 주문 번호)
		oseq = odao.LookupMaxOseq();
		// 3. 그 주문번호와 함께 카트리스트에 있는 상품들을 하나 하나 order_detail 테이블에 추가함.
		//    -CartList를 아이디로 한번 더 조회함.
		// 4. 카트테이블에서 방금 주문한 상품을 삭제함.
		List<CartVO> list = cdao.getCartList(id);
		for (CartVO cvo : list) {
			odao.insertOrderDetail( cvo , oseq );
			odao.deleteCart( cvo.getCseq());
			
		}			
		// 5. 주문리스트와 주문번호를 해시맵에 담음.
		result.put("oseq", oseq);		
		return result;
	}

	public HashMap<String, Object> listOrderByOseq(int oseq) {
		HashMap<String, Object> result = new HashMap<String, Object> ();
		List<OrderVO> list = odao.listOrderByOseq(oseq);
		result.put("orderList", list);
		int totalPrice = 0;
		for( OrderVO ovo : list) {
			totalPrice += ovo.getPrice2() * ovo.getQuantity();
		}
		result.put("totalPrice",totalPrice);
		return result;
	}

	public int insertOrderOne(int pseq, int quantity, String id) {
		HashMap<String, Object> result = new HashMap<String, Object> ();
		int oseq = 0;	
		odao.insertOrders(id);		
		oseq = odao.LookupMaxOseq();		
		odao.insertOrderDetailOne( pseq, quantity, oseq);	
		return oseq;
		}

	public ArrayList<OrderVO> getFinalListIng(String id) {
		
		//최종 리턴될 오더 리스트
		ArrayList<OrderVO>orderList = new ArrayList<OrderVO>();
		
		// 1. id로 진행 중인 주문의 주문번호들을 조회함.
		List<Integer> oseqList = odao.selectSeqOrderIng(id);
		// 2. 주문번호들로 반복실행을 실행함.
		for( int oseq : oseqList) {
			
			// 3. 하나의 주문번호로 주문내역을 조회함.
		    List<OrderVO> orderListIng = odao.listOrderByOseq(oseq);
		    
		    // 4. orderListIng 에서 첫 번째 주문을 따로 저장함.
		    OrderVO ovo = orderListIng.get(0);
		    
		    // 5. orderListIng의 주문들의 요약정보를 ovo에 저장함.
		    ovo.setPname(ovo.getPname() + " 포함 " + orderListIng.size() + " 건");
		    int totalPrice = 0;
		    for (OrderVO ovo1 : orderListIng)
		    	totalPrice += ovo1.getPrice2() * ovo1.getQuantity();
		    ovo.setPrice2(totalPrice);
		    
		    // 6.리턴될 리스트에 ovo를 add함.
		    orderList.add(ovo);
		}
		return orderList;
	}			
	
	
		
	
}
