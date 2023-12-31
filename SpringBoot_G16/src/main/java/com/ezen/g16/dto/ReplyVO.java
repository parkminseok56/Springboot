package com.ezen.g16.dto;

import java.sql.Timestamp;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ReplyVO {

	private int replynum;
	private int boardnum;
	private String userid;
	private Timestamp writedate;
	@NotEmpty(message="내용을 입력하세요")
	@NotNull(message="내용을 입력하세요")
	private String content;
	
}
