package com.ezen.g15.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ezen.g15.dto.QnaVO;

@Mapper
public interface IQnaDao {

	List<QnaVO> listQna();

	Object getQna(int qseq);

}
