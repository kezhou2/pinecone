/**
 * 
 */
package com.tenline.pinecone.mobile.android;

import java.util.ArrayList;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.tenline.pinecone.mobile.android.receiver.NetworkReceiver;
import com.tenline.pinecone.mobile.android.service.RESTService;
import com.tenline.pinecone.mobile.android.service.RESTTask;
import com.tenline.pinecone.mobile.android.service.ServiceConnectionHelper;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;

/**
 * @author Bill
 *
 */
public abstract class AbstractListActivity extends SherlockListActivity {

	private NetworkReceiver receiver = new NetworkReceiver();
	
	protected ServiceConnectionHelper restHelper = new ServiceConnectionHelper();
	
	public RESTService getRESTService() {return (RESTService) restHelper.getService();}
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(getClass().getSimpleName(), "onCreate"); registerForContextMenu(getListView());
		getSupportActionBar().setDisplayHomeAsUpEnabled(true); getSupportActionBar().setHomeButtonEnabled(true);
        if (!restHelper.isBound()) bindService(new Intent(this, RESTService.class), restHelper, Context.BIND_AUTO_CREATE);
        IntentFilter filter = new IntentFilter(); filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION); registerReceiver(receiver, filter);
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.options_menu, menu); return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.user_logout: new LogoutTask(this).execute(); break;
		case android.R.id.home: finish(); break;}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, view, menuInfo); getMenuInflater().inflate(R.menu.context_menu, menu); 
	}
	
	@Override
    protected void onDestroy() {
        super.onDestroy(); Log.i(getClass().getSimpleName(), "onDestroy");
        if (restHelper.isBound()) {unbindService(restHelper); restHelper.setBound(false);} unregisterReceiver(receiver);
    }
	
	/**
	 * 
	 * @param result
	 */
	protected abstract void buildListAdapter(Object[] result);
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	protected class LogoutTask extends RESTTask {

		/**
		 * 
		 * @param context
		 */
		protected LogoutTask(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected Object[] doTaskAction(Object... params) {
			// TODO Auto-generated method stub
			try {getRESTService().get(RESTService.LOGOUT_URL);}
			catch (Exception e) {Log.e(getClass().getSimpleName(), e.getMessage());} return params;
		}
		
		@Override
		protected void onPostExecute(Object[] result) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(progress.getContext(), LoginActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); startActivity(intent);
			super.onPostExecute(result);
		}
		
	}
	
	public void doInitListViewTask(Object... params) {
		new InitListViewTask(this).execute(params);
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class InitListViewTask extends RESTTask {

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
