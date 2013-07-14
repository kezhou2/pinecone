package cc.pinecone.renren.devicecontroller.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cc.pinecone.renren.devicecontroller.config.Config;
import cc.pinecone.renren.devicecontroller.dao.PineconeApi;
import cc.pinecone.renren.devicecontroller.model.ExDeviceInfo;
import cc.pinecone.renren.devicecontroller.service.LoginUserDetailsImpl;
import cc.pinecone.renren.devicecontroller.servlet.DataTablesParamUtility;
import cc.pinecone.renren.devicecontroller.servlet.JQueryDataTableParamModel;

import com.tenline.pinecone.platform.model.Authority;
import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Entity;
import com.tenline.pinecone.platform.model.Item;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.model.Variable;
import com.tenline.pinecone.platform.sdk.RESTClient;

@Controller
public class PineconeController {

	@Autowired
	private MessageSource msgSrc;
	
	@Autowired
	public AuthenticationManager authenticationManager;

	private static PineconeApi pApi;

	private static RESTClient client;
	
	private final String ADMIN_NAME = "admin";
	private final String ADMIN_PWD = "admin";
	private final int PAGE_NUM = 20;
	
	private static final Logger logger = LoggerFactory
			.getLogger(PageController.class);

	public RESTClient getRESTClient() {
		if (client == null) {
			client = new RESTClient(AppConfig.REST_URL);
		}
		return client;
	}

	public PineconeApi getPineconeAPI() {
		if (pApi == null) {
			pApi = new PineconeApi();
		}
		return pApi;
	}

