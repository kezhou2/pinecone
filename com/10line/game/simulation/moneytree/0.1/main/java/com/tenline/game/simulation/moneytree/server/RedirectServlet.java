package com.tenline.game.simulation.moneytree.server;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tenline.game.simulation.moneytree.shared.RenrenConfig;

@SuppressWarnings("serial")
public class RedirectServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, java.io.IOException {
		request.setAttribute("appId", RenrenConfig.APP_ID);
		RequestDispatcher welcomeDispatcher = request.getRequestDispatcher("redirect.jsp");
		welcomeDispatcher.forward(request, response);
	}
}
