package com.tenline.pinecone.platform.osgi.monitor.tool;

import java.math.BigInteger;
import java.net.InetAddress;

import org.apache.log4j.Logger;

import com.tenline.pinecone.platform.osgi.monitor.model.Point;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright:Beijing Institute of Satellite Information Engineering 2006
 * </p>
 * <p>
 * Company:Beijing Institute of Satellite Information Engineering
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public final class NumTools {
	protected static Logger logger = Logger.getLogger(Point.class);
	/**
	 * Returns a <tt>String</tt> containing unsigned hexadecimal numbers as
	 * digits. The <tt>String</tt> will coontain two hex digit characters for
	 * each byte from the passed in <tt>byte[]</tt>.<br>
	 * The bytes will be separated by a space character.
	 * <p>
	 * 
	 * @param data
	 *            the array of bytes to be converted into a hex-string.
	 * @return the generated hexadecimal representation as <code>String</code>.
	 */
	public static final String bytes2HexString(byte[] data) {
		// double size, two bytes (hex range) for one byte
		StringBuffer buf = new StringBuffer(data.length * 2);
		for (int i = 0; i < data.length; i++) {
			// don't forget the second hex digit
			if (((int) data[i] & 0xff) < 0x10) {
				buf.append("0");
			}
			buf.append(Long.toString((int) data[i] & 0xff, 16));
			if (i < data.length - 1) {
				buf.append(" ");
			}
		}
		return buf.toString().toUpperCase();
	} // toHex

	/**
	 * Returns a <tt>byte[]</tt> containing the given byte as unsigned
	 * hexadecimal number digits.
	 * <p>
	 * 
	 * @param i
	 *            int data the int to be converted into a hex string.
	 * @return byte[] the generated hexadecimal representation as
	 *         <code>byte[]</code>.
	 */
	public static final byte[] byte2HexBytes(int i) {
		StringBuffer buf = new StringBuffer(2);
		// don't forget the second hex digit
		if (((int) i & 0xff) < 0x10) {
			buf.append("0");
		}
		buf.append(Long.toString((int) i & 0xff, 16).toUpperCase());
		return buf.toString().getBytes();
	} // toHex

	public static final byte[] short2HexBytes(int value) {
		StringBuffer buf = new StringBuffer(4);
		int convert = value & 0xFFFF;
		if (convert < 0x1000) {
			buf.append("0");
		}
		if (convert < 0x100) {
			buf.append("0");
		}
		if (convert < 0x10) {
			buf.append("0");
		}
		buf.append(Long.toString(convert, 16).toUpperCase());
		return buf.toString().getBytes();
	}

	public static final byte[] short2Ascii(short i) {
		byte[] result = new byte[2];
		if (i < 10) {
			result[0] = 0;
		} else {
			result[0] = (byte) (i / 10);
		}
		result[1] = (byte) (i - (byte) (result[0] * 10));
		return result;
	}

	public static final byte[] int2Decimal(int value) {
		String str = Integer.toString(value);
		int count = str.getBytes().length;
		byte[] result = new byte[count];
		for (int i = 0; i < count; i++) {
			result[i] = Byte.parseByte(str.substring(i, i + 1));
		}
		return result;
	}

	public static final int bytes2Decimal(byte[] value) {
		int result = 0;
		for (int i = 0; i < value.length; i++) {
			result += value[i] * Math.pow(10, value.length - 1 - i);
		}
		return result;
	}

	public static final byte[] decimals2Ascii(byte[] value) {
		byte[] result = new byte[value.length * 2];
		byte[] temp = new byte[2];
		for (int i = 0; i < value.length; i++) {
			temp = byte2HexBytes((int) value[i]);
			result[i * 2] = temp[0];
			result[i * 2 + 1] = temp[1];
		}
		return result;
	}

	public static final byte[] short2Bytes(short value) {
		byte[] result = new byte[2];
		result[0] = (byte) (value >>> 8);
		result[1] = (byte) (value >>> 0);
		return result;
	}

	public static final byte[] int2Bytes(int value) {
		byte[] result = new byte[4];
		result[0] = (byte) (value >>> 0);
		result[1] = (byte) (value >>> 8);
		result[2] = (byte) (value >>> 16);
		result[3] = (byte) (value >>> 24);
		return result;
	}

	public static final byte[] short2Decimal(short value) {
		String str = ""; // =Integer.toString(value);
		byte[] result = new byte[2];
		if (value < 10) {
			str = str + '0';
		}
		str = str + Integer.toString(value);
		int count = str.getBytes().length;
		for (int i = 0; i < count; i++) {
			result[i] = Byte.parseByte(str.substring(i, i + 1));
		}
		return result;
	}

	public static final short bytes2Short(byte[] value) {
		return (short) (((value[1] & 0xFF) << 0) + ((value[0] & 0xFF) << 8));
	}

	public static final byte[] int2Ascii(int value) {
		byte[] result = new byte[2];
		result[0] = (byte) ((value >>> 0) & 0xF);
		if (result[0] > 10) {
			result[0] += 'a';
		} else {
			result[0] += '0';
		}
		result[1] = (byte) ((value >>> 4) & 0xF + '0');
		if (result[1] > 10) {
			result[1] += 'a';
		} else {
			result[1] += '0';
		}
		return result;
	}

	public static final byte byte2Ascii(byte value) {
		byte result = 0;
		if (value < 10) {
			result = (byte) (value + '0');
		} else {
			result = (byte) (value + 'A');
			// result=this.int2HexBytes();
		}
		return result;
	}

	public static final byte[] bytes2Ascii(byte[] value) {
		byte[] result = new byte[value.length];
		for (int i = 0; i < value.length; i++) {
			result[i] = byte2Ascii(value[i]);
		}
		return result;
	}

	public static final byte ascii2Byte(byte value) {
		byte result;
		if (value == 0x2B) {
			result = 0;
		} else if (value == 0x2D) {
			result = 1;
		} else {
			result = (byte) (value - '0');
		}
		return result;
	}

	public static final byte[] ascii2Bytes(byte[] value) {
		byte[] result = new byte[value.length];
		for (int i = 0; i < value.length; i++) {
			result[i] = ascii2Byte(value[i]);
		}
		return result;
	}

	public static final byte[] Checksum2Ascii(int value) {
		byte[] result = new byte[2];
		StringBuffer buf = new StringBuffer(2);
		// char charValue;
		// don't forget the second hex digit
		if (((int) value & 0xff) < 0x10) {
			buf.append("0");
		}
		buf.append(Long.toString((int) value & 0xff, 16).toUpperCase()); // get
																			// hexstring
		// charValue=buf.charAt(0);
		String str = buf.substring(0, 1);
		result[0] = (byte) Long.parseLong(str, 16);
		// result[0]=
		str = buf.substring(1);
		// result[1]=(byte)charValue;
		result[1] = (byte) Long.parseLong(str, 16);
		return result; // bytes2Ascii(result);
		// return buf.toString().getBytes();
	}

	public static final byte getAscii(byte value) {
		byte result = 0;
		if (value < 10) {
			result = (byte) (value + 0x30);
		} else if (value < 0x10) {
			result = (byte) (value + 0x40 - 0x10);
		}
		return result;
	}

	public static final byte[] getAsciis(byte[] value) {
		byte[] result = new byte[value.length];
		for (int i = 0; i < value.length; i++) {
			result[i] = getAscii(value[i]);
		}
		return result;
	}

	public static final int Ascii2Checksum(byte[] value) {
		StringBuffer sbuf = new StringBuffer(2);
		for (int i = 0; i < value.length; i++) {
			sbuf.append((char) value[i]);
		}
		return (int) Long.parseLong(new String(sbuf), 16);
	}

	/**
	 * Returns the broadcast address for the subnet of the host the code is
	 * executed on.
	 * 
	 * @return the broadcast address as <tt>InetAddress</tt>.
	 */
	public static final InetAddress getBroadcastAddress() {
		byte[] addr = new byte[4];
		try {
			addr = InetAddress.getLocalHost().getAddress();
			return InetAddress.getByName("" + addr[0] + "." + addr[1] + "."
					+ addr[2] + ".255");
		} catch (Exception ex) {
			logger.error(ex.toString(), ex);
			return null;
		}
	} // getBroadcastAddress

	public static String bytes2String(byte[] value) {
		String result = "";
		for (int i = 0; i < value.length; i++) {
			result = result + value;
		}
		return result;
	}

	/**
	 * calculate the LRC value of the byte array
	 * 
	 * @param data
	 *            byte[]
	 * @param offset
	 *            int
	 * @param length
	 *            int
	 * @return int
	 */
	public final static int getLRC(byte[] data, int offset, int length) {
		byte lrc = 0;
		for (int i = offset; i < offset + length; i++) {
			lrc += data[i];
		}
		return (int) -lrc;
	}

	public final static int ascii2Decimal(byte value) {
		StringBuffer sb = new StringBuffer(1);
		sb.append((char) value);
		return Integer.parseInt(new String(sb), 16);
	}

	public final static byte[] ascii2Decimal(byte[] src) {
		byte[] result = new byte[src.length];
		for (int i = 0; i < src.length; i++) {
			result[i] = (byte) ascii2Decimal(src[i]);
		}
		return result;
	}

	public static int convertBitArray2Int(byte[] value) {
		String str = new String(value);
		str = "0X" + str; // for Hexdecimal reason
		return Integer.decode(str).intValue();
	}

	public static int address2Ascii(int value) {
		byte[] aBytes = address2Bytes(value);
		byte[] aBytesAscii = bytes2Ascii(aBytes);
		short result = bytes2Short(aBytesAscii);
		return result;
	}

	private static byte[] address2Bytes(int value) {
		byte[] aByte = new byte[2];
		if (value > 100) {
		}
		aByte[0] = (byte) (value / 10);
		aByte[1] = (byte) (value - aByte[0] * 10);
		return aByte;
	}

	/**
	 * @todo 将一个十进制byte值转换为二进制字符串,不足8位的在前面补0
	 * @param b
	 *            Byte
	 * @return String
	 */
	public static String decToBin(Byte dec) {
		byte dec_byte = dec.byteValue();
		String bin = null;
		if (dec_byte < 0) {
			bin = Integer.toBinaryString(Integer.parseInt(dec.toString()))
					.substring(24);
		} else {
			bin = Integer.toBinaryString(Integer.parseInt(dec.toString()));
			while (bin.length() < 8) {
				bin = "0" + bin;
			}
		}
		return bin;
	}

	/**
	 * @todo 将一个二进制字符串转换为十进制整数
	 * @param b
	 *            String 二进制字符串
	 * @return int十进制整数
	 */
	public static int binToDec(String bin) {
		BigInteger bigI = new BigInteger(bin, 2);
		String str_dec = bigI.toString();
		return Integer.parseInt(str_dec);
	}

	/**
	 * 幂运算
	 * 
	 * @param base
	 *            底数
	 * @param index
	 *            指数
	 * @return
	 */
	public static int pow(int base, int index) {
		return (int) Math.pow(base, index);
	}
} // class SerialUtil
