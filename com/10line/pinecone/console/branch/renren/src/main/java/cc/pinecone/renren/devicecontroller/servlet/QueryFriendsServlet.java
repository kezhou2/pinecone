package cc.pinecone.renren.devicecontroller.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;

import cc.pinecone.renren.devicecontroller.controller.AppConfig;
import cc.pinecone.renren.devicecontroller.controller.PageController;
import cc.pinecone.renren.devicecontroller.service.LoginUserDetailsImpl;

import com.renren.api.client.RenrenApiClient;
import com.renren.api.client.param.impl.AccessToken;
import com.tenline.pinecone.platform.model.Entity;
import com.tenline.pinecone.platform.model.Variable;
import com.tenline.pinecone.platform.sdk.RESTClient;

public class QueryFriendsServlet extends HttpServlet {

private static final Logger logger = LoggerFactory.getLogger(PageController.class);
	
	private static RenrenApiClient api;
	
	@Override
	public void init() throws ServletException {
		super.init();
		api = RenrenApiClient.getInstance();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
		
		JSONArray friendIds = api.getFriendsService().getFriends(0, 10000, new AccessToken(access_token));
		int size = friendIds.size();
		iTotalRecords = size;
		JSONArray friends = api.getFriendsService().getFriends(0, 20, new AccessToken(access_token) );
		iTotalDisplayRecords = friends.size();
		
		JSONArray data = new JSONArray();

		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("sEcho", sEcho);
		jsonResponse.put("iTotalRecords", iTotalRecords);
		jsonResponse.put("iTotalDisplayRecords", iTotalDisplayRecords);
			
		for(int i=0;i<friends.size();i++){
			JSONObject f = (JSONObject)friends.get(i);
			JSONArray row = new JSONArray();
			row.add("<a href='img/demo/big.jpg' title='' class='lightbox'><img src='"+(String)f.get("tinyurl")+"' alt='' /></a>");
			row.add((String)f.get("name"));
			row.add((String)f.get("sex"));
			row.add("<ul class='table-controls'>"+
						"<li><a href='#' class='btn tip' title='Share'><i class='ico-share'></i></a></li>"+
					"</ul>");
			data.add(row);
		}
		jsonResponse.put("aaData", data);
			
		response.setContentType("application/json");
		response.getWriter().print(jsonResponse.toString());
		
	}

}
