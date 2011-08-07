/*
 * @(#)ShortPoint.java 1.0 2006-6-16 Copyright 2006 CASC. All rights reserved. CASC PROPRIETARY/CONFIDENTIAL. Use is subject to
 * license terms.
 */
package com.tenline.pinecone.platform.osgi.monitor.model.pointtype;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.tenline.pinecone.platform.osgi.monitor.model.Point;

/**
 * @author qi xinhu
 */
public class FishShortPoint extends Point {
	public void writeData(DataOutput aDataOutput) {
		try {
			short idata = ((Short) getValue()).shortValue();
			// 低位在前，高位在后
			aDataOutput.writeShort(Short.reverseBytes(idata));
		} catch (IOException e) {
			logger.error(e.toString(),e);
		}
	}

	public void readData(DataInput aDataInput) throws IOException {
		short idata = 0;
		// 低位在前，高位在后
		idata = Short.reverseBytes(aDataInput.readShort());
		setValue(idata);
	}

	public void setMinValue(Object aMinValue) {
		if (aMinValue instanceof String) {
			super.setMinValue(Short.valueOf((String) (aMinValue)));
		} else {
			super.setMinValue(aMinValue);
		}
	}

	public void setMaxValue(Object aMaxValue) {
		if (aMaxValue instanceof String) {
			super.setMaxValue(Integer.valueOf((String) (aMaxValue)));
		} else {
			super.setMaxValue(aMaxValue);
		}
	}

	public void setValue(Object aValue) {
		if (aValue instanceof String) {
			super.setValue(Integer.valueOf((String) (aValue)));
		} else {
			super.setValue(aValue);
		}
	}
}
