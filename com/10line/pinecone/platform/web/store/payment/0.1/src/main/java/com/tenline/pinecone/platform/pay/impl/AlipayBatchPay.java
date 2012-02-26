/**
 * 
 */
package com.tenline.pinecone.platform.pay.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import com.tenline.pinecone.platform.pay.BatchPayInterface;
import com.tenline.pinecone.platform.pay.model.PayInfo;

/**
 * create alibaba batch file to transfer 
 * @author WongYQ
 * 
 */
public class AlipayBatchPay implements BatchPayInterface {
	/**
	 * logger
	 */
	protected static Logger logger = Logger.getLogger(AlipayBatchPay.class.toString());
	/**
	 *  alipaybatch info
	 */
	private ArrayList<PayInfo> payInfoList;
	/**
	 * 
	 */
	public AlipayBatchPay(ArrayList<PayInfo> payInfoList) {
		this.payInfoList = payInfoList;
	}

	/**
	 * create alipay batch file txt
	 * @return
	 */
	@Override
	public boolean createBatchFile() {
		boolean result = false;
		try {
			Date today = Calendar.getInstance().getTime();
			SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
			String day = sd.format(today);
			String path = "../downfiles/moneytree/" + day + "/";
			String fileName = path + "/AlipayBatch.txt";
			File f = new File(path);
			if (!f.exists()) {
				f.mkdirs();
			}
			File txt = new File(fileName);
			if (!txt.exists()) {
				txt.createNewFile();
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
			for (PayInfo payino :payInfoList) {
				writer.write(payino.getUserAccountID()+"\t\t"+payino.getPayNumber());
				writer.newLine();
			}
			writer.flush();
			writer.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}
}
