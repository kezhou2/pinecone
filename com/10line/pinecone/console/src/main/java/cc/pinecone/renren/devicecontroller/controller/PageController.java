package cc.pinecone.renren.devicecontroller.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cc.pinecone.renren.devicecontroller.dao.PineconeApi;
import cc.pinecone.renren.devicecontroller.service.LoginUserDetailsImpl;
import cc.pinecone.renren.devicecontroller.servlet.DataTablesParamUtility;
import cc.pinecone.renren.devicecontroller.servlet.JQueryDataTableParamModel;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Entity;
import com.tenline.pinecone.platform.sdk.RESTClient;

/**
 * Sample controller for going to the home page with a message
 */
@Controller
public class PageController {

	@Autowired
	private MessageSource msgSrc;
	
	private static PineconeApi pApi;
	
	private static RESTClient client;
	public RESTClient getRESTClient() {
		if(client == null){
			client = new RESTClient(AppConfig.REST_URL);
		}
		return client;
	}
	
	public PineconeApi getPineconeAPI(){
		if(pApi == null){
			pApi = new PineconeApi();
		}
		return pApi;
	}

	private static final Logger logger = LoggerFactory
			.getLogger(PageController.class);

	@RequestMapping(value = "/login.html")
	public String login(HttpServletRequest request,HttpServletResponse response) {
		logger.info("login.html");
		System.out.println("login.html");
		return "login";
	}
	
	@RequestMapping(value = "/register.html")
	public String register(Model model) {
		logger.info("register.html");
		System.out.println("register.html");
		return "register";
	}
	
	@RequestMapping(value = "/index.html")
	public String index(HttpServletRequest request,HttpServletResponse response) {
		logger.info("index.html");
		System.out.println("index.html");
		
		//base process flow
		SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");  
		String username = securityContextImpl.getAuthentication().getName();
		
		request.getSession().setAttribute("username", username);
		response.setCharacterEncoding("UTF-8");
		return "index";
	}

	@RequestMapping(value = "/devices.html")
	public String devices(HttpServletRequest request,HttpServletResponse response) {
		logger.info("devices.html");
		System.out.println("devices.html");
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		System.out.println("username:"+username+"\npassword:"+password);
		return "devices";
	}
	
	@RequestMapping(value = "/variable.html", method = RequestMethod.GET)
	public String variable(HttpServletRequest request,HttpServletResponse response) {
		logger.info("variable.html");
		System.out.println("variable.html");
		String id = request.getParameter("id");
		
		SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");  
		String username = securityContextImpl.getAuthentication().getName();
		String password = securityContextImpl.getAuthentication().getCredentials().toString();
		
		try{
			ArrayList<Entity> devs = (ArrayList<Entity>) this.getRESTClient().get("/device/"+id,username,password);
			Device dev = (Device) devs.get(0);
			request.setAttribute("device", dev);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		request.setAttribute("querydeviceid", id);
		
		return "variable";
	} 

	@RequestMapping(value = "/setting.html")
	public String registry(HttpServletRequest request,HttpServletResponse response) {
		logger.info("setting.html");
		System.out.println("setting.html");
		response.setCharacterEncoding("UTF-8");
		return "setting";
	}

	@RequestMapping(value = "/friends.html")
	public String friends(HttpServletRequest request,HttpServletResponse response) {
		logger.info("friends.html");
		System.out.println("friends.html");
		response.setCharacterEncoding("UTF-8");
		return "friends";
	}
	
	@RequestMapping(value = "/profile.html")
	public String profile(HttpServletRequest request,HttpServletResponse response) {
		logger.info("profile.html");
		System.out.println("profile.html");
		String userid = request.getParameter("userid");
		//TODO
		response.setCharacterEncoding("UTF-8");
		return "profile";
	}

}
