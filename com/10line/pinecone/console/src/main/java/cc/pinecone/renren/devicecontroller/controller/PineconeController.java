package cc.pinecone.renren.devicecontroller.controller;

import java.io.IOException;
import java.io.PrintWriter;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cc.pinecone.renren.devicecontroller.dao.PineconeApi;
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

	private static PineconeApi pApi;

	private static RESTClient client;
	
	private final int OVERTIME = 20000;
	private final int BEAT_TIME = 2000;
	private final String ADMIN_NAME = "admin";
	private final String ADMIN_PWD = "admin";

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
		
		logger.info("queryVariable.html");
		System.out.println("queryVariable");
		String id = request.getParameter("id");
		System.out.println(id);
		
		JQueryDataTableParamModel param = DataTablesParamUtility.getParam(request);
		
		String sEcho = param.sEcho;
		int iTotalRecords = 0; 
		int iTotalDisplayRecords = 0;
		JSONArray data = new JSONArray(); //data that will be shown in the table

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
					row.add("<strong varid='"+var.getId()+"'>loading...</strong>");
				}else{
					row.add("<strong varid='"+var.getId()+"'>--</strong>");
				}
				if(var.getType().equals(Variable.READ)){
					row.add("<span class='dynamictrend' varid='"+var.getId()+"'>Loading...</span>");
				}else{
					row.add("");
				}
				
				StringBuilder sb = new StringBuilder();
//				sb.append(	"<ul class='table-controls'>");
//				sb.append(		"<li>");
//				sb.append(			"<div class='btn-group'>");
//				if(var.getType().equals(Variable.READ)){
//					sb.append(			"<button class='disabled btn dropdown-toggle' data-toggle='dropdown'>Setting <span class='caret dd-caret'></span></button>");
//				}else{
//					sb.append(			"<button class='btn dropdown-toggle' data-toggle='dropdown'>Setting <span class='caret dd-caret'></span></button>");
//					sb.append(			"<ul class='dropdown-men'>");
//						for(Item item:var.getItems()){
//							sb.append(		"<li><a href='#' onclick='publish('"+var.getId()+"','"+item.getValue()+"')' >"+item.getValue()+"</a></li>");
//						}
//					sb.append(			"</ul>");
//				}
//				sb.append(			"</div>");
//				sb.append(		"</li>");
//				sb.append(	"</ul>");
				
				if(var.getType().equals(Variable.WRITE)){
					sb.append("<select id='index"+index+"' name='select2' class='styled'>");
				}else{
					sb.append("<select id='index"+index+"' name='select2' class='styled'>");
				}
				
				sb.append("<option value='none'>Setting</option>");
				for(Item it:var.getItems()){
					sb.append("<option value='"+it.getValue()+"'>"+it.getValue()+"</option>");
				}
				sb.append("</select>");
				row.add(sb.toString());
				
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
		response.setContentType("application/json");
		response.getWriter().print(jsonResponse.toString());

	}
	
	
}
