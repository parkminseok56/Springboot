package com.ezen.G17.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ezen.G17.service.OrderService;

@Controller
public class OrderController {

	@Autowired
	OrderService os;

	
}
