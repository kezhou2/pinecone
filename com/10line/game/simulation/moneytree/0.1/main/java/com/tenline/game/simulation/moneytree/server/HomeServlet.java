package com.tenline.game.simulation.moneytree.server;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.renren.api.client.RenrenApiClient;
import com.renren.api.client.param.impl.SessionKey;
import com.tenline.game.simulation.moneytree.shared.RenrenConfig;

@SuppressWarnings("serial")
public class HomeServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, java.io.IOException {
		String sessionKey = request.getParameter("xn_sig_session_key");
		String renrenUserId = request.getParameter("xn_sig_user");
		System.out.println("session:"+sessionKey);
		System.out.println("renrenUserId:"+renrenUserId);
		if (sessionKey != null && renrenUserId != null) {
			RenrenApiClient apiClient = RenrenApiClient.getInstance();
			JSONArray userInfo = apiClient.getUserService().getInfo(renrenUserId, "name,headurl", new SessionKey(sessionKey));
			System.out.println("userInfo:"+userInfo);
			if (userInfo != null && userInfo.size() > 0) {
				JSONObject currentUser = (JSONObject) userInfo.get(0);
				if (currentUser != null) {
					String userName = (String) currentUser.get("name");
					String userHead = (String) currentUser.get("headurl");
					System.out.println("userName:"+userName);
					System.out.println("userHead:"+userHead);
					request.setAttribute("userName", userName);
					request.setAttribute("userHead", userHead);
					request.setAttribute("xn_sig_session_key", sessionKey);
					request.setAttribute("xn_sig_user", renrenUserId);
				}
			}
		}
		
		request.setAttribute("appId", RenrenConfig.APP_ID);
		RequestDispatcher welcomeDispatcher = request.getRequestDispatcher("/GoldTree.html");
		welcomeDispatcher.forward(request, response);
	}
}
