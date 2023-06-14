package com.ezen.g11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezen.g11.dao.IUserDao;

@Controller
public class UserController {

	@Autowired
	IUserDao udao;
	
	@RequestMapping("/")
	public String root(Model model) {
		
		List<UserDto> list = udao.getList();
		
	}
}
