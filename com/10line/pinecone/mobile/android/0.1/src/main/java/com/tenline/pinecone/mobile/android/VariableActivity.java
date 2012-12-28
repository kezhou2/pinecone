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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SimpleAdapter;

/**
 * @author Bill
 *
 */
public class VariableActivity extends AbstractListActivity implements ChannelServiceListener {

	public static final String ACTIVITY_ACTION = "com.tenline.pinecone.mobile.android.variable";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(getClass().getSimpleName(), "onCreate");
		initFetchTask("/device/" + getIntent().getStringExtra("deviceId") + "/variables");
		initChannelTask(Protocol.MODE_STREAM, getIntent().getStringExtra("deviceId"));
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.options, menu);
	    return super.onCreateOptionsMenu(menu);
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
				service.join();
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
	
	private ServiceConnectionHelper channel = new ServiceConnectionHelper();
	
	@Override
    protected void onStart() {
        super.onStart();
        bindService(new Intent(this, ChannelService.class), channel, Context.BIND_AUTO_CREATE);
    }
	
	@Override
    protected void onStop() {
        super.onStop();
        if (channel.isBound()) {
            unbindService(channel);
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

	private ArrayList<HashMap<String, String>> items;
	
	@Override
	protected void buildListAdapter(ArrayList<?> result) {
		// TODO Auto-generated method stub
		items = new ArrayList<HashMap<String, String>>();      
        for (int i=0; i<result.size(); i++) {
        	HashMap<String, String> item = new HashMap<String, String>();
        	item.put("variable_text", ((Variable) result.get(i)).getName());
        	item.put("variable_value", "60");
        	items.add(item);
        }
		setListAdapter(new SimpleAdapter(this, items, R.layout.variable_item, 
			new String[]{"variable_text","variable_value"}, new int[]{R.id.variable_text,R.id.variable_value}));
	}

}
