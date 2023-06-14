package com.ezen.g10.dao;

import java.util.List;

import com.ezen.g10.dto.BbsDto;

public interface IBbsDao {
   
	public List <BbsDto> getList();  // 게시글 전체조회 - 매개변수가 없고, 리턴값은 List <BbsDto>형
	public int write (BbsDto bdto);  // 게시글 쓰기 - 매개변수가 BbsDto, 리턴값은 int형
	public int update(BbsDto bdto);  // 게시글 수정 - 매개변수가 BbsDto, 리턴값은 int형
	public int delete(int id);       // 게시글 삭제 - 매개변수가 int형, 리턴값은 int형
	public BbsDto view(int id);      // 게시글 하나보기 - 매개변수가 없고, 리턴값은 BbsDto
}  
