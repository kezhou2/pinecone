package cc.pinecone.renren.devicecontroller.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tenline.pinecone.platform.model.Entity;
import com.tenline.pinecone.platform.model.Item;
import com.tenline.pinecone.platform.model.Variable;
import com.tenline.pinecone.platform.sdk.RESTClient;

import cc.pinecone.renren.devicecontroller.controller.AppConfig;
import cc.pinecone.renren.devicecontroller.dao.PineconeApi;

public class QueryVariableServlet extends HttpServlet {

	private RESTClient client;
	
	@Override
	public void init() throws ServletException {
		super.init();
		client = new RESTClient(AppConfig.BASE_URL);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("QueryVariableServlet");
		String id = (String)req.getParameter("id");
		
		List<Variable> list = new ArrayList<Variable>();
		try {
			ArrayList<Entity> vars = (ArrayList<Entity>) client.get("/device/"+id+"/variables","admin","admin");
			for(Entity ent:vars){
				Variable var = (Variable)ent;
				list.add(var);
			}
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		req.setAttribute("list", list);
		
	}

}
