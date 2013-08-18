package cc.pinecone.renren.devicecontroller.servlet;

import java.io.File;
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
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;

import cc.pinecone.renren.devicecontroller.config.Config;
import cc.pinecone.renren.devicecontroller.controller.AppConfig;
import cc.pinecone.renren.devicecontroller.service.LoginUserDetailsImpl;

@SuppressWarnings("serial")
public class SubscribeFavoritesServlet extends HttpServlet {

	private static Map<String, Connector> connectorMap = new LinkedHashMap<String, Connector>();
	private static final Logger logger = Logger.getLogger(SubscribeFavoritesServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
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
		String code = request.getParameter("devicecodes");
		logger.info("recived deviceCodes:"+code);
		String[] deviceCodes = new String[0];
		if(code != null)
			deviceCodes = code.split("_");
		
		if(isDisconnect != null && isDisconnect.equals("true")){
			logger.info("ready to disconnect");
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

		//variable ids
		String ids = request.getParameter("ids");
		logger.info("recived ids*****:"+ids);
		Object obj = JSONValue.parse(ids);
		JSONArray array=(JSONArray)obj;
		
		//device ids
		String devids = request.getParameter("deviceids");
		logger.info("recived deviceIds:"+devids);
		String[] deviceIds = new String[0];
		if(code != null)
			deviceIds = devids.split("_");
		
		SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");  
		UserDetails ud = (UserDetails)securityContextImpl.getAuthentication().getPrincipal();
		String userid = null;
		if(ud instanceof LoginUserDetailsImpl){
			LoginUserDetailsImpl lud = (LoginUserDetailsImpl)ud;
			userid = lud.getUserid();
		}

		//get user config
		String path =  request.getSession().getServletContext().getRealPath("/");
		Config conf = Config.getInstance(userid, path+File.separatorChar+AppConfig.getCachePath());

		JSONArray result = new JSONArray();
		for(int i=0;i<deviceCodes.length;i++){
			
			String deviceCode = deviceCodes[i];
			String deviceId = deviceIds[i];
	
			if(connectorMap.get(deviceCode) == null){
				logger.info("#################initial deviceCode:"+deviceCode);
				try {
					Connector con = new Connector(deviceId,"pinecone@device."+deviceCode+".publish");
					connectorMap.put(deviceCode, con);
					
					JSONArray varArray = (JSONArray)array.get(i);
					for(int j=0;j<varArray.size();j++)
						con.addVariable(varArray.get(j).toString());
				
				} catch (Exception e) {
					e.printStackTrace();
				}	
			}else{
				logger.info("#################getting data. deviceCode:"+deviceCodes[i]);
				Connector connector = connectorMap.get(deviceCode);
				if(connector != null){
					
					JSONObject o = new JSONObject();
					o.put("deviceCode", deviceCode);
					o.put("value", connector.getJSONValue(conf));
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
			request.getRequestDispatcher("index.html").forward(request, response);
		}
	}

}
