/**
 * 
 */
package com.tenline.pinecone.platform.web.service.restful;

import java.text.SimpleDateFormat;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

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
	public void generateBatch(String from, String to, HttpServletResponse response) throws Exception {
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
		response.getWriter().write(result.toString());
		response.getWriter().flush();
	}

}
