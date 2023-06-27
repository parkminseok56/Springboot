package com.ezen.G17.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ezen.G17.service.ProductService;

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
	   
	   
	   mav.setViewName("desktopIndex");
	   return mav;
   }
   
   
   
   // ------------------------------------------------------------------------------------------
   
   @RequestMapping("/mobilemain")
   public ModelAndView mobilemain() {
	   ModelAndView mav = new ModelAndView();
	   
	   
	   mav.setViewName("mobileIndex");
	   return mav;
   }
   
   
}