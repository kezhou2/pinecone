package cn.jcenterhome.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SessionFactory {
	private static String CONFIG_FILE_LOCATION = "/hibernate.cfg.xml";
	private static org.hibernate.SessionFactory sessionFactory;

	public static synchronized void buildSessionFactory() throws SQLException {
		if (sessionFactory == null) {
			Map<String, String> jchConfig = JavaCenterHome.jchConfig;
			String dbHost = jchConfig.get("dbHost");
			String dbPort = jchConfig.get("dbPort");
			String dbName = jchConfig.get("dbName");
			String dbUser = jchConfig.get("dbUser");
			String dbPw = jchConfig.get("dbPw");
			String dbCharset = jchConfig.get("dbCharset");
			
			//get VCAP_SERVICES FROM CloudFoundry.
			String VCAP_SERVICES = System.getenv("VCAP_SERVICES");
			if(VCAP_SERVICES != null){
				System.out.println("Change Database connection info.");
				try{
					JSONObject jsonObj = new JSONObject(VCAP_SERVICES);
					String[] names = jsonObj.getNames(jsonObj);
					for(String n:names){
						JSONArray array = jsonObj.getJSONArray(n);
						JSONObject obj = array.getJSONObject(0);
						JSONObject credentials = obj.getJSONObject("credentials");
						//get database instance name;
						String credentials_name = credentials.getString("name");
						dbName = credentials_name;
						jchConfig.put("dbName", credentials_name);
						System.out.println("credentials_name:"+credentials_name);
						//get database host
						String credentials_host = credentials.getString("host");
						dbHost = credentials_host;
						jchConfig.put("dbHost", credentials_host);
						System.out.println("credentials_host:"+credentials_host);
						//get database port
						String credentials_port = credentials.getString("port");
						dbPort = credentials_port;
						jchConfig.put("dbPort", credentials_port);
						System.out.println("credentials_port:"+credentials_port);
						//get database instance username
						String credentials_user = credentials.getString("user");
						dbUser = credentials_user;
						jchConfig.put("dbUser", credentials_user);
						System.out.println("credentials_user:"+credentials_user);
						//get database instance user's password
						String credentials_password = credentials.getString("password");
						dbPw = credentials_password;
						jchConfig.put("dbPw", credentials_password);
						System.out.println("credentials_password:"+credentials_password);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			if (connect(dbHost, dbPort, dbName, dbUser, dbPw, dbCharset)) {
				Properties extraProperties = new Properties();
				extraProperties.setProperty("hibernate.connection.url", "jdbc:mysql://" + dbHost + ":"
						+ dbPort + "/" + dbName + "?zeroDateTimeBehavior=convertToNull");
				extraProperties.setProperty("hibernate.connection.username", dbUser);
				extraProperties.setProperty("hibernate.connection.password", dbPw);
				extraProperties.setProperty("hibernate.connection.characterEncoding", dbCharset);
				extraProperties.setProperty("hibernate.connection.characterSetResults", dbCharset);
				Configuration configuration = new Configuration();
				configuration = configuration.configure(CONFIG_FILE_LOCATION);
				configuration = configuration.addProperties(extraProperties);
				sessionFactory = configuration.buildSessionFactory();
			}
		}
	}

	public static Session getSession() throws SQLException {
		if (sessionFactory == null) {
			buildSessionFactory();
		}
		return sessionFactory.getCurrentSession();
	}

	public static void rebuildSessionFactory() throws SQLException {
		sessionFactory = null;
		buildSessionFactory();
	}

	public static org.hibernate.SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	private static boolean connect(String dbHost, String dbPort, String dbName, String dbUser, String dbPw,
			String dbCharset) throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Connection conn = DriverManager.getConnection("jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName
				+ "?useUnicode=true&characterEncoding=" + dbCharset, dbUser, dbPw);
		if (conn != null) {
			if (!conn.isClosed()) {
				conn.close();
				conn = null;
			}
			return true;
		}
		return false;
	}
}