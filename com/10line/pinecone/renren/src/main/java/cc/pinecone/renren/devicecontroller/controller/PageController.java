package cc.pinecone.renren.devicecontroller.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cc.pinecone.renren.devicecontroller.dao.PineconeApi;

import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.sdk.RESTClient;

/**
 * Sample controller for going to the home page with a message
 */
@Controller
public class PageController {

	@Autowired
	private MessageSource msgSrc;
	
	private static PineconeApi pApi;
	
	private static RESTClient client;
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

	private static final Logger logger = LoggerFactory
			.getLogger(PageController.class);

	@RequestMapping(value = "/login.html")
	public String login(HttpServletRequest request,HttpServletResponse response) {
		logger.info("login.html");
		System.out.println("login.html");
		return "login";
	}
	
	@RequestMapping(value = "/register.html")
	public String register(Model model) {
		logger.info("register.html");
		System.out.println("register.html");
		return "register";
	}
	
	@RequestMapping(value = "/index.html")
	public String index(HttpServletRequest request,HttpServletResponse response) {
		logger.info("index.html");
		System.out.println("index.html");
		
		System.out.println(request.getSession().getAttribute("access_token"));
		System.out.println(request.getSession().getAttribute("xn_sig_user"));
		System.out.println(request.getSession().getAttribute("xn_sig_session_key"));
		return "index";
	}

	@RequestMapping(value = "/devices.html")
	public String devices(HttpServletRequest request,HttpServletResponse response) {
		logger.info("devices.html");
		System.out.println("devices.html");
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		System.out.println("username:"+username+"\npassword:"+password);
		
		User user = pApi.login(username, password);
		return "devices";
	}

	@RequestMapping(value = "/setting.html")
	public String registry(Model model) {
		logger.info("setting.html");
		System.out.println("setting.html");
		return "setting";
	}

	@RequestMapping(value = "/friends.html")
	public String friends(Model model) {
		logger.info("friends.html");
		System.out.println("friends.html");
		return "friends";
	}

}
