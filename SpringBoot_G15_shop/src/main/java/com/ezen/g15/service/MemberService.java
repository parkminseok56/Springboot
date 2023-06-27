package com.ezen.g15.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.g15.dao.IMemberDao;
import com.ezen.g15.dto.MemberVO;

@Service
public class MemberService {
 
	 @Autowired
	 IMemberDao mdao;

	
	public void joinKakao(MemberVO mvo) {
		mdao.joinKakao( mvo );
		
	}

	public void insertMember( MemberVO membervo) {
		mdao.insertMember(membervo);
	}

	public void updateMember( MemberVO membervo) {
		mdao.updateMember(membervo);
		
	}

	public void withdrawalMember(String id) {
		mdao.withDrawalMember(id);
		
	}

	public void getMember(String string) {
		mdao.getMember(string);
		
	}
}
