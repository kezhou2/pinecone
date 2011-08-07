/**
 * 
 */
package com.tenline.pinecone.platform.osgi.monitor;

import java.io.DataInputStream;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.tenline.pinecone.platform.osgi.monitor.mina.protocol.AbstractProtocolDecoder;
import com.tenline.pinecone.platform.osgi.monitor.model.Command;
import com.tenline.pinecone.platform.osgi.monitor.model.Point;

/**
 * @author Administrator
 * 
 */
public class UsbFishTankDecoder extends AbstractProtocolDecoder {

	@Override
	protected void splitPacket(byte[] packet, ProtocolDecoderOutput output) {
		// TODO Auto-generated method stub

	}

	@Override
	public void decode(IoSession arg0, IoBuffer in, ProtocolDecoderOutput output)
			throws Exception {
		byte[] data = in.array();
		
		DataInputStream input = new DataInputStream(in.asInputStream());
		input.skip(1);
		if (data.length == 0) {
			// wrong
		} else if (data.length == 1) {
			if (data[0] == 0x03) {
				// set bubble ok

			} else if (data[0] == 0x04) {
				// set temp ok
				Point point = DeviceService.getInstance()
						.getDevice(this.deviceID)
						.getControlPoint("");
//				point.readData(new DataInputStream(input));
			}
		} else {
			if (data[0] == 0x02) {
				// temp state
				Command cmd = DeviceService.getInstance()
						.getDevice(this.deviceID)
						.getCommand(this.deviceID, "2");
				Point point = DeviceService
						.getInstance()
						.getDevice(this.deviceID)
						.getStatePoint(
						"com.10line.pinecone.platform.osgi.monitor.pinecone-device-usbfishtank.temperatureState");
				short temp = input.readShort();
				point.setValue(reverseValue(temp));
				cmd.getStatePointList().get(0).setValue(reverseValue(temp));
				output.write(cmd);
			}
		}

	}

	/**
	 * @param temp
	 * @return reverse temp value 2 string
	 */
	private String reverseValue(short temp) {
		String str = "-1";
		if (temp >= 752 && temp <= 767) {
			str = "-1";
		} else if (temp >= 739 && temp <= 751) {
			str = "0";
		} else if (temp >= 723 && temp <= 738) {
			str = "1";
		} else if (temp >= 706 && temp <= 722) {
			str = "2";
		} else if (temp >= 690 && temp <= 705) {
			str = "3";
		} else if (temp >= 678 && temp <= 689) {
			str = "4";
		} else if (temp >= 664 && temp <= 677) {
			str = "5";
		} else if (temp >= 655 && temp <= 663) {
			str = "6";
		} else if (temp >= 645 && temp <= 654) {
			str = "7";
		} else if (temp >= 638 && temp <= 644) {
			str = "8";
		} else if (temp >= 627 && temp <= 637) {
			str = "9";
		} else if (temp >= 619 && temp <= 626) {
			str = "10";
		} else if (temp >= 610 && temp <= 618) {
			str = "11";
		} else if (temp >= 601 && temp <= 609) {
			str = "12";
		} else if (temp >= 591 && temp <= 600) {
			str = "13";
		} else if (temp >= 581 && temp <= 590) {
			str = "14";
		} else if (temp >= 574 && temp <= 580) {
			str = "15";
		} else if (temp >= 562 && temp <= 573) {
			str = "16";
		} else if (temp >= 549 && temp <= 561) {
			str = "17";
		} else if (temp >= 539 && temp <= 548) {
			str = "18";
		} else if (temp >= 527 && temp <= 538) {
			str = "19";
		} else if (temp >= 514 && temp <= 526) {
			str = "20";
		} else if (temp >= 499 && temp <= 513) {
			str = "21";
		} else if (temp >= 486 && temp <= 498) {
			str = "22";
		} else if (temp >= 476 && temp <= 485) {
			str = "23";
		} else if (temp >= 458 && temp <= 475) {
			str = "24";
		} else if (temp >= 444 && temp <= 457) {
			str = "25";
		} else if (temp >= 433 && temp <= 443) {
			str = "26";
		} else if (temp >= 423 && temp <= 432) {
			str = "27";
		} else if (temp >= 413 && temp <= 422) {
			str = "28";
		} else if (temp >= 403 && temp <= 412) {
			str = "29";
		} else if (temp >= 393 && temp <= 402) {
			str = "30";
		} else if (temp >= 383 && temp <= 392) {
			str = "31";
		} else if (temp >= 372 && temp <= 382) {
			str = "32";
		} else if (temp >= 363 && temp <= 371) {
			str = "33";
		} else if (temp >= 357 && temp <= 362) {
			str = "34";
		} else if (temp >= 350 && temp <= 356) {
			str = "35";
		} else if (temp >= 343 && temp <= 349) {
			str = "36";
		} else if (temp >= 337 && temp <= 342) {
			str = "37";
		} else if (temp >= 332 && temp <= 336) {
			str = "38";
		} else if (temp >= 327 && temp <= 331) {
			str = "39";
		} else if (temp >= 322 && temp <= 326) {
			str = "40";
		} else if (temp >= 317 && temp <= 321) {
			str = "41";
		} else if (temp >= 312 && temp <= 316) {
			str = "42";
		} else if (temp >= 307 && temp <= 311) {
			str = "43";
		} else if (temp >= 303 && temp <= 306) {
			str = "44";
		} else if (temp >= 299 && temp <= 302) {
			str = "45";
		} else if (temp >= 295 && temp <= 298) {
			str = "46";
		} else if (temp >= 291 && temp <= 294) {
			str = "47";
		} else if (temp >= 286 && temp <= 290) {
			str = "48";
		} else if (temp >= 280 && temp <= 285) {
			str = "49";
		} else if (temp >= 274 && temp <= 279) {
			str = "50";
		} else if (temp >= 268 && temp <= 273) {
			str = "51";
		} else if (temp >= 262 && temp <= 267) {
			str = "52";
		} else if (temp >= 256 && temp <= 261) {
			str = "53";
		} else if (temp >= 250 && temp <= 255) {
			str = "54";
		} else if (temp >= 245 && temp <= 249) {
			str = "55";
		} else if (temp >= 240 && temp <= 244) {
			str = "53";
		}
		return str;
	}

}
