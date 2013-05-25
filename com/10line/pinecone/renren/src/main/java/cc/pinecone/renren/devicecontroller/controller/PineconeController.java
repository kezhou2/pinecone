package cc.pinecone.renren.devicecontroller.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cc.pinecone.renren.devicecontroller.dao.PineconeApi;

import com.tenline.pinecone.platform.model.Entity;
import com.tenline.pinecone.platform.model.Item;
import com.tenline.pinecone.platform.model.Variable;
import com.tenline.pinecone.platform.sdk.RESTClient;

@Controller
public class PineconeController {
	
	@Autowired
	private MessageSource msgSrc;
	
	private static PineconeApi pApi;
	
	private static RESTClient client;
	
	private static final Logger logger = LoggerFactory
			.getLogger(PageController.class);
	
	public RESTClient getRESTClient() {
		if(client == null){
			client = new RESTClient(AppConfig.BASE_URL);
		}
		return client;
	}
	
	public PineconeApi getPineconeAPI(){
		if(pApi == null){
			pApi = new PineconeApi();
		}
		return pApi;
	}
	
	@RequestMapping(value = "/disconnectdevice.html", method=RequestMethod.GET)
	public void disconnectDevice(HttpServletRequest request,HttpServletResponse response){
		logger.info("queryVariable.html");
		System.out.println("queryVariable");
		String id = request.getParameter("id");
		System.out.println(id);
		//TODO
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print("true");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/queryvariable.html", method=RequestMethod.GET)
	public String queryVariable(HttpServletRequest request,HttpServletResponse response){
		logger.info("queryVariable.html");
		System.out.println("queryVariable");
		String id = request.getParameter("id");
		System.out.println(id);
		
		List<Variable> list = new ArrayList<Variable>();
		try {
			ArrayList<Entity> vars = (ArrayList<Entity>) this.getRESTClient().get("/device/"+id+"/variables","admin","admin");
			for(Entity ent:vars){
				Variable var = (Variable)ent;
				ArrayList<Item> itemlist = new ArrayList<Item>();
				ArrayList<Entity> items = (ArrayList<Entity>) client.get("/variable/"+var.getId()+"/items","admin","admin");
				for(Entity ee:items){
					Item item = (Item)ee;
					itemlist.add(item);
				}
				var.setItems(itemlist);
				list.add(var);
			}
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.setAttribute("list", list);
		
		return "variable";
	}

}
