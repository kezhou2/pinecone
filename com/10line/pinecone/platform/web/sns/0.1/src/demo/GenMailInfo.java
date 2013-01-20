package demo;

import java.util.HashMap;
import java.util.Map;

import cn.jcenterhome.util.Serializer;

public class GenMailInfo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map globalMail= new HashMap();
		globalMail.put("port",25);
		globalMail.put("mailusername",1);
		globalMail.put("maildelimiter",0);
		globalMail.put("server","smtp.163.com");
		globalMail.put("auth_password","198297");
		globalMail.put("from","liu_guo_1982@163.com");
		globalMail.put("auth",1);
		globalMail.put("auth_username","liu_guo_1982@163.com");
		String ss = Serializer.serialize(globalMail);
		System.out.println(ss);
	}

}
