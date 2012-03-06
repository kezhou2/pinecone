/**
 * 
 */
package com.tenline.pinecone.platform.web.payment.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import com.tenline.pinecone.platform.web.payment.BatchPayInterface;
import com.tenline.pinecone.platform.web.payment.model.PayInfo;
import com.tenline.pinecone.platform.web.payment.util.PineconeAccount;

/**
 * create icbc batch file to transfer
 * 
 * @author WongYQ
 * 
 */
public class ICBCBatchPay implements BatchPayInterface {
	/**
	 * logger
	 */
	protected static Logger logger = Logger.getLogger(ICBCBatchPay.class
			.toString());
	/**
	 * icbc paybatch info
	 */
	private ArrayList<PayInfo> payInfoList;

	/**
	 * 
	 */
	public ICBCBatchPay(ArrayList<PayInfo> payInfoList) {
		this.payInfoList = payInfoList;
	}

	/**
	 * create icbc batch file txt 
	 * @return
	 */
	public String createBatchFile() {
		String result;
		try {
			String cfgFilePath = System.getProperty("catalina.home");
			Date today = Calendar.getInstance().getTime();
			SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
			String day = sd.format(today);
			String path = cfgFilePath+"/webapps/paycenter/downfiles/moneytree/" + day + "/";
			String fileName = path + "/ICBCBatch.txt";
			File f = new File(path);
			if (!f.exists()) {
				f.mkdirs();
			}
			File txt = new File(fileName);
			if (!txt.exists()) {
				txt.createNewFile();
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
			writer.write("#总计信息");
			writer.write("#注意：本文件中的金额均以分为单位!");
			writer.write("#币种|日期|总计标志|总金额|总笔数|");
			long summary = 0;
			for (PayInfo payino : payInfoList) {
				summary += payino.getPayNumber();
			}
			writer.write("RMB|" + day + "|1|" + summary * 100 + "|"
					+ payInfoList.size() + "|");
			writer.flush();
			for (int i = 0; i < payInfoList.size(); i++) {
				PayInfo payino = payInfoList.get(i);
				writer.write("RMB|" + day + "|" + (i + 1) + "|"
						+ PineconeAccount.Account_ICBC + "|"
						+ PineconeAccount.Account_ICBC_Type + "|"
						+ payino.getUserAccountID() + "|"
						+ payino.getUserAccountBankName() + "|"
						+ payino.getPayNumber() * 100 + "||"
						+ payino.getPayInfo() + "||" + payino.getUserPhone());
				writer.newLine();
			}
			writer.flush();
			writer.close();
			result = fileName;
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		return result;
	}
}
