/**
 * 
 */
package com.tenline.pinecone.platform.sdk;

import java.util.UUID;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.internal.MemoryPersistence;

/**
 * @author Bill
 *
 */
public class ChannelClient {
	
	// MQTT Client and Topic
	private String topic;
	private MqttClient client;
	
	/**
	 * 
	 * @param baseUrl
	 * @throws Exception
	 */
	public ChannelClient(String baseUrl) throws Exception {
		// TODO Auto-generated method stub
		client = new MqttClient(baseUrl, UUID.randomUUID().toString(), new MemoryPersistence());
		client.connect();
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	public void disconnect() throws Exception {
		client.unsubscribe(topic);
		if (client.isConnected()) {client.disconnect();}
		topic = null; client = null;
	}
	
	/**
	 * 
	 * @param callback
	 * @param topic
	 * @throws Exception
	 */
	public void listen(MqttCallback callback, String topic) throws Exception {
		this.topic = topic;
		client.setCallback(callback);
    	client.subscribe(this.topic);
	}
	
	/**
	 * 
	 * @param topic
	 * @param payload
	 * @throws Exception
	 */
	public void publish(String topic, String payload) throws Exception {
    	client.getTopic(topic).publish(new MqttMessage(payload.getBytes())).waitForCompletion();
    }
	
}
