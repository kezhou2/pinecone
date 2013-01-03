/**
 * 
 */
package com.tenline.pinecone.mobile.android.service;

/**
 * @author Bill
 *
 */
public interface RESTTaskListener {

	Object[] doInBackground(String... params);
	
	void onPostExecute(Object[] result);
	
}
