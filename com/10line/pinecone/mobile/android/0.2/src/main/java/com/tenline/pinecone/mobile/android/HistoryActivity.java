/**
 * 
 */
package com.tenline.pinecone.mobile.android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.tenline.pinecone.mobile.android.service.HistoryService;
import com.tenline.pinecone.mobile.android.service.ProgressTask;
import com.tenline.pinecone.mobile.android.service.ServiceConnectionHelper;

/**
 * @author Bill
 *
 */
public class HistoryActivity extends AbstractListActivity {

	public static final String ACTIVITY_ACTION = "com.tenline.pinecone.mobile.android.history";

	private ServiceConnectionHelper historyHelper = new ServiceConnectionHelper();
	
	private HistoryService getHistoryService() {return (HistoryService) historyHelper.getService();}
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); getSupportActionBar().setTitle(R.string.history_title);
        if (!historyHelper.isBound()) bindService(new Intent(this, HistoryService.class), historyHelper, Context.BIND_AUTO_CREATE);
        new InitListViewTask(this).execute(getIntent().getStringExtra("variableId"));
    }
	
	@Override
    protected void onDestroy() {
        super.onDestroy();
        if (historyHelper.isBound()) {unbindService(historyHelper); historyHelper.setBound(false);}
    }
	
	@Override
    public boolean onPrepareOptionsMenu(Menu menu) {
    	menu.setGroupVisible(R.id.device_items, false); return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, view, menuInfo); 
		menu.setGroupVisible(R.id.device_items, false); menu.setGroupVisible(R.id.variable_items, false); 
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
				while (!historyHelper.isBound()) {Thread.sleep(100);}
				params[0] = getHistoryService().getAll(params[0].toString());
			} catch (Exception e) {Log.e(getClass().getSimpleName(), e.getMessage());} return params;
		}
		
		@Override
		protected void onPostExecute(Object[] result) {
			// TODO Auto-generated method stub
			buildListAdapter(result); super.onPostExecute(result);
		}
		
	}
	
	@Override
	@SuppressWarnings("unchecked")
	protected void buildListAdapter(Object[] result) {
		Map<String, String> histories = (Map<String, String>) result[0];
		if (histories.keySet().size() == 0) Toast.makeText(this, R.string.error_no_history, Toast.LENGTH_LONG).show();
		ArrayList<HashMap<String, String>> items = new ArrayList<HashMap<String, String>>();
		for (String key : histories.keySet()) {
			HashMap<String, String> item = new HashMap<String, String>();
			item.put("historyDate", key); item.put("historyValue", histories.get(key));
			items.add(item);
		} String[] from = new String[]{"historyDate", "historyValue"};
		int[] to = new int[]{R.id.history_date, R.id.history_value}; 
		setListAdapter(new SimpleAdapter(this, items, R.layout.history_item, from, to));
	}

}
