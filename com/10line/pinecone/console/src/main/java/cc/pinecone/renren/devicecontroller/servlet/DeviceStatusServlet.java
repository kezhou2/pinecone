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
import org.json.simple.JSONValue;

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

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("device status check.");
		String isDisconnect = req.getParameter("isDisconnect");
		System.out.println("isDisconnect========================"+isDisconnect);
		String codes = req.getParameter("codes");
		System.out.println("recived:"+codes);

		String[] codeArray = codes.split(";");
		
		if(isDisconnect != null && isDisconnect.equals("true")){
			System.out.println("ready to disconnect");
			for(String code:codeArray){
				if(connectorMap.get(code) != null){
					try {
						connectorMap.get(code).destroy();
						connectorMap.remove(code);
						return;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		String result = "";
		for(String code:codeArray){
			if(connectorMap.get(code) == null){
				try {
					Connector con = new Connector(code,"pinecone@device."+code+".publish");
					//FIXME need to change back.
	//				Connector con = new Connector(deviceCode,"pinecone@device."+deviceCode);
					connectorMap.put(code, con);
				} catch (Exception e) {
					e.printStackTrace();
				}	
			}else{
				Connector connector = connectorMap.get(code);
				if(connector != null)
					result = result+code+":"+connector.getDeviceStatus();
			}
		}
		System.out.println(result);
		
		resp.setContentType("text/html; charset=utf-8"); 
		resp.setCharacterEncoding("UTF-8");
		resp.setHeader("Cache-Control","no-cache");
		PrintWriter out=resp.getWriter();
		
		System.out.println("json string/////////////:"+result);
		out.write(result);
		out.close();
	}

}
