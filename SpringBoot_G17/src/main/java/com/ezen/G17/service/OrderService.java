package com.ezen.G17.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.ezen.G17.dao.IOrderDao;

public class OrderService {
  
	@Autowired
	IOrderDao odao;
	
}
