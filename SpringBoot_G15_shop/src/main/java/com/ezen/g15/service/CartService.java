package com.ezen.g15.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.g15.dao.ICartDao;
import com.ezen.g15.dto.CartVO;

@Service
public class CartService {

	@Autowired
	ICartDao cdao;

	public void insertCart(CartVO cvo) {
		cdao.insertCart(cvo);
		
	}

	public HashMap<String, Object> getCartList(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
