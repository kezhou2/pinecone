/**
 * 
 */
package com.tenline.pinecone.platform.web.payment;

import java.util.Date;
import java.util.logging.Logger;

import com.tenline.pinecone.platform.sdk.ExchangeAPI;

/**
 * @author wangyq
 * 
 */
public abstract class BatchPayment {

	/**
	 * 
	 */
	protected Logger logger = Logger.getLogger(getClass().getName());

	/**
	 * 
	 */
	protected ExchangeAPI exchangeAPI = new ExchangeAPI(
			"pinecone-service.cloudfoundry.com", "80", null);
	
	/**
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public abstract String createBatchFile(Date from, Date to);
	
}
