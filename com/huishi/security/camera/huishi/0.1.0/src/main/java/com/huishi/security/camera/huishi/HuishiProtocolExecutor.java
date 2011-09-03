/**
 * 
 */
package com.huishi.security.camera.huishi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
	 * @param bundle
	 */
	public HuishiProtocolExecutor(Bundle bundle) {
		super(bundle);
		// TODO Auto-generated constructor stub
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
							 "/decoder_control.cgi?&user=admin&pwd=123456&command=" + item.getValue() +
							 "&onestep=2";
				HttpResponse resp = client.execute((HttpUriRequest) new HttpGet(uri));
				logger.info(resp.getEntity().toString());
				resp.getEntity().getContent().close();
				publisher.publish(device);
			} else if (variable.getName().equals(bundle.getHeaders().get("Video-Stream").toString())) {
				String uri = "http://" + bundle.getHeaders().get("Address").toString() + ":" +
					bundle.getHeaders().get("Port").toString() + 
					"/snapshot.cgi?user=admin&pwd=123456";
				HttpResponse response = client.execute((HttpUriRequest) new HttpGet(uri));
				
				InputStream in = response.getEntity().getContent();
//				int len = in.available();
//				int data;
				ByteArrayOutputStream out = new ByteArrayOutputStream();
//				while((data=in.read())!=-1){
//					out.write(data);
//				}
//				byte []datas = new byte[len];
//				in.read(datas);
				ImageIO.write(ImageIO.read(in), "jpeg", out);
				in.close();
				Item item = new Item();
				item.setValue(new String(out.toByteArray()));
				logger.info("Image Length: " + item.getValue().getBytes().length);
				variable.setItems(new ArrayList<Item>());
				variable.getItems().add(item);
				publisher.publish(device);
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