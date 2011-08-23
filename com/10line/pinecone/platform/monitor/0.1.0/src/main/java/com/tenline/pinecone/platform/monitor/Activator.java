/**
 * 
 */
package com.tenline.pinecone.platform.monitor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Item;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.model.Variable;
import com.tenline.pinecone.platform.monitor.mina.MinaSerialEndpoint;
import com.tenline.pinecone.platform.monitor.tool.DeviceService;
import com.tenline.pinecone.platform.monitor.tool.ItemService;
import com.tenline.pinecone.platform.monitor.tool.VariableService;
import com.tenline.pinecone.platform.sdk.APIListener;
import com.tenline.pinecone.platform.sdk.DeviceAPI;
import com.tenline.pinecone.platform.sdk.UserAPI;

/**
 * @author Bill
 * 
 */
public class Activator implements BundleActivator {

	private String snsId = "251760162";

	private UserAPI userAPI;

	private DeviceAPI deviceAPI;

	private static BundleContext bundleContext;

	private ArrayList<IEndpoint> endpoints;

	/**
	 * 
	 */
	public Activator() {
		// TODO Auto-generated constructor stub
		endpoints = new ArrayList<IEndpoint>();
		userAPI = new UserAPI(IConstants.WEB_SERVICE_HOST,
				IConstants.WEB_SERVICE_PORT, new APIListener() {

					@Override
					public void onMessage(Object message) {
						// TODO Auto-generated method stub
						Object[] users = ((Collection<?>) message).toArray();
						for (int i = 0; i < users.length; i++) {
							try {
								deviceAPI.showByUser("id=='"
										+ ((User) users[i]).getId() + "'");
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}

					@Override
					public void onError(String error) {
						// TODO Auto-generated method stub
						System.out.println(error);
					}

				});
		deviceAPI = new DeviceAPI(IConstants.WEB_SERVICE_HOST,
				IConstants.WEB_SERVICE_PORT, new APIListener() {

					@Override
					public void onMessage(Object message) {
						// TODO Auto-generated method stub
						Object[] devices = ((Collection<?>) message).toArray();
						for (int i = 0; i < devices.length; i++) {
							try {
								System.out.println(((Device) devices[i])
										.getSymbolicName());
								Activator.this
										.initializeEndpoint((Device) devices[i]);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}

					@Override
					public void onError(String error) {
						// TODO Auto-generated method stub
						System.out.println(error);
					}

				});
	}

	/**
	 * 
	 * @param device
	 * @throws Exception
	 */
	private void initializeEndpoint(Device device) throws Exception {
		Bundle bundle = getBundle(device.getSymbolicName());
		if (bundle.getHeaders().get("Baud-Rate") != null) {
			MinaSerialEndpoint endpoint = new MinaSerialEndpoint();
			endpoint.initialize(device);
			endpoints.add(endpoint);
		}
		// TCP Server
		// TCP Client
	}

	/**
	 * 
	 * @param symbolicName
	 * @return
	 */
	public static Bundle getBundle(String symbolicName) {
		for (int i = 0; i < bundleContext.getBundles().length; i++) {
			Bundle bundle = bundleContext.getBundles()[i];
			if (bundle.getSymbolicName().equals(symbolicName)) {
				return bundle;
			}
		}
		// Query OSGI Bundle Repository
		return null;
	}

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		// TODO Auto-generated method stub
		// ClientUI window = new ClientUI(bundleContext);
		// window.open();
		Activator.bundleContext = bundleContext;
		userAPI.show("snsId=='" + snsId + "'");

	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		// TODO Auto-generated method stub
		Activator.bundleContext = null;
		while (endpoints.size() > 0) {
			endpoints.get(0).close();
			endpoints.remove(0);
		}
	}

	@SuppressWarnings("null")
	public void test() {
		boolean has = false;
		Object[] devices = null;
		for (int i = 0; i < devices.length; i++) {
			if (((Device) devices[0]).getSymbolicName().contains("efish")) {
				has = true;
				break;
			}
		}
		if (!has) {
			for (IEndpoint end : endpoints) {
				try {
					AbstractProtocolBuilder builder = ((MinaSerialEndpoint) end)
							.getFactory().getBuilder();
					if (builder.getClass().toString().contains("efish")) {
						Field field = builder.getClass().getField("metaData");
						Device dev = (Device) field.get(builder);
						System.out.println(dev.getSymbolicName());
						DeviceService.getInstance().saveDevice(dev);
						Collection<Variable> variables = dev.getVariables();
						for (Variable var : variables) {
							VariableService.getInstance().saveVariable(var);
							Collection<Item> items = var.getItems();
							for (Item item : items) {
								ItemService.getInstance().saveItem(item);
							}
						}
						break;
					}
				} catch (Exception e) {

				}
			}
		}
	}
}
