package com.ezen.g17.dao;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IOrderDao {

	void insertOrder(HashMap<String, Object> paramMap);
	void listOrderByOseq(HashMap<String, Object> paramMap);
	void insertOrderOne(HashMap<String, Object> paramMap);
	void listOrderByIdIng(HashMap<String, Object> paramMap);
	void listOrderByIdAll(HashMap<String, Object> paramMap);


	

}
