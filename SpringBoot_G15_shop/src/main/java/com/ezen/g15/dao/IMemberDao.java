package com.ezen.g15.dao;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.ezen.g15.dto.MemberVO;

@Mapper
public interface IMemberDao {

	

	void joinKakao(MemberVO mvo);

	void insertMember(MemberVO membervo);

	void updateMember(MemberVO membervo);

	void withDrawalMember(String id);

	MemberVO getMember(String id);



	
}
