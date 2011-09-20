/**
 * 
 */
package com.tenline.pinecone.platform.sdk;

/**
 * @author Bill
 *
 */
public interface APIListener {
	
	/**
	 * 
	 * @param message
	 */
	void onMessage(Object message);
	
	/**
	 * 
	 * @param error
	 */
	void onError(String error);

}
