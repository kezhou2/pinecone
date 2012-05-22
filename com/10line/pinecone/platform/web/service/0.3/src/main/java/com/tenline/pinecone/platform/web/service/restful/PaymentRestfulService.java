/**
 * 
 */
package com.tenline.pinecone.platform.web.service.restful;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tenline.pinecone.platform.model.Exchange;
import com.tenline.pinecone.platform.web.service.ExchangeService;
import com.tenline.pinecone.platform.web.service.PaymentService;

/**
 * @author wangyq
 * 
 */
@Service
public class PaymentRestfulService implements PaymentService {
	
	private ExchangeService exchangeService;

	/**
	 * 
	 */
	@Autowired
	public PaymentRestfulService(ExchangeService exchangeService) {
		// TODO Auto-generated method stub
		this.exchangeService = exchangeService;
	}

	@Override
	public String generateBatch(String from, String to) throws ParseException {
		// TODO Auto-generated method stub
		StringBuilder result = new StringBuilder();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Collection<Exchange> exchanges = exchangeService.show("type=='" + Exchange.PAYOUT + "'");
		for (Exchange exchange : exchanges) {
			if (exchange.getTimestamp().after(format.parse(from)) && 
				exchange.getTimestamp().before(format.parse(to))) {
				result.append(exchange.getAccount().getNumber() + "\t\t" + exchange.getNut() + "\n");
			}
		}
		if (result.toString() == null) return "No Exchange";
		else return result.toString();
	}

}
