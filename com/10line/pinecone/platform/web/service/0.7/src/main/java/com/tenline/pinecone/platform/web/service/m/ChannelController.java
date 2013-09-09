package com.tenline.pinecone.platform.web.service.m;

import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.internal.MemoryPersistence;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Repository
@RequestMapping("/channel")
public class ChannelController{
	
	private MqttClient client;
	
	private MqttClient getClient() throws Exception{
		if(client == null){
			client = new MqttClient("tcp://www.pinecone.cc:1883", UUID.randomUUID().toString(), new MemoryPersistence());
			client.connect();
		}else{
			if(!client.isConnected()){
				client = null;
			}
		}
		
		return client;
	}
	
	@RequestMapping(value = "/subscribe" ,method = RequestMethod.POST)
	public @ResponseBody void subscribe(HttpServletRequest req, HttpServletResponse resp) throws MqttException, Exception{
		System.out.println("###Call subscribe");
		String code = req.getParameter("code");
		
		try{
			resp.setContentType("text/html; charset=utf-8"); 
			resp.setCharacterEncoding("UTF-8");
			resp.setHeader("Cache-Control","no-cache");
			final PrintWriter out=resp.getWriter();
			
			this.getClient().setCallback(new MqttCallback(){
	
				@Override
				public void connectionLost(Throwable arg0) {
					System.out.println("###connectionLost");
					out.write("{}");
				}
	
				@Override
				public void deliveryComplete(MqttDeliveryToken arg0) {System.out.println("###deliveryComplete");}
	
				@Override
				public void messageArrived(MqttTopic arg0, MqttMessage arg1)
						throws Exception {
					System.out.println("###messageArrived");
					byte[] buf = arg1.getPayload();
					if(buf != null){
						out.write(new String(buf));
					}
				}
				
			});
			this.getClient().subscribe("pinecone@device."+code+".publish");

		}finally{
			this.getClient().unsubscribe("pinecone@device."+code+".publish");
		}
	}
	
	public void subscribe(String code) throws MqttException, Exception{
		System.out.println("###Call subscribe");
		try{
			
			this.getClient().setCallback(new MqttCallback(){
	
				@Override
				public void connectionLost(Throwable arg0) {
					System.out.println("{}");
				}
	
				@Override
				public void deliveryComplete(MqttDeliveryToken arg0) {
					System.out.println("delivery");
				}
	
				@Override
				public void messageArrived(MqttTopic arg0, MqttMessage arg1)
						throws Exception {
					System.out.println("###messageArrived");
					byte[] buf = arg1.getPayload();
					if(buf != null){
						System.out.println(new String(buf));
					}
				}
				
			});
			this.getClient().subscribe("pinecone@device."+code+".publish");
			System.out.println("###subscribed...");

		}finally{
//			this.getClient().unsubscribe("pinecone@device."+code+".publish");
			System.out.println("###finally");
		}
	}
	
	public static void main(String[] args){
		ChannelController cc = new ChannelController();
		try {
			cc.subscribe("a83cb7d3-f9ba-4329-93b8-50a98df6c2cf");
		} catch (MqttException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
