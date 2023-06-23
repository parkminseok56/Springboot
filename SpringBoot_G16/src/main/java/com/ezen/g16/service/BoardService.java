package com.ezen.g16.service;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.g16.dao.IBoardDao;
import com.ezen.g16.dto.Paging;

@Service
public class BoardService {

	@Autowired	
	IBoardDao bdao;
	
	public void selectBoard(HashMap<String, Object> paramMap) {
		
		HttpServletRequest request = (HttpServletRequest) paramMap.get("request");
		HttpSession session = request.getSession();
		
		int page =  1;
		if( request.getParameter("page")!=null) {
			page = Integer.parseInt( request.getParameter("page"));
			session.setAttribute("page",page);
		}else if( session.getAttribute("page")!=null) {
			page = (Integer)session.getAttribute("page");
		}else {
			session.removeAttribute("page");
		}	
		
		
		Paging paging = new Paging();
		paging.setPage(page);
		
		// getAllCOunt 메서드를 이용한 총 게시물 개수를 리턴
		// 필요한건 게시물 개수를 계산해서 나에게 담아 보내줄 OUT 변수가 필요함.
		paramMap.put("cnt", 0);
		bdao.getAllCount(paramMap);
		// getALlCount가 실행되고 나면 "cnt" 키 값에 해당하는 벨류가 총 게시물 개수가 됨.
		int count =(Integer)paramMap.get("cnt");
	}


	
}











