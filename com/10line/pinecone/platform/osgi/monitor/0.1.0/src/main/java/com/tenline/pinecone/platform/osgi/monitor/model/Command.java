package com.tenline.pinecone.platform.osgi.monitor.model;

import java.util.List;
import java.util.TreeMap;

import com.tenline.pinecone.platform.model.Variable;


public class Command implements Cloneable {
	private String deviceId; // 所属设备的id
	private String id; // Command id,对应于接口文档中的数据类别
	private String name; // 英文名称
	private String type; // 类型，输入（in）或输出（out）
	private String alias; // 中文名称
	private byte isShow; // 0=不显示,1=显示
	private long queryTime;
	private Variable varable;
	public Variable getVarable() {
		return varable;
	}

	public void setVarable(Variable varable) {
		this.varable = varable;
	}
	/**
	 * 控制点List
	 */
	private List<Point>			controlPointList;
	/**
	 * 状态点List
	 */
	private List<Point>			statePointList;

	private TreeMap<String, Object> controlMap; // 控制点Map
	private Device device; // 所属的Device
	private boolean isOnlyResponseInOneCommand; // 用在Device模式下的控制令解析
	private boolean hasResponseInOneCommand; // 是否包含控制响应
	private boolean hasMark; // point里的mark用来标识多个point同时使用或只使用部分point
	private int length; // dataLength set(),data get()
	private int status = COMMAND_STATUS_INVALID;
	/**
	 * 01H=所有参数控制到位 02H =收到非法码字 03H =控制参数超限 04H =处于本控态不执行控制令
	 */
	public final static int COMMAND_STATUS_INVALID = 0;
	public final static int COMMNAD_STATUS_VALID = 1;
	public final static int COMMAND_STATUS_ILLEGAL_CODE = 2;
	public final static int COMMAND_STATUS_OVER_LIMIT = 3;
	public final static int COMMAND_STATUS_DENY = 4;

	public Device getDevice() {
		return this.device;
	}

	public void setDevice(Device aDevice) {
		this.device = aDevice;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int aStatus) {
		this.status = aStatus;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String aId) {
		this.id = aId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String aName) {
		this.name = aName;
	}

	public String getType() {
		return type;
	}

	public void setType(String aType) {
		this.type = aType;
	}

	public String getAlias() {
		return this.alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public byte getIsShow() {
		return isShow;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public void setIsShow(byte isShow) {
		this.isShow = isShow;
	}

	public void setMap(TreeMap<String, Object> controlMap) {
		this.controlMap = controlMap;
	}

	public TreeMap<String, Object> getControlMap() {
		return this.controlMap;
	}

	public boolean isOnlyResponseInOneCommand() {
		return isOnlyResponseInOneCommand;
	}

	public void setIsOnlyResponseInOneCommand(List<Point> controlPointList) {
		int responseInOneCommand = 0;
		for (Point point : controlPointList) {
			if (point.getResponse() != null) {
				responseInOneCommand++;
			}
		}
		this.isOnlyResponseInOneCommand = responseInOneCommand == 1 ? true
				: false;
		setHasResponseInOneCommand(responseInOneCommand == 0 ? false : true);
	}

	public boolean isHasResponseInOneCommand() {
		return hasResponseInOneCommand;
	}

	public void setHasResponseInOneCommand(boolean hasResponseInOneCommand) {
		this.hasResponseInOneCommand = hasResponseInOneCommand;
	}

	public boolean isHasMark() {
		return hasMark;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String toString() {
		return this.name;
	}

	public long getQueryTime() {
		return queryTime;
	}

	public void setQueryTime(long queryTime) {
		this.queryTime = queryTime;
	}
	public List<Point> getPointList() {
		if (this.getType().equalsIgnoreCase("out")) {
			return this.controlPointList;
		} else if (this.getType().equalsIgnoreCase("in")) {
			return this.statePointList;
		} else {
			return null;
		}
	}
	
	public List<Point> getControlPointList() {
		return controlPointList;
	}
	
	public void setControlPointList(List<Point> controlPointList) {
		this.controlPointList = controlPointList;
	}
	
	public List<Point> getStatePointList() {
		return statePointList;
	}
	
	public void setStatePointList(List<Point> statePointList) {
		this.statePointList = statePointList;
	}
	public Object clone() {
		Command command = new Command();
		command.id = this.id;
		command.device = this.device;
		command.name = this.name;
		command.type = this.type;
		command.alias = this.alias;
		command.status = this.status;
		// command.controlPointList = this.controlPointList;
		// command.statePointList = this.statePointList;
		command.controlMap = this.controlMap;
		command.isShow = this.isShow;
		return command;
	}
}
