package cc.pinecone.renren.devicecontroller.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tenline.pinecone.platform.model.User;

/**
 * Sample controller for going to the home page with a message
 */
@Controller
public class PageController {

	private MessageSource msgSrc;

	@Autowired
	public void AccountsController(MessageSource msgSrc) {
		this.msgSrc = msgSrc;
	}

	private static final Logger logger = LoggerFactory
			.getLogger(PageController.class);

	@RequestMapping(value = "/redirect.html")
	public String redirect(Model model) {
//		String datePat = msgSrc.getMessage("application.title", null, Locale.CHINA);
//		System.out.println("get message:"+datePat);
		logger.info("redirect");
		System.out.println("redirect");
		model.addAttribute("appid", AppConfig.APP_ID);
		return "redirect";
	}
	
	@RequestMapping(value = "/home.html")
	public ModelAndView home(HttpServletRequest request,HttpServletResponse response) {
//		String access_token = request.getParameter("access_token");
//		String xn_sig_user = request.getParameter("xn_sig_user");
//		String xn_sig_session_key = request.getParameter("xn_sig_session_key");
//		
//		System.out.println(access_token+"///"+xn_sig_user+"///"+xn_sig_session_key);
//		String accessToken = model.
//		if(EnvConfig.getAccess_token() == null){
//			Window.alert("跳转到登陆页面");
//			return;
//		}
//		
//		service.isExist(EnvConfig.getUserid(), new AsyncCallback<User>(){
//
//			@Override
//			public void onFailure(Throwable caught) {
//				Window.alert("连接Pinecone平台失败！");
//			}
//
//			@Override
//			public void onSuccess(User result) {
//				if(result == null){
//					if(Window.confirm("你的信息还没有在Pinecone平台注册，是否同意注册？")){
//						service.register(EnvConfig.getUserid(), "", "", new AsyncCallback<User>(){
//
//							@Override
//							public void onFailure(Throwable caught) {
//								Window.alert("初始化新用户信息失败！");
//							}
//
//							@Override
//							public void onSuccess(User result) {
//								EnvConfig.setUser(result);
//								RootPanel.get().add(new MainPanel());
//							}
//							
//						});
//					}
//				}else{
//					EnvConfig.setUser(result);
//					RootPanel.get().add(new MainPanel());
//				}
//			}});
			
		
//		logger.info("home.jsp");
//		System.out.println("home.jsp");
//		model.addAttribute("appid", AppConfig.APP_ID);
//		return "home";
		return new ModelAndView("home");
	}

	@RequestMapping(value = "/developer.html")
	public String developer(Model model) {
		logger.info("developer.html");
		System.out.println("developer.html");
		return "developer";
	}

	@RequestMapping(value = "/login.html")
	public String login(Model model) {
		logger.info("login.html");
		System.out.println("login.html");
		return "login";
	}

	@RequestMapping(value = "/products.html")
	public String products(Model model) {
		logger.info("products.html");
		System.out.println("products.html");
		return "products";
	}

	@RequestMapping(value = "/registry.html")
	public String registry(Model model) {
		logger.info("registry.html");
		System.out.println("registry.html");
		return "registry";
	}

	@RequestMapping(value = "/sensation.html")
	public String sensation(Model model) {
		logger.info("sensation.html");
		System.out.println("sensation.html");
		return "sensation";
	}

}
