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

	/**
	 * subscribe data type:
	 * [ids:[[var1_1,var1_2,var1_3...],[var2_1,var2_2,var2_3...]..], deviceCodes:deviceCode1_deviceCode2_...]
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try{
			String isDisconnect = req.getParameter("isDisconnect");
			System.out.println("isDisconnect========================"+isDisconnect);
			String ids = req.getParameter("ids");
			System.out.println("recived:"+ids);
			JSONArray array = new JSONArray();
			if(ids != null){
				Object obj = JSONValue.parse(ids);
				array=(JSONArray)obj;
			}
			
			//here acturally received device code from the jsp page.
			String code = req.getParameter("devicecodes");
			System.out.println("recived:"+code);
			String[] deviceCodes = new String[0];
			if(code != null)
				deviceCodes = code.split("_");
			
			if(isDisconnect != null && isDisconnect.equals("true")){
				System.out.println("ready to disconnect");
				for(String deviceCode:deviceCodes){
					if(connectorMap.get(deviceCode) != null){
						try {
							connectorMap.get(deviceCode).destroy();
							connectorMap.remove(deviceCode);
							connectorMap.clear();
							return;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			
			String result = "";
			String res = "";
			for(int i=0;i<deviceCodes.length;i++){
				String deviceCode = deviceCodes[i];
		
				if(connectorMap.get(deviceCode) == null){
					try {
						Connector con = new Connector(deviceCode,"pinecone@device."+deviceCode+".publish");
						connectorMap.put(deviceCode, con);
						
						JSONArray varArray = (JSONArray)array.get(i);
						for(int j=0;j<varArray.size();j++)
							con.addVariable(varArray.get(j).toString());
					
					} catch (Exception e) {
						e.printStackTrace();
					}	
				}else{
					Connector connector = connectorMap.get(deviceCode);
					if(connector != null)
						res = connector.getStringValues();
				}
				result = result+deviceCode+","+res+"/";
			}
			System.out.println(result);
			
			resp.setContentType("text/html; charset=utf-8"); 
			resp.setCharacterEncoding("UTF-8");
			resp.setHeader("Cache-Control","no-cache");
			PrintWriter out=resp.getWriter();
			
			System.out.println("json string------------------------------:"+result);
			out.write(result);
			out.close();
		}catch(Exception ex){
			ex.printStackTrace();
			req.getRequestDispatcher("index.html").forward(req, resp);
		}
	}
}
