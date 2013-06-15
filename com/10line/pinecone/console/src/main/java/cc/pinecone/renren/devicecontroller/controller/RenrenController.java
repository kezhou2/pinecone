package cc.pinecone.renren.devicecontroller.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cc.pinecone.renren.devicecontroller.service.LoginUserDetailsImpl;
import cc.pinecone.renren.devicecontroller.servlet.DataTablesParamUtility;
import cc.pinecone.renren.devicecontroller.servlet.JQueryDataTableParamModel;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Entity;
import com.tenline.pinecone.platform.sdk.RESTClient;

@Controller
public class RenrenController {
	
	private static final Logger logger = LoggerFactory.getLogger(PageController.class);

}
