package cc.pinecone.renren.devicecontroller.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cc.pinecone.renren.devicecontroller.config.Config;
import cc.pinecone.renren.devicecontroller.dao.PineconeApi;
import cc.pinecone.renren.devicecontroller.service.LoginUserDetailsImpl;

import com.tenline.pinecone.platform.sdk.RESTClient;

@Controller
public class AlermController {
	@Autowired
	private MessageSource msgSrc;
	
	private static PineconeApi pApi;
	
	public final String TYPE_NUMERIC = "numeric";
	public final String TYPE_STRING = "string";
	public final String LOG = "log";
	public final String PAGE = "page";
	public final String SOUND = "sound";
	public final String SMS = "sms";
	public final String EMAIL = "email";
	
	private static RESTClient client;
	public RESTClient getRESTClient() {
		if(client == null){
			client = new RESTClient(AppConfig.REST_URL);
		}
		return client;
	}
	
	public PineconeApi getPineconeAPI(){
		if(pApi == null){
			pApi = new PineconeApi();
		}
		return pApi;
	}

	private static final Logger logger = Logger.getLogger(AlermController.class);
	
	@RequestMapping(value = "/setalerm.html")
	public void setAlerm(HttpServletRequest request,HttpServletResponse response) throws FileNotFoundException, IOException, ServletException {
		logger.info("setalerm.html");
		
		String deviceId = request.getParameter("deviceId");
		String variableId = request.getParameter("variableId");
		String conditionType = request.getParameter("conditionType");
		String condition = request.getParameter("condition");
		String variablevalue = request.getParameter("variablevalue");
		String clog = request.getParameter("clog");
		String cpage = request.getParameter("cpage");
		String csound = request.getParameter("csound");
		String csms = request.getParameter("csms");
		String cemail = request.getParameter("cemail");
		String cellphone = request.getParameter("cellphone");
		String email = request.getParameter("email");
		
		SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");  
//		String username = securityContextImpl.getAuthentication().getName();
//		String password = securityContextImpl.getAuthentication().getCredentials().toString();
		UserDetails ud = (UserDetails)securityContextImpl.getAuthentication().getPrincipal();
		String userid = null;
		if(ud instanceof LoginUserDetailsImpl){
			LoginUserDetailsImpl lud = (LoginUserDetailsImpl)ud;
			userid = lud.getUserid();
		}

		//get user config
		String path =  request.getSession().getServletContext().getRealPath("/");
		Config conf = Config.getInstance(userid, path+File.separatorChar+AppConfig.getCachePath());
		
		JSONObject ob = new JSONObject();
//		ob.put("conditionType", conditionType);
//		ob.put("condition", condition);
//		ob.put("variablevalue", variablevalue);
//		ob.put("clog", clog);
//		ob.put("cpage", cpage);
//		ob.put("csound", csound);
//		ob.put("csms", csms);
//		ob.put("cemail", cemail);
//		ob.put("cellphone", cellphone);
//		ob.put("email", email);
		
		conf.addAlerm(deviceId, variableId, ob.toJSONString());
		
		PrintWriter out = response.getWriter();
		out.print("true");
	}
}
