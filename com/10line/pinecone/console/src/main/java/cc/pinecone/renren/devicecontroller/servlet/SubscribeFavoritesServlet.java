package cc.pinecone.renren.devicecontroller.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;

import cc.pinecone.renren.devicecontroller.config.Config;
import cc.pinecone.renren.devicecontroller.controller.AppConfig;
import cc.pinecone.renren.devicecontroller.service.LoginUserDetailsImpl;

public class SubscribeFavoritesServlet extends HttpServlet {

	private static Map<String, Connector> connectorMap = new LinkedHashMap<String, Connector>();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String isDisconnect = request.getParameter("isDisconnect");
		System.out.println("isDisconnect========================"+isDisconnect);
		
		String ids = request.getParameter("ids");
		System.out.println("recived:"+ids);
		Object obj = JSONValue.parse(ids);
		JSONArray array=(JSONArray)obj;
		
		//here acturally received device code from the jsp page.
		String code = request.getParameter("devicecodes");
		System.out.println("recived:"+code);
		String[] deviceCodes = new String[0];
		if(code != null)
			deviceCodes = code.split("_");
		
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
		
		List<String> deviceIds = conf.getFocusDeviceIds();
		if(isDisconnect != null && isDisconnect.equals("true")){
			System.out.println("ready to disconnect");
			for(String deviceCode:deviceIds){
				if(connectorMap.get(deviceCode) != null){
					try {
						connectorMap.get(deviceCode).destroy();
						connectorMap.remove(deviceCode);
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
				System.out.println("#################initial deviceCode:"+deviceCodes[i]);
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
				System.out.println("#################getting data. deviceCode:"+deviceCodes[i]);
				Connector connector = connectorMap.get(deviceCode);
				if(connector != null)
					res = connector.getStringValues();
			}
			result = result+deviceCode+","+res+"/";
		}
		System.out.println(result);
		
		response.setContentType("text/html; charset=utf-8"); 
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control","no-cache");
		PrintWriter out=response.getWriter();
		
		System.out.println("json string------------------------------:"+result);
		out.write(result);
		out.close();
		
	}

}
