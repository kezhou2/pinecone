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

import cc.pinecone.renren.devicecontroller.controller.TestAPI;

import com.tenline.pinecone.platform.model.Device;

public class QueryPineconeServlet extends HttpServlet {

	private static final long serialVersionUID = -8711776634122256591L;

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		JQueryDataTableParamModel param = DataTablesParamUtility.getParam(req);
		
		String sEcho = param.sEcho;
		int iTotalRecords; // total number of records (unfiltered)
		int iTotalDisplayRecords;//value will be set when code filters companies by keyword
		JSONArray data = new JSONArray(); //data that will be shown in the table

		TestAPI t = new TestAPI();
		ArrayList<Device> list = t.getAllDevice1();
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
			row.add(c.getName());
			row.add("Feb 12, 2012. 12:28");
			row.add(c.getCode());
			row.add("<ul class='navbar-icons'>"+
						"<li><a href='#' class='tip' title='Add new option'><i class='icon-plus'></i></a></li>"+
						"<li><a href='#' class='tip' title='View statistics'><i class='icon-reorder'></i></a></li>"+
						"<li><a href='#' class='tip' title='Parameters'><i class='icon-cogs'></i></a></li>"+
					"</ul>");
			data.add(row);
		}
		jsonResponse.put("aaData", data);
			
		resp.setContentType("application/json");
		resp.getWriter().print(jsonResponse.toString());
	}

	
}
