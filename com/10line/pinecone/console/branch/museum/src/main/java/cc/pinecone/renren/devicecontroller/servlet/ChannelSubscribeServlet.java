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
import org.json.simple.JSONValue;

@SuppressWarnings("serial")
public class ChannelSubscribeServlet extends HttpServlet {

	private static Map<String, Connector> connectorMap = new LinkedHashMap<String, Connector>();
	private static final Logger logger = Logger.getLogger(ChannelSubscribeServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	/**
	 * subscribe data type:
	 * [ids:[[var1_1,var1_2,var1_3...],[var2_1,var2_2,var2_3...]..], deviceCodes:deviceCode1_deviceCode2_...]
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if(req.getSession(false)==null)
			req.getRequestDispatcher("index.html").forward(req, resp);
		
		try{
			String isDisconnect = req.getParameter("isDisconnect");
			logger.info("isDisconnect========================"+isDisconnect);
			String ids = req.getParameter("ids");
			logger.info("recived:"+ids);
			JSONArray array = new JSONArray();
			if(ids != null){
				Object obj = JSONValue.parse(ids);
				array=(JSONArray)obj;
			}
			
			//here acturally received device code from the jsp page.
			String code = req.getParameter("devicecodes");
			logger.info("recived:"+code);
			String[] deviceCodes = new String[0];
			if(code != null)
				deviceCodes = code.split("_");
			
			if(isDisconnect != null && isDisconnect.equals("true")){
				logger.info("ready to disconnect");
				for(String deviceCode:deviceCodes){
					if(connectorMap.get(deviceCode) != null){
						connectorMap.get(deviceCode).destroy();
						connectorMap.remove(deviceCode);
						connectorMap.clear();
						return;
					}
				}
			}
			
			String result = "";
			String res = "";
			for(int i=0;i<deviceCodes.length;i++){
				String deviceCode = deviceCodes[i];
		
				if(connectorMap.get(deviceCode) == null){
					Connector con = new Connector(deviceCode,"pinecone@device."+deviceCode+".publish");
					connectorMap.put(deviceCode, con);
					
					JSONArray varArray = (JSONArray)array.get(i);
					for(int j=0;j<varArray.size();j++)
						con.addVariable(varArray.get(j).toString());	
				}else{
					Connector connector = connectorMap.get(deviceCode);
					if(connector != null)
						res = connector.getStringValues();
				}
				result = result+deviceCode+","+res+"/";
			}
			logger.info(result);
			
			resp.setContentType("text/html; charset=utf-8"); 
			resp.setCharacterEncoding("UTF-8");
			resp.setHeader("Cache-Control","no-cache");
			PrintWriter out=resp.getWriter();
			
			logger.info("json string------------------------------:"+result);
			out.write(result);
			out.close();
		}catch(Exception ex){
			ex.printStackTrace();
			req.getRequestDispatcher("index.html").forward(req, resp);
		}
	}
}
