package cc.pinecone.renren.devicecontroller.controller;

import java.io.File;
import java.util.ArrayList;
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

import cc.pinecone.renren.devicecontroller.config.Config;
import cc.pinecone.renren.devicecontroller.dao.PineconeApi;
import cc.pinecone.renren.devicecontroller.model.FocusDevice;
import cc.pinecone.renren.devicecontroller.model.FocusVariable;
import cc.pinecone.renren.devicecontroller.service.LoginUserDetailsImpl;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Entity;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.model.Variable;
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
		String password = securityContextImpl.getAuthentication().getCredentials().toString();
		System.out.println("old:"+password);
		
		System.out.println("new:"+securityContextImpl.getAuthentication().getCredentials().toString());
		
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
		UserDetails ud = (UserDetails)securityContextImpl.getAuthentication().getPrincipal();
		String userid = null;
		if(ud instanceof LoginUserDetailsImpl){
			LoginUserDetailsImpl lud = (LoginUserDetailsImpl)ud;
			userid = lud.getUserid();
		}
		
		try{
			ArrayList<Entity> devs = (ArrayList<Entity>) this.getRESTClient().get("/device/"+id,username,password);
			Device dev = (Device) devs.get(0);
			request.setAttribute("device", dev);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		request.setAttribute("querydeviceid", id);
		
		//get user config
		String path =  request.getSession().getServletContext().getRealPath("/");
		System.out.println("path:"+path);
		Config conf = Config.getInstance(userid, path+File.separatorChar+AppConfig.getCachePath());
		
		if(conf.getDevice(id) == null){
			request.setAttribute("addedFavorate", false);
		}else{
			request.setAttribute("addedFavorate", true);
		}
		
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
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/favorites.html")
	public String favorites(HttpServletRequest request,HttpServletResponse response) {
		logger.info("favorites.html");
		System.out.println("favorites.html");
		
		SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");  
		UserDetails ud = (UserDetails)securityContextImpl.getAuthentication().getPrincipal();
		String userid = null;
		if(ud instanceof LoginUserDetailsImpl){
			LoginUserDetailsImpl lud = (LoginUserDetailsImpl)ud;
			userid = lud.getUserid();
		}
		//get user config
		String path =  request.getSession().getServletContext().getRealPath("/");
		Config conf = Config.getInstance(userid, path+File.separatorChar+AppConfig.getCachePath());
		List<String> deviceIds = conf.getFocusDeviceIds();
		
		JSONArray focus = new JSONArray();
		for(String devid:deviceIds){
			List<String> variableIds = conf.getFocusDeviceVariableIds(devid);
			StringBuilder strIds = new StringBuilder();
			for(String varid:variableIds)
				strIds.append(varid+"_");
			JSONObject o = new JSONObject();
			o.put("deviceId", devid);
			o.put("variableIds", strIds.toString());
			focus.add(o);
		}
		
		System.out.println("json:"+focus.toJSONString());
		request.setAttribute("focusConf", focus.toJSONString());

		return "favorites";
	}
	
	@RequestMapping(value = "/profile.html")
	public String profile(HttpServletRequest request,HttpServletResponse response) {
		logger.info("profile.html");
		System.out.println("profile.html");
		
		SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");  
		String username = securityContextImpl.getAuthentication().getName();
		String password = securityContextImpl.getAuthentication().getCredentials().toString();
		
		User user = this.getPineconeAPI().login(username, password);
		
		request.setAttribute("myname", user.getName());
		request.setAttribute("myemail", user.getEmail());
		response.setCharacterEncoding("UTF-8");
		return "profile";
	}
	
	@RequestMapping(value = "/environment.html")
	public String environment(HttpServletRequest request,HttpServletResponse response) {
		logger.info("environment.html");
		System.out.println("environment.html");
		
		response.setCharacterEncoding("UTF-8");
		return "environment";
	}

}
