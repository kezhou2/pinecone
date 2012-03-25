package com.tenline.game.simulation.moneytree.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.renren.api.client.RenrenApiClient;
import com.renren.api.client.param.impl.SessionKey;
import com.renren.api.client.services.PayService;
import com.tenline.game.simulation.moneytree.shared.Order;

@SuppressWarnings("serial")
public class FinishDealServlet extends HttpServlet {

	private HashMap<String,Boolean> orderMap = null;
	private OrderPersistence orderPersistence = null;
	
	@Override
	public void init() throws ServletException {
		orderMap = new LinkedHashMap<String,Boolean>();
		try {
			orderPersistence = OrderPersistence.getInstance();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.init();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("FinishDealServlet");
		
		String key = (String)req.getAttribute("xn_sig_skey");
		String orderId = (String)req.getAttribute("xn_sig_order_id");
		String userId = (String)req.getAttribute("xn_sig_user");
		
		//TODO 验证key
		
		//TODO 判断该订单是否已经发货
		if(!orderMap.containsKey(orderId)){
			orderMap.put(orderId, false);
		}else if(orderMap.get(orderId)){
			return;
		}
		
		try {
			Order o = orderPersistence.getOrderById(orderId);
			if(o != null){
				RenrenApiClient apiClient = RenrenApiClient.getInstance();
				PayService ps = apiClient.getPayService();
				ps.isCompleted(Long.parseLong(o.getOrderId()), new SessionKey(o.getSessionKey()));
				
				resp.getWriter().printf("{\"app_res_user\":%s,\"app_res_order_id\":%s,\"app_res_amount\":%s}", o.getUserId(),o.getOrderId(),o.getAmount());
				RequestDispatcher welcomeDispatcher = req.getRequestDispatcher("/pay.jsp");
				welcomeDispatcher.forward(req, resp);
				return ;
			}
		}catch(NumberFormatException e){
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		
		RequestDispatcher welcomeDispatcher = req.getRequestDispatcher("/error.jsp");
		welcomeDispatcher.forward(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("FinishDealServlet");
		
		String key = (String)req.getParameter("xn_sig_skey");
		String orderId = (String)req.getParameter("xn_sig_order_id");
		String userId = (String)req.getParameter("xn_sig_user");
		
		//TODO 验证key
		System.out.println("Key:"+key+"order:"+orderId+"userid:"+userId);
		
		//TODO 判断该订单是否已经发货
		if(!orderMap.containsKey(orderId)){
			orderMap.put(orderId, false);
		}else if(orderMap.get(orderId)){
			return;
		}
		
		try {
			Order o = orderPersistence.getOrderById(orderId);
			if(o != null){
				RenrenApiClient apiClient = RenrenApiClient.getInstance();
				PayService ps = apiClient.getPayService();
				ps.isCompleted(Long.parseLong(o.getOrderId()), new SessionKey(o.getSessionKey()));
				
				System.out.printf("{\"app_res_user\":%s,\"app_res_order_id\":%s,\"app_res_amount\":%s}", o.getUserId(),o.getOrderId(),o.getAmount());
				resp.getWriter().printf("{\"app_res_user\":%s,\"app_res_order_id\":%s,\"app_res_amount\":%s}", o.getUserId(),o.getOrderId(),o.getAmount());
				RequestDispatcher welcomeDispatcher = req.getRequestDispatcher("/pay.jsp");
				welcomeDispatcher.forward(req, resp);
				return ;
			}
		}catch(NumberFormatException e){
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		
		RequestDispatcher welcomeDispatcher = req.getRequestDispatcher("/error.jsp");
		welcomeDispatcher.forward(req, resp);
	}

}
