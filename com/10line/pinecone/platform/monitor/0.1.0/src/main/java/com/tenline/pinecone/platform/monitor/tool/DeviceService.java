package com.tenline.pinecone.platform.monitor.tool;

import java.util.ArrayList;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.monitor.IConstants;
import com.tenline.pinecone.platform.sdk.APIListener;
import com.tenline.pinecone.platform.sdk.DeviceAPI;

public class DeviceService {
	private DeviceAPI addApi;
	private Device addDev;

	private DeviceAPI queryApi;
	private ArrayList<Device> queryDevs;

	@SuppressWarnings("unused")
	private DeviceAPI delApi;
	private static DeviceService instance = null;

	private DeviceService() {
		addApi = new DeviceAPI(IConstants.WEB_SERVICE_HOST, IConstants.WEB_SERVICE_PORT,
				new APIListener() {
					@Override
					public void onMessage(Object message) {
						Device dev = (Device) message;
						setAddDev(dev);
						System.out.println("add device ok: " + dev.getName());
					}

					@Override
					public void onError(String error) {
						setAddDev(null);
						System.out.println("add device error: " + error);
					}
				});
		queryApi = new DeviceAPI(IConstants.WEB_SERVICE_HOST, IConstants.WEB_SERVICE_PORT,
				new APIListener() {
					@SuppressWarnings({ "unchecked" })
					@Override
					public void onMessage(Object message) {
						ArrayList<Device> devs = (ArrayList<Device>) message;
						setQueryDevs(devs);
						System.out.println("query device ok:" + devs.size());
					}

					@Override
					public void onError(String error) {
						setQueryDevs(null);
						System.out.println("getuser error: " + error);
					}
				});
		delApi = new DeviceAPI(IConstants.WEB_SERVICE_HOST, IConstants.WEB_SERVICE_PORT,
				new APIListener() {
					@Override
					public void onMessage(Object message) {
						System.out.println("del device ok");
					}

					@Override
					public void onError(String error) {
						System.out.println("del device error: " + error);
					}
				});
	}

	/**
	 * @return
	 */
	public static DeviceService getInstance() {
		if (instance == null) {
			instance = new DeviceService();
		}
		return instance;
	}

	@SuppressWarnings("finally")
	public Device saveDevice(Device dev) {
		try {
			this.addApi.create(dev);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return this.addDev;
		}
	}

	public ArrayList<Device> getDeviceBySymName(String synName) {
		try {
			this.queryApi.show("symbolicName=='" + synName + "'");
			return getQueryDevs();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("finally")
	public ArrayList<Device> getDeviceByUser(User user) {
		try {
			if (user == null) {
				setQueryDevs(null);
			} else {
				this.queryApi.show("user=='" + user.getId() + "'");
			}
		} catch (Exception e) {
			e.printStackTrace();
			setQueryDevs(null);
		} finally {
			return getQueryDevs();
		}
	}

	public Device getAddDev() {
		return addDev;
	}

	public void setAddDev(Device addDev) {
		this.addDev = addDev;
	}

	public ArrayList<Device> getQueryDevs() {
		return queryDevs;
	}

	public void setQueryDevs(ArrayList<Device> queryDevs) {
		this.queryDevs = queryDevs;
	}

}
