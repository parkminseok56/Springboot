package com.ezen.g17.dao;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface IMemberDao {

	void getMember(HashMap<String, Object> paramMap);


}
