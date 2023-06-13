package com.ezen.g08;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ContentDto {

	@NotEmpty(message="Writer is Empty")
	@NotNull(message="Writer is Null")
	private String writer;
	
	@NotEmpty(message="Content is Empty")
	@NotNull(message="Content is Null")
	private String content;
	
}
