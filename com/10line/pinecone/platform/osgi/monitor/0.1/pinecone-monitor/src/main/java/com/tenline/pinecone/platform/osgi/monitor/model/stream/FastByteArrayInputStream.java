package com.tenline.pinecone.platform.osgi.monitor.model.stream;

import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 * Title: This class is a replacement for ByteArrayInputStream that does not synchronize every byte read.
 * </p>
 * <p>
 * Description: Task Management Application
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006 Beijing Institute of Satellite Information Engineering
 * </p>
 * <p>
 * Company: Beijing Institute of Satellite Information Engineering
 * </p>
 * 
 * @author lewhwa
 * @version 1.0
 */
public class FastByteArrayInputStream extends InputStream {
	/**
	 * Number of bytes in the input buffer.
	 */
	protected int		count;
	/**
	 * Actual position pointer into the input buffer.
	 */
	protected int		pos;
	/**
	 * Marked position pointer into the input buffer.
	 */
	protected int		mark;
	/**
	 * Input buffer <tt>byte[]</tt>.
	 */
	protected byte[]	buf;
	
	/**
	 * Creates an input stream.
	 * 
	 * @param buffer
	 *            the data to read.
	 */
	public FastByteArrayInputStream(byte[] buffer) {
		buf = buffer;
		count = buffer.length;
		pos = 0;
		mark = 0;
	}// constructor
	
	/**
	 * Creates an input stream.
	 * 
	 * @param buffer
	 *            the data to read.
	 * @param offset
	 *            the byte offset at which to begin reading.
	 * @param length
	 *            the number of bytes to read.
	 */
	public FastByteArrayInputStream(byte[] buffer, int offset, int length) {
		buf = buffer;
		pos = offset;
		count = length;
	}// constructor
	
	// --- begin ByteArrayInputStream compatible methods ---
	public int available() {
		return count - pos;
	}// available
	
	public boolean markSupported() {
		return true;
	}// markSupported
	
	public void mark(int readlimit) {
		mark = pos;
	}// mark
	
	public void reset() {
		pos = mark;
	}// reset
	
	public long skip(long count) {
		int myCount = (int) count;
		if (myCount + pos > this.count) {
			myCount = this.count - pos;
		}
		pos += myCount;
		return myCount;
	}// skip
	
	public int read() throws IOException {
		return (pos < count) ? (buf[pos++] & 0xff) : (-1);
	}// read
	
	public int read(byte[] toBuf) throws IOException {
		return read(toBuf, 0, toBuf.length);
	}// read
	
	public int read(byte[] toBuf, int offset, int length) throws IOException {
		int avail = count - pos;
		if (avail <= 0) {
			return -1;
		}
		if (length > avail) {
			length = avail;
		}
		for (int i = 0; i < length; i++) {
			toBuf[offset++] = buf[pos++];
		}
		return length;
	}// read
	
	// --- end ByteArrayInputStream compatible methods ---
	/**
	 * Returns the underlying data being read.
	 * 
	 * @return the underlying data.
	 */
	public byte[] getBufferBytes() {
		return buf;
	}// getBufferBytes
	
	/**
	 * Returns the offset at which data is being read from the buffer.
	 * 
	 * @return the offset at which data is being read.
	 */
	public int getBufferOffset() {
		return pos;
	}// getBufferOffset
	
	/**
	 * Returns the end of the buffer being read.
	 * 
	 * @return the end of the buffer.
	 */
	public int getBufferLength() {
		return count;
	}// getBufferLength
}
