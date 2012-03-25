package com.tenline.game.simulation.moneytree.client;

import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.user.client.ui.HTML;
import com.tenline.game.simulation.moneytree.shared.RenrenConfig;

public class OrderWindow extends Window {
	
	public OrderWindow(long orderId, String token) {
		setSize("350", "280");
		FitLayout fitLayout = new FitLayout();
		this.setLayout(fitLayout);
		
		String html = "<form method=\"post\" target=\"_top\" action=\""+RenrenConfig.SUBMIT_ORDER_URL+"\">"+
	      "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"+
	        "<tr>"+
	          "<td width=\"20%\" class=\"infoLabel\">应用ID：</td>"+
	          "<td><input type=\"text\" id=\"app_id\" name=\"app_id\" value=\""+RenrenConfig.APP_ID+"\"></td>"+
	        "</tr>"+
	        "<tr>"+
	          "<td>订单号：</td>"+
	          "<td><input type=\"text\" id=\"order_number\" name=\"order_number\" value=\""+orderId+"\"></td>"+
	        "</tr>"+
	        "<tr>"+
	          "<td>Token：</td>"+
	          "<td><input type=\"text\" id=\"token\" name=\"token\" value=\""+token+"\"></td>"+
	        "</tr>"+
	        "<tr>"+
	          "<td>跳转链接</td>"+
	          "<td><input type=\"text\" id=\"redirect_url\" name=\"redirect_url\" value=\""+RenrenConfig.CALLBACK_URL+"\"></td>"+
	        "</tr>"+
	        "<tr>"+
	          "<td><input type=\"submit\" value=\"确认支付\"></td>"+
	          "<td>&nbsp;</td>"+
	        "</tr>"+
	      "</table>"+
	      "<p><br>"+
	      "</p>"+
	"</form>";
		
		HTML htmlPanel = new HTML(html, true);
		OrderWindow.this.add(htmlPanel);
	}
	
	 
}
