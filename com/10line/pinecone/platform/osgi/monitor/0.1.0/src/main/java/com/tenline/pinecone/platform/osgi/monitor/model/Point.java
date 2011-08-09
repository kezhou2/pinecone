package com.tenline.pinecone.platform.osgi.monitor.model;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.tenline.pinecone.platform.model.Variable;
import com.tenline.pinecone.platform.osgi.monitor.model.point.PointComboBoxGroup;
import com.tenline.pinecone.platform.osgi.monitor.model.point.PointComboBoxItem;
import com.tenline.pinecone.platform.osgi.monitor.model.point.PointResponse;
import com.tenline.pinecone.platform.osgi.monitor.model.point.PointType;
import com.tenline.pinecone.platform.osgi.monitor.tool.NumTools;
import com.tenline.pinecone.platform.osgi.monitor.xml.DeviceParam;

public class Point implements Cloneable {
	/**
	 * logger
	 */
	protected static Logger logger = Logger.getLogger(Point.class);
	private Variable variable;

	public com.tenline.pinecone.platform.model.Variable getVariable() {
		return variable;
	}

	public void setVariable(
			com.tenline.pinecone.platform.model.Variable variable) {
		this.variable = variable;
	}

	private String pointId = ""; // Id号
	private String name = ""; // 英文名称
	private String alias = ""; // 中文名称
	private String deviceId = ""; // 所属设备Id
	private Object value = ""; // 实际的值
	private String commandId = ""; // 对应接口文件的数据类型
	private int length; // 字节长度
	private String dataType = ""; // 数据类型int
	private Object maxValue = new Object(); // 最大值
	private Object minValue = new Object(); // 最小值
	private String unit = ""; // 单位
	private Object steping = ""; // 步进
	private LinkedList<PointComboBoxGroup> guiComboxItems = new LinkedList<PointComboBoxGroup>(); // 界面显示为组合框的内容
	private String offset;
	private PointResponse response; // 控制响应
	/**
	 * 自定义类型的读写顺序：HiToLo正序读写、LoToHi为反序书写
	 */
	private String sequence;
	/**
	 * HiToLo正序读写
	 */
	public static final String HI_TO_LO = "HiToLo";
	/**
	 * LoToHi为反序书写
	 */
	public static final String LO_TO_HI = "LoToHi";
	/**
	 * point 开始结束位置 开始位(从1开始)，结束位，用于错误诊断
	 */
	private int posStart = 0;
	private int posEnd = 0;

	public static Point createPoint(DeviceParam deviceParam, String dataType,
			String basePath, String filePath) {
		Point point = null;
		try {
			// 获得自定义类型
			PointType pointType = deviceParam.getPointType(dataType, basePath,
					filePath);
			if (pointType == null) {
				return null;
			}
			// 获得配置文件中自定义类型的路径
			String classpath = pointType.getClassPath();
			if (classpath == null || classpath.trim().equals("")) {
				return null;
			}
			// 生成point对象
			point = (Point) (Class.forName(classpath).newInstance());
			point.setSequence(pointType.getSequence());
			return point;
		} catch (InstantiationException e) {
			logger.warn(e.toString(), e);
			return null;
		} catch (IllegalAccessException e) {
			logger.warn(e.toString(), e);
			return null;
		} catch (ClassNotFoundException e) {
			logger.warn(e.toString(), e);
			return null;
		}
	}

	public String getPointId() {
		return this.pointId;
	}

