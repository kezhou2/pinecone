package cc.pinecone.renren.devicecontroller.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

@SuppressWarnings("serial")
public class DeviceStatusServlet extends HttpServlet {

	private static Map<String, Connector> connectorMap = new LinkedHashMap<String, Connector>();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doGet(req, resp);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String isDisconnect = request.getParameter("isDisconnect");
		System.out.println("isDisconnect========================"+isDisconnect);
		
		//device codes
		String jsonData = request.getParameter("jsonData");
		System.out.println("recived:"+jsonData);
		JSONArray array = (JSONArray)JSONValue.parse(jsonData);
		
		if(isDisconnect != null && isDisconnect.equals("true")){
			System.out.println("ready to disconnect");
			for(int i=0;i<array.size();i++){
				JSONObject obj = (JSONObject)array.get(i);
				if(connectorMap.get(obj.get("deviceCode")) != null){
					try {
						connectorMap.get(obj.get("deviceCode")).destroy();
						connectorMap.remove(obj.get("deviceCode"));
						return;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

		
		JSONArray result = new JSONArray();
		for(int i=0;i<array.size();i++){
			JSONObject obj = (JSONObject)array.get(i);
			String deviceCode = (String)obj.get("deviceCode");
			long deviceId = (Long)obj.get("deviceId");
	
			if(connectorMap.get(deviceCode) == null){
				System.out.println("#################initial deviceCode:"+deviceCode);
				try {
					Connector con = new Connector(""+deviceId,"pinecone@device."+deviceCode+".publish");
					connectorMap.put(deviceCode, con);
					
				} catch (Exception e) {
					e.printStackTrace();
				}	
			}else{
				System.out.println("#################getting data. deviceCode:"+deviceCode);
				Connector connector = connectorMap.get(deviceCode);
				if(connector != null){
					JSONObject o = new JSONObject();
					o.put("deviceCode", deviceCode);
					o.put("deviceId", deviceId);
					o.put("status", connector.getDeviceStatus());
					result.add(o);
				}
			}
		}
		
		response.setContentType("text/html; charset=utf-8"); 
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control","no-cache");
		PrintWriter out=response.getWriter();
		
		System.out.println("json string------------------------------:"+result.toJSONString());
		out.write(result.toJSONString());
		out.close();
	}

}
