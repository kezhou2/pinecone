package com.tenline.pinecone.platform.osgi.monitor.service;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.sdk.APIListener;
import com.tenline.pinecone.platform.sdk.DeviceAPI;

public class DeviceService {
	private static Logger logger = Logger.getLogger(DeviceService.class);
	private DeviceAPI addApi;
	private Device addDev;

	private DeviceAPI queryApi;
	private ArrayList<Device> queryDevs;

	@SuppressWarnings("unused")
	private DeviceAPI delApi;
	private static DeviceService instance = null;

	private DeviceService() {
		addApi = new DeviceAPI("pinecone.web.service.10line.cc", "80",
				new APIListener() {
					@Override
					public void onMessage(Object message) {
						Device dev = (Device) message;
						setAddDev(dev);
						logger.info("add device ok: " + dev.getName());
					}

					@Override
					public void onError(String error) {
						setAddDev(null);
						logger.error("add device error: " + error);
					}
				});
		queryApi = new DeviceAPI("pinecone.web.service.10line.cc", "80",
				new APIListener() {
					@SuppressWarnings({ "unchecked" })
					@Override
					public void onMessage(Object message) {
						ArrayList<Device> devs = (ArrayList<Device>) message;
						setQueryDevs(devs);
						logger.info("query device ok:" + devs.size());
					}

					@Override
					public void onError(String error) {
						setQueryDevs(null);
						logger.error("getuser error: " + error);
					}
				});
		delApi = new DeviceAPI("pinecone.web.service.10line.cc", "80",
				new APIListener() {
					@Override
					public void onMessage(Object message) {
						logger.info("del device ok");
					}

					@Override
					public void onError(String error) {
						logger.error("del device error: " + error);
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

	public void saveDevice(Device dev) {
		try {
			this.addApi.create(dev);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public ArrayList<Device> getDeviceBySymName(String synName) {
		try {
			this.queryApi.show("symbolicName=='" + synName + "'");
			return getQueryDevs();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	@SuppressWarnings("finally")
	public ArrayList<Device> getDeviceByUser(User user) {
		try {
			if(user == null){
				setQueryDevs(null);
			}else{
				this.queryApi.show("user=='" + user.getId() + "'");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			setQueryDevs(null);
		}  finally{
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
