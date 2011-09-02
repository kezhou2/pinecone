/**
 * 
 */
package com.tenline.pinecone.platform.monitor;

import java.util.ArrayList;
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
	private ChannelAPI normalChannel;
	/**
	 * stream channel
	 */
	private ChannelAPI streamChannel;

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
	 * current device
	 */
	private Device currentDevice;

	/**
	 * 
	 */
	public Publisher() {
		// TODO Auto-generated constructor stub
		currentDevice = new Device();
		currentDevice.setVariables(new ArrayList<Variable>());
		Variable variable = new Variable();
		variable.setName("");
		variable.setItems(new ArrayList<Item>());
		Item item = new Item();
		item.setValue("");
		item.setText("");
		variable.getItems().add(item);
		currentDevice.getVariables().add(variable);

		normalChannel = new ChannelAPI(IConstants.WEB_SERVICE_HOST,
				IConstants.WEB_SERVICE_PORT, new APIListener() {

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
		streamChannel = new ChannelAPI(IConstants.WEB_SERVICE_HOST,
				IConstants.WEB_SERVICE_PORT, new APIListener() {

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
		variableAPI = new VariableAPI(IConstants.WEB_SERVICE_HOST,
				IConstants.WEB_SERVICE_PORT, new APIListener() {

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
		recordAPI = new RecordAPI(IConstants.WEB_SERVICE_HOST,
				IConstants.WEB_SERVICE_PORT, new APIListener() {

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
	 * 
	 * @param content
	 */
	public void publish(Device content) {
		try {
			Object[] variables = content.getVariables().toArray();
			Variable var = (Variable) variables[0];
			if (var.getType().equals("image")) {
				publishImage(content, var);
			} else {
				publishNormal(content, variables);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param content
	 * @param variable
	 * @throws Exception
	 */
	private void publishImage(Device content, Variable variable)
			throws Exception {
		Item item = (Item) variable.getItems().toArray()[0];
		streamChannel.publish(variable.getId() + "-device",
				"application/image", item.getValue());
		System.out.println("Publish: " + variable.getId() + "-device" + ", "
				+ System.currentTimeMillis() / 1000 + " value: image.");
	}

	/**
	 * @param content
	 * @param variables
	 * @throws Exception
	 */
	private void publishNormal(Device content, Object[] variables)
			throws Exception {
		normalChannel.publish(device.getId() + "-device", "application/json",
				content);
		Object[] currentvariables = currentDevice.getVariables().toArray();
		for (int i = 0; i < variables.length; i++) {
			Variable variable = (Variable) variables[i];
			Item item = (Item) variable.getItems().toArray()[0];
			System.out.println("Publish: " + device.getId() + "-device" + ", "
					+ System.currentTimeMillis() / 1000 + " value: "
					+ item.getValue());
			for (int j = 0; j < currentvariables.length; j++) {
				Variable currentVariable = (Variable) currentvariables[j];
				if (variable.getName().equals(currentVariable.getName())) {
					Item currentItem = (Item) currentVariable.getItems()
							.toArray()[0];
					if (!item.getValue().equals(currentItem.getValue())) {
						System.out.println("Record: " + device.getId()
								+ "-device" + ", value: " + item.getValue());
						Record record = new Record();
						record.setValue(item.getValue());
						record.setVariable(getVariable(variable.getName()));
						recordAPI.create(record);
					}
				}
			}
		}
		currentDevice = content;
	}

	/**
	 * Get Variable
	 * 
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
	 * @param device
	 *            the device to set
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
