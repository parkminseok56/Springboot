package com.ezen.g15.dto;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class QnaVO {

	private Integer qseq;
	@NotNull(message ="제목을 입력하시오") 	
	private String subject;
	@NotNull(message ="내용을 입력하시오") 
	private String content;
	private String reply;
	private String id;
	private String  rep;
	private Timestamp indate;
	private String passcheck;
	private String pass;
	
}
