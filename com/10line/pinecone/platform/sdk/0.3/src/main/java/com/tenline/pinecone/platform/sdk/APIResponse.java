/**
 * 
 */
package com.tenline.pinecone.platform.sdk;

/**
 * @author Bill
 *
 */
public class APIResponse {
	
	/**
	 * Whether response is done or not
	 */
	private boolean isDone;
	
	/**
	 * Response Message
	 */
	private Object message;

	/**
	 * 
	 */
	public APIResponse() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param isDone the isDone to set
	 */
	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}

	/**
	 * @return the isDone
	 */
	public boolean isDone() {
		return isDone;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(Object message) {
		this.message = message;
	}

	/**
	 * @return the message
	 */
	public Object getMessage() {
		return message;
	}

}
