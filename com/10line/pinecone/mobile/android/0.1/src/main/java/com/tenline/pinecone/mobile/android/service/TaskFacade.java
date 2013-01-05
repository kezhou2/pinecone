/**
 * 
 */
package com.tenline.pinecone.mobile.android.service;

import android.content.Context;

/**
 * @author Bill
 *
 */
public class TaskFacade {

	public static void initRESTTask(Context context, String... params) {
		RESTTask task = new RESTTask(context); task.execute(params);
	}

}