	public void setPointId(String pointId) {
		this.pointId = pointId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public Object getValue() {
		return value;
	}

	public String getCommandId() {
		return commandId;
	}

	public void setCommandId(String commandId) {
		this.commandId = commandId;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Object getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Object maxValue) {
		this.maxValue = maxValue;
	}

	public Object getMinValue() {
		return minValue;
	}

	public void setMinValue(Object minValue) {
		this.minValue = minValue;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Object getSteping() {
		return steping;
	}

	public void setSteping(Object steping) {
		this.steping = steping;
	}

	public LinkedList<PointComboBoxGroup> getGuiComboxItems() {
		return guiComboxItems;
	}

	public String getOffset() {
		return offset;
	}

	public void setOffset(String offset) {
		this.offset = offset;
	}

	public PointResponse getResponse() {
		return response;
	}

	public void setResponse(PointResponse response) {
		this.response = response;
	}

	public String readMark(DataInput aDataInput) throws IOException {
		String str = "";
		str = String.valueOf(aDataInput.readUnsignedByte());
		return str;
	}

	public String toString() {
		return this.alias;
	}

	public void writeData(DataOutput aDataOutput) {
	}

	public void readData(DataInput aDataInput) throws IOException {
	}

	public boolean isValid(Object value) {
		return false;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public void readDataDoNotUseThisValue(DataInput aDataInput, int dataLength)
			throws IOException {
		for (int i = 0; i < dataLength; i++) {
			aDataInput.readByte();
		}
	}

	public int getPosStart() {
		return posStart;
	}

	public int getPosEnd() {
		return posEnd;
	}

	public void setPos(int posStart, int posEnd) {
		this.posStart = posStart;
		this.posEnd = posEnd;
	}

	/**
	 * @todo 设置点值，并将改值设置到comboxgroup中的value，如果按照bit存储的，将设置在每个comboxItems中
	 * @param value
	 *            Object
	 */
	public void setValue(Object value) {
		this.value = value;
		if (this.guiComboxItems.size() >= 1) {
			for (int i = 0; i < guiComboxItems.size(); i++) {
				PointComboBoxGroup pointComBox = (PointComboBoxGroup) guiComboxItems
						.get(i);
				byte begin = pointComBox.getBegin();
				byte end = pointComBox.getEnd();
				if (begin == -1 || end == -1) {
					pointComBox.setValue(String.valueOf(value));
				} else {
					// String binary_value =
					// TimeTools.getInstance().decToBin((Byte) value);
					/**
					 * 2010 04 01 给了823新加的BaseToHexToASCIIPoint做的改动
					 */
					String binary_value = NumTools.decToBin((byte) Double
							.parseDouble(value.toString()));
					int string_begin = 7 - end;
					int string_end = 7 - begin;
					if (string_begin < 0) {
						string_begin = 0;
					}
					String str = "";
					if (string_end < 8) {
						str = binary_value.substring(string_begin,
								string_end + 1);
					} else {
						str = binary_value.substring(string_begin);
					}
					int value_int = NumTools.binToDec(str);
					pointComBox.setValue(Integer.toString(value_int));
				}
			}
		}
	}

	/**
	 * @todo 界面用
	 * @param comboBoxTexts
	 *            String[]
	 */
	public void setStringValue(String[] comboBoxTexts) {
		if (this.getGuiComboxItems().size() == 1) {
			PointComboBoxGroup pointComBoxGroup = (PointComboBoxGroup) this.guiComboxItems
					.get(0);
			PointComboBoxItem pointComboBoxItem = (PointComboBoxItem) pointComBoxGroup
					.getComboBoxText_ComboBoxItem_Map().get(comboBoxTexts[0]);
			String comboBoxItemValue = pointComboBoxItem.getValue();
			pointComBoxGroup.setValue(comboBoxItemValue);
			this.setValue(pointComBoxGroup.getValue());
		} else {
			for (int i = 0; i < comboBoxTexts.length; i++) {
				PointComboBoxGroup pointComBoxGroup = (PointComboBoxGroup) this.guiComboxItems
						.get(i);
				PointComboBoxItem pointComboBoxItem = (PointComboBoxItem) pointComBoxGroup
						.getComboBoxText_ComboBoxItem_Map().get(
								comboBoxTexts[i]);
				String comboBoxValue = pointComboBoxItem.getValue();
				pointComBoxGroup.setValue(comboBoxValue);
			}
			this.setValue(this.setByteValueOfMutiComboBox());
		}
	}

	/**
	 * @todo 根据每一个combox中的vaule值，设置本point的value值
	 */
	public Byte setByteValueOfMutiComboBox() {
		StringBuffer binaryString = new StringBuffer("00000000");
		for (int i = 0; i < this.guiComboxItems.size(); i++) {
			PointComboBoxGroup pointComboBoxGroup = (PointComboBoxGroup) guiComboxItems
					.get(i);
			byte guiComboxItemsBegin = pointComboBoxGroup.getBegin();
			byte guiComboxItemsEnd = pointComboBoxGroup.getEnd();
			String guiComboxItemsValue = pointComboBoxGroup.getValue();
			Byte guiComboxItemsValue_byte = new Byte(guiComboxItemsValue);
			String guiComboxItemsValue_binarybyte = NumTools
					.decToBin(guiComboxItemsValue_byte);
			int len = guiComboxItemsEnd - guiComboxItemsBegin + 1;
			String subString = guiComboxItemsValue_binarybyte
					.substring(7 - len + 1);
			for (int j = 1; j <= len; j++) {
				binaryString.setCharAt(8 - guiComboxItemsBegin - j,
						subString.charAt(subString.length() - j));
			}
		}
		int v = NumTools.binToDec(binaryString.toString());
		if (v > 127 && v <= 256) {
			v = v - 256;
		}
		byte value_byte = Byte.parseByte(String.valueOf(v));
		return Byte.valueOf(value_byte);
	}

	public Object getValueforGUI() {
		try {
			List<PointComboBoxGroup> comBoxList = this.getGuiComboxItems();
			int ComBoxListCount = comBoxList.size();
			if (ComBoxListCount == 0) {
				// 表示没有ComBox直接返回参数值
				return this.getValue();
			}
			String[] ComBoxItemsText = new String[ComBoxListCount];
			for (int i = 0; i < ComBoxListCount; i++) {
				PointComboBoxGroup comboBoxGroup = comBoxList.get(i);
				// comboBoxGroup.getComBoxItemsforValue().get(comboBoxGroup.getValue())是要获得PointComboBoxItem对象
				ComBoxItemsText[i] = comboBoxGroup
						.getComboBoxValue_ComboBoxItem_Map()
						.get(comboBoxGroup.getValue()).getText();
			}
			if (ComBoxItemsText.length == 1) {
				// 单point含有单个ComBox，返回参数值
				return ComBoxItemsText[0];
			}
			// 单point含有多个ComBox，返回数组
			return ComBoxItemsText;
		} catch (Exception e) {
			logger.warn(e.toString(), e);
			// TODO 数据参数超限
			return "参数超限！超限值为：" + this.getValue();
		}
	}

	public Object clone() {
		Point point = new Point();
		point.alias = this.alias;
		point.commandId = this.commandId;
		point.dataType = this.dataType;
		point.deviceId = this.deviceId;
		point.guiComboxItems = this.guiComboxItems;
		point.length = this.length;
		point.maxValue = this.maxValue;
		point.name = this.name;
		point.minValue = this.minValue;
		point.offset = this.offset;
		point.pointId = this.pointId;
		point.response = this.response;
		point.unit = this.unit;
		point.value = this.value;
		return point;
	}
}
