package com.ezen.g17.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ezen.g17.service.AdminService;

@Controller
public class AdminController {

	@Autowired
	AdminService as;
	
	
	@RequestMapping("/admin")
	public String admin() {
		return "admin/adminLoginForm";
	}
	
	
	@RequestMapping("adminLogin")
	public ModelAndView adminLogin(HttpServletRequest request, Model model,
						@RequestParam(value="workId", required=false) String workId,
						@RequestParam(value="workPwd", required=false) String workPwd) {
		   HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("wordkId", wordkId);
		paramMap.put("ref_cursor", null);
	    as.getAdmin(paramMap); // 아이디로 관리자 조회
		ArrayList<E> <HashMap<String, Object>> list
		= (ArrayList <HashMap<String, Object>> ) paramMap.get("ref_cursor");
		String url ="admin/adminLoginForm";
		if( list.size() == 0 ) {
			model.addAttribute("msg" , "아이디가 없어여");
			return "admin/adminLoginForm";
		}
		 HashMap<String, Object> resultMap = list.get(0);
		}if( resultMap.get("PWD") == null) {
			model.addAttribute("msg" , "관리자에게 문의하세여");
		}else if( workPwd.equals( (String) resultMap.get"")) {
			mav.addObject("msg" , "패쓰워드를 입력하세요");
			mav.setViewName("admin/adminLoginForm");
			return mav;
		}
		
		int result = as.workerCheck( workId, workPwd );
		
		if(result == 1) {
	    		HttpSession session = request.getSession();
	    		session.setAttribute("workId", workId);
	    		mav.setViewName("redirect:/productList");
		} else if (result == 0) {
	        	mav.addObject("message", "비밀번호를 확인하세요.");
	        	mav.setViewName("admin/adminLoginForm");
		} else if (result == -1) {
	    		mav.addObject("message", "아이디를 확인하세요.");
	    		mav.setViewName("admin/adminLoginForm");
		}	
		
		return mav;
	}
	
	
	
}




















