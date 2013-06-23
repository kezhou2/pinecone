package cc.pinecone.renren.devicecontroller.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.internal.MemoryPersistence;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import cc.pinecone.renren.devicecontroller.controller.AppConfig;

import com.tenline.pinecone.platform.sdk.ChannelClient;

public class ChannelSubscribeServlet extends HttpServlet {

	private static Map<String, Connector> connectorMap = new LinkedHashMap<String, Connector>();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	
		String isDisconnect = req.getParameter("isDisconnect");
		System.out.println("isDisconnect========================"+isDisconnect);
		String ids = req.getParameter("ids");
		System.out.println("recived:"+ids);
		//here acturally received device code from the jsp page.
		String deviceid = req.getParameter("deviceid");
		System.out.println("recived:"+deviceid);
		if(isDisconnect != null && isDisconnect.equals("true")){
			System.out.println("ready to disconnect");
			if(connectorMap.get(deviceid) != null){
				try {
					connectorMap.get(deviceid).destroy();
					connectorMap.remove(deviceid);
					return;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
//		JSONObject json = new JSONObject();
		String result = "";
		
		if(connectorMap.get(deviceid) == null){
			try {
//				Connector con = new Connector(deviceid,"pinecone@device."+deviceid+".subscribe");
				//FIXME need to change back.
				Connector con = new Connector(deviceid,"pinecone@device."+deviceid);
				connectorMap.put(deviceid, con);
				
				Object obj = JSONValue.parse(ids);
				JSONArray array=(JSONArray)obj;
				
				for(int i=0;i<array.size();i++){
					con.addVariable(array.get(i).toString());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}else{
			Connector connector = connectorMap.get(deviceid);
			if(connector != null)
//				json.put("data", connector.getJSONValues());
				result = connector.getStringValues();
		}
		result = ""+deviceid+","+result;
		System.out.println(deviceid);
		System.out.println(result);
		
		resp.setContentType("text/html; charset=utf-8"); 
		resp.setCharacterEncoding("UTF-8");
		resp.setHeader("Cache-Control","no-cache");
		PrintWriter out=resp.getWriter();
		
		System.out.println("json string------------------------------:"+result);
		out.write(result);
		out.close();
	}
}
