package com.tenline.pinecone.platform.osgi.monitor.model.comm;

import org.apache.mina.transport.serial.SerialAddress.DataBits;
import org.apache.mina.transport.serial.SerialAddress.FlowControl;
import org.apache.mina.transport.serial.SerialAddress.Parity;
import org.apache.mina.transport.serial.SerialAddress.StopBits;

/**
 * @author wyq
 */
public class SerialComm extends CommType {
	private int baudRate;
	private DataBits dataBits;
	private StopBits stopBits;
	private Parity parity;
	private FlowControl flowControl;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getBaudRate() {
		return baudRate;
	}

	public void setBaudRate(String baudRate) {
		if (baudRate == null) {
			return;
		}
		this.baudRate = Integer.parseInt(baudRate);
	}

	public DataBits getDataBits() {
		return dataBits;
	}

	public void setDataBits(String databits) {
		if (databits == null) {
			return;
		}
		if (databits.equals("5")) {
			this.dataBits = DataBits.DATABITS_5;
		}
		if (databits.equals("6")) {
			this.dataBits = DataBits.DATABITS_6;
		}
		if (databits.equals("7")) {
			this.dataBits = DataBits.DATABITS_7;
		}
		if (databits.equals("8")) {
			this.dataBits = DataBits.DATABITS_8;
		}
	}

	public StopBits getStopBits() {
		return stopBits;
	}

	public void setStopBits(String stopbits) {
		if (stopbits == null) {
			return;
		}
		if (stopbits.equals("1")) {
			this.stopBits = StopBits.BITS_1;
		}
		if (stopbits.equals("1.5")) {
			this.stopBits = StopBits.BITS_1_5;
		}
		if (stopbits.equals("2")) {
			this.stopBits = StopBits.BITS_2;
		}
	}

	public Parity getParity() {
		return parity;
	}

	public void setParity(String parity) {
		if (parity == null) {
			return;
		}
		parity = parity.toLowerCase();
		if (parity.equals("none")) {
			this.parity = Parity.NONE;
		}
		if (parity.equals("even")) {
			this.parity = Parity.EVEN;
		}
		if (parity.equals("odd")) {
			this.parity = Parity.ODD;
		}
	}

	public FlowControl getFlowControl() {
		return flowControl;
	}

	public void setFlowControl(String flowControl) {
		if (flowControl == null) {
			return;
		}
		flowControl = flowControl.toLowerCase();
		if (flowControl.equals("none")) {
			this.flowControl = FlowControl.NONE;
		}
		if (flowControl.equals("rtscts_in")) {
			this.flowControl = FlowControl.RTSCTS_IN;
		}
		if (flowControl.equals("rtscts_out")) {
			this.flowControl = FlowControl.RTSCTS_OUT;
		}
		if (flowControl.equals("xonxoff_in")) {
			this.flowControl = FlowControl.XONXOFF_IN;
		}
		if (flowControl.equals("xonxoff_out")) {
			this.flowControl = FlowControl.XONXOFF_OUT;
		}
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String toString() {
		return this.getName();
	}
}
