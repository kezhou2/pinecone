/**
 * 
 */
package com.tenline.pinecone.mobile.android;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import nl.justobjects.pushlet.core.Event;
import nl.justobjects.pushlet.core.Protocol;

import com.tenline.pinecone.mobile.android.service.ChannelService;
import com.tenline.pinecone.mobile.android.service.ChannelServiceListener;
import com.tenline.pinecone.mobile.android.service.RESTService;
import com.tenline.pinecone.mobile.android.service.ServiceConnectionHelper;
import com.tenline.pinecone.mobile.android.service.TaskFacade;
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
	
	private static final String GET_VARIABLES_WITH_DEVICE = "getVariablesWithDevice";
	private static final String LISTEN_TO_CHANNEL = "listenToChannel";
	public static final String PUBLISH_TO_CHANNEL = "publishToChannel";
	private static final String UNSUBSCRIBE_FROM_CHANNEL = "unsubscribeFromChannel";
	
	private static VariableActivity instance;
	
	public static VariableActivity getInstance() {return instance;}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); instance = this;
		TaskFacade.initRESTTask(this, GET_VARIABLES_WITH_DEVICE, "/device/" + getIntent().getStringExtra("deviceId") + "/variables");
        if (!channel.isBound()) bindService(new Intent(this, ChannelService.class), channel, Context.BIND_AUTO_CREATE);
		initChannelTask(LISTEN_TO_CHANNEL, Protocol.MODE_STREAM, getIntent().getStringExtra("deviceId"));
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
				if (params[0].equals(LISTEN_TO_CHANNEL)) {
					while (!service.isJoined()) {Thread.sleep(100);}
					service.listen(VariableActivity.this, params[1], params[2]);
				}
				if (params[0].equals(UNSUBSCRIBE_FROM_CHANNEL)) {service.unsubscribe();}
				if (params[0].equals(PUBLISH_TO_CHANNEL)) {
					HashMap<String, String> attributes = new HashMap<String, String>();
					attributes.put("var_id", params[1]); attributes.put("var_value", params[2]);
					service.publish(getIntent().getStringExtra("deviceId"), attributes);
				}
				return true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Log.e(getClass().getSimpleName(), e.getMessage());
				return false;
			}
		}
		
	}
	
	public void initChannelTask(String... params) {
		new ChannelTask().execute(params);
	}
	
	private ServiceConnectionHelper channel = new ServiceConnectionHelper();
	
	@Override
    protected void onDestroy() {
        super.onDestroy();
        if (channel.isBound()) {
            unbindService(channel);
            channel.setBound(false);
            initChannelTask(UNSUBSCRIBE_FROM_CHANNEL);
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
		handler.sendMessage(handler.obtainMessage(SUBSCRIBE_DATA, theEvent));
	}
	
	private static final int SUBSCRIBE_DATA = 0;
	private Handler handler = new Handler() {
		
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
	
	@Override
	public Object[] doInBackground(String... params) {
		// TODO Auto-generated method stub
		Object[] result = Arrays.asList(params).toArray();
		try {
			while (!helper.isBound()) {Thread.sleep(100);}
			RESTService service = (RESTService) helper.getService();
			if (result[0].equals(GET_VARIABLES_WITH_DEVICE)) {
				Object[] temp = ((ArrayList<?>) service.get(result[1].toString())).toArray();
				Object[] flag = {result[0]}; result = new Object[temp.length + 1];
				System.arraycopy(flag, 0, result, 0, 1); System.arraycopy(temp, 0, result, 1, temp.length);
			} else {
				result = super.doInBackground(params);	
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(getClass().getSimpleName(), e.getMessage());
		}
		return result;
	}
	
	@Override
	public void onPostExecute(Object[] result) {
		// TODO Auto-generated method stub
		try {
			if (result[0].equals(GET_VARIABLES_WITH_DEVICE)) {
				Object[] data = new Object[result.length - 1];
				System.arraycopy(result, 1, data, 0, data.length); buildListAdapter(data);
			} else {
				super.onPostExecute(result);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(getClass().getSimpleName(), e.getMessage());
		}
	}

}
