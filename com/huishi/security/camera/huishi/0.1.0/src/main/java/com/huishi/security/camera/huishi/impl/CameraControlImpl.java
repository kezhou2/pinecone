package com.huishi.security.camera.huishi.impl;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpRequest;

import com.huishi.security.camera.huishi.interfaces.CameraControl;
import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Item;
import com.tenline.pinecone.platform.model.Variable;
import com.tenline.pinecone.platform.monitor.Publisher;

public class CameraControlImpl implements CameraControl {

	@Override
	public void snapshot(Publisher publisher) {
		// TODO Auto-generated method stub
		InputStream snapshot = null;
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse cameraResponse;
		try {
			cameraResponse = httpClient.execute((HttpUriRequest) new BasicHttpRequest("GET", "http://192.168.1.138/snapshot.cgi?user=admin&pwd=123456"));

			snapshot = cameraResponse.getEntity().getContent();
			BufferedImage buffImage = ImageIO.read(snapshot);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ImageIO.write(buffImage, "JPEG", outputStream);
			byte[] byteImage = outputStream.toByteArray();
			
			Device imageDevice = new Device();
			Item imageItem = new Item();
			ByteArrayInputStream imageByteInputStream = new ByteArrayInputStream(byteImage);
			DataInputStream imageData = new DataInputStream(imageByteInputStream);
			imageItem.setText(imageData.readUTF());
			imageItem.setValue(imageData.readUTF());
			Variable variable = new Variable();
			variable.getItems().add(imageItem);
			imageDevice.getVariables().add(variable);
			publisher.publish(imageDevice);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void videostream(Publisher publisher) {
		// TODO Auto-generated method stub
		InputStream snapshot = null;
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse cameraResponse;
		Device imageDevice = null;
		Item imageItem = null;
		Variable variable = null;
		try {
			cameraResponse = httpClient.execute((HttpUriRequest) new BasicHttpRequest("GET", "http://192.168.1.138/snapshot.cgi?user=admin&pwd=123456"));

			snapshot = cameraResponse.getEntity().getContent();
			BufferedImage buffImage = ImageIO.read(snapshot);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ImageIO.write(buffImage, "JPEG", outputStream);
			byte[] byteImage = outputStream.toByteArray();
			
			while(true){
				imageDevice = new Device();
				imageItem = new Item();
				variable = new Variable();
				ByteArrayInputStream imageByteInputStream = new ByteArrayInputStream(byteImage);
				DataInputStream imageData = new DataInputStream(imageByteInputStream);
				imageItem.setText(imageData.readUTF());
				imageItem.setValue(imageData.readUTF());
				variable.getItems().add(imageItem);
				imageDevice.getVariables().add(variable);
				publisher.publish(imageDevice);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void headUp() {
		// TODO Auto-generated method stub

		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse cameraResponse;
		try {
			cameraResponse = httpClient.execute((HttpUriRequest) new BasicHttpRequest("GET", "http://192.168.1.138/snapshot.cgi?user=admin&pwd=123456"));
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void headDown() {
		// TODO Auto-generated method stub
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse cameraResponse;
		try {
			cameraResponse = httpClient.execute((HttpUriRequest) new BasicHttpRequest("GET", "http://192.168.1.138/snapshot.cgi?user=admin&pwd=123456"));
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void headLeft() {
		// TODO Auto-generated method stub
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse cameraResponse;
		try {
			cameraResponse = httpClient.execute((HttpUriRequest) new BasicHttpRequest("GET", "http://192.168.1.138/snapshot.cgi?user=admin&pwd=123456"));
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void headRight() {
		// TODO Auto-generated method stub
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse cameraResponse;
		try {
			cameraResponse = httpClient.execute((HttpUriRequest) new BasicHttpRequest("GET", "http://192.168.1.138/snapshot.cgi?user=admin&pwd=123456"));
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
