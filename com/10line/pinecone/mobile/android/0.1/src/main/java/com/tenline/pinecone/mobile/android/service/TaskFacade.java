/**
 * 
 */
package com.tenline.pinecone.mobile.android.service;

/**
 * @author Bill
 *
 */
public class TaskFacade {

	public static void initRESTTask(RESTTaskListener listener, String... params) {
		RESTTask task = new RESTTask(); task.setListener(listener); task.execute(params);
	}

}
