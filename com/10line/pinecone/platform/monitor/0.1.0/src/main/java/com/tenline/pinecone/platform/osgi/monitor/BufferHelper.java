/**
 * 
 */
package com.tenline.pinecone.platform.osgi.monitor;

/**
 * @author Bill
 *
 */
public class BufferHelper {

	/**
	 * 
	 */
	public BufferHelper() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Append Buffer
	 * @param appendBytes
	 * @param buffer
	 * @return
	 */
	public static byte[] appendBuffer(byte[] appendBytes, byte[] buffer) {
		int length = 0;
		if (buffer != null) length += buffer.length;
		if (appendBytes != null) length += appendBytes.length;
		byte[] result = new byte[length];
		int index = 0;
		if (buffer != null) {
			System.arraycopy(buffer, 0, result, 0, buffer.length);
			index = buffer.length;
		}
		if (appendBytes != null) System.arraycopy(appendBytes, 0, result, index, appendBytes.length);
		return result;
	}
	
	/**
	 * Insert Buffer
	 * @param insertedBytes
	 * @param index
	 * @param buffer
	 * @return
	 */
	public static byte[] insertBuffer(byte[] insertedBytes, int index, byte[] buffer) {
		byte[] result = new byte[insertedBytes.length + buffer.length];
		if (index >= 0) {
			System.arraycopy(buffer, 0, result, 0, index + 1);
			System.arraycopy(insertedBytes, 0, result, index + 1, insertedBytes.length);
			System.arraycopy(buffer, index + 1, result, index + insertedBytes.length + 1, buffer.length - index - 1);
		} else {
			System.arraycopy(insertedBytes, 0, result, 0, insertedBytes.length);
			System.arraycopy(buffer, 0, result, insertedBytes.length, buffer.length);
		}
		return result;
	}

}
