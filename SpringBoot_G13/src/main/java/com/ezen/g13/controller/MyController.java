package com.ezen.g13.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezen.g13.service.MyService;

@Controller
public class MyController {

	@Autowired
	MyService ms;
	
	
	 @RequestMapping("/")
	 public String root() {
		 return "buy_ticket";
	 }
	 
	 @RequestMapping("/buyTicketCard")
	 public String buyTicketCard(
			 @RequestParam("id") String id,
			 @RequestParam("amount") int amount,
			 @RequestParam("error") int error, Model model) {
		
		 int result = ms.buy(id, amount, error);
		 // 사용자 동작은 구매 버튼 한 번 클릭이고, 데이터 베이스 작업은 transaction1 테이블과 transaction2 테이블에
		 // 레코드를 추가함. controller에서는 service의 메서드를 한 번 호출하고
		 // service에서 dao 의 메서드 두 개를 각 각 따로 호출하여 두 개의 테이블에 insert 함.
		 
		 // 구매 작업 성공 여부를 result 에 리턴 받아서 성공이면  buy_ticket_end.jsp로
		 // 실패이면,   buy_ticket_error.jsp로 이동함.
		 
		 model.addAttribute("id",id);
		 model.addAttribute("amount",amount);
		 model.addAttribute("error",error);
		 
		 if(result == 1) return "buy_ticket_end";  // 구매 성공 페이지로 이동
		 else return "buy_ticket_error";  // 구매 실패 페이지로 이동
	 }
	 
}
