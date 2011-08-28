/**
 * 
 */
package com.tenline.pinecone.platform.monitor;

import java.util.Collection;

import org.apache.log4j.Logger;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Item;
import com.tenline.pinecone.platform.model.Record;
import com.tenline.pinecone.platform.model.Variable;
import com.tenline.pinecone.platform.sdk.APIListener;
import com.tenline.pinecone.platform.sdk.ChannelAPI;
import com.tenline.pinecone.platform.sdk.RecordAPI;
import com.tenline.pinecone.platform.sdk.VariableAPI;

/**
 * @author Bill
 *
 */
public class Publisher {
	
	/**
	 * Publisher Logger
	 */
	private Logger logger = Logger.getLogger(getClass());

	/**
	 * Publisher Device
	 */
	private Device device;
	
	/**
	 * Publisher Channel
	 */
	private ChannelAPI channel;
	
	/**
	 * Publisher Variable API
	 */
	private VariableAPI variableAPI;
	private Collection<Variable> variables;
	
	/**
	 * Publisher Record API
	 */
	private RecordAPI recordAPI;
	
	/**
	 * 
	 */
	public Publisher() {
		// TODO Auto-generated constructor stub
		channel = new ChannelAPI(IConstants.WEB_SERVICE_HOST, IConstants.WEB_SERVICE_PORT, new APIListener() {

			@Override
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				logger.info(message);
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				logger.error(error);
			}
			
		});
		variableAPI = new VariableAPI(IConstants.WEB_SERVICE_HOST, IConstants.WEB_SERVICE_PORT, new APIListener() {

			@Override
			public void onError(String arg0) {
				// TODO Auto-generated method stub
				logger.error(arg0);
			}

			@Override
			@SuppressWarnings("unchecked")
			public void onMessage(Object arg0) {
				// TODO Auto-generated method stub
				variables = (Collection<Variable>) arg0;
			}
			
		});
		recordAPI = new RecordAPI(IConstants.WEB_SERVICE_HOST, IConstants.WEB_SERVICE_PORT, new APIListener() {

			@Override
			public void onError(String arg0) {
				// TODO Auto-generated method stub
				logger.error(arg0);
			}

			@Override
			public void onMessage(Object arg0) {
				// TODO Auto-generated method stub
			}
			
		});
	}
	
	/**
	 * Publish Content
	 * @param content
	 */
	public void publish(Device content) {
		try {
			channel.publish(device.getId() + "-device", "application/json", content);
			logger.info("Publish: " + device.getId() + "-device");			
			Object[] variables = content.getVariables().toArray();
			for (int i=0; i<variables.length; i++) {
				Variable variable = (Variable) variables[i];
				Item item = (Item) variable.getItems().toArray()[0];
				Record record = new Record();
				record.setValue(item.getValue());
				record.setVariable(getVariable(variable.getName()));
				recordAPI.create(record);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Get Variable
	 * @param name
	 * @return
	 */
	private Variable getVariable(String name) {
		for (Variable variable : variables) {
			if (variable.getName().equals(name)) {
				return variable;
			}
		}
		return null;
	}

	/**
	 * @param device the device to set
	 */
	public void setDevice(Device device) {
		try {
			this.device = device;
			variableAPI.showByDevice("id=='" + device.getId() + "'");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @return the device
	 */
	public Device getDevice() {
		return device;
	}

}
