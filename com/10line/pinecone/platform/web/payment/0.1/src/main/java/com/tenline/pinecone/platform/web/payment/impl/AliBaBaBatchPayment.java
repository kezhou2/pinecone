/**
 * 
 */
package com.tenline.pinecone.platform.web.payment.impl;

import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;

import com.tenline.pinecone.platform.model.Exchange;
import com.tenline.pinecone.platform.sdk.development.APIResponse;
import com.tenline.pinecone.platform.web.payment.BatchPayment;

/**
 * @author wangyq
 * 
 */
public class AliBaBaBatchPayment extends BatchPayment {

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String createBatchFile(Date from, Date to) {
		StringBuilder result = new StringBuilder();
		try {
			APIResponse response = exchangeAPI.show("type=='" + Exchange.PAYOUT
					+ "'");
			if (response.isDone()) {
				Collection<Exchange> exchanges = (Collection<Exchange>) response
						.getMessage();
				for (Exchange exchange : exchanges) {
					if (exchange.getTimestamp().after(from)
							&& exchange.getTimestamp().before(to)) {
						result.append(exchange.getAccount().getNumber()
								+ "\t\t" + exchange.getNut() + "\n");
					}
				}
			} else {
				logger.log(Level.SEVERE, response.getMessage().toString());
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
		return result.toString();
	}
}
