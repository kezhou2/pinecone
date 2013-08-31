package cc.pinecone.renren.devicecontroller.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

@SuppressWarnings("serial")
public class DeviceStatusServlet extends HttpServlet {

	private static Map<String, Connector> connectorMap = new LinkedHashMap<String, Connector>();
	private static final Logger logger = Logger.getLogger(DeviceStatusServlet.class);
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
		if(request.getSession(false)==null)
			request.getRequestDispatcher("index.html").forward(request, response);
		
		try{
			String isDisconnect = request.getParameter("isDisconnect");
			logger.info("isDisconnect========================"+isDisconnect);
			
			//device codes
			String jsonData = request.getParameter("jsonData");
			logger.info("recived:"+jsonData);
			JSONArray array = (JSONArray)JSONValue.parse(jsonData);
			
			if(isDisconnect != null && isDisconnect.equals("true")){
				logger.info("ready to disconnect");
				for(int i=0;i<array.size();i++){
					JSONObject obj = (JSONObject)array.get(i);
					if(connectorMap.get(obj.get("deviceCode")) != null){
						connectorMap.get(obj.get("deviceCode")).destroy();
						connectorMap.remove(obj.get("deviceCode"));
						connectorMap.clear();
						return;
					
					}
				}
			}
	
			
			JSONArray result = new JSONArray();
			for(int i=0;i<array.size();i++){
				JSONObject obj = (JSONObject)array.get(i);
				String deviceCode = (String)obj.get("deviceCode");
				long deviceId = (Long)obj.get("deviceId");
		
				if(connectorMap.get(deviceCode) == null){
					logger.info("#################initial deviceCode:"+deviceCode);
					Connector con = new Connector(""+deviceId,"pinecone@device."+deviceCode+".publish");
					connectorMap.put(deviceCode, con);
				}else{
					logger.info("#################getting data. deviceCode:"+deviceCode);
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
			
			logger.info("json string------------------------------:"+result.toJSONString());
			out.write(result.toJSONString());
			out.close();
		}catch(Exception ex){
			ex.printStackTrace();
			request.getRequestDispatcher("index.html").forward(request, response);
		}
	}

}
