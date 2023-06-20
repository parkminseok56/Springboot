package com.ezen.g15.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ezen.g15.service.OrderService;

@Controller
public class OrderController {
 
	@Autowired
	OrderService os;
	
	@RequestMapping("/orderInsert")
	public ModelAndView orderInsert(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		
		HttpSession session = request.getSession();
		MemberVO mvo = (MemberVO)session.getAttribute("loginUser");
		int oseq = 0;
		if( mvo == null) {
			mav.setViewName("member/login");
		}else {
			
			HashMap<String, Object> result = os.insertOrder(mvo.getId());
			
			// 총 주문 금액
			mav.addObject("totalPrice", (Integer)result.get("totalPrice"));
			// 주문 상품 리스트		
			}
		
		return mav;
		
	}
	
}
