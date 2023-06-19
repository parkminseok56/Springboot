package com.ezen.g14.dto;

import java.sql.Timestamp;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ReplyVO {
  
	  private int replynum;  
	  private int boardnum; 
	  private String userid; 
	  private Timestamp  writedate; 
	  @NotEmpty(message="내용을 입력하시므니다")
	  @NotNull(message="내용을 입력하시므니다")	  
	  private String  content; 
	
	
	
	
}
