/**
 * 
 */
package com.tenline.pinecone.platform.osgi.monitor;

import java.util.TreeMap;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.tenline.pinecone.platform.osgi.monitor.mina.protocol.AbstractProtocolEncoder;
import com.tenline.pinecone.platform.osgi.monitor.model.Command;
import com.tenline.pinecone.platform.osgi.monitor.model.stream.BytesOutputStream;

/**
 * @author Administrator
 * 
 */
public class UsbFishTankEncoder extends AbstractProtocolEncoder {

	@Override
	public void encode(IoSession arg0, Object message,
			ProtocolEncoderOutput output) throws Exception {
		Command command = (Command) message;
		IoBuffer buffer = IoBuffer.allocate(1).setAutoExpand(true);
		byte[] packet = buildPacketData(command);
		buffer.put(packet);
		buffer.flip();
		output.write(buffer);
	}

	@Override
	public byte[] buildPacketData(Command command) {
		BytesOutputStream m_ByteOut = new BytesOutputStream(1024);
		try {
			if (command.getId().equals("2")) {

				m_ByteOut.writeByte(0x02);

			} else if (command.getId().equals("3")) {
				// bubble control like 03 01 aa bb
				TreeMap<String, Object> controlMap = command.getControlMap();
				m_ByteOut.writeByte(0x03);
				m_ByteOut.writeByte(0x01);
				Short value = Short.valueOf(controlMap.get("commstar.serial.usbfishtank.temperatureCtrl")
						.toString());
				m_ByteOut.writeShort(value);

			} else if (command.getId().equals("4")) {
				// temperature control like 04 01 aa bb
				TreeMap<String, Object> controlMap = command.getControlMap();
				m_ByteOut.writeByte(0x04);
				m_ByteOut.writeByte(0x01);
				Integer value = Integer.valueOf(controlMap.get("commstar.serial.usbfishtank.bubbleCtrl")
						.toString());
				m_ByteOut.writeShort(value);
			}
			return m_ByteOut.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
