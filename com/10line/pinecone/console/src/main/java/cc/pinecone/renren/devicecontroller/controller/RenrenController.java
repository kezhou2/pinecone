package cc.pinecone.renren.devicecontroller.controller;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cc.pinecone.renren.devicecontroller.service.LoginUserDetailsImpl;
import cc.pinecone.renren.devicecontroller.servlet.DataTablesParamUtility;
import cc.pinecone.renren.devicecontroller.servlet.JQueryDataTableParamModel;

import com.renren.api.client.RenrenApiClient;
import com.renren.api.client.param.impl.AccessToken;
import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Entity;
import com.tenline.pinecone.platform.sdk.RESTClient;

@Controller
public class RenrenController {
	
	private static final Logger logger = LoggerFactory.getLogger(PageController.class);
	
	private static RenrenApiClient api;

	public RenrenApiClient getRenrenAPI(){
		if(api == null){
			api = RenrenApiClient.getInstance();
		}
		return api;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getfriends.html", method = RequestMethod.GET)
	public void getFriends(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String access_token = (String)request.getSession().getAttribute("access_token");
		
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
		
		
		JQueryDataTableParamModel param = DataTablesParamUtility.getParam(request);
		
		String sEcho = param.sEcho;
		int iTotalRecords; // total number of records (unfiltered)
		int iTotalDisplayRecords;//value will be set when code filters companies by keyword
		
		JSONArray friendIds = getRenrenAPI().getFriendsService().getFriends(0, 10000, new AccessToken(access_token));
		int size = friendIds.size();
		iTotalRecords = size;
		JSONArray friends = getRenrenAPI().getFriendsService().getFriends(0, 20, new AccessToken(access_token) );
		iTotalDisplayRecords = friends.size();
		
		JSONArray data = new JSONArray();

		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("sEcho", sEcho);
		jsonResponse.put("iTotalRecords", iTotalRecords);
		jsonResponse.put("iTotalDisplayRecords", iTotalDisplayRecords);
			
		for(int i=0;i<friends.size();i++){
			JSONObject f = (JSONObject)friends.get(i);
			JSONArray row = new JSONArray();
			row.add("<a href='"+(String)f.get("headurl")+"' title='' class='lightbox'><img src='"+(String)f.get("tinyurl")+"' alt='' /></a>");
			row.add((String)f.get("name"));
			if(((String)f.get("sex")).equals("0")){
				row.add("female");
			}else if(((String)f.get("sex")).equals("1")){
				row.add("male");
			}else{
				row.add("");
			}
			row.add("<ul class='table-controls'>"+
						"<li><a href='#' class='btn tip' title='Invite'><i class=' ico-thumbs-up'></i></a></li>"+
						"<li><a href='#' class='btn tip' title='Message'><i class='icon-envelope-alt'></i></a></li>"+
					"</ul>");
			data.add(row);
		}
		jsonResponse.put("aaData", data);
			
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}
	
	
	@RequestMapping(value = "/invite.html", method = RequestMethod.GET)
	public void invite(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
	}

}
