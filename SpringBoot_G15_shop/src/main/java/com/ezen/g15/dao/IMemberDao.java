package com.ezen.g15.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ezen.g15.dto.MemberVO;

@Mapper
public interface IMemberDao {

	MemberVO getMember(String id);

	
}
