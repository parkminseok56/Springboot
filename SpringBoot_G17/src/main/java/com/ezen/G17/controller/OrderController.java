package com.ezen.g17.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ezen.g15.dto.MemberVO;
import com.ezen.g15.dto.OrderVO;
import com.ezen.g17.service.OrderService;

@Controller
public class OrderController {

	@Autowired
	OrderService os;
	
	@RequestMapping("/orderInsert")
	public String orderInsert(HttpServletRequest request) {
		int oseq = 0;
		HttpSession session = request.getSession();		
		HashMap<String, Object> loginUser
         = (HashMap<String, Object> )session.getAttribute("loginUser");		
		if (loginUser == null) {
			return "member/login";
		} else {
			// 카트에서 장바구니 목록 조회
			
			
			HashMap<String, Object> paramMap =  new	HashMap<String, Object>();
			paramMap.put("id",loginUser.get("ID"));
			paramMap.put("oseq", 0 );
			os.insertOrder(paramMap);
			oseq = Integer.parseInt( paramMap.get("oseq").toString)) ;
			// 주문 상품 리스트
		}
		return "redirect:/orderList?oseq=" + oseq;
	}
	
	
	@RequestMapping("/orderList")
	public ModelAndView orderList(
			@RequestParam("oseq") int oseq, 
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		HashMap<String, Object> loginUser 
		=  	(HashMap<String, Object>)session.getAttribute("loginUser");
		if (loginUser == null) {
			mav.setViewName("member/login");
		} else {
			HashMap<String, Object> paramMap = new	HashMap<String, Object>();
			paramMap.put("oseq",oseq);
			paramMap.put("ref_cursor", null );
			os.listOrdeByOseq(paramMap);
			ArrayList<HashMap<String, Object>> list
			= (ArrayList <HashMap<String, Object>>) paramMap.get("ref_cursor");			
			mav.addObject("orderList",(List<OrderVO>) result.get("orderList"));
			mav.addObject("totalPrice",(Integer)result.get("totalPrice"));
			mav.setViewName("mypage/orderList");
		}
		return mav;
	}

	
	
}






















