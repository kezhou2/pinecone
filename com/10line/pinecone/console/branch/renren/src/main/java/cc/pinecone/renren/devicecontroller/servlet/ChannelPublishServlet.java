package cc.pinecone.renren.devicecontroller.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

@SuppressWarnings("serial")
public class ChannelPublishServlet extends HttpServlet{
	
	private static Map<String, Connector> connectorMap = new LinkedHashMap<String, Connector>();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String varid = req.getParameter("variableid");
		System.out.println("recived:"+varid);
		String value = req.getParameter("vvalue");
		System.out.println("recived:"+value);
		String deviceid = req.getParameter("deviceid");
		System.out.println("recived:"+deviceid);
		
		if(connectorMap.get(deviceid) == null){
			try {
				Connector con = new Connector(deviceid);
				connectorMap.put(deviceid, con);
				
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		
		resp.setContentType("text/html; charset=utf-8"); 
		resp.setCharacterEncoding("UTF-8");
		resp.setHeader("Cache-Control","no-cache");
		PrintWriter out=resp.getWriter();
		
		Connector connector = connectorMap.get(deviceid);
		if(connector != null){
			try {
				connector.publish(varid, value);
				out.write("true");
				out.close();
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		out.write("false");
		out.close();
	}

}
