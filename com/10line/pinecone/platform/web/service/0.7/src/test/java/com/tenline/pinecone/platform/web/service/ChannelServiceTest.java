/**
 * 
 */
package com.tenline.pinecone.platform.web.service;

import static org.junit.Assert.assertEquals;

import java.util.logging.Level;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tenline.pinecone.platform.sdk.ChannelClient;

/**
 * @author Bill
 *
 */
public class ChannelServiceTest extends AbstractServiceTest implements MqttCallback {
	
	private String topic = "pinecone@device.test";
	private ChannelClient client;
	
	@Before
	public void testSetup() throws Exception {
		client = new ChannelClient("tcp://www.pinecone.cc:1883");
		client.listen(this, topic);
	}
	
	@After
	public void testShutdown() throws Exception {
		topic = null;
		client.disconnect();
		client = null;	
	}
	
	@Test
	public void test() throws Exception {
		client.publish(topic, "Hello World");
	}

	@Override
	public void connectionLost(Throwable cause) {
		// TODO Auto-generated method stub
		logger.log(Level.SEVERE, cause.getMessage());
	}

	@Override
	public void deliveryComplete(MqttDeliveryToken token) {
		// TODO Auto-generated method stub
		logger.log(Level.INFO, "deliveryComplete");
	}

	@Override
	public void messageArrived(MqttTopic topic, MqttMessage message) throws Exception {
		// TODO Auto-generated method stub
		assertEquals("Hello World", new String(message.getPayload()));
	}

}
