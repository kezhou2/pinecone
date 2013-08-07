package cc.pinecone.renren.devicecontroller.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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
import cc.pinecone.renren.devicecontroller.service.LoginUserDetailsImpl;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Entity;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.sdk.RESTClient;

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

	private static final Logger logger = Logger.getLogger(PageController.class);

	@RequestMapping(value = "/login.html")
	public String login(HttpServletRequest request,HttpServletResponse response) {
		logger.info("login.html");
		return "login";
	}
	
	@RequestMapping(value = "/register.html")
	public String register(Model model) {
		logger.info("register.html");
		return "register";
	}
	
	@RequestMapping(value = "/index.html")
	public String index(HttpServletRequest request,HttpServletResponse response) {
		logger.info("index.html");
		
		//base process flow
		SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");  
		String username = securityContextImpl.getAuthentication().getName();
		String password = securityContextImpl.getAuthentication().getCredentials().toString();
		logger.info("old:"+password);
		
		logger.info("new:"+securityContextImpl.getAuthentication().getCredentials().toString());
		
		request.getSession().setAttribute("username", username);
		response.setCharacterEncoding("UTF-8");
		return "index";
	}

	@RequestMapping(value = "/devices.html")
	public String devices(HttpServletRequest request,HttpServletResponse response) {
		logger.info("devices.html");
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		logger.info("username:"+username+"\npassword:"+password);
		response.setCharacterEncoding("UTF-8");
		return "devices";
	}
	
	@RequestMapping(value = "/variable.html", method = RequestMethod.GET)
	public String variable(HttpServletRequest request,HttpServletResponse response) {
		logger.info("variable.html");
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
		logger.info("path:"+path);
		Config conf = Config.getInstance(userid, path+File.separatorChar+AppConfig.getCachePath());
		
		if(conf.getDevice(id) == null){
			request.setAttribute("addedFavorate", false);
		}else{
			request.setAttribute("addedFavorate", true);
		}
		response.setCharacterEncoding("UTF-8");
		return "variable";
	} 

	@RequestMapping(value = "/setting.html")
	public String registry(HttpServletRequest request,HttpServletResponse response) {
		logger.info("setting.html");
		response.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		return "setting";
	}

	@RequestMapping(value = "/friends.html")
	public String friends(HttpServletRequest request,HttpServletResponse response) {
		logger.info("friends.html");
		response.setCharacterEncoding("UTF-8");
		return "friends";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/favorites.html")
	public String favorites(HttpServletRequest request,HttpServletResponse response) {
		logger.info("favorites.html");
		
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
		
		JSONArray jsonData = new JSONArray();
		String deviceids = "";
		String variableids = "";
		for(String devid:deviceIds){
			deviceids = deviceids+devid+"_";
			List<String> variableIds = conf.getFocusDeviceVariableIds(devid);
			JSONArray data = new JSONArray();
			for(int i=0;i<variableIds.size();i++){
				data.add(Integer.parseInt(variableIds.get(i)));
				variableids = variableids+variableIds.get(i)+"_";
			}
			
			jsonData.add(data);
		}
		
		logger.info("json:"+jsonData.toJSONString());
		request.setAttribute("jsonData", jsonData.toJSONString());
		request.setAttribute("deviceIds", deviceids);
		request.setAttribute("variableIds", variableids);
		response.setCharacterEncoding("UTF-8");
		return "favorites";
	}
	
	@RequestMapping(value = "/profile.html")
	public String profile(HttpServletRequest request,HttpServletResponse response) {
		logger.info("profile.html");
		SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");  
		String username = securityContextImpl.getAuthentication().getName();
		String password = securityContextImpl.getAuthentication().getCredentials().toString();
		
		User user = this.getPineconeAPI().login(username, password);
		
		request.setAttribute("myname", user.getName());
		request.setAttribute("myemail", user.getEmail());
		response.setCharacterEncoding("UTF-8");
		return "profile";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/environment.html")
	public String environment(HttpServletRequest request,HttpServletResponse response) {
		logger.info("environment.html");
		
		//get user name ,password, userid
		SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");  
		String username = securityContextImpl.getAuthentication().getName();
		logger.info("Username:" + username);  
		String password = securityContextImpl.getAuthentication().getCredentials().toString();
		logger.info("Credentials:" + password);
		UserDetails ud = (UserDetails)securityContextImpl.getAuthentication().getPrincipal();
		String userid = null;
		if(ud instanceof LoginUserDetailsImpl){
			LoginUserDetailsImpl lud = (LoginUserDetailsImpl)ud;
			userid = lud.getUserid();
		}
		
		JSONArray jsonData = new JSONArray();
		ArrayList<Device> list = new ArrayList<Device>();
		try {
			ArrayList<Entity> devs = (ArrayList<Entity>) getRESTClient().get("/user/"+userid+"/devices",username,password);
			for(Entity e:devs){
				Device dev = (Device) e;
				JSONObject ob = new JSONObject();
				ob.put("deviceId", dev.getId());
				ob.put("deviceCode", dev.getCode());
				jsonData.add(ob);
				list.add(dev);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		logger.info("json:"+jsonData.toJSONString());
		request.setAttribute("jsonData", jsonData.toJSONString());
		
		request.setAttribute("list", list);
		response.setCharacterEncoding("UTF-8");
		return "environment";
	}
	
	@RequestMapping(value = "/alermsetting.html")
	public String alermsetting(HttpServletRequest request,HttpServletResponse response) {
		logger.info("alermsetting.html");
		String devid = request.getParameter("deviceId");
		String varid = request.getParameter("variableId");
		
		request.setAttribute("deviceId", devid);
		request.setAttribute("variableId", varid);
		response.setCharacterEncoding("UTF-8");
		return "alermsetting";
	}
	

	@RequestMapping(value = "/403.html")
	public String goto403(HttpServletRequest request,HttpServletResponse response) {
		return "403";
	}
	
	@RequestMapping(value = "/404.html")
	public String goto404(HttpServletRequest request,HttpServletResponse response) {
		return "404";
	}
	
	@RequestMapping(value = "/405.html")
	public String goto405(HttpServletRequest request,HttpServletResponse response) {
		return "405";
	}
	
	@RequestMapping(value = "/500.html")
	public String goto500(HttpServletRequest request,HttpServletResponse response) {
		return "500";
	}
	
	@RequestMapping(value = "/503.html")
	public String goto503(HttpServletRequest request,HttpServletResponse response) {
		return "503";
	}
	
	@RequestMapping(value = "/devicedetail.html", method = RequestMethod.GET)
	public String devicedetail(HttpServletRequest request,HttpServletResponse response) {
		logger.info("devicedetail.html");
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
		logger.info("path:"+path);
		Config conf = Config.getInstance(userid, path+File.separatorChar+AppConfig.getCachePath());
		
		if(conf.getDevice(id) == null){
			request.setAttribute("addedFavorate", false);
		}else{
			request.setAttribute("addedFavorate", true);
		}
		response.setCharacterEncoding("UTF-8");
		return "devicedetail";
	}
}
