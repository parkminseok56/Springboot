package com.ezen.g15.dto;

import java.sql.Timestamp;

import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class MemberVO {
	
	@NotEmpty(message ="id를 입력하시오")	
	private String id;
	@NotEmpty(message ="비밀번호를 입력하시오")	
	private String pwd;
	@NotEmpty(message ="이름을 입력하시오")	
	private String name;			
    @NotEmpty(message ="이메일을 입력하시오") 
	private String  email;  
	private String  Zip_num;
	private String  address1;
	private String  address2;
	private String  address3;
	private String  phone;
	private String  useyn;
	private Timestamp indate;
	private String  provider;
}

