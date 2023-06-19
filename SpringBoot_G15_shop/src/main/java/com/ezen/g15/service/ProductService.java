package com.ezen.g15.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.g15.dao.IProductDao;

@Service
public class ProductService {

	@Autowired
	IProductDao pdao;
	
}
