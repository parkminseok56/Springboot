package com.ezen.g17.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.g17.controller.ArrayList;
import com.ezen.g17.dao.ICartDao;

@Service
public class CartService {

	@Autowired
	ICartDao cdao;

	public void insertCart(HashMap<String, Object> paramMap) {
		cdao.insertCart(paramMap) ;		
		
	}

	public void listCart(HashMap<String, Object> paramMap) {
		cdao.listCart(paramMap) ;		
		ArrayList<HashMap<String, Object>> list
		= (ArrayList <HashMap<String, Object>>) paramMap.get("ref_cursor");
		int totalPrice = 0;
		for(  HashMap<String, Object> cart:list) {
			totalPrice +
		}
	}

	
}