	@RequestMapping(value = "/disconnectdevice.html", method = RequestMethod.GET)
	public void disconnectDevice(HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("disconnectdevice.html");
		System.out.println("disconnectdevice");
		String id = request.getParameter("id");
		System.out.println("id:" + id);

		try {
			this.getRESTClient().delete("/device/" + id);
			PrintWriter out = response.getWriter();
			out.print("true");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/activedevice.html", method = RequestMethod.GET)
	public void activeDevice(HttpServletRequest request,
			HttpServletResponse response) {
		SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");  
		String username = securityContextImpl.getAuthentication().getName();
		String password = securityContextImpl.getAuthentication().getCredentials().toString();
		logger.info("activeDevice.html");
		System.out.println("activeDevice");
		String code = request.getParameter("code");
		String name = request.getParameter("name");
		System.out.println("code:" + code + " name:" + name);
		UserDetails ud = (UserDetails)securityContextImpl.getAuthentication().getPrincipal();
		String userid = null;
		if(ud instanceof LoginUserDetailsImpl){
			LoginUserDetailsImpl lud = (LoginUserDetailsImpl)ud;
			userid = lud.getUserid();
		}

		try {
			Device dev = (Device) (this.getRESTClient().get("/device/search/codes?code=" + code,
					username, password)).toArray()[0];

			String msg = client.post("/device/" + dev.getId() + "/user",
					"/user/" + userid);
			System.out.println("adding device to user:" + msg);
			Device device = new Device();
			device.setName(name);
			msg = client.put("/device/" + dev.getId(), device);
			System.out.println("changing device name:" + msg);

			PrintWriter out = response.getWriter();
			out.print("true");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setCharacterEncoding("UTF-8");
	}
	
	@RequestMapping(value = "/registeruser.html")
	public String registerUser(HttpServletRequest request,	HttpServletResponse response) {
		System.out.println("registeruser.html");
		String username = (String)request.getParameter("username");
		String password = (String)request.getParameter("password1");
		String email = (String)request.getParameter("emailValid");
		System.out.println("username:"+username+"\npassword:"+password+"\nemail:"+email);
		
		try {
			if(username == null || password == null){
				request.setAttribute("isregister", "false");
				return "register";
			}
			
			ArrayList<Entity> users = (ArrayList<Entity>) this.getRESTClient().get(
					"/user/search/names?name=" + username, ADMIN_NAME, ADMIN_PWD);
			if (users.size() == 0) {
				User u = new User();
				u.setName(username);
				u.setPassword(password);
				u.setEmail(email);
				u.setId(Long.getLong(client.post("/user", u)));

				Authority authority = new Authority();
				authority.setAuthority("ROLE_USER");
				authority.setUserName(u.getName());
				authority.setId(Long.getLong(client.post("/authority", authority)));
				
				client.post("/authority/" + authority.getId() + "/user", "/user/" + u.getId());

				request.setAttribute("isregister", "true");
				return "login";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "register";
	}
	
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/queryvariable.html", method = RequestMethod.GET)
	public void queryVariable(HttpServletRequest request,HttpServletResponse response) throws IOException {
		SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");  
		String username = securityContextImpl.getAuthentication().getName();
		String password = securityContextImpl.getAuthentication().getCredentials().toString();
		UserDetails ud = (UserDetails)securityContextImpl.getAuthentication().getPrincipal();
		String userid = null;
		if(ud instanceof LoginUserDetailsImpl){
			LoginUserDetailsImpl lud = (LoginUserDetailsImpl)ud;
			userid = lud.getUserid();
		}
		
		logger.info("queryVariable.html");
		System.out.println("queryVariable");
		String id = request.getParameter("id");
		System.out.println(id);
		
		//get user config
		String path =  request.getSession().getServletContext().getRealPath("/");
		System.out.println("path:"+path);
		Config conf = Config.getInstance(userid, path+File.separatorChar+AppConfig.getCachePath());
		List<String> idsList = conf.getFocusDeviceVariableIds(id);
		System.out.println("idsList size:"+idsList.size());
		for(String s:idsList){
			System.out.print("###"+s+"|");
		}
		System.out.println();
		
		JQueryDataTableParamModel param = DataTablesParamUtility.getParam(request);
		
		String sEcho = param.sEcho;
		int iTotalRecords = 0; 
		int iTotalDisplayRecords = 0;
		JSONArray data = new JSONArray(); //data that will be shown in the table

		String strLoading=msgSrc.getMessage("pineconecontroller.loading", null, null);
		String strHistoryData = msgSrc.getMessage("pineconecontroller.historydata", null, null);
		String strAddFavorites = msgSrc.getMessage("pineconecontroller.addfavorites", null, null);
		String strRemoveFavorites = msgSrc.getMessage("pineconecontroller.removefavorites", null, null);
		
		List<Variable> list = new ArrayList<Variable>();
		try {
			ArrayList<Entity> vars = (ArrayList<Entity>) this.getRESTClient()
					.get("/device/" + id + "/variables", username, password);
			int index=0;
			for (Entity ent : vars) {
				Variable var = (Variable) ent;
				ArrayList<Item> itemlist = new ArrayList<Item>();
				if(var.getType().equals(Variable.WRITE)){
					ArrayList<Entity> items = (ArrayList<Entity>) client
							.get("/variable/" + var.getId() + "/items", username,password);
					for (Entity ee : items) {
						Item item = (Item) ee;
						itemlist.add(item);
					}
				}
				var.setItems(itemlist);
				list.add(var);
				
				JSONArray row = new JSONArray();
				row.add(var.getId());
				row.add(var.getType());
				row.add(var.getName());
				
				if(var.getType().equals(Variable.READ)){
					row.add("<strong varid='"+var.getId()+"'>"+strLoading+"</strong>");
				}else{
					row.add("<strong varid='"+var.getId()+"'>--</strong>");
				}
				if(var.getType().equals(Variable.READ)){
					row.add("<span class='dynamictrend' varid='"+var.getId()+"'>"+strLoading+"</span>");
				}else{
					row.add("");
				}
				
				StringBuilder sb = new StringBuilder();
				if(var.getType().equals(Variable.WRITE)){
					sb.append("<select id='index"+index+"' name='select2' class='styled'>");
				}else{
					sb.append("<select id='index-"+index+"' name='select2' class='styled'>");
					sb.append("<option value=''>None</option>");
				}
				
				sb.append("<option></option>");
				for(Item it:var.getItems()){
					sb.append("<option value='"+var.getId()+"_"+it.getValue()+"'>"+it.getValue()+"</option>");
				}
				sb.append("</select>");
				row.add(sb.toString());
				
				String addStr = new String("<ul class='table-controls'>"+
												"<li><a href='history.html?id="+var.getId()+"&type=minute&period=10' data-fancybox-type='iframe' id='historyShow' class='btn tip' title='"+strHistoryData+"'><i class='fam-chart-bar'></i></a></li>"+
												"<li><a href='#' id='var"+var.getId()+"' onclick='addVariable("+id+","+var.getId()+")' class='btn tip' title='"+strAddFavorites+"'><i class='fam-bell-add'></i></a></li>"+
											"</ul>");
				
				for(String sId:idsList){
					if(sId.equals(""+var.getId())){
						addStr = "<ul class='table-controls'>"+
									"<li><a href='history.html?id="+var.getId()+"&type=minute&period=10' data-fancybox-type='iframe' id='historyShow' class='btn tip' title='"+strHistoryData+"'><i class='fam-chart-bar'></i></a></li>"+
									"<li><a href='#' id='var"+var.getId()+"' onclick='removeVariable("+id+","+var.getId()+")' class='btn tip' title='"+strRemoveFavorites+"'><i class='fam-bell-delete'></i></a></li>"+
								"</ul>";
						break;
					}
				}
				
				row.add(addStr);
				
				data.add(row);
				index++;
			}
			
			iTotalRecords = vars.size();
			iTotalDisplayRecords = vars.size();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JSONObject jsonResponse = new JSONObject();
		
		jsonResponse.put("sEcho", sEcho);
		jsonResponse.put("iTotalRecords", iTotalRecords);
		jsonResponse.put("iTotalDisplayRecords", iTotalDisplayRecords);

		jsonResponse.put("aaData", data);
		System.out.println(jsonResponse.toJSONString());
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.getWriter().print(jsonResponse.toString());

	}
	
	@RequestMapping(value = "/changepassword.html")
	public String changePassword(HttpServletRequest request,HttpServletResponse response) throws IOException {
		SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");  
		String username = securityContextImpl.getAuthentication().getName();
		String password = securityContextImpl.getAuthentication().getCredentials().toString();
		
		logger.info("changePassword.html");
		System.out.println("changePassword.html");
		
		String oldpwd = request.getParameter("oldpassword");
		String newpwd = request.getParameter("newpassword");
		String myname = request.getParameter("myname");
		String myemail = request.getParameter("myemail");
		System.out.println("old:" + oldpwd + " new:" + newpwd);
		boolean isSuccess = false;
		if(password.equals(oldpwd)){		
			try {
				ArrayList<Entity> users = (ArrayList<Entity>) this.getRESTClient().get(
						"/user/search/names?name=" + username, username, oldpwd);
				if(users.size()>0){
					User user = (User)users.get(0);
					user.setPassword(newpwd);
					client.post("/user/"+user.getId(), user);
					Authentication r = new UsernamePasswordAuthenticationToken(username, newpwd);
					Authentication result = authenticationManager.authenticate(r);
					SecurityContextHolder.getContext().setAuthentication(result);
					isSuccess = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		if(isSuccess)
			request.setAttribute("changePwd", "true");
		else
			request.setAttribute("changePwd", "false");

		request.setAttribute("myname", myname);
		request.setAttribute("myemail", myemail);
		return "profile";
	}
	
	@RequestMapping(value = "/changeprofile.html")
	public String changeProfile(HttpServletRequest request,HttpServletResponse response) throws IOException {
		SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");  
		String username = securityContextImpl.getAuthentication().getName();
		String password = securityContextImpl.getAuthentication().getCredentials().toString();
		
		logger.info("changeProfile.html");
		System.out.println("changeProfile.html");
		
		String name = request.getParameter("username");
		String email = request.getParameter("email");

		boolean isSuccess = false;
		try {
			ArrayList<Entity> users = (ArrayList<Entity>) this.getRESTClient().get(
					"/user/search/names?name=" + username, username, password);
			if(users.size()>0){
				User user = (User)users.get(0);
				user.setName(name);
				user.setPassword(password);
				user.setEmail(email);
				String res = client.post("/user/"+user.getId(), user);
				System.out.println(res);
				isSuccess = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(isSuccess)
			request.setAttribute("changeProfile", "true");
		else
			request.setAttribute("changeProfile", "false");

		request.setAttribute("myname", name);
		request.setAttribute("myemail", email);
		return "profile";
	}
	
	
	
	@RequestMapping(value = "/adddevicetofocus.html", method = RequestMethod.POST)
	public void addDeviceToFocus(HttpServletRequest request,HttpServletResponse response) throws IOException {
		logger.info("adddevicetofocus.html");
		System.out.println("adddevicetofocus.html");
		String id = request.getParameter("deviceid");
		
		SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");  
		String username = securityContextImpl.getAuthentication().getName();
		String password = securityContextImpl.getAuthentication().getCredentials().toString();
		UserDetails ud = (UserDetails)securityContextImpl.getAuthentication().getPrincipal();
		String userid = null;
		if(ud instanceof LoginUserDetailsImpl){
			LoginUserDetailsImpl lud = (LoginUserDetailsImpl)ud;
			userid = lud.getUserid();
		}
		//get user config
		String path =  request.getSession().getServletContext().getRealPath("/");
		Config conf = Config.getInstance(userid, path+File.separatorChar+AppConfig.getCachePath());
		
		boolean isSuccess = false;
		try {
			ArrayList<Entity> vars = (ArrayList<Entity>) this.getRESTClient()
					.get("/device/" + id + "/variables", username, password);
			for (Entity ent : vars) {
				Variable var = (Variable) ent;
				isSuccess = conf.addFocusVariable(id, ""+var.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		response.setContentType("text/html; charset=utf-8"); 
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control","no-cache");
		PrintWriter out=response.getWriter();
		
		if(isSuccess)
			out.write("true");
		else
			out.write("false");
		out.close();
	}
	
	@RequestMapping(value = "/removedevicetofocus.html", method = RequestMethod.POST)
	public void removeDeviceToFocus(HttpServletRequest request,HttpServletResponse response) throws IOException {
		logger.info("removedevicetofocus.html");
		System.out.println("removedevicetofocus.html");
		String id = request.getParameter("deviceid");
		
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
		
		boolean isSuccess = false;
		try {
			isSuccess = conf.deleteDevice(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		response.setContentType("text/html; charset=utf-8"); 
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control","no-cache");
		PrintWriter out=response.getWriter();
		
		if(isSuccess)
			out.write("true");
		else
			out.write("false");
		out.close();
	}
	
	@RequestMapping(value = "/addvariabletofocus.html", method = RequestMethod.POST)
	public void addVariableToFocus(HttpServletRequest request,HttpServletResponse response) throws IOException {
		logger.info("addvariabletofocus.html");
		System.out.println("addvariabletofocus.html");
		String devid = request.getParameter("deviceid");
		String varid = request.getParameter("variableid");
		
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
		
		response.setContentType("text/html; charset=utf-8"); 
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control","no-cache");
		PrintWriter out=response.getWriter();
		
		if(conf.addFocusVariable(devid, varid))
			out.write("true");
		else
			out.write("false");
		out.close();
	}
	
	@RequestMapping(value = "/removevariabletofocus.html", method = RequestMethod.POST)
	public void removeVariableToFocus(HttpServletRequest request,HttpServletResponse response) throws IOException {
		logger.info("removevariabletofocus.html");
		System.out.println("removevariabletofocus.html");
		String devid = request.getParameter("deviceid");
		String varid = request.getParameter("variableid");
		
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
		
		response.setContentType("text/html; charset=utf-8"); 
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control","no-cache");
		PrintWriter out=response.getWriter();
		
		if(conf.deleteFocusVariable(devid, varid))
			out.write("true");
		else
			out.write("false");
		out.close();
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/queryfocusvariable.html", method = RequestMethod.GET)
	public void queryfocusVariable(HttpServletRequest request,HttpServletResponse response) throws IOException {
		logger.info("queryfocusvariable.html");
		System.out.println("queryfocusvariable");

		SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");  
		String username = securityContextImpl.getAuthentication().getName();
		String password = securityContextImpl.getAuthentication().getCredentials().toString();
		UserDetails ud = (UserDetails)securityContextImpl.getAuthentication().getPrincipal();
		String userid = null;
		if(ud instanceof LoginUserDetailsImpl){
			LoginUserDetailsImpl lud = (LoginUserDetailsImpl)ud;
			userid = lud.getUserid();
		}

		//get user config
		String path =  request.getSession().getServletContext().getRealPath("/");
		Config conf = Config.getInstance(userid, path+File.separatorChar+AppConfig.getCachePath());
		
		JQueryDataTableParamModel param = DataTablesParamUtility.getParam(request);
		String sEcho = param.sEcho;
		int iTotalRecords = 0; 
		int iTotalDisplayRecords = 0;
		JSONArray data = new JSONArray(); //data that will be shown in the table
		
		String strLoading=msgSrc.getMessage("pineconecontroller.loading", null, null);
		String strHistoryData = msgSrc.getMessage("pineconecontroller.historydata", null, null);
		String strRemoveFavorites = msgSrc.getMessage("pineconecontroller.removefavorites", null, null);
		String strDeviceName = msgSrc.getMessage("pineconecontroller.devicename", null, null);
		String strDeviceCode = msgSrc.getMessage("pineconecontroller.devicecode", null, null);
		String strAlermSetting = msgSrc.getMessage("pineconecontroller.alermsetting", null, null);
		
		List<String> deviceIds = conf.getFocusDeviceIds();
		
		int count = 0;
		try{
			for(String deviceId:deviceIds){
				ArrayList<Entity> devs = (ArrayList<Entity>) this.getRESTClient()
						.get("/device/" + deviceId, username, password);
				Device device = (Device)devs.get(0);
				
				//one device title row for table
				JSONArray r = new JSONArray();
				r.add("<Strong deviceId='"+device.getId()+"' deviceCode='"+device.getCode()+"'>"+strDeviceName+device.getName()+" "+strDeviceCode+device.getCode()+"</strong>");
				r.add("");
				r.add("");
				r.add("");
				r.add("");
				r.add("");
				r.add("");
				r.add("<ul class='table-controls'>"+
						"<li><a href='#' onclick='removeDevice("+deviceId+")' class='btn tip' title='"+strRemoveFavorites+"'><i class='fam-bell-delete'></i></a></li>"+
					"</ul>");
				data.add(r);
				count++;
				
				List<String> variableIds = conf.getFocusDeviceVariableIds(deviceId);
				for(String variableId:variableIds){
					ArrayList<Entity> vars = (ArrayList<Entity>) this.getRESTClient()
							.get("/variable/" + variableId, username, password);
					for (Entity ent : vars) {
						Variable var = (Variable) ent;
						ArrayList<Item> itemlist = new ArrayList<Item>();
						if(var.getType().equals(Variable.WRITE)){
							ArrayList<Entity> items = (ArrayList<Entity>) client
									.get("/variable/" + var.getId() + "/items", username,password);
							for (Entity ee : items) {
								Item item = (Item) ee;
								itemlist.add(item);
							}
						}
						var.setItems(itemlist);
						
						JSONArray row = new JSONArray();
						row.add(var.getId());
						row.add(var.getType());
						row.add(var.getName());
						
						if(var.getType().equals(Variable.READ)){
							row.add("<strong varid='"+var.getId()+"'>"+strLoading+"</strong>");
						}else{
							row.add("<strong varid='"+var.getId()+"'>--</strong>");
						}
						if(var.getType().equals(Variable.READ)){
							row.add("<span class='dynamictrend' varid='"+var.getId()+"'>"+strLoading+"</span>");
						}else{
							row.add("");
						}
						
						String alermStr = conf.getVariable(""+device.getId(), variableId).getAlermString();
						if(alermStr != null && !alermStr.equals("")){
							JSONParser p = new JSONParser();
							JSONObject ob = (JSONObject)p.parse(alermStr);
							
							row.add("<Strong alermVarid='"+var.getId()+"'>"+ob.get("condition").toString()+ob.get("variablevalue").toString()+"</strong>");
						}else{
							row.add("<Strong alermVarid='"+var.getId()+"' />");
						}
						
						StringBuilder sb = new StringBuilder();
						if(var.getType().equals(Variable.WRITE)){
							sb.append("<select id='index"+var.getId()+"' name='select2' class='styled'>");
						}else{
							sb.append("<select id='index-"+var.getId()+"' name='select2' class='styled'>");
							sb.append("<option value=''>None</option>");
						}
						
						sb.append("<option></option>");
						for(Item it:var.getItems()){
							sb.append("<option value='"+deviceId+"_"+var.getId()+"_"+it.getValue()+"'>"+it.getValue()+"</option>");
						}
						sb.append("</select>");
						row.add(sb.toString());
						
						row.add("<ul class='table-controls'>"+
								"<li><a href='alermsetting.html?deviceId="+deviceId+"&variableId="+var.getId()+"' id='alermVariable' class='btn tip' title='"+strAlermSetting+"'><i class='fam-monitor-edit'></i></a></li>"+
								"<li><a href='history.html?id="+var.getId()+"&type=minute&period=10' data-fancybox-type='iframe' id='historyShow' class='btn tip' title='"+strHistoryData+"'><i class='fam-chart-bar'></i></a></li>"+
								"<li><a href='#' onclick='removeVariable("+deviceId+","+var.getId()+")' class='btn tip' title='"+strRemoveFavorites+"'><i class='fam-bell-delete'></i></a></li>"+
								"</ul>");
						data.add(row);
						count++;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		iTotalRecords = count;
		iTotalDisplayRecords = count;
		
		JSONObject jsonResponse = new JSONObject();
		
		jsonResponse.put("sEcho", sEcho);
		jsonResponse.put("iTotalRecords", iTotalRecords);
		jsonResponse.put("iTotalDisplayRecords", iTotalDisplayRecords);

		jsonResponse.put("aaData", data);
		System.out.println(jsonResponse.toJSONString());
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.getWriter().print(jsonResponse.toString());
	}
	
	@RequestMapping(value = "/editdeviceinfo.html", method = RequestMethod.GET)
	public void editDeviceInfo(HttpServletRequest request,HttpServletResponse response) throws IOException {
		logger.info("editDeviceInfo.html");
		System.out.println("editDeviceInfo");
		
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
		
		String devid = request.getParameter("id");
		String mac = request.getParameter("mac");
		String addr = new String(request.getParameter("addr").getBytes("iso8859-1"),"utf-8"); 
		System.out.println("devid:" + devid + " mac:" + mac+" addr:"+addr);
		ExDeviceInfo info = new ExDeviceInfo();
		info.setId(Long.parseLong(devid));
		info.setMacId(mac);
		info.setAddress(addr);
		conf.addDeviceExtInfo(info);
		PrintWriter out = response.getWriter();
		out.print("true");
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/querydevices.html", method = RequestMethod.GET)
	public void queryDevices(HttpServletRequest request,HttpServletResponse response) throws IOException {
		//get user name ,password, userid
		SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");  
		String username = securityContextImpl.getAuthentication().getName();
		System.out.println("Username:" + username);  
		String password = securityContextImpl.getAuthentication().getCredentials().toString();
		System.out.println("Credentials:" + password);
		UserDetails ud = (UserDetails)securityContextImpl.getAuthentication().getPrincipal();
		String userid = null;
		if(ud instanceof LoginUserDetailsImpl){
			LoginUserDetailsImpl lud = (LoginUserDetailsImpl)ud;
			userid = lud.getUserid();
		}
		
		//get user config
		String path =  request.getSession().getServletContext().getRealPath("/");
		System.out.println("path:"+path);
		Config conf = Config.getInstance(userid, path+File.separatorChar+AppConfig.getCachePath());
		
		//get request parameter from http request
		JQueryDataTableParamModel param = DataTablesParamUtility.getParam(request);
		
		String sEcho = param.sEcho;
		int iTotalRecords; // total number of records (unfiltered)
		int iTotalDisplayRecords;//value will be set when code filters companies by keyword
		JSONArray data = new JSONArray(); //data that will be shown in the table

		String strRemoveFavorites = msgSrc.getMessage("pineconecontroller.removefavorites", null, null);
		String strAddFavorites = msgSrc.getMessage("pineconecontroller.addfavorites", null, null);
		String strDetail = msgSrc.getMessage("pineconecontroller.detail", null, null);
		String strDisconnect = msgSrc.getMessage("pineconecontroller.disconnect", null, null);
		String strEdit = msgSrc.getMessage("pineconecontroller.edit", null, null);
		
		ArrayList<Device> list = new ArrayList<Device>();
		//TODO the rest web services is not paginated. so here need to change.
		int startPage = param.iDisplayStart/PAGE_NUM;
		int startIndex = param.iDisplayStart%PAGE_NUM;
		int pageCount = (param.iDisplayLength - param.iDisplayStart) / PAGE_NUM + 1;
		
		try {
			ArrayList<Entity> devs = (ArrayList<Entity>) getRESTClient().get("/user/"+userid+"/devices",username,password);
			for(Entity e:devs){
				Device dev = (Device) e;
				list.add(dev);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	
		System.out.println("size:"+list.size());
		iTotalRecords = list.size();
		List<Device> devices = new LinkedList<Device>();
		for(Device d : list){
			if(d.getName() == null){
				continue;
			}
			if(	d.getName().toLowerCase().contains(param.sSearch.toLowerCase())){
				devices.add(d);
				continue;
			}
			
			if(d.getCode() == null){
				continue;
			}
			if(d.getCode().toLowerCase().contains(param.sSearch.toLowerCase())){
				devices.add(d);
				continue;
			}
			devices.add(d);
		}
		iTotalDisplayRecords = devices.size();//Number of companies that matches search criterion should be returned
		
		final int sortColumnIndex = param.iSortColumnIndex;
		final int sortDirection = param.sSortDirection.equals("asc") ? -1 : 1;
		
		Collections.sort(devices, new Comparator<Device>(){
			@Override
			public int compare(Device c1, Device c2) {	
				switch(sortColumnIndex){
				case 2:
					return c1.getName().compareTo(c2.getName()) * sortDirection;
				case 3:
					return c1.getCode().compareTo(c2.getCode()) * sortDirection;
				}
				return 0;
			}
		});
		
		if(devices.size()< param.iDisplayStart + param.iDisplayLength)
			devices = devices.subList(param.iDisplayStart, devices.size());
		else
			devices = devices.subList(param.iDisplayStart, param.iDisplayStart + param.iDisplayLength);
	

		JSONObject jsonResponse = new JSONObject();
			
		jsonResponse.put("sEcho", sEcho);
		jsonResponse.put("iTotalRecords", iTotalRecords);
		jsonResponse.put("iTotalDisplayRecords", iTotalDisplayRecords);
			
		for(Device c : devices){
			ExDeviceInfo info = conf.getDeviceExtInfo(c.getId().toString());
			JSONArray row = new JSONArray();
			row.add("<a href='img/demo/big.jpg' title='' class='lightbox'><img src='img/device_card.png' alt='' /></a>");
			row.add(c.getId());
			row.add(c.getName());
			row.add(c.getCode());
			row.add(info.getMacId());
			row.add(info.getAddress());
			if(conf.getDevice(""+c.getId()) == null){
				row.add("<ul class='table-controls'>"+
							"<li><a href='variable.html?id="+c.getId()+"' class='btn tip' title='"+strDetail+"'><i class='fam-zoom'></i></a></li>"+
							"<li><a href='#' class='btn tip' onclick='disconnect("+c.getId()+")' title='"+strDisconnect+"'><i class='fam-disconnect'></i></a></li>"+
							"<li><a href='#' class='btn tip' onclick='editDeviceInfo("+c.getId()+")' title='"+strEdit+"'><i class='fam-application-edit'></i></a></li>"+
							"<li><a id='device"+c.getId()+"' href='#' class='btn tip' onclick='addDevice("+c.getId()+")' title='"+strAddFavorites+"'><i class='fam-bell-add'></i></a></li>"+
						"</ul>");
			}else{
				row.add("<ul class='table-controls'>"+
							"<li><a href='variable.html?id="+c.getId()+"' class='btn tip' title='"+strDetail+"'><i class='fam-zoom'></i></a></li>"+
							"<li><a href='#' class='btn tip' onclick='disconnect("+c.getId()+")' title='"+strDisconnect+"'><i class='fam-disconnect'></i></a></li>"+
							"<li><a href='#' class='btn tip' onclick='editDeviceInfo("+c.getId()+")' title='"+strEdit+"'><i class='fam-application-edit'></i></a></li>"+
							"<li><a id='device"+c.getId()+"' href='#' class='btn tip' onclick='removeDevice("+c.getId()+")' title='"+strRemoveFavorites+"'><i class='fam-bell-delete'></i></a></li>"+
						"</ul>");
			}
			data.add(row);
		}
		jsonResponse.put("aaData", data);
		System.out.println(jsonResponse.toJSONString());
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.getWriter().print(jsonResponse.toString());
	}
}
