package com.ezen.g14.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ezen.g14.dto.MemberVO;
import com.ezen.g14.service.MemberService;

@Controller
public class MemberController {
     
	@Autowired
	 MemberService ms;
	
	
	@RequestMapping("/")
	public String root() {
		return "member/loginForm";
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login( @ModelAttribute("dto") @Valid MemberVO membervo,
			              BindingResult result,
			              HttpServletRequest request,
			              Model model) {
		
		String url = "member/loginForm";
		
		if(result.getFieldError("userid") != null) {
			model.addAttribute("message", result.getFieldError("userid").getDefaultMessage());			
		}else if (result.getFieldError("pwd") != null) {
			model.addAttribute("message", result.getFieldError("pwd").getDefaultMessage());						
	    }else {
		   // 아이디 비번이 정상 입력된 경우
	    	MemberVO mvo = ms.getMember( membervo.getUserid());
	    	if ( mvo == null) model.addAttribute("message","아이디가 없으므니다");
	    		
	    	else if ( mvo.getPwd() == null)	model.addAttribute("message","DB 오류. 관리자에게 문의하시므니다");
	    		
	    	else if ( !mvo.getPwd().equals(membervo.getPwd()))
	    		model.addAttribute("message","비밀번호가 맞지않스므니다");
	    		
	        else if ( mvo.getPwd().equals( membervo.getPwd())) {
	        	HttpSession session = request.getSession();
	        	session.setAttribute("loginUser", mvo);
	        	url  = "redirect:/main";
	    	   
	       }
	    }
		return url;
		
	}
}
