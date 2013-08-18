package cc.pinecone.renren.devicecontroller.servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;

import cc.pinecone.renren.devicecontroller.config.Config;
import cc.pinecone.renren.devicecontroller.controller.AppConfig;
import cc.pinecone.renren.devicecontroller.model.ExDeviceInfo;
import cc.pinecone.renren.devicecontroller.service.LoginUserDetailsImpl;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Entity;
import com.tenline.pinecone.platform.sdk.RESTClient;

public class QueryDeviceServlet extends HttpServlet {

	private static final long serialVersionUID = -8711776634122256591L;
	private static final Logger logger = Logger.getLogger(QueryDeviceServlet.class);
	
//	private final int PAGE_NUM = 20;
	
	private RESTClient client = new RESTClient(AppConfig.REST_URL);

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if(req.getSession(false)==null)
			req.getRequestDispatcher("index.html").forward(req, resp);
		
		try{
			//get user name ,password, userid
			SecurityContextImpl securityContextImpl = (SecurityContextImpl) req.getSession().getAttribute("SPRING_SECURITY_CONTEXT");  
			String username = securityContextImpl.getAuthentication().getName();
			logger.info("Username:" + username);  
			String password = securityContextImpl.getAuthentication().getCredentials().toString();
			logger.info("Credentials:" + password);
			UserDetails ud = (UserDetails)securityContextImpl.getAuthentication().getPrincipal();
			String userid = null;
			if(ud instanceof LoginUserDetailsImpl){
				LoginUserDetailsImpl lud = (LoginUserDetailsImpl)ud;
				userid = lud.getUserid();
			}
			
			//get user config
			String path =  req.getSession().getServletContext().getRealPath("/");
			logger.info("path:"+path);
			Config conf = Config.getInstance(userid, path+File.separatorChar+AppConfig.getCachePath());
			
			//get request parameter from http request
			JQueryDataTableParamModel param = DataTablesParamUtility.getParam(req);
			
			String sEcho = param.sEcho;
			int iTotalRecords; // total number of records (unfiltered)
			int iTotalDisplayRecords;//value will be set when code filters companies by keyword
			JSONArray data = new JSONArray(); //data that will be shown in the table
	
			ArrayList<Device> list = new ArrayList<Device>();
//			int startPage = param.iDisplayStart/PAGE_NUM;
//			int startIndex = param.iDisplayStart%PAGE_NUM;
//			int pageCount = (param.iDisplayLength - param.iDisplayStart) / PAGE_NUM + 1;
			
			try {
				ArrayList<Entity> devs = (ArrayList<Entity>) client.get("/user/"+userid+"/devices",username,password);
				for(Entity e:devs){
					Device dev = (Device) e;
					list.add(dev);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		
			logger.info("size:"+list.size());
			iTotalRecords = list.size();
			List<Device> devices = new LinkedList<Device>();
			for(Device d : list){
				if(d.getName() == null){
					continue;
				}
				if(	d.getName().toLowerCase().contains(param.sSearch.toLowerCase())){
					devices.add(d);
					continue;
				}
				
				if(d.getCode() == null){
					continue;
				}
				if(d.getCode().toLowerCase().contains(param.sSearch.toLowerCase())){
					devices.add(d);
					continue;
				}
				devices.add(d);
			}
			iTotalDisplayRecords = devices.size();//Number of companies that matches search criterion should be returned
			
			final int sortColumnIndex = param.iSortColumnIndex;
			final int sortDirection = param.sSortDirection.equals("asc") ? -1 : 1;
			
			Collections.sort(devices, new Comparator<Device>(){
				@Override
				public int compare(Device c1, Device c2) {	
					switch(sortColumnIndex){
					case 2:
						return c1.getName().compareTo(c2.getName()) * sortDirection;
					case 3:
						return c1.getCode().compareTo(c2.getCode()) * sortDirection;
					}
					return 0;
				}
			});
			
			if(devices.size()< param.iDisplayStart + param.iDisplayLength)
				devices = devices.subList(param.iDisplayStart, devices.size());
			else
				devices = devices.subList(param.iDisplayStart, param.iDisplayStart + param.iDisplayLength);
		
	
			JSONObject jsonResponse = new JSONObject();
				
			jsonResponse.put("sEcho", sEcho);
			jsonResponse.put("iTotalRecords", iTotalRecords);
			jsonResponse.put("iTotalDisplayRecords", iTotalDisplayRecords);
				
			for(Device c : devices){
				ExDeviceInfo info = conf.getDeviceExtInfo(c.getId().toString());
				JSONArray row = new JSONArray();
				row.add("<a href='img/demo/big.jpg' title='' class='lightbox'><img src='img/device_card.png' alt='' /></a>");
				row.add(c.getId());
				row.add(c.getName());
				row.add(c.getCode());
				row.add(info.getMacId());
				row.add(info.getAddress());
				if(conf.getDevice(""+c.getId()) == null){
					row.add("<ul class='table-controls'>"+
								"<li><a href='variable.html?id="+c.getId()+"' class='btn tip' title='View'><i class='fam-zoom'></i></a></li>"+
								"<li><a href='#' class='btn tip' onclick='disconnect("+c.getId()+")' title='Disconnect'><i class='fam-disconnect'></i></a></li>"+
								"<li><a href='#' class='btn tip' onclick='editDeviceInfo("+c.getId()+")' title='Edit Information'><i class='fam-application-edit'></i></a></li>"+
								"<li><a id='device"+c.getId()+"' href='#' class='btn tip' onclick='addDevice("+c.getId()+")' title='Add to favorites'><i class='fam-bell-add'></i></a></li>"+
							"</ul>");
				}else{
					row.add("<ul class='table-controls'>"+
								"<li><a href='variable.html?id="+c.getId()+"' class='btn tip' title='View'><i class='fam-zoom'></i></a></li>"+
								"<li><a href='#' class='btn tip' onclick='disconnect("+c.getId()+")' title='Disconnect'><i class='fam-disconnect'></i></a></li>"+
								"<li><a href='#' class='btn tip' onclick='editDeviceInfo("+c.getId()+")' title='Edit Information'><i class='fam-application-edit'></i></a></li>"+
								"<li><a id='device"+c.getId()+"' href='#' class='btn tip' onclick='removeDevice("+c.getId()+")' title='Remove from favorites'><i class='fam-bell-delete'></i></a></li>"+
							"</ul>");
				}
				data.add(row);
			}
			jsonResponse.put("aaData", data);
			logger.info(jsonResponse.toJSONString());
			resp.setCharacterEncoding("UTF-8");
			resp.setContentType("application/json");
			resp.getWriter().print(jsonResponse.toString());
		}catch(Exception ex){
			ex.printStackTrace();
			req.getRequestDispatcher("index.html").forward(req, resp);
		}
	}

	
}
