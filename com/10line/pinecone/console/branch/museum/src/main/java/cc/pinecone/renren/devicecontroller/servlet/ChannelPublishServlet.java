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

@SuppressWarnings("serial")
public class ChannelPublishServlet extends HttpServlet{
	
	private static Map<String, Connector> connectorMap = new LinkedHashMap<String, Connector>();
	private static final Logger logger = Logger.getLogger(ChannelPublishServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if(req.getSession(false)==null)
			req.getRequestDispatcher("index.html").forward(req, resp);
		
		try{
			String varid = req.getParameter("variableid");
			logger.info("recived:"+varid);
			String value = req.getParameter("vvalue");
			logger.info("recived:"+value);
			String devicecode = req.getParameter("devicecode");
			logger.info("recived:"+devicecode);
			
			if(connectorMap.get(devicecode) == null){
				try {
					Connector con = new Connector(devicecode,"pinecone@device."+devicecode+".subscribe");
					connectorMap.put(devicecode, con);
				} catch (Exception e) {
					e.printStackTrace();
				}	
			}
			
			resp.setContentType("text/html; charset=utf-8"); 
			resp.setCharacterEncoding("UTF-8");
			resp.setHeader("Cache-Control","no-cache");
			PrintWriter out=resp.getWriter();
			
			Connector connector = connectorMap.get(devicecode);
			if(connector != null){
				try {
					connector.publish("pinecone@device."+devicecode+".subscribe",varid, value);
					out.write("true");
					out.close();
					return;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			out.write("false");
			out.close();
		}catch(Exception ex){
			ex.printStackTrace();
			req.getRequestDispatcher("index.html").forward(req, resp);
		}
	}

}
