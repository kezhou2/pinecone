package com.tenline.pinecone.platform.web.store.payment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.tenline.pinecone.platform.model.Account;
import com.tenline.pinecone.platform.model.Transaction;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.sdk.AccountAPI;
import com.tenline.pinecone.platform.sdk.TransactionAPI;
import com.tenline.pinecone.platform.sdk.development.APIResponse;
import com.tenline.pinecone.platform.web.store.payment.impl.AlipayBatchPay;
import com.tenline.pinecone.platform.web.store.payment.impl.ICBCBatchPay;
import com.tenline.pinecone.platform.web.store.payment.model.PayInfo;

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
	 * transactionAPI
	 */
	private static TransactionAPI transactionAPI = new TransactionAPI(
			"http://pinecone-service.cloudfoundry.com/", "80", null);

	/**
	 * accountAPI
	 */
	private static AccountAPI accountAPI = new AccountAPI(
			"http://pinecone-service.cloudfoundry.com/", "80", null);

	/**
	 * queryBatchPayInfo and save into list
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<PayInfo> queryBatchPayInfo() {
		ArrayList<PayInfo> payInfoList = new ArrayList<PayInfo>();
		try {
			APIResponse rst = transactionAPI.show("type = '"
					+ Transaction.INCOME + "'");
			if (rst.isDone()) {
				Collection<Transaction> transactionList = (Collection<Transaction>) rst
						.getMessage();
				System.out.println(transactionList.size());
				for (Transaction transaction : transactionList) {
					Integer nut = transaction.getNut();
					User user = transaction.getUser();
					APIResponse acountResp = accountAPI.showByUser("id='"
							+ user.getId() + "'");
					if (acountResp.isDone()) {
						// need to update, consider more than 1 account;
						Account account = (Account) (acountResp.getMessage());
						PayInfo info = new PayInfo();
						info.setUserID(user.getId());
						info.setUserName(user.getName());
						info.setPayNumber(nut);
						info.setUserAccountID(account.getNumber());
						info.setUserAccountBankName(account.getBank().getName());
						info.setUserPhone(user.getPhone());
						info.setPayInfo("MoneyTree");
						payInfoList.add(info);
					} else {
						logger.log(Level.SEVERE, acountResp.getMessage()
								.toString());
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
	public static boolean createICBCBatch() {
		ArrayList<PayInfo> infoList = BatchPayInfoCreater.queryBatchPayInfo();
		if (infoList != null && infoList.size() > 0) {
			ICBCBatchPay pay = new ICBCBatchPay(infoList);
			return pay.createBatchFile();
		} else {
			return false;
		}
	}

	/**
	 * create batch file
	 * 
	 * @return
	 */
	public static boolean createAliBatch() {
		ArrayList<PayInfo> infoList = BatchPayInfoCreater.queryBatchPayInfo();
		if (infoList != null && infoList.size() > 0) {
			AlipayBatchPay pay = new AlipayBatchPay(infoList);
			return pay.createBatchFile();
		} else {
			return false;
		}
	}
}
