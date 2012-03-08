package com.tenline.pinecone.platform.web.payment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.tenline.pinecone.platform.model.Account;
import com.tenline.pinecone.platform.model.Exchange;
import com.tenline.pinecone.platform.sdk.ExchangeAPI;
import com.tenline.pinecone.platform.sdk.development.APIResponse;
import com.tenline.pinecone.platform.web.payment.impl.AlipayBatchPay;
import com.tenline.pinecone.platform.web.payment.impl.ICBCBatchPay;
import com.tenline.pinecone.platform.web.payment.model.PayInfo;

/**
 * @author wangyq
 * 
 */
public class BatchPayInfoCreater {
	/**
	 * logger
	 */
	protected static Logger logger = Logger.getLogger(BatchPayInfoCreater.class
			.toString());
	/**
	 * exchangeAPI
	 */
	private static ExchangeAPI exchangeAPI = new ExchangeAPI(
			"pinecone-service.cloudfoundry.com", "80", null);

	/**
	 * queryBatchPayInfo and save into list
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<PayInfo> queryBatchPayInfo(Date from, Date to) {
		ArrayList<PayInfo> payInfoList = new ArrayList<PayInfo>();
		try {
			// System.out.println("type=='" + Exchange.PAYOUT
			// + "' && TO_DAYS(timestamp) - TO_DAYS('"+from+"') > 0");
			// System.out.println("type=='" + Exchange.PAYOUT
			// + "' && timestamp >= " + from + " && timestamp <= " + to);
			// APIResponse rst = exchangeAPI.show("type=='" + Exchange.PAYOUT
			// + "' && TO_DAYS(timestamp) - TO_DAYS('"+from+"') > 0");
			APIResponse rst = exchangeAPI.show("type=='" + Exchange.PAYOUT
					+ "'");
			if (rst.isDone()) {
				Collection<Exchange> exchangeList = (Collection<Exchange>) rst
						.getMessage();
				System.out.println(exchangeList.size());
				for (Exchange exchange : exchangeList) {
					boolean after = exchange.getTimestamp().after(from);
					boolean before = exchange.getTimestamp().before(to);
					System.out.println(after + ":" + before);
					if (after && before) {
						Integer nut = exchange.getNut();
						Account account = exchange.getAccount();
						// need to update, consider more than 1 account;
						PayInfo info = new PayInfo();
						// User user = account.getUser();
						// info.setUserID(user.getId());
						// info.setUserName(user.getName());
						info.setPayNumber(nut);
						info.setUserAccountID(account.getNumber());
						// info.setUserAccountBankName(account.getBank().getName());
						// info.setUserPhone(user.getPhone());
						info.setPayInfo("MoneyTree");
						payInfoList.add(info);
					}
				}
			} else {
				logger.log(Level.SEVERE, rst.getMessage().toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return payInfoList;
	}

	/**
	 * create batch file
	 * 
	 * @return
	 */
	public static String createICBCBatch(Date from, Date to) {
		ArrayList<PayInfo> infoList = BatchPayInfoCreater.queryBatchPayInfo(
				from, to);
		if (infoList != null && infoList.size() > 0) {
			ICBCBatchPay pay = new ICBCBatchPay(infoList);
			return pay.createBatchFile();
		} else {
			return null;
		}
	}

	/**
	 * create batch file
	 * 
	 * @return
	 */
	public static String createAliBatch(Date from, Date to) {
		ArrayList<PayInfo> infoList = BatchPayInfoCreater.queryBatchPayInfo(
				from, to);
		if (infoList != null && infoList.size() > 0) {
			AlipayBatchPay pay = new AlipayBatchPay(infoList);
			return pay.createBatchFile();
		} else {
			return null;
		}
	}
}
