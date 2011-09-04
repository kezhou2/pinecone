/**
 * 
 */
package com.honking.pet.aquarium.efish;

import java.util.TreeMap;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.osgi.framework.Bundle;

import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.monitor.BufferHelper;
import com.tenline.pinecone.platform.monitor.ProtocolHelper;
import com.tenline.pinecone.platform.monitor.mina.AbstractMinaProtocolEncoder;

/**
 * @author Bill
 * 
 */
public class EfishProtocolEncoder extends AbstractMinaProtocolEncoder {

	/**
	 * 
	 * @param bundle
	 */
	public EfishProtocolEncoder(Bundle bundle) {
		super(bundle);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void encode(IoSession arg0, Object arg1, ProtocolEncoderOutput arg2)
			throws Exception {
		// TODO Auto-generated method stub
		try{
			transmitPacket(buildPacket((Device) arg1), arg2);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	protected byte[] buildPacket(Device device) {
		// TODO Auto-generated method stub
		return buildPacketType(ProtocolHelper.marshel(device));
	}

	@Override
	protected byte[] buildPacketType(TreeMap<String, String> map) {
		// TODO Auto-generated method stub
		byte[] bytes = null;
		for (String key : map.keySet()) {
			if (key.equals(bundle.getHeaders().get("Water-Temperature"))) {
				if (map.get(key) == null) {
					bytes = new byte[] { 0x02 };
				} else {
					bytes = BufferHelper.appendBuffer(new byte[] { 0x04, 0x01 }, bytes);
					bytes = BufferHelper.appendBuffer(buildPacketData(map), bytes);
				}
			} else if (key.equals(bundle.getHeaders().get("Oxygen-Generation"))) {
				bytes = BufferHelper.appendBuffer(new byte[] { 0x03, 0x01 }, bytes);
				bytes = BufferHelper.appendBuffer(buildPacketData(map), bytes);
			}	
		}
		return bytes;
	}

	@Override
	protected byte[] buildPacketData(TreeMap<String, String> map) {
		// TODO Auto-generated method stub
		byte[] bytes = null;
		for (String key : map.keySet()) {
			String temp = map.get(key);
			if (key.equals(bundle.getHeaders().get("Water-Temperature"))) {
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
			} else if (key.equals(bundle.getHeaders().get("Oxygen-Generation"))) {
				String[] temps = temp.split("-");
				bytes = new byte[] { Byte.valueOf(temps[0]), (byte) (Byte.valueOf(temps[1]) * 5) };
			}	
		}
		return bytes;
	}

	@Override
	protected byte buildPacketCheck(byte[] bytes) {
		// TODO Auto-generated method stub
		return 0;
	}

}
