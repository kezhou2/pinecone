/**
 * 
 */
package com.tenline.pinecone.mobile.android;

import java.util.ArrayList;
import java.util.HashMap;

import nl.justobjects.pushlet.core.Event;
import nl.justobjects.pushlet.core.Protocol;

import com.tenline.pinecone.mobile.android.service.ChannelService;
import com.tenline.pinecone.mobile.android.service.ChannelServiceListener;
import com.tenline.pinecone.mobile.android.service.ServiceConnectionHelper;
import com.tenline.pinecone.platform.model.Variable;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;

/**
 * @author Bill
 *
 */
public class VariableActivity extends AbstractListActivity implements ChannelServiceListener, ViewBinder {

	public static final String ACTIVITY_ACTION = "com.tenline.pinecone.mobile.android.variable";
	
	private ServiceConnectionHelper channelHelper = new ServiceConnectionHelper();
	
	private ChannelService getChannelService() {return (ChannelService) channelHelper.getService();}
	
	private static VariableActivity instance; public static VariableActivity getInstance() {return instance;}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); instance = this;
		doInitListViewTask("/device/" + getIntent().getStringExtra("deviceId") + "/variables");
        if (!channelHelper.isBound()) {
        	bindService(new Intent(this, ChannelService.class), channelHelper, Context.BIND_AUTO_CREATE);
        	new ListenToChannelTask().execute(Protocol.MODE_STREAM, getIntent().getStringExtra("deviceId"));
        }
		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ItemActivity.ACTIVITY_ACTION);
				intent.putExtra("variableId", String.valueOf(view.getId()));
				intent.putExtra("variableName", ((TextView) view.findViewById(R.id.variable_name)).getText());
				startActivity(intent);
			}
			
		});
	}
	
	@Override
    protected void onDestroy() {
        super.onDestroy();
        if (channelHelper.isBound()) {
        	unbindService(channelHelper); channelHelper.setBound(false); new UnsubscribeFromChannelTask().execute();
        }
    }
	
	@Override
    public boolean onPrepareOptionsMenu(Menu menu) {
    	menu.setGroupVisible(R.id.device_items, false); return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {case R.id.user_logout: new LogoutTask(this).execute(); break;}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class ListenToChannelTask extends AsyncTask<String, Void, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				while (!channelHelper.isBound()) {Thread.sleep(100);} 
				while (!getChannelService().isJoined()) {Thread.sleep(100);} 
				getChannelService().listen(VariableActivity.this, params[0], params[1]); return true;
			} catch (Exception e) {Log.e(getClass().getSimpleName(), e.getMessage()); return false;}
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	public class PublishToChannelTask extends AsyncTask<String, Void, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				HashMap<String, String> attributes = new HashMap<String, String>();
				attributes.put("var_id", params[0]); attributes.put("var_value", params[1]);
				getChannelService().publish(getIntent().getStringExtra("deviceId"), attributes); return true;
			} catch (Exception e) {Log.e(getClass().getSimpleName(), e.getMessage()); return false;}
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class UnsubscribeFromChannelTask extends AsyncTask<String, Void, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {getChannelService().unsubscribe(); return true;} 
			catch (Exception e) {Log.e(getClass().getSimpleName(), e.getMessage()); return false;}
		}
		
	}
	
	private static final int SUBSCRIBE_DATA = 0;
	private Handler subscriber = new Handler() {
		
		@Override
		public void handleMessage(Message msg) {
            switch(msg.what) {
            case SUBSCRIBE_DATA:
            	Event theEvent = (Event) msg.obj;
            	for (int i=0; i<getListView().getCount(); i++) {
        			View view = getListView().getChildAt(i);
        			if (view.getId() == Long.valueOf(theEvent.getField("var_id"))) {
        				SimpleAdapter adapter = (SimpleAdapter) getListAdapter();
        				TextView variableValue = (TextView) view.findViewById(R.id.variable_value);
        				adapter.setViewText(variableValue, theEvent.getField("var_value")); break;
        			}
        		} break;
            }
        }
		
    };
	
	@Override
	public void onAbort(Event theEvent) {
		// TODO Auto-generated method stub
		Log.i(getClass().getSimpleName(), theEvent.toString());
	}

	@Override
	public void onData(Event theEvent) {
		// TODO Auto-generated method stub
		Log.i(getClass().getSimpleName(), theEvent.toString());
		subscriber.sendMessage(subscriber.obtainMessage(SUBSCRIBE_DATA, theEvent));
	}
	
	@Override
	public void onHeartbeat(Event theEvent) {
		// TODO Auto-generated method stub
		Log.i(getClass().getSimpleName(), theEvent.toString());
	}

	@Override
	public void onError(String message) {
		// TODO Auto-generated method stub
		Log.e(getClass().getSimpleName(), message);
	}
	
	@Override
	protected void buildListAdapter(Object[] result) {
		// TODO Auto-generated method stub
		ArrayList<HashMap<String, Variable>> items = new ArrayList<HashMap<String, Variable>>();      
        for (int i=0; i<result.length; i++) {
        	HashMap<String, Variable> item = new HashMap<String, Variable>();
        	item.put("variable", (Variable) result[i]); items.add(item);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, items, R.layout.variable_item, new String[]{"variable"}, new int[]{R.id.variable});
        adapter.setViewBinder(this); setListAdapter(adapter);
	}

	@Override
	public boolean setViewValue(View view, Object data, String textRepresentation) {
		// TODO Auto-generated method stub
		Variable variable = (Variable) data; view.setId(Long.valueOf(variable.getId()).intValue());
		if (variable.getType().contains(Variable.WRITE)) view.setClickable(false); else view.setClickable(true);
		((TextView) view.findViewById(R.id.variable_name)).setText(variable.getName()); return true;
	}

}
