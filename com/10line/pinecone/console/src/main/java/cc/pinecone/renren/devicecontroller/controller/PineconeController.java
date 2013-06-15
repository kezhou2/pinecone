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
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cc.pinecone.renren.devicecontroller.dao.PineconeApi;
import cc.pinecone.renren.devicecontroller.service.LoginUserDetailsImpl;

import com.tenline.pinecone.platform.model.Authority;
import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Entity;
import com.tenline.pinecone.platform.model.Item;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.model.Variable;
import com.tenline.pinecone.platform.sdk.RESTClient;

@Controller
public class PineconeController {

	@Autowired
	private MessageSource msgSrc;

	private static PineconeApi pApi;

	private static RESTClient client;
	
	private final int OVERTIME = 20000;
	private final int BEAT_TIME = 2000;
	private final String ADMIN_NAME = "admin";
	private final String ADMIN_PWD = "admin";

	private static final Logger logger = LoggerFactory
			.getLogger(PageController.class);

	public RESTClient getRESTClient() {
		if (client == null) {
			client = new RESTClient(AppConfig.REST_URL);
		}
		return client;
	}

	public PineconeApi getPineconeAPI() {
		if (pApi == null) {
			pApi = new PineconeApi();
		}
		return pApi;
	}

	@RequestMapping(value = "/disconnectdevice.html", method = RequestMethod.GET)
	public void disconnectDevice(HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("disconnectdevice.html");
		System.out.println("disconnectdevice");
		String id = request.getParameter("id");
		System.out.println("id:" + id);

		try {
			this.getRESTClient().delete("/device/" + id);
			PrintWriter out = response.getWriter();
			out.print("true");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/activedevice.html", method = RequestMethod.GET)
	public void activeDevice(HttpServletRequest request,
			HttpServletResponse response) {
		SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");  
		String username = securityContextImpl.getAuthentication().getName();
		String password = securityContextImpl.getAuthentication().getCredentials().toString();
		logger.info("activeDevice.html");
		System.out.println("activeDevice");
		String code = request.getParameter("code");
		String name = request.getParameter("name");
		System.out.println("code:" + code + " name:" + name);
		UserDetails ud = (UserDetails)securityContextImpl.getAuthentication().getPrincipal();
		String userid = null;
		if(ud instanceof LoginUserDetailsImpl){
			LoginUserDetailsImpl lud = (LoginUserDetailsImpl)ud;
			userid = lud.getUserid();
		}

		try {
			Device dev = (Device) (this.getRESTClient().get("/device/search/codes?code=" + code,
					username, password)).toArray()[0];

			String msg = client.post("/device/" + dev.getId() + "/user",
					"/user/" + userid);
			System.out.println("adding device to user:" + msg);
			Device device = new Device();
			device.setName(name);
			msg = client.put("/device/" + dev.getId(), device);
			System.out.println("changing device name:" + msg);

			PrintWriter out = response.getWriter();
			out.print("true");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setCharacterEncoding("UTF-8");
	}
	
	@RequestMapping(value = "/registeruser.html")
	public String registerUser(HttpServletRequest request,	HttpServletResponse response) {
		System.out.println("registeruser.html");
		String username = (String)request.getParameter("username");
		String password = (String)request.getParameter("password1");
		String email = (String)request.getParameter("emailValid");
		System.out.println("username:"+username+"\npassword:"+password+"\nemail:"+email);
		
		try {
			if(username == null || password == null){
				request.setAttribute("isregister", "false");
				return "register";
			}
			
			ArrayList<Entity> users = (ArrayList<Entity>) this.getRESTClient().get(
					"/user/search/names?name=" + username, ADMIN_NAME, ADMIN_PWD);
			if (users.size() == 0) {
				User u = new User();
				u.setName(username);
				u.setPassword(password);
				u.setEmail(email);
				u.setId(Long.getLong(client.post("/user", u)));

				Authority authority = new Authority();
				authority.setAuthority("ROLE_USER");
				authority.setUserName(u.getName());
				authority.setId(Long.getLong(client.post("/authority", authority)));
				
				client.post("/authority/" + authority.getId() + "/user", "/user/" + u.getId());

				request.setAttribute("isregister", "true");
				return "login";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "register";
	}
	
	
	
	@RequestMapping(value = "/queryvariable.html", method = RequestMethod.GET)
	public String queryVariable(HttpServletRequest request,
			HttpServletResponse response) {
		SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");  
		String username = securityContextImpl.getAuthentication().getName();
		String password = securityContextImpl.getAuthentication().getCredentials().toString();
		
		logger.info("queryVariable.html");
		System.out.println("queryVariable");
		String id = request.getParameter("id");
		System.out.println(id);

		List<Variable> list = new ArrayList<Variable>();
		Device dev = null;
		try {
			ArrayList<Entity> vars = (ArrayList<Entity>) this.getRESTClient()
					.get("/device/" + id + "/variables", username, password);
			for (Entity ent : vars) {
				Variable var = (Variable) ent;
				ArrayList<Item> itemlist = new ArrayList<Item>();
				ArrayList<Entity> items = (ArrayList<Entity>) client
						.get("/variable/" + var.getId() + "/items", username,
								password);
				for (Entity ee : items) {
					Item item = (Item) ee;
					itemlist.add(item);
				}
				var.setItems(itemlist);
				list.add(var);
			}
			
			ArrayList<Entity> devs = (ArrayList<Entity>) this.getRESTClient().get("/device/"+id,username,password);
			dev = (Device) devs.get(0);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		request.setAttribute("list", list);
		request.setAttribute("device", dev);
		response.setCharacterEncoding("UTF-8");
		return "variable";
	}
	
	
}
