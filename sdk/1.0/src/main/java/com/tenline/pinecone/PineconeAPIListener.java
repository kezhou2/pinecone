/**
 * 
 */
package com.tenline.pinecone;

/**
 * @author Bill
 *
 */
public interface PineconeAPIListener {
	
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
