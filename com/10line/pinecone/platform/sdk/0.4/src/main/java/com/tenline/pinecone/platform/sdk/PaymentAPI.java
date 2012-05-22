/**
 * 
 */
package com.tenline.pinecone.platform.sdk;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.tenline.pinecone.platform.sdk.development.APIResponse;
import com.tenline.pinecone.platform.sdk.development.ResourceAPI;

/**
 * @author Bill
 *
 */
public class PaymentAPI extends ResourceAPI {
	
	/**
	 * @param host
	 * @param port
	 * @param context
	 */
	public PaymentAPI(String host, String port, String context) {
		super(host, port, context);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param from
	 * @param to
	 * @return
	 * @throws Exception
	 */
	public APIResponse generateBatch(Date from, Date to) throws Exception {
		APIResponse response = new APIResponse();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String requestUrl = url + "/api/payment/generate/batch/"+format.format(from)+"/@From/"+format.format(to)+"/@To";
		connection = (HttpURLConnection) new URL(requestUrl).openConnection();
		connection.setConnectTimeout(TIMEOUT);
		connection.connect();
		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			response.setDone(true);
			response.setMessage(new String(new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8")).readLine()));
			connection.getInputStream().close();
		} else {
			response.setDone(false);
			response.setMessage("Generate Batch Error Code: Http (" + connection.getResponseCode() + ")");
		}
		connection.disconnect();
		return response;
	}

}
