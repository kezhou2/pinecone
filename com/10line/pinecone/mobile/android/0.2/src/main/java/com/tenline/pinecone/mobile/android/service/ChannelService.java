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
	
	// MQTT Client
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
					baseUrl = "tcp://m2m.eclipse.org:1883";
					client = new MqttClient(baseUrl, UUID.randomUUID().toString(), new MemoryPersistence());
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
					if (client.isConnected()) {client.disconnect(); client = null;}
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
	 * @throws Exception
	 */
	public void listen(MqttCallback callback) throws Exception {
		client.setCallback(callback);
	}
	
	/**
	 * 
	 * @param topic
	 * @throws Exception
	 */
	public void subscribe(String topic) throws Exception {
		client.subscribe(topic);
	}
	
	/**
	 * 
	 * @param topic
	 * @throws Exception
	 */
	public void unsubscribe(String topic) throws Exception {
		client.unsubscribe(topic);
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
