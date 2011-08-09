package com.tenline.pinecone.platform.osgi.monitor.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Item;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.model.Variable;
import com.tenline.pinecone.platform.osgi.monitor.model.Command;
import com.tenline.pinecone.platform.osgi.monitor.model.Point;
import com.tenline.pinecone.platform.osgi.monitor.model.point.PointComboBoxGroup;
import com.tenline.pinecone.platform.osgi.monitor.model.point.PointComboBoxItem;
import com.tenline.pinecone.platform.osgi.monitor.service.DeviceService;
import com.tenline.pinecone.platform.osgi.monitor.service.ItemService;
import com.tenline.pinecone.platform.osgi.monitor.service.UserService;
import com.tenline.pinecone.platform.osgi.monitor.service.VariableService;
import com.tenline.pinecone.platform.osgi.monitor.xml.DeviceParam;

public class ProtocolConverter {
	public static Device Command2Device(Command command) {
		Device dev = new Device();
		dev.setId(command.getDeviceId());
		dev.setName(command.getDevice().getName());
		dev.setSymbolicName(command.getDevice().getAlias());
		dev.setUser(null);
		dev.setVersion(null);
		dev.setVariables(null);
		return dev;
	}

	/**
	 * convert monitor device 2 model device 2 save db
	 * 
	 * @param param
	 * @return device
	 */
	public static Device ConvertMonitorDev2ModelDev(
			com.tenline.pinecone.platform.osgi.monitor.model.Device monitorDevice) {
		if (monitorDevice == null) {
			return null;
		}
		ArrayList<Device> devices = DeviceService.getInstance()
				.getDeviceBySymName(monitorDevice.getDeviceId());
		for (Device dev : devices) {
			if (dev.getSymbolicName().equals(monitorDevice.getDeviceId())) {
				// has been saved
				return dev;
			}
		}
		// no save
		Device device = new Device();
		device.setName(monitorDevice.getName());
		device.setSymbolicName(monitorDevice.getDeviceId());
		device.setVersion("0.1.0");
		User user = UserService.getInstance().getUserBySnsId("251760162");
		device.setUser(user);
		DeviceService.getInstance().saveDevice(device);
		device = DeviceService.getInstance().getAddDev();
		return device;
	}

	public static void setVariblesInCommand(Device device, DeviceParam param) {
		List<Command> cmdList = param.getMonitorDevice().getCommandList();
		for (Command cmd : cmdList) {
			if (cmd.getType().equals("in")) {
				// / 转换stateppoint
				List<Point> statePoints = cmd.getStatePointList();
				for (Point point : statePoints) {
					if (point.getVariable() == null) {
						getVariable(device, cmd, point);
					}
				}
			} else if (cmd.getType().equalsIgnoreCase("out")) {
				List<Point> ctrlPoints = cmd.getControlPointList();
				for (Point point : ctrlPoints) {
					if (point.getVariable() == null) {
						getVariable(device, cmd, point);
					}
				}
			}
		}
		return;
	}

	private static void getVariable(Device device, Command cmd, Point point) {
		// 初始化
		ArrayList<Variable> vars = VariableService.getInstance()
				.getVariableByDevId(device.getId());
		boolean isSave = false;
		for (Variable var : vars) {
			if (var.getName().equals(point.getName())) {
				isSave = true;
				point.setVariable(var);
			}
		}
		if (!isSave) {
			// 没有保存
			Variable var = new Variable();
			var.setDevice(device);
			var.setName(point.getName());
			if (cmd.getType().equalsIgnoreCase("in")) {
				var.setType("Read_Discrete");
			} else {
				var.setType("Write_Discrete");
			}
			VariableService.getInstance().saveVariable(var);
			var = VariableService.getInstance().getAddVar();
			ArrayList<Item> items = new ArrayList<Item>();
			LinkedList<PointComboBoxGroup> guiComboxItems = point
					.getGuiComboxItems();
			if (guiComboxItems == null) {
				// add min
				Item minItem = new Item();
				minItem.setText(point.getMinValue().toString());
				minItem.setValue(point.getMaxValue().toString());
				minItem.setVariable(var);
				items.add(minItem);
				ItemService.getInstance().saveItem(minItem);
				// add max
				Item maxItem = new Item();
				maxItem.setText(point.getMaxValue().toString());
				maxItem.setValue(point.getMaxValue().toString());
				maxItem.setVariable(var);
				ItemService.getInstance().saveItem(maxItem);
				items.add(maxItem);

			} else {

				for (PointComboBoxGroup group : guiComboxItems) {
					HashMap<String, PointComboBoxItem> itemMap = group
							.getComboBoxText_ComboBoxItem_Map();
					for (String value : itemMap.keySet()) {
						Item item = new Item();
						item.setText(itemMap.get(value).getText());
						item.setValue(itemMap.get(value).getValue());
						item.setVariable(var);
						ItemService.getInstance().saveItem(item);
						items.add(item);
					}
				}
			}
//			var.setItems(items);
			cmd.setVarable(var);
		}
	}
}
