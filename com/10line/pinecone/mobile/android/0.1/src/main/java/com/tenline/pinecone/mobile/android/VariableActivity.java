/**
 * 
 */
package com.tenline.pinecone.mobile.android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nl.justobjects.pushlet.core.Event;
import nl.justobjects.pushlet.core.Protocol;
import nl.justobjects.pushlet.util.PushletException;

import com.tenline.pinecone.mobile.android.service.ChannelService;
import com.tenline.pinecone.mobile.android.service.ChannelServiceListener;
import com.tenline.pinecone.mobile.android.service.ServiceConnectionHelper;
import com.tenline.pinecone.platform.model.Variable;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
	
	private static VariableActivity instance;
	
	public static VariableActivity getInstance() {return instance;}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); instance = this;
		initFetchTask("/device/" + getIntent().getStringExtra("deviceId") + "/variables");
        if (!channel.isBound()) bindService(new Intent(this, ChannelService.class), channel, Context.BIND_AUTO_CREATE);
		initChannelTask(Protocol.MODE_STREAM, getIntent().getStringExtra("deviceId"));
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
    public boolean onPrepareOptionsMenu(Menu menu) {
    	menu.setGroupVisible(R.id.device_items, false);
    	return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.user_logout: logout(); break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class ChannelTask extends AsyncTask<String, Void, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				while (!channel.isBound()) {Thread.sleep(100);}
				ChannelService service = (ChannelService) channel.getService();
				service.listen(VariableActivity.this, params[0], params[1]);
				return true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Log.e(getClass().getSimpleName(), e.getMessage());
				return false;
			}
		}
		
	}
	
	private void initChannelTask(String mode, String subject) {
		new ChannelTask().execute(mode, subject);
	}
	
	private void clearChannelTask() {
		try {
			((ChannelService) channel.getService()).unsubscribe();
		} catch (PushletException e) {
			// TODO Auto-generated catch block
			Log.e(getClass().getSimpleName(), e.getMessage());
		}
	}
	
	private ServiceConnectionHelper channel = new ServiceConnectionHelper();
	
	public void onPublish(Map<String, String> theAttributes) {
		try {
			ChannelService service = (ChannelService) channel.getService();
			service.publish(getIntent().getStringExtra("deviceId"), theAttributes);
		} catch (PushletException e) {
			// TODO Auto-generated catch block
			Log.e(getClass().getSimpleName(), e.getMessage());
		}
	}
	
	@Override
    protected void onDestroy() {
        super.onDestroy();
        if (channel.isBound()) {
            unbindService(channel);
            clearChannelTask();
            channel.setBound(false);
        }
    }

	@Override
	public void onAbort(Event theEvent) {
		// TODO Auto-generated method stub
		Log.i(getClass().getSimpleName(), theEvent.toString());
	}

	@Override
	public void onData(Event theEvent) {
		// TODO Auto-generated method stub
		Log.i(getClass().getSimpleName(), theEvent.toString());
		for (int i=0; i<getListView().getCount(); i++) {
			View view = getListView().getChildAt(i);
			if (view.getId() == Long.valueOf(theEvent.getField("var_id"))) {
				SimpleAdapter adapter = (SimpleAdapter) getListAdapter();
				TextView variableValue = (TextView) view.findViewById(R.id.variable_value);
				adapter.setViewText(variableValue, theEvent.getField("var_value")); break;
			}
		}
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
	protected void buildListAdapter(ArrayList<?> result) {
		// TODO Auto-generated method stub
		ArrayList<HashMap<String, Variable>> items = new ArrayList<HashMap<String, Variable>>();      
        for (int i=0; i<result.size(); i++) {
        	HashMap<String, Variable> item = new HashMap<String, Variable>();
        	item.put("variable", (Variable) result.get(i)); items.add(item);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, items, R.layout.variable_item, new String[]{"variable"}, new int[]{R.id.variable});
        adapter.setViewBinder(this); setListAdapter(adapter);
	}

	@Override
	public boolean setViewValue(View view, Object data, String textRepresentation) {
		// TODO Auto-generated method stub
		Variable variable = (Variable) data; view.setId(Long.valueOf(variable.getId()).intValue());
		((TextView) view.findViewById(R.id.variable_name)).setText(variable.getName()); return true;
	}

}
