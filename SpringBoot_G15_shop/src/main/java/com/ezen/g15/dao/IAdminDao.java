package com.ezen.g15.dao;

import java.util.List;

import javax.validation.Valid;

import org.apache.ibatis.annotations.Mapper;

import com.ezen.g15.dto.Paging;
import com.ezen.g15.dto.ProductVO;

@Mapper
public interface IAdminDao {

	String getPwd(String workId);
	int getAllCount(String string, String string2, String key);
	List<ProductVO> listProduct(Paging paging, String key);
	void insertProduct(ProductVO productvo);
	void updateProduct(ProductVO productvo);


	
}
