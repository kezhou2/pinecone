package cc.pinecone.renren.devicecontroller.servlet;

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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;

import cc.pinecone.renren.devicecontroller.controller.AppConfig;
import cc.pinecone.renren.devicecontroller.controller.TestAPI;
import cc.pinecone.renren.devicecontroller.service.LoginUserDetailsImpl;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Entity;
import com.tenline.pinecone.platform.sdk.RESTClient;

public class QueryDeviceServlet extends HttpServlet {

	private static final long serialVersionUID = -8711776634122256591L;
	
	private RESTClient client = new RESTClient(AppConfig.REST_URL);

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		SecurityContextImpl securityContextImpl = (SecurityContextImpl) req.getSession().getAttribute("SPRING_SECURITY_CONTEXT");  
		String username = securityContextImpl.getAuthentication().getName();
		System.out.println("Username:" + username);  
		String password = securityContextImpl.getAuthentication().getCredentials().toString();
		System.out.println("Credentials:" + password);
		UserDetails ud = (UserDetails)securityContextImpl.getAuthentication().getPrincipal();
		String userid = null;
		if(ud instanceof LoginUserDetailsImpl){
			LoginUserDetailsImpl lud = (LoginUserDetailsImpl)ud;
			userid = lud.getUserid();
		}
		
		
		JQueryDataTableParamModel param = DataTablesParamUtility.getParam(req);
		
		String sEcho = param.sEcho;
		int iTotalRecords; // total number of records (unfiltered)
		int iTotalDisplayRecords;//value will be set when code filters companies by keyword
		JSONArray data = new JSONArray(); //data that will be shown in the table

		ArrayList<Device> list = new ArrayList<Device>();
		try {
			ArrayList<Entity> devs = (ArrayList<Entity>) client.get("/user/"+userid+"/devices",username,password);
			for(Entity e:devs){
				Device dev = (Device) e;
				list.add(dev);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	
		System.out.println("size:"+list.size());
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
				case 1:
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
			JSONArray row = new JSONArray();
			row.add("<a href='img/demo/big.jpg' title='' class='lightbox'><img src='http://placehold.it/37x37' alt='' /></a>");
			row.add(c.getId());
			row.add(c.getName());
			row.add(c.getCode());
			row.add("<ul class='table-controls'>"+
						"<li><a href='queryvariable.html?id="+c.getId()+"' class='btn tip' title='View'><i class='ico-eye-open'></i></a></li>"+
						"<li><a href='#' class='btn tip' onclick='disconnect("+c.getId()+")' title='Disconnect'><i class=' ico-minus'></i></a></li>"+
						"<li><a href='#' class='btn tip' title='Share'><i class='ico-share'></i></a></li>"+
					"</ul>");
			data.add(row);
		}
		jsonResponse.put("aaData", data);
		System.out.println(jsonResponse.toJSONString());
		resp.setContentType("application/json");
		resp.getWriter().print(jsonResponse.toString());
	}

	
}
