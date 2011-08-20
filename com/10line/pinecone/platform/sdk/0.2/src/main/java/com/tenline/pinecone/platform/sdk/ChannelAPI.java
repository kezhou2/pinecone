/**
 * 
 */
package com.tenline.pinecone.platform.sdk;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.mapped.Configuration;
import org.codehaus.jettison.mapped.MappedNamespaceConvention;
import org.codehaus.jettison.mapped.MappedXMLStreamReader;
import org.codehaus.jettison.mapped.MappedXMLStreamWriter;

import com.tenline.pinecone.platform.model.Device;

/**
 * @author Bill
 *
 */
public class ChannelAPI extends AbstractAPI {

	private JAXBContext context;
	private Marshaller marshaller;
	private Unmarshaller unmarshaller;
	
	/**
	 * @param host
	 * @param port
	 * @param listener
	 */
	public ChannelAPI(String host, String port, APIListener listener) {
		super(host, port, listener);
		// TODO Auto-generated constructor stub
		try {
			context = JAXBContext.newInstance(Device.class);
			marshaller = context.createMarshaller();
			unmarshaller = context.createUnmarshaller();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param subject
	 * @throws Exception
	 */
	public void subscribe(String subject) throws Exception {
		connection = (HttpURLConnection) new URL(url + "/api/channel/subscribe/" + subject).openConnection();
		connection.setConnectTimeout(TIMEOUT);
		connection.connect();
		byte[] bytes = new byte[connection.getInputStream().available()];
		connection.getInputStream().read(bytes);
		connection.getInputStream().close();
		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			if (connection.getContentType().indexOf("application/json") >= 0) {
				JSONObject obj = new JSONObject(new String(bytes, "utf-8"));
				listener.onMessage(unmarshaller.unmarshal(new MappedXMLStreamReader(obj, 
						new MappedNamespaceConvention(new Configuration()))));
			} else {
				listener.onMessage(bytes);
			}
		}else listener.onError("Subscribe Channel Error Code: Http (" + connection.getResponseCode() + ")");
		connection.disconnect();
	}
	
	/**
	 * 
	 * @param subject
	 * @param contentType
	 * @param content
	 * @throws Exception
	 */
	public void publish(String subject, String contentType, Object content) throws Exception {
		connection = (HttpURLConnection) new URL(url + "/api/channel/publish/" + subject).openConnection();
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", contentType + "; charset=utf-8");
		connection.setUseCaches(false);
		connection.setConnectTimeout(TIMEOUT);
		connection.connect();
		if (contentType.indexOf("application/json") >= 0) {
			marshaller.marshal(content, new MappedXMLStreamWriter(new MappedNamespaceConvention(new Configuration()), 
					new OutputStreamWriter(connection.getOutputStream(), "utf-8")));
		} else {
			connection.getOutputStream().write((byte[]) content);
		}
		connection.getOutputStream().flush();
        connection.getOutputStream().close();
		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) listener.onMessage("Publish Successful!");
		else listener.onError("Publish Channel Error Code: Http (" + connection.getResponseCode() + ")");
		connection.disconnect();
	}

}
