package cn.jcenterhome.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.mail.smtp.SMTPTransport;


public class Mail {
	private final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
//	private boolean mailUserName = true;
//	private String host = null;
//	private int port = 25;
//	private boolean auth = true;
//	private String username = null;
//	private String password = null;
	private HttpServletRequest request = null;
	
	private String elastic_username = null;
	private String elastic_api_key = null;
	private String elastic_from = null;
	private String elastic_fromname = null;
	private String elastic_frommail = null;
	private final String REST_API = "https://api.elasticemail.com/mailer/send";
	
	ThreadPoolExecutor executor = null;
	{
		executor = new ThreadPoolExecutor(1, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(5));
	}

	@SuppressWarnings("unchecked")
	public Mail(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		Map<String, Object> globalMail = Common.getCacheDate(request, response, "/data/cache/cache_mail.jsp",
				"globalMail");
		
		elastic_username = (String)globalMail.get("elastic_username"); 
		elastic_api_key = (String)globalMail.get("elastic_api_key");
		elastic_from = (String)globalMail.get("elastic_from");
		elastic_fromname = (String)globalMail.get("elastic_fromname");
		elastic_frommail = (String)globalMail.get("elastic_frommail");
		
//		if ((Integer) globalMail.get("mailusername") == 0) {
//			mailUserName = false;
//		}
//		host = globalMail.get("server").toString();
//		port = (Integer) globalMail.get("port");
//		if (port == 0) {
//			port = 25;
//		}
//		if ((Integer) globalMail.get("auth") == 0) {
//			auth = false;
//		}
//		username = globalMail.get("auth_username").toString();
//		password = globalMail.get("auth_password").toString();
//		
//		fromMail = (String)globalMail.get("from");
		
		
	}

	
//	private synchronized void createSession() {
//		Properties mailProps = new Properties();
//		mailProps.setProperty("mail.transport.protocol", "smtp");
//		mailProps.setProperty("mail.smtp.host", host);
//		mailProps.setProperty("mail.smtp.port", String.valueOf(port));
//		if ("smtp.gmail.com".equals(host)) {
//			mailProps.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
//			mailProps.setProperty("mail.smtp.socketFactory.fallback", "false");
//			mailProps.setProperty("mail.smtp.socketFactory.port", String.valueOf(port));
//		}
//		if (auth) {
//			mailProps.put("mail.smtp.auth", "true");
//		}
//		session = Session.getDefaultInstance(mailProps, new Authenticator() {
//			protected PasswordAuthentication getPasswordAuthentication() {
//				return new PasswordAuthentication(username, password);
//			}
//		});
//	}
//
//	private MimeMessage createMimeMessage() {
//		if (session == null) {
//			createSession();
//		}
//		return new MimeMessage(session);
//	}

	
	public boolean sendMessage(String from, String toEmail, String subject, String body) {
		if(from == null){
			from = this.elastic_frommail;
		}
		String charset = JavaCenterHome.JCH_CHARSET;
		
		ElasticMail msg = new ElasticMail();
		msg.setApiKey(elastic_api_key);
		msg.setFromName(elastic_fromname);
		msg.setUserName(elastic_username);
		
		Map<String, Object> sConfig = (Map<String, Object>) request.getAttribute("sConfig");
		Map<String, Object> sGlobal = (Map<String, Object>) request.getAttribute("sGlobal");
		String siteName = (String) sConfig.get("sitename");
		int timestamp = (Integer) sGlobal.get("timestamp");
		String timeoffset = Common.getTimeOffset(sGlobal, sConfig);
		body = Common.getMessage(request, "template_sendmail", siteName, body, Common.getSiteUrl(request),
				Common.gmdate("yyyy-MM-dd HH:mm", timestamp, timeoffset));
		try {
			if (from == null) {
				from = MimeUtility.encodeText(siteName, charset, "B") + " <" + sConfig.get("adminemail")
						+ ">";
			} else {
				List<String> mats = Common.pregMatch(from, "^(.+?) \\<(.+?)\\>$");
				if (mats.size() == 2) {
					from = MimeUtility.encodeText(mats.get(0), charset, "B") + " <" + mats.get(1) + ">";
				}
			}
			List<String> mats = Common.pregMatch(toEmail, "^(.+?) \\<(.+?)\\>$");
			if (mats.size() == 2) {
				toEmail = MimeUtility.encodeText(mats.get(0), charset, "B") + " <"
						+ mats.get(1) + ">";
			}
			subject = MimeUtility.encodeText(("[" + siteName + "] " + subject).replaceAll("[\r|\n]", ""),
					charset, "B");
			
			//set subject
			if(subject != null){
				msg.setSubject(subject);
			}
			
			//set body content
			body = body.replaceAll("\n\r", "\r").replaceAll("\r\n", "\n").replaceAll("\r", "\n").replaceAll(
					"\n", "\r\n");
			if (body != null) {
				msg.setBody(body);
			}

			//set to-mails
			String toEmails[] = toEmail.split(",");
			String sTo = "";
			for(int i=0;i<toEmails.length;i++){
				sTo = toEmails[i]+";";
			}
			msg.setTo(sTo);
			
			String fromName = "";
			String fromEmail;
			if (from.matches("^.*<.*>$")) {
				int index = from.indexOf("<");
				fromName = from.substring(0, index);
				fromEmail = from.substring(index + 1, from.length() - 1);
			} else {
				fromEmail = from;
			}
			msg.setFrom(fromEmail);
			msg.setFromName(fromName);
			
			sendMessages(msg);
		} catch (Exception e) {
			e.printStackTrace();
			FileHelper.writeLog(request, "SMTP", e.getMessage());
			return false;
		}
		return true;
	}

