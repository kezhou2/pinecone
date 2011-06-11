/**
 * 
 */
package com.tenline.pinecone.platform.sdk;

import org.jboss.resteasy.client.ClientRequest;

/**
 * @author Bill
 *
 */
public class ChannelAPI extends AbstractAPI {

	/**
	 * @param host
	 * @param port
	 * @param listener
	 */
	public ChannelAPI(String host, String port,
			APIListener listener) {
		super(host, port, listener);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param subject
	 * @throws Exception
	 */
	public void subscribe(String subject) throws Exception {
		request = new ClientRequest(url + "/api/channel/subscribe/{subject}");
		request.pathParameter("subject", subject);
		response = request.get();
		if (response.getStatus() == 200) listener.onMessage(response.getEntity(byte[].class));
		else listener.onError("Subscribe Channel Error Code: Http (" + response.getStatus() + ")");
		response.releaseConnection();
	}
	
	/**
	 * 
	 * @param subject
	 * @param contentType
	 * @param contentBytes
	 * @throws Exception
	 */
	public void publish(String subject, String contentType, byte[] contentBytes) throws Exception {
		request = new ClientRequest(url + "/api/channel/publish/{subject}");
		request.pathParameter("subject", subject).body(contentType, contentBytes);
		response = request.post();
		if (response.getStatus() == 200) listener.onMessage("Publish Successful!");
		else listener.onError("Publish Channel Error Code: Http (" + response.getStatus() + ")");
		response.releaseConnection();
	}

}
