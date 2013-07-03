/**
 * 
 */
package com.tenline.pinecone.mobile.android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.tenline.pinecone.mobile.android.view.ActivateDeviceDialogBuilder;
import com.tenline.pinecone.mobile.android.view.DeleteDeviceDialogBuilder;
import com.tenline.pinecone.mobile.android.view.FormEditText;
import com.tenline.pinecone.mobile.android.view.ModifyDeviceDialogBuilder;
import com.tenline.pinecone.platform.model.Device;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author Bill
 *
 */
public class DeviceActivity extends AbstractListActivity {
	
	public static final String ACTIVITY_ACTION = "com.tenline.pinecone.mobile.android.device";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); registerForContextMenu(getListView());
		getSupportActionBar().setTitle(R.string.device_title);
		doInitListViewTask("/user/" + getIntent().getStringExtra("userId") + "/devices");
		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				if (((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() == null) {
					Toast.makeText(getApplicationContext(), R.string.network_turn_off, Toast.LENGTH_LONG).show();
				} else {
					Intent intent = new Intent(VariableActivity.ACTIVITY_ACTION);
					intent.putExtra("deviceId", String.valueOf(view.getId()));
					intent.putExtra("deviceCode", view.getTag().toString());
					startActivity(intent);
				}
			}
			
		});
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
		switch(item.getItemId()) {case R.id.device_activate: showDialog(ActivateDeviceDialogBuilder.DIALOG_ID); break;}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	@SuppressWarnings("deprecation")
	protected void onPrepareDialog(final int id, final Dialog dialog) {
		super.onPrepareDialog(id, dialog);
		switch(id) {
		case ModifyDeviceDialogBuilder.DIALOG_ID: ((AlertDialog) dialog).setTitle(getIntent().getStringExtra("deviceName")); break;
		case DeleteDeviceDialogBuilder.DIALOG_ID: ((AlertDialog) dialog).setTitle(getIntent().getStringExtra("deviceName")); break;
		}
	}
	
	private ActivateDeviceDialogBuilder builder;
	
	@Override
	@SuppressWarnings("deprecation")
	protected Dialog onCreateDialog(int id) {
		switch(id) {
		case ActivateDeviceDialogBuilder.DIALOG_ID:
			builder = new ActivateDeviceDialogBuilder(this); return builder.getDialog();
		case ModifyDeviceDialogBuilder.DIALOG_ID:
			return new ModifyDeviceDialogBuilder(this).getDialog();
		case DeleteDeviceDialogBuilder.DIALOG_ID:
			return new DeleteDeviceDialogBuilder(this).getDialog();
		}
		return super.onCreateDialog(id);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, view, menuInfo); getMenuInflater().inflate(R.menu.context_menu, menu);
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		getIntent().putExtra("deviceId", String.valueOf(info.targetView.getId()));
		getIntent().putExtra("deviceName", ((TextView) info.targetView.findViewById(R.id.device_name)).getText().toString());
		switch(item.getItemId()) {
		case R.id.device_delete: showDialog(DeleteDeviceDialogBuilder.DIALOG_ID); break;
		case R.id.device_modify: showDialog(ModifyDeviceDialogBuilder.DIALOG_ID); break;
		} return super.onContextItemSelected(item);
	}

	@Override
	protected void buildListAdapter(Object[] result) {
		// TODO Auto-generated method stub
		if (result.length == 0) Toast.makeText(this, R.string.error_device_code_is_not_existed, Toast.LENGTH_LONG).show();
		ArrayList<HashMap<String, String>> items = new ArrayList<HashMap<String, String>>();
		for (int i=0; i<result.length; i++) {
			HashMap<String, String> item = new HashMap<String, String>();
			Device device = (Device) result[i];
			item.put("deviceId", device.getId().toString());
			item.put("deviceCode", device.getCode());
			item.put("deviceName", device.getName());
			items.add(item);
		}	
        setListAdapter(new DeviceAdapter(this, items, R.layout.device_item, new String[]{"deviceName"}, new int[]{R.id.device_name}));
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class DeviceAdapter extends SimpleAdapter {
		
		public DeviceAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
			super(context, data, resource, from, to);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		@SuppressWarnings("unchecked")
	    public View getView(int position, View convertView, ViewGroup parent) {
			View view = super.getView(position, convertView, parent);
			HashMap<String, String> item = (HashMap<String, String>) getItem(position);
			view.setTag(item.get("deviceCode")); 
			view.setId(Integer.valueOf(item.get("deviceId")));
			return view;
		}
		
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if (scanResult != null) {((FormEditText) builder.getDialog().findViewById(R.id.device_code_input)).setText(scanResult.getContents());}
	}

}
