package com.tenline.pinecone.platform.osgi.monitor.tool;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeTools {
	private static TimeTools	self	= new TimeTools();
	
	private TimeTools() {
	}
	
	public static TimeTools getInstance() {
		return self;
	}
	
	public static String getCurrentTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss [SSS]");
		String today = formatter.format(Calendar.getInstance().getTime());
		return today;
	}
}
