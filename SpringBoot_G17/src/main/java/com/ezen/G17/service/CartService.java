package com.ezen.G17.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.ezen.G17.dao.ICartDao;

public class CartService {

	@Autowired
	ICartDao cdao;
}
