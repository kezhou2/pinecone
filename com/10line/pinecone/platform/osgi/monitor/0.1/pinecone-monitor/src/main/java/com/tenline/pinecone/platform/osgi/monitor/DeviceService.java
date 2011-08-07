package com.tenline.pinecone.platform.osgi.monitor;

import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;

import com.tenline.pinecone.platform.osgi.monitor.model.Command;
import com.tenline.pinecone.platform.osgi.monitor.model.Device;
import com.tenline.pinecone.platform.osgi.monitor.tool.FilePath;
import com.tenline.pinecone.platform.osgi.monitor.tool.ProtocolConverter;
import com.tenline.pinecone.platform.osgi.monitor.xml.DeviceParam;
import com.tenline.pinecone.platform.osgi.monitor.xml.XMLLoad;

public class DeviceService implements IDeviceSerivce {
	/**
	 * logger
	 */
	private static Logger logger = Logger.getLogger(XMLLoad.class);
	/**
	 * device table
	 */
	private static Hashtable<String, DeviceParam> deviceTable;
	/**
	 * 
	 */
	private static DeviceService self = new DeviceService();

	public DeviceService() {
		deviceTable = new Hashtable<String, DeviceParam>();
	}

	public static DeviceService getInstance() {
		return self;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tenline.pinecone.platform.osgi.monitor.IDeviceSerivce#loadNewDevice
	 * (java.lang.String)
	 */
	@Override
	public boolean loadNewDevice(String cfgPath) {
		try{
			DeviceParam deviceParam = this.loadConfigration(cfgPath);
			deviceTable.put(deviceParam.getMonitorDevice().getDeviceId(), deviceParam);
			return true;
		}catch(Exception e){
			logger.info(e.getMessage(),e);
			return  false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tenline.pinecone.platform.osgi.monitor.IDeviceSerivce#getDevice(java
	 * .lang.String)
	 */
	@Override
	public DeviceParam getDevice(String deviceId) {
		if (deviceId != null) {
			return deviceTable.get(deviceId);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tenline.pinecone.platform.osgi.monitor.IDeviceSerivce#getDeviceNums()
	 */
	@Override
	public int getDeviceNums() {
		return deviceTable.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tenline.pinecone.platform.osgi.monitor.IDeviceSerivce#containsDevice
	 * (java.lang.String)
	 */
	@Override
	public boolean containsDevice(String deviceId) {
		if (deviceId != null) {
			return deviceTable.contains(deviceId);
		}
		return false;
	}

	/**
	 * @param deviceCfgPath
	 * @return load configration
	 */
	public  DeviceParam loadConfigration(String deviceCfgPath) {
		try {
			DeviceParam deviceParam = new DeviceParam();
			// get device
			deviceParam.loadDevice(deviceCfgPath);
			Device device = deviceParam.getMonitorDevice();
			// get command list
			deviceParam.loadCommands(device, deviceCfgPath);
			List<Command> commandList = deviceParam.getCommands();
			device.setCommandList(commandList);
			for (Command command : commandList) {
				command.setDevice(device);
				command.setDeviceId(device.getDeviceId());
			}

			deviceParam.loadPointConfig(device, deviceCfgPath
					, FilePath.ControlPointPath);
			deviceParam.loadPointConfig(device, deviceCfgPath
					, FilePath.StatePointPath);
			ProtocolConverter.setVariblesInCommand(deviceParam.getModelDevice(), deviceParam);
			return deviceParam;
		} catch (Exception e) {
			logger.error(e.toString(), e);
			return null;
		}

	}

}
