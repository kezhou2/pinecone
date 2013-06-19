/**
 * 
 */
package com.tenline.pinecone.mobile.android.service;

import java.util.UUID;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.internal.MemoryPersistence;

import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * @author Bill
 *
 */
public class ChannelService extends AbstractService {
	
	// MQTT Client and Topic
	private String topic;
	private MqttClient client;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		Log.i(getClass().getSimpleName(), "onBind");
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					client = new MqttClient("tcp://" + baseUrl + ":1883", UUID.randomUUID().toString(), new MemoryPersistence());
					client.connect();
				} catch (MqttException e) {
					// TODO Auto-generated catch block
					Log.e(getClass().getSimpleName(), e.getMessage());
				}
			}
			
		}).start();
		return super.onBind(intent);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i(getClass().getSimpleName(), "onDestroy");
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					client.unsubscribe(topic);
					if (client.isConnected()) {client.disconnect();}
					topic = null; client = null;
				} catch (MqttException e) {
					// TODO Auto-generated catch block
					Log.e(getClass().getSimpleName(), e.getMessage());
				}
			}
			
		}).start();
	}
	
	/**
	 * 
	 * @param callback
	 * @param topic
	 * @throws Exception
	 */
	public void listen(MqttCallback callback, String topic) throws Exception {
		while (!client.isConnected()) {Thread.sleep(100);} this.topic = topic;
		client.setCallback(callback); client.subscribe(this.topic);
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
