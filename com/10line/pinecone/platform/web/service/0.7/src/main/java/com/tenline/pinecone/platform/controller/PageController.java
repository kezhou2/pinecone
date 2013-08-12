package com.tenline.pinecone.platform.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tenline.pinecone.platform.Constant;
import com.tenline.pinecone.platform.model.Authority;
import com.tenline.pinecone.platform.model.Entity;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.sdk.RESTClient;

@Controller
public class PageController {
	
	private static RESTClient client;
	public RESTClient getRESTClient() {
		if (client == null) {
			client = new RESTClient(Constant.REST_URL);
		}
		return client;
	}

	
	@RequestMapping(value = "/login.html")
	public String login(HttpServletRequest request,HttpServletResponse response) {
		System.out.println("login.html");
		response.setContentType("text/html;charset=UTF-8");
		return "login";
	}
	
	@RequestMapping(value = "/console.html")
	public String console(HttpServletRequest request,HttpServletResponse response) {
		System.out.println("console.html");
		response.setContentType("text/html;charset=UTF-8");
		return "console";
	}
	
	@RequestMapping(value = "/index.html")
	public String index(HttpServletRequest request,HttpServletResponse response) {
		System.out.println("index.html");
		response.setContentType("text/html;charset=UTF-8");
		return "index";
	}
	
	@RequestMapping(value = "/signup.html")
	public String signup(HttpServletRequest request,HttpServletResponse response) {
		System.out.println("signup.html");
		response.setContentType("text/html;charset=UTF-8");
		return "signup";
	}
	
	@RequestMapping(value = "/registeruser.html")
	public String registerUser(HttpServletRequest request,	HttpServletResponse response) {
		System.out.println("registeruser.html");
		response.setContentType("text/html;charset=UTF-8");
		String username = (String)request.getParameter("username");
		String password = (String)request.getParameter("password");
		String email = (String)request.getParameter("email");
		System.out.println("username:"+username+"\npassword:"+password+"\nemail:"+email);
		
		try {
			if(username == null || password == null){
				request.setAttribute("isregister", "false");
				return "signup";
			}
			
			ArrayList<Entity> users = (ArrayList<Entity>) this.getRESTClient().get(
					"/user/search/names?name=" + username, Constant.getAdminId(), Constant.getAdminPassword());
			if (users.size() == 0) {
				User u = new User();
				u.setName(username);
				u.setPassword(password);
				u.setEmail(email);
				String id =  this.getRESTClient().post("/user", u);
				System.out.println("post user:"+id);
				u.setId(Long.getLong(id));

				
				Authority authority = new Authority();
				authority.setAuthority("ROLE_USER");
				authority.setUserName(u.getName());
				authority.setId(Long.getLong( this.getRESTClient().post("/authority", authority)));
				
				String auId = client.post("/authority/" + authority.getId() + "/user", "/user/" + u.getId());
				System.out.println("post auth:"+auId);
				request.setAttribute("isregister", "true");
				return "login";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "signup";
	}
}
