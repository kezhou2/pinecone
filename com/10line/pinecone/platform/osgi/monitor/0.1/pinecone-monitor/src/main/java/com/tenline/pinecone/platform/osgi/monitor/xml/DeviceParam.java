package com.tenline.pinecone.platform.osgi.monitor.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;

import com.tenline.pinecone.platform.osgi.monitor.mina.protocol.AbstractProtocolDecoder;
import com.tenline.pinecone.platform.osgi.monitor.mina.protocol.AbstractProtocolEncoder;
import com.tenline.pinecone.platform.osgi.monitor.model.Command;
import com.tenline.pinecone.platform.osgi.monitor.model.Device;
import com.tenline.pinecone.platform.osgi.monitor.model.Point;
import com.tenline.pinecone.platform.osgi.monitor.model.comm.CommType;
import com.tenline.pinecone.platform.osgi.monitor.model.comm.SerialComm;
import com.tenline.pinecone.platform.osgi.monitor.model.comm.SocketComm;
import com.tenline.pinecone.platform.osgi.monitor.model.device.DeviceStatus;
import com.tenline.pinecone.platform.osgi.monitor.model.point.PointComboBoxGroup;
import com.tenline.pinecone.platform.osgi.monitor.model.point.PointComboBoxItem;
import com.tenline.pinecone.platform.osgi.monitor.model.point.PointResponse;
import com.tenline.pinecone.platform.osgi.monitor.model.point.PointType;
import com.tenline.pinecone.platform.osgi.monitor.model.point.ResponseItem;
import com.tenline.pinecone.platform.osgi.monitor.tool.FilePath;
import com.tenline.pinecone.platform.osgi.monitor.tool.ProtocolConverter;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class DeviceParam implements IDeviceParam {
	private static Logger logger = Logger.getLogger(DeviceParam.class);

	/**
	 * command lists
	 */
	List<Command> commands;
	/**
	 * command map
	 */
	private TreeMap<String, Command> commandMap;
	/**
	 * control point list
	 */
	private TreeMap<String, Point> controlPointsMap;
	/**
	 * state point list
	 */
	private TreeMap<String, Point> statePointsMap;
	private Device monitorDevice;
	private com.tenline.pinecone.platform.model.Device modelDevice;

	public DeviceParam() {
		commands = new ArrayList<Command>();
		commandMap = new TreeMap<String, Command>();
		controlPointsMap = new TreeMap<String, Point>();
		statePointsMap = new TreeMap<String, Point>();
	}

	/**
	 * @ create device comm type
	 */
	@SuppressWarnings("unchecked")
	public CommType loadCommType(String type, String devicePath) {
		Document doc;
		try {
			if (type.equals("serial")) {
				doc = XMLLoad.getXMLDocument(devicePath + FilePath.PortPath);
				if (doc == null) {
					System.out.println("Failed at parsing "
							+ FilePath.DevicePath);
					return null;
				}
				Element root = doc.getRootElement();

				List<Element> list = root.getChildren();
				if (list.size() > 1) {
					return null;
				}
				Element element = list.get(0);
				SerialComm commType = new SerialComm();
				commType.setId(element.getChildText("id"));
				commType.setComment(element.getChildText("comment"));
				commType.setName(element.getChildText("name"));
				commType.setBaudRate(element.getChildText("baudRate"));
				commType.setDataBits(element.getChildText("dataBits"));
				commType.setFlowControl(element.getChildText("flowControl"));
				commType.setParity(element.getChildText("parity"));
				commType.setStopBits(element.getChildText("stopBits"));
				return commType;
			} else if (type.contains("tcp") || type.contains("udp")) {
				doc = XMLLoad.getXMLDocument(FilePath.SocketPath);
				if (doc == null) {
					System.out.println("Failed at parsing "
							+ FilePath.DevicePath);
					return null;
				}
				Element root = doc.getRootElement();
				List<Element> list = root.getChildren();
				if (list.size() > 1) {
					return null;
				}
				Element element = list.get(0);
				SocketComm commType = new SocketComm();
				commType.setId(element.getChildText("id"));
				commType.setComment(element.getChildText("comment"));
				commType.setIp(element.getChildText("ip"));
				commType.setPort(element.getChildText("port"));
				return commType;
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
			System.out.println("Failed at parsing " + FilePath.DevicePath);
			return null;
		}

	}

	/**
	 * @param filePath
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean loadDevice(String devicePath) {
		Document doc;
		try {
			doc = XMLLoad.getXMLDocument(devicePath + FilePath.DevicePath);
			if (doc != null) {
				Element root = doc.getRootElement();

				List<Element> deviceList = root.getChildren();
				if (deviceList.size() > 1) {
					return false;
				}
				Element element = deviceList.get(0);
				monitorDevice = new Device();
				monitorDevice.setDeviceId(element.getChildText("id"));
				monitorDevice.setName(element.getChildText("name"));

				monitorDevice.setCommType(this.loadCommType(
						element.getChildText("commType"), devicePath));

				monitorDevice.setAlias(element.getChildText("alias"));
				monitorDevice.setSystem(element.getChildText("system"));
				monitorDevice.setSubSystem(element.getChildText("subSystem"));
				monitorDevice.setEnvironmentId(element.getChildText("environmentId"));
				String protocolEncoder = element.getChildText("encoder");
				AbstractProtocolEncoder encoder;
				if (protocolEncoder != null) {
					encoder = (AbstractProtocolEncoder) (Class
							.forName(protocolEncoder).newInstance());
					encoder.setDeviceID(monitorDevice.getDeviceId());
					monitorDevice.setEncoder(encoder);
				}
				String protocolDecoder = element.getChildText("decoder");
				AbstractProtocolDecoder decoder;
				if (protocolEncoder != null) {
					decoder = (AbstractProtocolDecoder) (Class
							.forName(protocolDecoder).newInstance());
					monitorDevice.setDecoder(decoder);
					decoder.setDeviceID(monitorDevice.getDeviceId());
				}
				monitorDevice.setDeviceStatus(DeviceStatus.DEVICE_NOT_INIT);

				return true;
			} else {
				System.out.println("Failed at parsing " + devicePath
						+ FilePath.DevicePath);
				return false;
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
			System.out.println("Failed at parsing " + devicePath
					+ FilePath.DevicePath);
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public boolean loadCommands(Device device, String devicePath) {

		Document doc;
		try {
			doc = XMLLoad.getXMLDocument(devicePath + FilePath.CommandPath);
			if (doc != null) {
				Element root = doc.getRootElement();
				List<Element> elementList = root.getChildren();
				for (Element element : elementList) {
					Command command = new Command();
					command.setId(element.getChildText("id"));
					command.setAlias(element.getChildText("alias"));
					command.setName(element.getChildText("name"));
					command.setDeviceId(element.getChildText("deviceId"));
					command.setType(element.getChildText("type"));
					command.setQueryTime(Long.valueOf(element
							.getChildText("querytime")));
					this.commandMap.put(device.getDeviceId() + command.getId(),
							command);
					command.setStatePointList(new ArrayList<Point>());
					command.setControlPointList(new ArrayList<Point>());
					commands.add(command);
				}
				device.setCommandList(commands);
			} else {
				logger.warn("Warn at parsing " + FilePath.CommandPath);
				return false;
			}
		} catch (Exception e) {
			System.out.println("Error at parsing " + FilePath.CommandPath);
			logger.error(e.toString(), e);
			return false;
		}
		return true;
	}

	/**
	 * @todo 将指定的设备所对应的点放入List对象中
	 * @param device
	 *            Device
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public synchronized Vector<Point> loadPointConfig(Device device,String basePath,
			String pointPath) {
		Vector<Point> list = new Vector<Point>();
		Document doc;
		try {
			doc = XMLLoad.getXMLDocument(basePath+"/"+pointPath);
			if (doc != null) {
				Element root = doc.getRootElement();
				List<Element> pointList = root.getChildren();
				for (Element element : pointList) {
					String dataType = element.getChildText("dataType");
					Point point = Point.createPoint(this,dataType,basePath,
							FilePath.PointTypePath);
					if (point != null) {
						point.setDataType(dataType);
						point.setPointId(element.getChildText("id"));
						point.setAlias(element.getChildText("alias"));
						point.setName(element.getChildText("name"));
						point.setLength(Integer.parseInt(element
								.getChildText("length")));
						point.setDataType(element.getChildText("dataType"));
						point.setMaxValue(element.getChildText("maxValue"));
						point.setMinValue(element.getChildText("minValue"));
						point.setUnit(element.getChildText("unit"));
						point.setDeviceId(element.getChildText("deviceId"));
						point.setCommandId(element.getChildText("commandId"));
						point.setOffset(element.getChildText("offset"));
						point.setSteping(element.getChildText("steping"));
						Element responseElement = element.getChild("response"); // 读出1个<response></response>
						if (responseElement != null) {
							PointResponse pointResponse = new PointResponse();
							pointResponse.setRightValue(Byte
									.parseByte(responseElement
											.getChildText("rightvalue")));
							pointResponse.setValue(Byte
									.parseByte(responseElement
											.getChildText("rightvalue")));
							List<Element> items = responseElement
									.getChildren("item"); // 读出1个<item></item>
							for (Element item : items) {
								ResponseItem responseItem = new ResponseItem();
								responseItem.setText(item.getChildText("text"));
								responseItem.setValue(Byte.parseByte(item
										.getChildText("value")));
								pointResponse
										.getResponseText_ResponseItem_Map()
										.put(responseItem.getText(),
												responseItem);
								pointResponse
										.getResponseValue_ResponseItem_Map()
										.put(responseItem.getValue(),
												responseItem);
							}
							point.setResponse(pointResponse);
						}
						List<Element> comboxList = element
								.getChildren("comBox"); // 读出多个<comBox></comBox>
						for (Element comboxElement : comboxList) {
							// 读出1个<comBox></comBox>
							PointComboBoxGroup pointComBox = new PointComboBoxGroup();
							pointComBox.setBegin(Byte.parseByte(comboxElement
									.getChildText("begin")));
							pointComBox.setEnd(Byte.parseByte(comboxElement
									.getChildText("end")));
							List<Element> comBoxItems = comboxElement
									.getChildren("item"); // 读出1个<item></item>
							for (Element item : comBoxItems) {
								PointComboBoxItem comBoxItem = new PointComboBoxItem();
								comBoxItem.setText(item.getChildText("text"));
								comBoxItem.setValue(item.getChildText("value"));
								pointComBox.getComboBoxText_ComboBoxItem_Map()
										.put(comBoxItem.getText(), comBoxItem);
								pointComBox.getComboBoxValue_ComboBoxItem_Map()
										.put(comBoxItem.getValue(), comBoxItem);
							}
							point.getGuiComboxItems().add(pointComBox);
						}
						String temp = element.getChildText("value");
						point.setValue(temp);
						list.add(point);
						if (pointPath.endsWith(FilePath.ControlPointPath)) {
							this.controlPointsMap
									.put(point.getPointId(), point);
							this.getCommand(point.getDeviceId(), point.getCommandId()).getControlPointList().add(point);
						} else if (pointPath.endsWith(FilePath.StatePointPath)) {
							this.statePointsMap.put(point.getPointId(), point);
							this.getCommand(point.getDeviceId(), point.getCommandId()).getStatePointList().add(point);
						}
					} else {
						logger.error("Pasering " + pointPath + " faliled");
						return null;
					}
				}
			} else {
				logger.warn("Losing file " + pointPath);
				return null;
			}
		} catch (Exception e) {
			System.out.println("Faliled at parsing file " + pointPath);
			logger.error(e.toString(), e);
			return null;
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tenline.pinecone.platform.osgi.monitor.xml.IDeviceParam#getPointType
	 * (java.lang.String, java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public PointType getPointType(String dataType,String basePath, String filePath) {
		Document doc;
		try {
			doc = XMLLoad.getXMLDocument(basePath+"/"+filePath);
			if (doc != null) {
				Element root = doc.getRootElement();
				List<Element> pointTypeList = root.getChildren();
				for (Element element : pointTypeList) {
					if (dataType.equals(element.getChildText("name"))) {
						PointType pointType = new PointType();
						pointType.setName(element.getChildText("name"));
						pointType.setClassPath(element
								.getChildText("classPath"));
						pointType.setSequence(element.getChildText("sequence"));
						return pointType;
					}
				}
				return null;
			} else {
				logger.warn("Failed at parsing " + filePath);
				return null;
			}
		} catch (Exception e) {
			System.out.println("Failed at parsing " + filePath);
			logger.error(e.toString(), e);
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tenline.pinecone.platform.osgi.monitor.xml.IDeviceParam#getControlPoint
	 * (java.lang.String)
	 */
	@Override
	public Point getControlPoint(String pointId) {
		Point point = (Point) this.controlPointsMap.get(pointId);
		if (point != null) {
			return point;
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tenline.pinecone.platform.osgi.monitor.xml.IDeviceParam#getStatePoint
	 * (java.lang.String)
	 */
	@Override
	public Point getStatePoint(String pointId) {
		Point point = (Point) this.statePointsMap.get(pointId);
		if (point != null) {
			return point;
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tenline.pinecone.platform.osgi.monitor.xml.IDeviceParam#getCommand
	 * (java.lang.String, java.lang.String)
	 */
	@Override
	public Command getCommand(String deviceId, String commandId) {
		return this.commandMap.get(deviceId + commandId);
	}

	public Device getMonitorDevice() {
		return this.monitorDevice;
	}
	
	public com.tenline.pinecone.platform.model.Device getModelDevice() {
		if(modelDevice==null){
			modelDevice  = ProtocolConverter.ConvertMonitorDev2ModelDev(this.monitorDevice);
		}
		return modelDevice;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tenline.pinecone.platform.osgi.monitor.xml.IDeviceParam#getCommands()
	 */
	@Override
	public List<Command> getCommands() {
		return commands;
	}

}
