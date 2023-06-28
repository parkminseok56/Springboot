package com.ezen.g17.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ezen.g17.service.ProductService;

@Controller
public class ProductController {

	@Autowired
	ProductService ps;
	
	@RequestMapping("/")
	public String start() {
		return "start";
	}
	
	@RequestMapping("/webmain")
	public ModelAndView webmain() {
		ModelAndView mav = new ModelAndView();
		
		HashMap<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("ref_cursor1",null);
		paramMap.put("ref_cursor2",null);
		paramMap.put("ref_cursor3",null);
		
		ps.getBestNewBannerList(paramMap);
		
		 ArrayList<HashMap<String, Object>> list1
         = (ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor1");
		 ArrayList<HashMap<String, Object>> list2
         = (ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor2");
		 ArrayList<HashMap<String, Object>> list3
         = (ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor3");
		 
		 mav.addObject("newProductList",list1);
		 mav.addObject("bestProductList",list2);
		 mav.addObject("bannerList",list3);
		 mav.addObject("size",list3.size());

		
		mav.setViewName("desktopIndex");
		return mav;
	}
	
	
	//---------------------------------------------------------------------------------------------------------------
	
	@RequestMapping("/mobilemain")
	public ModelAndView mobilemain() {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("mobileIndex");
		return mav;
	} 
	
	
	  @RequestMapping(value="/catagory") 
	   public ModelAndView catagory(Model model,@RequestParam("kind") String kind) {		   
		   
			  

		   ModelAndView mav =new ModelAndView();
		   HashMap<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("kind",null);
			paramMap.put("ref_cursor",null);
		   ps.getKindList(paramMap);
		   ArrayList<HashMap<String, Object>> list
	         = (ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor");	
		   HashMap<String,Object> resultMap = list.get(0);
		   mav.addObject("productKindList", list);
		   mav.setViewName("product/productKind");
		   return mav;
		  }
	   
	   
		  @RequestMapping("productDetail") 
		   public ModelAndView product_detail(Model model,@RequestParam("pseq") int pseq) {   
			  ModelAndView mav =new ModelAndView();
			   HashMap<String,Object> paramMap = new HashMap<String,Object>();
				paramMap.put("pseq",pseq);
				paramMap.put("ref_cursor",null);
			   ps.getProduct(paramMap);
			   ArrayList<HashMap<String, Object>> list
		         = (ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor");			
			   HashMap<String,Object> resultMap = list.get(0);
			   mav.addObject("productVO", resultMap);
			   mav.setViewName("product/productDetail");
			   return mav;
			   
		   }
		   
	
}














