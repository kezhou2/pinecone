/**
 * 
 */
package com.honking.pet.aquarium.efish;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Item;
import com.tenline.pinecone.platform.model.Variable;
import com.tenline.pinecone.platform.monitor.BufferHelper;
import com.tenline.pinecone.platform.monitor.mina.AbstractMinaProtocolEncoder;

/**
 * @author Bill
 * 
 */
public class EfishProtocolEncoder extends AbstractMinaProtocolEncoder {

	/**
	 * 
	 * @param device
	 */
	public EfishProtocolEncoder(Device device) {
		super(device);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void encode(IoSession arg0, Object arg1, ProtocolEncoderOutput arg2)
			throws Exception {
		// TODO Auto-generated method stub
		transmitPacket(buildPacket((Device) arg1), arg2);
	}

	@Override
	protected byte[] buildPacket(Device device) {
		// TODO Auto-generated method stub
		return buildPacketType((Variable) device.getVariables().toArray()[0]);
	}

	@Override
	protected byte[] buildPacketType(Variable variable) {
		// TODO Auto-generated method stub
		byte[] bytes = null;
		if (variable.getName().equals(
				bundle.getHeaders().get("Water-Temperature"))) {
			if (variable.getItems() == null) {
				bytes = new byte[] { 0x02 };
			} else {
				bytes = BufferHelper.appendBuffer(new byte[] { 0x04, 0x01 },
						bytes);
				bytes = BufferHelper.appendBuffer(buildPacketData(variable),
						bytes);
			}
		} else if (variable.getName().equals(
				bundle.getHeaders().get("Oxygen-Generation"))) {
			bytes = BufferHelper.appendBuffer(new byte[] { 0x03, 0x01 }, bytes);
			bytes = BufferHelper.appendBuffer(buildPacketData(variable), bytes);
		}
		return bytes;
	}

	@Override
	protected byte[] buildPacketData(Variable variable) {
		// TODO Auto-generated method stub
		byte[] bytes = null;
		String temp = ((Item) variable.getItems().toArray()[0]).getValue();
		if (variable.getName().equals(
				bundle.getHeaders().get("Water-Temperature"))) {
			if (temp.equals("20")) {
				bytes = new byte[] { (byte) 0x08, (byte) 0x02 };
			} else if (temp.equals("21")) {
				bytes = new byte[] { (byte) 0xfa, (byte) 0x01 };
			} else if (temp.equals("22")) {
				bytes = new byte[] { (byte) 0xec, (byte) 0x01 };
			} else if (temp.equals("23")) {
				bytes = new byte[] { (byte) 0xdf, (byte) 0x01 };
			} else if (temp.equals("24")) {
				bytes = new byte[] { (byte) 0xd1, (byte) 0x01 };
			} else if (temp.equals("25")) {
				bytes = new byte[] { (byte) 0xc2, (byte) 0x01 };
			} else if (temp.equals("26")) {
				bytes = new byte[] { (byte) 0xb6, (byte) 0x01 };
			} else if (temp.equals("27")) {
				bytes = new byte[] { (byte) 0xab, (byte) 0x01 };
			} else if (temp.equals("28")) {
				bytes = new byte[] { (byte) 0xa1, (byte) 0x01 };
			} else if (temp.equals("29")) {
				bytes = new byte[] { (byte) 0x97, (byte) 0x01 };
			} else if (temp.equals("30")) {
				bytes = new byte[] { (byte) 0xbd, (byte) 0x01 };
			}
		} else if (variable.getName().equals(
				bundle.getHeaders().get("Oxygen-Generation"))) {
			String[] temps = temp.split("-");
			bytes = new byte[] { Byte.valueOf(temps[0]),
					(byte) (Byte.valueOf(temps[1]) * 5) };
		}
		return bytes;
	}

	@Override
	protected byte buildPacketCheck(byte[] bytes) {
		// TODO Auto-generated method stub
		return 0;
	}

}
