/**
 * 
 */
package com.tenline.pinecone.mobile.android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;

import com.tenline.pinecone.platform.model.Variable;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

/**
 * @author Bill
 *
 */
public class VariableActivity extends AbstractMessageActivity implements MqttCallback {

	public static final String ACTIVITY_ACTION = "com.tenline.pinecone.mobile.android.variable";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		doInitListViewTask("/device/" + getIntent().getStringExtra("deviceId") + "/variables");
        new ListenToChannelTask().execute(getIntent().getStringExtra("deviceCode"));
		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				if (((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() == null) {
					Toast.makeText(getApplicationContext(), R.string.network_turn_off, Toast.LENGTH_LONG).show();
				} else {
					Intent intent = new Intent(ItemActivity.ACTIVITY_ACTION);
					intent.putExtra("variableId", String.valueOf(view.getId()));
					intent.putExtra("variableName", ((TextView) view.findViewById(R.id.variable_name)).getText());
					intent.putExtra("deviceCode", getIntent().getStringExtra("deviceCode"));
					startActivity(intent);
				}
			}
			
		});
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
				getChannelService().listen(VariableActivity.this, "pinecone@device." + params[0] + ".publish"); return true;
			} catch (Exception e) {Log.e(getClass().getSimpleName(), e.getMessage()); return false;}
		}
		
	}
	
	private static final int SUBSCRIBE_DATA = 0;
	private Handler subscriber = new Handler() {
		
		@Override
		public void handleMessage(Message msg) {
            switch(msg.what) {
            case SUBSCRIBE_DATA:
				try {
					JsonNode node = mapper.readTree(((MqttMessage) msg.obj).getPayload());
					for (int i=0; i<getListView().getCount(); i++) {
	        			View view = getListView().getChildAt(i);
	        			if (view.getId() == Long.valueOf(node.get("id").asText())) {
	        				SimpleAdapter adapter = (SimpleAdapter) getListAdapter();
	        				TextView variableValue = (TextView) view.findViewById(R.id.variable_value);
	        				adapter.setViewText(variableValue, node.get("value").asText()); break;
	        			}
	        		} break;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Log.e(getClass().getSimpleName(), e.getMessage());
				}
            }
        }
		
    };
	
	@Override
	protected void buildListAdapter(Object[] result) {
		// TODO Auto-generated method stub
		ArrayList<HashMap<String, String>> items = new ArrayList<HashMap<String, String>>();      
        for (int i=0; i<result.length; i++) {
        	HashMap<String, String> item = new HashMap<String, String>();
        	Variable variable = (Variable) result[i];
        	item.put("variableId", variable.getId().toString()); 
        	item.put("variableName", variable.getName());
        	item.put("variableType", variable.getType());
        	items.add(item);
        }
        setListAdapter(new VariableAdapter(this, items, R.layout.variable_item, new String[]{"variableName"}, new int[]{R.id.variable_name}));
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class VariableAdapter extends SimpleAdapter {

		public VariableAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
			super(context, data, resource, from, to);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		@SuppressWarnings("unchecked")
	    public View getView(int position, View convertView, ViewGroup parent) {
			View view = super.getView(position, convertView, parent);
			HashMap<String, String> item = (HashMap<String, String>) getItem(position);
			view.setId(Integer.valueOf(item.get("variableId")));
			if (item.get("variableType").contains(Variable.WRITE)) {
				view.setClickable(false); 
				((ImageView) view.findViewById(R.id.variable_icon)).setImageResource(android.R.drawable.presence_online);
			} else {
				view.setClickable(true);
			}
			return view;
		}
		
	}

	@Override
	public void connectionLost(Throwable cause) {
		// TODO Auto-generated method stub
		Log.e(getClass().getSimpleName(), cause.getMessage());
	}

	@Override
	public void deliveryComplete(MqttDeliveryToken token) {
		// TODO Auto-generated method stub
		Log.i(getClass().getSimpleName(), "deliveryComplete");
	}

	@Override
	public void messageArrived(MqttTopic topic, MqttMessage message) throws Exception {
		// TODO Auto-generated method stub
		Log.i(getClass().getSimpleName(), topic.getName().toString());
		Log.i(getClass().getSimpleName(), new String(message.getPayload()));
		subscriber.sendMessage(subscriber.obtainMessage(SUBSCRIBE_DATA, message));
	}

}
