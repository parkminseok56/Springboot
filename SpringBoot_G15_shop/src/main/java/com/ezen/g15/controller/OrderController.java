package com.ezen.g15.controller;

import java.util.ArrayList;
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
import com.ezen.g15.service.OrderService;

@Controller
public class OrderController {

	@Autowired
	OrderService os;

	@RequestMapping("/orderInsert")
	public String orderInsert(HttpServletRequest request) {

		HttpSession session = request.getSession();
		MemberVO mvo = (MemberVO) session.getAttribute("loginUser");
		int oseq = 0;
		if (mvo == null) {
			return "member/login";
		} else if (mvo.getZip_num() == null || mvo.getAddress1() == null || mvo.getAddress2() == null) {
			return "redirect:/memberEditForm";
		} else {
			HashMap<String, Object> result = os.insertOrder(mvo.getId());
			// 총주문금액
			oseq = (Integer) result.get("oseq");
			// 주문 상품 리스트
		}
		return "redirect:/orderList?oseq=" + oseq;
	}

	@RequestMapping("/orderList")
	public ModelAndView orderList(@RequestParam("oseq") int oseq, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		MemberVO mvo = (MemberVO) session.getAttribute("loginUser");
		if (mvo == null) {
			mav.addObject("member/login");
		} else {
			HashMap<String, Object> result = os.listOrderByOseq(oseq);
			mav.addObject("orderList",(List<OrderVO>) result.get("orderList"));
			mav.addObject("totalPrice",(Integer)result.get("totalPrice"));
			mav.setViewName("mypage/orderList");
		}
		return mav;
	}

	
	
	@RequestMapping("/orderInsertOne") 
	public String orderInsertOne(
			@RequestParam("pseq") int pseq,
			@RequestParam("quantity") int quantity,
			HttpServletRequest request) {
		int oseq = 0;
		
		HttpSession session = request.getSession();
		MemberVO mvo = (MemberVO) session.getAttribute("loginUser");
		if (mvo == null) {
			return "member/login";
		} else {
			oseq = os.insertOrderOne ( pseq, quantity, mvo.getId());
		}
		return "redirect:/orderList?oseq=" + oseq;
	}
	
	
	@RequestMapping("myPage") // 진행 중인 주문 내역
	public ModelAndView mypage( HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		
		HttpSession session = request.getSession();
		MemberVO mvo = (MemberVO) session.getAttribute("loginUser");		
		if (mvo == null) {
		   mav.setViewName("member/login");
	} else {
		  ArrayList<OrderVO> finalList = os.getFinalListIng( mvo.getId());
			
		}
		mav.addObject("title", "진행중인 내역");
		mav.addObject("orderList", finalList);
		mav.setViewName("mypage/mypage");
			
       return mav;
}	
	
	
	
	
	
	
}
