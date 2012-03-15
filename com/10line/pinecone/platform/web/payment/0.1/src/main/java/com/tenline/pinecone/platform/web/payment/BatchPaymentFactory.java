/**
 * 
 */
package com.tenline.pinecone.platform.web.payment;

/**
 * @author wangyq
 *
 */
public class BatchPaymentFactory {
	
	/**
	 * 
	 */
	public BatchPaymentFactory() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Create Batch Payment
	 * @param subclass
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public static BatchPayment create(Class<?> subclass) 
		throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		return (BatchPayment) Class.forName(subclass.getName()).newInstance();
	}

}
