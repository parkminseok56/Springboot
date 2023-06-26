package com.ezen.g16.service;

import java.util.HashMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.g16.dao.IMemberDao;
import com.ezen.g16.dto.MemberVO;

@Service
public class MemberService {
	
	 @Autowired
     IMemberDao mdao;

	public void getMember(HashMap<String, Object> paramMap) {
		 mdao.getMember(paramMap);
		
	}

	public void joinKakao(HashMap<String, Object> paramMap) {
		mdao.joinKakao(paramMap);
		
	}

	public void insertMember(HashMap<String, Object> paramMap) {
		mdao.insertMember(paramMap);		
	}

	
}






