package com.ezen.g08;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ContentDto {
   
	@Size(min=4, max=20,message="Writer min 4, max 20")
	@NotEmpty(message="Writer is Empty")
	@NotNull(message="Writer is Null")
	private String writer;
	
	@NotEmpty(message="Content is Empty")
	@NotNull(message="Content is Null")
	private String content;
	
	
	// @NotEmpty 또는 @NotNull 같은 벨리데이션 어노테이션은 반드시 검사하고자 하는 멤버변수에만 붙여서 사용함.
	// 필요없는 곳에 붙여서 사용하게 되면, 에러가 있는지 검사하지 않아도 되는 곳에서 생각치 못한 에러가 발생하여 곤란함.
	private String name;
	
}
