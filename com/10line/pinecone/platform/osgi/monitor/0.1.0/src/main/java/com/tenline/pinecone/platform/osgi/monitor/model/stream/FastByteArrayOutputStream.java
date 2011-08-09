package com.tenline.pinecone.platform.osgi.monitor.model.stream;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * <p>Title: This class is a replacement implementation for ByteArrayOutputStream
 * that does not synchronize every byte written.</p>
 *
 * <p>Description: Task Management Application</p>
 *
 * <p>Copyright: Copyright (c) 2006 Beijing Institute of Satellite Information
 * Engineering</p>
 *
 * <p>Company: Beijing Institute of Satellite Information Engineering</p>
 *
 * @author lewhwa
 * @version 1.0
 */
public class FastByteArrayOutputStream extends OutputStream {
    /**
     * Defines the default oputput buffer size (100 bytes).
     */
    public static final int DEFAULT_INIT_SIZE = 100;
    /**
     * Defines the default increment of the output buffer size
     * (100 bytes).
     */
    public static final int DEFAULT_BUMP_SIZE = 100;

    /**
     * Number of bytes in the output buffer.
     */
    protected int count;

    /**
     * Increment of the output buffer size on overflow.
     */
    protected int bumpLen;

    /**
     * Output buffer <tt>byte[]</tt>.
     */
    protected byte[] buf;

    /**
     * Creates an output stream with default sizes.
     */
    public FastByteArrayOutputStream() {
        buf = new byte[DEFAULT_INIT_SIZE];
        bumpLen = DEFAULT_BUMP_SIZE;
    } //constructor

    /**
     * Creates an output stream with a default bump size and a given initial
     * size.
     *
     * @param initialSize the initial size of the buffer.
     */
    public FastByteArrayOutputStream(int initialSize) {
        buf = new byte[initialSize];
        bumpLen = DEFAULT_BUMP_SIZE;
    } //constructor

    /**
     * Creates an output stream with a given bump size and initial size.
     *
     * @param initialSize the initial size of the buffer.
     * @param bumpSize    the amount to increment the buffer.
     */
    public FastByteArrayOutputStream(int initialSize, int bumpSize) {
        buf = new byte[initialSize];
        bumpLen = bumpSize;
    } //constructor

    /**
     * Creates an output stream with a given initial buffer and a default
     * bump size.
     *
     * @param buffer the initial buffer; will be owned by this object.
     */
    public FastByteArrayOutputStream(byte[] buffer) {
        buf = buffer;
        bumpLen = DEFAULT_BUMP_SIZE;
    } //constructor

    /**
     * Creates an output stream with a given initial buffer and a given
     * bump size.
     *
     * @param buffer   the initial buffer; will be owned by this object.
     * @param bumpSize the amount to increment the buffer.
     */
    public FastByteArrayOutputStream(byte[] buffer, int bumpSize) {
        buf = buffer;
        bumpLen = bumpSize;
    } //constructor

// --- begin ByteArrayOutputStream compatible methods ---

    /**
     * Returns the number of bytes written to this
     * <tt>FastByteArrayOutputStream</tt>.
     *
     * @return the number of bytes written as <tt>int</tt>.
     */
    public int size() {
        return count;
    } //size

    /**
     * Resets this <tt>FastByteArrayOutputStream</tt>.
     */
    public void reset() {
        count = 0;
    } //reset

    public void write(int b) throws IOException {
        if (count + 1 > buf.length) {
            bump(1);
        }
        buf[count++] = (byte) b;
    } //write

    public void write(byte[] fromBuf) throws IOException {
        int needed = count + fromBuf.length - buf.length;
        if (needed > 0) {
            bump(needed);
        }
        for (int i = 0; i < fromBuf.length; i++) {
            buf[count++] = fromBuf[i];
        }
    } //write

    public void write(byte[] fromBuf, int offset, int length) throws
            IOException {

        int needed = count + length - buf.length;
        if (needed > 0) {
            bump(needed);
        }
        int fromLen = offset + length;

        for (int i = offset; i < fromLen; i++) {
            buf[count++] = fromBuf[i];
        }
    } //write

    /**
     * Writes the content of this <tt>FastByteArrayOutputStream</tt>
     * to the given output stream.
     *
     * @param out the output stream to be written to.
     *
     * @throws IOException if an I/O error occurs.
     */
    public synchronized void writeTo(OutputStream out) throws IOException {
        out.write(buf, 0, count);
    } //writeTo

    public String toString() {
        return new String(buf, 0, count);
    } //toString

    /**
     * Returns the content of this <tt>FastByteArrayOutputStream</tt>
     * as String.
     *
     * @param encoding the encoding to be used for conversion.
     * @return a newly allocated String.
     *
     * @throws UnsupportedEncodingException if the given encoding is not supported.
     */
    public String toString(String encoding) throws UnsupportedEncodingException {
        return new String(buf, 0, count, encoding);
    } //toString

    /**
     * Returns the written bytes in a newly allocated byte[]
     * of length getSize().
     *
     * @return a newly allocated byte[] with the content of the
     *         output buffer.
     */
    public byte[] toByteArray() {
        byte[] toBuf = new byte[count];
        System.arraycopy(buf, 0, toBuf, 0, count);
        //for (int i = 0; i < count; i++) {
        //  toBuf[i] = buf[i];
        //}
        return toBuf;
    } //toByteArray

// --- end ByteArrayOutputStream compatible methods ---

    /**
     * Copy the buffered data to the given array.
     *
     * @param toBuf  the buffer to hold a copy of the data.
     * @param offset the offset at which to start copying.
     */
    public void toByteArray(byte[] toBuf, int offset) {
        int toLen = (toBuf.length > count) ? count : toBuf.length;
        for (int i = offset; i < toLen; i++) {
            toBuf[i] = buf[i];
        }
    } //toByteArray

    /**
     * Returns the buffer owned by this object.
     *
     * @return the buffer.
     */
    public byte[] getBufferBytes() {
        return buf;
    } //getBufferBytes

    /**
     * Returns the offset of the internal buffer.
     *
     * @return always zero currently.
     */
    public int getBufferOffset() {
        return 0;
    } //getBufferOffset

    /**
     * Returns the length used in the internal buffer, that is, the offset at
     * which data will be written next.
     *
     * @return the buffer length.
     */
    public int getBufferLength() {
        return count;
    } //getBufferLength

    /**
     * Ensure that at least the given number of bytes are available in the
     * internal buffer.
     *
     * @param sizeNeeded the number of bytes desired.
     */
    public void makeSpace(int sizeNeeded) {

        int needed = count + sizeNeeded - buf.length;
        if (needed > 0) {
            bump(needed);
        }
    } //makeSpace

    /**
     * Skip the given number of bytes in the buffer.
     *
     * @param sizeAdded number of bytes to skip.
     */
    public void addSize(int sizeAdded) {
        count += sizeAdded;
    } //addSize

    private void bump(int needed) {

        byte[] toBuf = new byte[buf.length + needed + bumpLen];

        for (int i = 0; i < count; i++) {
            toBuf[i] = buf[i];
        }
        buf = toBuf;
    } //bump


}
