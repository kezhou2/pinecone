package com.tenline.pinecone.platform.osgi.monitor.model.stream;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * <p>Title: Class implementing a byte array output stream with a DataInput interface.</p>
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
public class BytesOutputStream extends FastByteArrayOutputStream implements DataOutput{
    private DataOutputStream m_Dout;

   /**
    * Constructs a new <tt>BytesOutputStream</tt> instance with
     * a new output buffer of the given size.
    *
    * @param size the size of the output buffer as <tt>int</tt>.
    */
    public BytesOutputStream(int size) {
      super(size);
      m_Dout = new DataOutputStream(this);
    }//BytesOutputStream

    /**
     * Constructs a new <tt>BytesOutputStream</tt> instance with
     * a given output buffer.
     *
     * @param buffer the output buffer as <tt>byte[]</tt>.
     */
    public BytesOutputStream(byte[] buffer) {
      buf = buffer;
      count = 0;
      m_Dout = new DataOutputStream(this);
    }//BytesOutputStream

    /**
     * Returns the reference to the output buffer.
     *
     * @return the reference to the <tt>byte[]</tt> output buffer.
     */
    public byte[] getBuffer() {
      return buf;
    }//getBuffer

    public void reset() {
      count = 0;
    }//reset

    public void writeBoolean(boolean v)
        throws IOException {
      m_Dout.writeBoolean(v);
    }//writeBoolean

    public void writeByte(int v)
        throws IOException {
      m_Dout.writeByte(v);
    }//writeByte

    public void writeShort(int v)
        throws IOException {
      m_Dout.writeShort(v);
    }//writeShort

    public void writeChar(int v)
        throws IOException {
      m_Dout.writeChar(v);
    }//writeChar

    public void writeInt(int v)
        throws IOException {
      m_Dout.writeInt(v);
    }//writeInt

    public void writeLong(long v)
        throws IOException {
      m_Dout.writeLong(v);
    }//writeLong

    //

    public void writeFloat(float v)
        throws IOException {
      m_Dout.writeFloat(v);
    }//writeFloat

    public void writeDouble(double v)
        throws IOException {
      m_Dout.writeDouble(v);
    }//writeDouble
    //

    public void writeBytes(String s)
        throws IOException {
      int len = s.length();
      for (int i = 0; i < len; i++) {
        this.write((byte) s.charAt(i));
      }
    }//writeBytes

    public void writeChars(String s)
        throws IOException {
      m_Dout.writeChars(s);
    }//writeChars

    public void writeUTF(String str)
        throws IOException {
      m_Dout.writeUTF(str);
    }//writeUTF



    public  void write3Byte(int v) throws IOException {

        m_Dout.write((v >>> 16) & 0xFF);
        m_Dout.write((v >>>  8) & 0xFF);
        m_Dout.write((v >>>  0) & 0xFF);

    }

}
