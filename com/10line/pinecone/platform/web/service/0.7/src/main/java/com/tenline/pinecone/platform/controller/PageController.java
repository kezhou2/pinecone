package com.tenline.pinecone.platform.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

	
	@RequestMapping(value = "/login.html")
	public String login(HttpServletRequest request,HttpServletResponse response) {
		System.out.println("login.html");
		return "login";
	}
	
	@RequestMapping(value = "/console.html")
	public String console(HttpServletRequest request,HttpServletResponse response) {
		System.out.println("console.html");
		return "console";
	}
	
	@RequestMapping(value = "/index.html")
	public String index(HttpServletRequest request,HttpServletResponse response) {
		System.out.println("index.html");
		return "index";
	}
	
	@RequestMapping(value = "/signup.html")
	public String signup(HttpServletRequest request,HttpServletResponse response) {
		System.out.println("signup.html");
		return "signup";
	}
}
