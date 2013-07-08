/**
 * 
 */
package com.tenline.pinecone.mobile.android;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

import com.tenline.pinecone.mobile.android.service.ProgressTask;

/**
 * @author Bill
 *
 */
public abstract class AbstractRESTActivity extends AbstractListActivity {

	public void doInitListViewTask(Object... params) {
		new InitListViewTask(this).execute(params);
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class InitListViewTask extends ProgressTask {

		/**
		 * 
		 * @param context
		 */
		private InitListViewTask(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected Object[] doTaskAction(Object... params) {
			// TODO Auto-generated method stub
			try {
				while (!restHelper.isBound()) {Thread.sleep(100);} 
				params = ((ArrayList<?>) getRESTService().get(params[0].toString())).toArray();
			} catch (Exception e) {Log.e(getClass().getSimpleName(), e.getMessage());} return params;
		}
		
		@Override
		protected void onPostExecute(Object[] result) {
			// TODO Auto-generated method stub
			buildListAdapter(result); super.onPostExecute(result);
		}
		
	}

}
