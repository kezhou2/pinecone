/**
 * 
 */
package com.tenline.pinecone.platform.osgi.device.efish;

import java.util.ArrayList;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Item;
import com.tenline.pinecone.platform.model.Variable;
import com.tenline.pinecone.platform.osgi.monitor.mina.AbstractMinaProtocolDecoder;

/**
 * @author Bill
 *
 */
public class EfishProtocolDecoder extends AbstractMinaProtocolDecoder {

	/**
	 * 
	 * @param device
	 */
	public EfishProtocolDecoder(Device device) {
		super(device);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected byte[] checkPacket(byte[] packet) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void splitPacket(byte[] packet, ProtocolDecoderOutput output) {
		// TODO Auto-generated method stub
		splitPacketType(packet, output);
	}

	@Override
	protected void splitPacketType(byte[] packet, ProtocolDecoderOutput output) {
		// TODO Auto-generated method stub
		if (packet[0] == 0x02) {
			splitPacketData(packet, output);	
		} else if (packet[0] == 0x03) {
			Device device = new Device();
			device.setVariables(new ArrayList<Variable>());
			Variable variable = new Variable();
			variable.setName(bundle.getHeaders().get("Oxygen-Generation").toString());
			device.getVariables().add(variable);
			output.write(device);
		} else if (packet[0] == 0x04) {
			Device device = new Device();
			device.setVariables(new ArrayList<Variable>());
			Variable variable = new Variable();
			variable.setName(bundle.getHeaders().get("Water-Temperature").toString());
			device.getVariables().add(variable);
			output.write(device);
		}
	}

	@Override
	protected void splitPacketData(byte[] packet, ProtocolDecoderOutput output) {
		// TODO Auto-generated method stub
		Device device = new Device();
		device.setVariables(new ArrayList<Variable>());
		Variable variable = new Variable();
		variable.setName(bundle.getHeaders().get("Water-Temperature").toString());
		variable.setItems(new ArrayList<Item>());
		Item item = new Item();
		String value = null;
//		if (true) {
//			value = "20";
//		} else if (true) {
//			value = "21";
//		} else if (true) {
//			value = "22";
//		} else if (true) {
//			value = "23";
//		} else if (true) {
//			value = "24";
//		} else if (true) {
//			value = "25";
//		} else if (true) {
//			value = "26";
//		} else if (true) {
//			value = "27";
//		} else if (true) {
//			value = "28";
//		} else if (true) {
//			value = "29";
//		} else if (true) {
//			value = "30";
//		} 
		item.setValue(value);
		variable.getItems().add(item);
		device.getVariables().add(variable);
		output.write(device);
	}

	@Override
	protected String splitPacketRejectedDescription(byte[] rejectedCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean doDecode(IoSession arg0, IoBuffer in,
			ProtocolDecoderOutput output) throws Exception {
		// TODO Auto-generated method stub
		int start = in.position();
		int count = -1;
		while (in.hasRemaining()) {
			byte current = in.get();
			if (current == 0x02) {
				start = in.position();
				count = 2;
			} else if (current == 0x03 || current == 0x04) {
				start = in.position();
				count = 1;
			} else {
				if (count > 0) count--;
				if (count == 0) {
					int position = in.position();
					int limit = in.limit();
					try {
						in.position(start);
						in.limit(position);
						splitPacket(in.slice().array(), output);
					} finally {
						in.position(position);
						in.limit(limit);
					}
					return true;
				}
			}
		}
		in.position(start);
		return false;
	}

}
