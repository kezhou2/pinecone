package demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class ElasticMail {

	public static String SendElasticEmail(String userName, String apiKey,
			String from, String fromName, String subject, String body, String to) {

		try {

			// Construct the data
			String data = "userName=" + userName;
			data += "&api_key=" + apiKey;
			data += "&from=" + from;
			data += "&from_name=" + fromName;
			data += "&subject=" + URLEncoder.encode(subject, "UTF-8");
			data += "&body_html=" + URLEncoder.encode(body, "UTF-8");
			data += "&to=" + to;

			// Send data
			URL url = new URL("https://api.elasticemail.com/mailer/send");
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

			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		String taskId = ElasticMail.SendElasticEmail("liugy503@gmail.com",
				"1511bd2f-208e-4647-ac73-c6306545b505", "liugy503@gmail.com",
				"Liu Guoyang", "Test Mail", "This is a test mail",
				"liu_guo_1982@163.com;liugyang@yahoo.cn");
		System.out.println("TaskId:"+taskId);
	}

}