	private void sendMessages(ElasticMail message) {
		Collection<ElasticMail> messages = Collections.singletonList(message);
		if (messages.size() == 0) {
			return;
		}
		executor.execute(new EmailTask(messages));
	}

	
	
	private class EmailTask implements Runnable {
		private Collection<ElasticMail> messages;
		private int timestamp;
		private String timeoffset;
		private String onlineIP;
		private int supe_uid;
		private String requestURI;

		public EmailTask(Collection<ElasticMail> messages) {
			this.messages = messages;
			Map<String, Object> sGlobal = (Map<String, Object>) request.getAttribute("sGlobal");
			Map<String, Object> sConfig = (Map<String, Object>) request.getAttribute("sConfig");
			this.timestamp = (Integer) sGlobal.get("timestamp");
			this.timeoffset = Common.getTimeOffset(sGlobal, sConfig);
			this.onlineIP = Common.getOnlineIP(request);
			this.supe_uid = (Integer) sGlobal.get("supe_uid");
			this.requestURI = (String) request.getAttribute("requestURI");
		}

		public void run() {
			for(ElasticMail msg:messages){
				try {

					// Construct the data
					String data = "userName=" + msg.getUserName();
					data += "&api_key=" + msg.getApiKey();
					data += "&from=" + msg.getFrom();
					data += "&from_name=" + msg.getFromName();
					data += "&subject=" + URLEncoder.encode(msg.getSubject(), "UTF-8");
					data += "&body_html=" + URLEncoder.encode(msg.getBody(), "UTF-8");
					data += "&to=" + msg.getTo();

					// Send data
					URL url = new URL(REST_API);
					URLConnection conn = url.openConnection();
					conn.setDoOutput(true);
					OutputStreamWriter wr = new OutputStreamWriter(
							conn.getOutputStream());
					wr.write(data);
					wr.flush();
					BufferedReader rd = new BufferedReader(new InputStreamReader(
							conn.getInputStream()));
					String result = rd.readLine();
					wr.close();
					rd.close();

					if(result == null){
						FileHelper.writeLog(timestamp, timeoffset, onlineIP, supe_uid, requestURI, "SMTP", "Do not received mail sending task's id.");
					}
				} catch (Exception e) {
					e.printStackTrace();
					String message = e.getMessage();
					if (message == null) {
						message = "";
					}
					message = "(" + REST_API + ") CONNECT - Unable to connect to the SMTP server¡£"
							+ message.replace("\t", "");
					FileHelper.writeLog(timestamp, timeoffset, onlineIP, supe_uid, requestURI, "SMTP", message);
				}
			}
		}

	}
	
	private class ElasticMail {
		private String userName;
		private String apiKey;
		private String from;
		private String fromName;
		private String subject;
		private String body;
		private String to;
		
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getApiKey() {
			return apiKey;
		}
		public void setApiKey(String apiKey) {
			this.apiKey = apiKey;
		}
		public String getFrom() {
			return from;
		}
		public void setFrom(String from) {
			this.from = from;
		}
		public String getFromName() {
			return fromName;
		}
		public void setFromName(String fromName) {
			this.fromName = fromName;
		}
		public String getSubject() {
			return subject;
		}
		public void setSubject(String subject) {
			this.subject = subject;
		}
		public String getBody() {
			return body;
		}
		public void setBody(String body) {
			this.body = body;
		}
		public String getTo() {
			return to;
		}
		public void setTo(String to) {
			this.to = to;
		}
	}
}