package com.tenline.pinecone.platform.osgi.monitor.model.pointtype;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author qi xinhu
 */
public class BytePoint extends Numbers {
	public void writeData(DataOutput aDataOutput) {
		try {
			aDataOutput.writeByte((Byte) getValue());
		} catch (IOException e) {
			logger.error(e.toString(),e);
		}
	}
	
	public void readData(DataInput aDataInput) throws IOException {
		setValue(Byte.valueOf(aDataInput.readByte()));
	}
	
	public void setMinValue(Object aMinValue) {
		if (aMinValue == null || aMinValue.toString().trim().equals("")) {
			super.setMinValue(Byte.MIN_VALUE);
		} else if (aMinValue instanceof String) {
			super.setMinValue(Byte.valueOf((String) (aMinValue)));
		} else {
			super.setMinValue(aMinValue);
		}
	}
	
	public void setMaxValue(Object aMaxValue) {
		if (aMaxValue == null || aMaxValue.toString().trim().equals("")) {
			super.setMaxValue(Byte.MAX_VALUE);
		} else if (aMaxValue instanceof String) {
			super.setMaxValue(Byte.valueOf((String) (aMaxValue)));
		} else {
			super.setMaxValue(aMaxValue);
		}
	}
	
	public void setValue(Object aValue) {
		if (aValue instanceof String) {
			super.setValue(Byte.valueOf((String) (aValue)));
		} else {
			super.setValue(aValue);
		}
	}
}
