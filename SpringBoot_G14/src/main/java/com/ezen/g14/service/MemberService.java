package com.ezen.g14.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.g14.dao.IMemberDao;
import com.ezen.g14.dto.MemberVO;

@Service
public class MemberService {

	
	@Autowired
	IMemberDao mdao;

	public MemberVO getMember(String userid) {
		
		// MemberVO mvo = mdao.getMember( userid);
		// return mvo;
		return mdao.getMember( userid);
	}
	
	
}
