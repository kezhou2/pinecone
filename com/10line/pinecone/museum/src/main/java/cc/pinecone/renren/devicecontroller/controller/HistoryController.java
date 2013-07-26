package cc.pinecone.renren.devicecontroller.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cc.pinecone.renren.devicecontroller.dao.PineconeApi;
import cc.pinecone.renren.devicecontroller.service.LoginUserDetailsImpl;

import com.tenline.pinecone.platform.sdk.HistoryClient;
import com.tenline.pinecone.platform.sdk.RESTClient;

@Controller
public class HistoryController {
	
	@Autowired
	private MessageSource msgSrc;
	
	private static PineconeApi pApi;
	
	private static RESTClient client;
	
	private static HistoryClient hisClient;

	private final String MINUTE = "minute";
	private final String SECOND = "sec";
	private final String HOUR = "hour";
	private final long FILL_VALUE = -99999;
	
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
	
	private static final Logger logger = LoggerFactory
			.getLogger(PageController.class);
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/history.html")
	public String history(HttpServletRequest request,HttpServletResponse response) throws ParseException {
		logger.info("history.html");
		System.out.println("history.html");
		String id = request.getParameter("id");
		System.out.println("id:"+id);
		String type = request.getParameter("type");
		System.out.println("type:"+type);
		String per = request.getParameter("period");
		System.out.println("period"+per);
		if(per != null);
			int period = Integer.parseInt(per);
		
		Date startDate = getStartDate(type, period);
		Date endDate = new Date();
		
		JSONArray data = new JSONArray();
		
		long interval = 0;
		if(type.equals(SECOND)){
			interval = 1000;
		}else if(type.equals(MINUTE)){
			interval = 60*1000;
		}else if(type.equals(HOUR)){
			interval = 60*60*1000;
		}
		
		HistoryClient client = new HistoryClient(AppConfig.HISTORY_URL);
		try{
			for(int i=0;i<period;i++){
				JSONArray ob = new JSONArray();
				Date date = new Date(startDate.getTime()+i*interval);
				ob.add(date.getTime());
				long value = FILL_VALUE;
				if(type.equals(SECOND)){
					value = getSecondValue(client,id, date);
				}else if(type.equals(MINUTE)){
					value = getMinuteValue(client,id, date);
				}else if(type.equals(HOUR)){
					value = getHourValue(client,id, date);
				}
				
				if(value == FILL_VALUE){
					ob.add(0);
				}else{
					ob.add(value);
				}
				data.add(ob);
			}
		}finally{
			client.disconnect();
		}
		
		request.setAttribute("data", data.toJSONString());
		request.setAttribute("type", type);
		request.setAttribute("startDate", startDate.getTime());
		request.setAttribute("endDate", endDate.getTime());
		request.setAttribute("id", id);
		request.setAttribute("updateDate", endDate.toString());
		
		return "history";
	}
	
	private Date getStartDate(String type,int period){
		Date date = new Date();
		if(type.equals(MINUTE)){
			date.setTime(date.getTime()-(period*60*1000));
		}else if(type.equals(HOUR)){
			date.setTime(date.getTime()-(period*60*60*1000));
		}else if(type.equals(SECOND)){
			date.setTime(date.getTime()-(period*1000));
		}
		return date;
	}
	
	private long getSecondValue(HistoryClient client, String id, Date point){
		String value = client.getValue(id, point);
		if(value != null){
			return Long.parseLong(value);
		}else{
			return FILL_VALUE;
		}
	}
	
	private long getMinuteValue(HistoryClient client,String id, Date point){
		for(long i=point.getTime()-10000;i<point.getTime()+10000;i=i+1000){
			long value = getSecondValue(client,id, new Date(i));
			if(value != FILL_VALUE){
				return value;
			}
		}
		
		return FILL_VALUE;
	}
	
	private long getHourValue(HistoryClient client,String id, Date point){
		long min = 60*1000;
		for(long i=(point.getTime()-10*min);i<(point.getTime()+10*min);i=i+min){
			long value = this.getMinuteValue(client,id, new Date(i));
			if(value != FILL_VALUE)
				return value;
		}
		
		return FILL_VALUE;
	}
}
