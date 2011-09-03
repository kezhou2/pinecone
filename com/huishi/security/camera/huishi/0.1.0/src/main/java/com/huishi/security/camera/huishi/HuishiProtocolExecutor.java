/**
 * 
 */
package com.huishi.security.camera.huishi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.osgi.framework.Bundle;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Item;
import com.tenline.pinecone.platform.model.Variable;
import com.tenline.pinecone.platform.monitor.Publisher;
import com.tenline.pinecone.platform.monitor.http.AbstractHttpClientProtocolExecutor;

/**
 * @author Bill
 *
 */
public class HuishiProtocolExecutor extends AbstractHttpClientProtocolExecutor {
	
	/**
	 * 
	 */
	private static ArrayList<Integer> isStreamed; 

	/**
	 * @param bundle
	 */
	public HuishiProtocolExecutor(Bundle bundle) {
		super(bundle);
		// TODO Auto-generated constructor stub
		isStreamed = new ArrayList<Integer>();
	}

	@Override
	protected void execute(HttpClient client, Device device, Publisher publisher) {
		// TODO Auto-generated method stub
		try {
			Variable variable = (Variable) device.getVariables().toArray()[0];
			if (variable.getName().equals(bundle.getHeaders().get("Angle-Control").toString())) {
				Item item = (Item) variable.getItems().toArray()[0];
				String uri = "http://" + bundle.getHeaders().get("Address").toString() + ":" + 
							 bundle.getHeaders().get("Port").toString() +
							 "/decoder_control.cgi?command=" + item.getValue() +
							 "&onestep=2&user=admin&passwd=123456";
				HttpResponse response = client.execute((HttpUriRequest) new HttpGet(uri));
				item.setValue(response.getEntity().getContent().toString());
				response.getEntity().getContent().close();
//				publisher.publish(device);
			} else if (variable.getName().equals(bundle.getHeaders().get("Video-Stream").toString())) {
				if (!isStreamed.contains(client.hashCode())) {
					isStreamed.add(client.hashCode());
					String uri = "http://" + bundle.getHeaders().get("Address").toString() + ":" +
		 						bundle.getHeaders().get("Port").toString() + 
		 						"/videostream.cgi?user=admin&pwd=123456";
					HttpResponse response = client.execute((HttpUriRequest) new HttpGet(uri));
					ByteArrayOutputStream output = new ByteArrayOutputStream();
					String[] types = variable.getType().split("_");
					for (String type : types) {
						if (type.indexOf("image") >= 0) {
							ImageIO.write(ImageIO.read(response.getEntity().getContent()), 
									type.substring(type.indexOf("/") + 1), output);	break;
						}
					}
					byte[] byteImage = output.toByteArray();
					while (byteImage.length > 0) {
						Item item = new Item();
						item.setValue(new String(byteImage));
						variable.setItems(new ArrayList<Item>());
						variable.getItems().add(item);
//						publisher.publish(device);
					}
					isStreamed.remove(client.hashCode());	
				}
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}