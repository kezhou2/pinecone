package cc.pinecone.renren.devicecontroller.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cc.pinecone.renren.devicecontroller.dao.PineconeApi;

import com.tenline.pinecone.platform.model.Entity;
import com.tenline.pinecone.platform.sdk.RESTClient;

@Controller
public class ValidateController {

	@Autowired
	private MessageSource msgSrc;

	@Autowired
	public AuthenticationManager authenticationManager;

	private static PineconeApi pApi;

	private static RESTClient client;

	private final String ADMIN_NAME = "admin";
	private final String ADMIN_PWD = "admin";

	private static final Logger logger = Logger.getLogger(ValidateController.class);

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

	@RequestMapping(value = "/validatename.html", method = RequestMethod.GET)
	public void validatename(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		logger.info("validatename.html");
		String fieldId = request.getParameter("fieldId");
		String fieldValue = request.getParameter("fieldValue");
		logger.info("fieldId:" + fieldId + "|fieldValue:" + fieldValue);
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			if(fieldId.equals("username")){
				ArrayList<Entity> users = (ArrayList<Entity>) this.getRESTClient()
						.get("/user/search/names?name=" + fieldValue, ADMIN_NAME,
								ADMIN_PWD);
				if (users.size() == 0) {
					pw.write("[\"" + fieldId + "\"," + true + "]");
				} else {
					pw.write("[\"" + fieldId + "\"," + false + "]");
				}
			}else if(fieldId.equals("emailValid")){
				ArrayList<Entity> users = (ArrayList<Entity>) this.getRESTClient().get("/user/search/emails?email=" + fieldValue, ADMIN_NAME,
								ADMIN_PWD);
				if (users.size() == 0) {
					pw.write("[\"" + fieldId + "\"," + true + "]");
				} else {
					pw.write("[\"" + fieldId + "\"," + false + "]");
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pw.flush();
			pw.close();
		}
	}
	@RequestMapping(value = "/validateemail.html", method = RequestMethod.GET)
	public void validateemail(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		logger.info("validateemail.html");
		String fieldId = request.getParameter("fieldId");
		String fieldValue = request.getParameter("fieldValue");
		logger.info("fieldId:" + fieldId + "|fieldValue:" + fieldValue);
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			if(fieldId.equals("username")){
				ArrayList<Entity> users = (ArrayList<Entity>) this.getRESTClient()
						.get("/user/search/names?name=" + fieldValue, ADMIN_NAME,
								ADMIN_PWD);
				if (users.size() == 0) {
					pw.write("[\"" + fieldId + "\"," + true + "]");
				} else {
					pw.write("[\"" + fieldId + "\"," + false + "]");
				}
			}else if(fieldId.equals("emailValid")){
				ArrayList<Entity> users = (ArrayList<Entity>) this.getRESTClient().get("/user/search/emails?email=" + fieldValue, ADMIN_NAME,
								ADMIN_PWD);
				if (users.size() == 0) {
					pw.write("[\"" + fieldId + "\"," + true + "]");
				} else {
					pw.write("[\"" + fieldId + "\"," + false + "]");
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pw.flush();
			pw.close();
		}
	}
}
