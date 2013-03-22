/**
 * 
 */
package com.tenline.pinecone.mobile.android;

import java.util.ArrayList;
import java.util.HashMap;

import org.codehaus.jackson.node.ObjectNode;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.tenline.pinecone.mobile.android.view.ActivateDeviceDialogBuilder;
import com.tenline.pinecone.mobile.android.view.DeleteDeviceDialogBuilder;
import com.tenline.pinecone.mobile.android.view.FormEditText;
import com.tenline.pinecone.mobile.android.view.ModifyDeviceDialogBuilder;
import com.tenline.pinecone.platform.model.Device;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * @author Bill
 *
 */
public class DeviceActivity extends AbstractMessageActivity implements ViewBinder {
	
	public static final String ACTIVITY_ACTION = "com.tenline.pinecone.mobile.android.device";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); registerForContextMenu(getListView());
		doInitListViewTask("/user/" + getIntent().getStringExtra("userId") + "/devices");
		getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(VariableActivity.ACTIVITY_ACTION);
				intent.putExtra("deviceId", String.valueOf(view.getTag()));
				startActivity(intent);
			}
			
		});
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.device_activate: showDialog(ActivateDeviceDialogBuilder.DIALOG_ID); break;
		case R.id.user_logout: new LogoutTask(this).execute(); break;}
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
				ObjectNode node = mapper.createObjectNode(); node.put("action", params[0]);
				getChannelService().publish("pinecone@device." + params[1], mapper.writeValueAsString(node)); return true;
			} catch (Exception e) {Log.e(getClass().getSimpleName(), e.getMessage()); return false;}
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
		getIntent().putExtra("deviceId", info.targetView.getTag().toString());
		getIntent().putExtra("deviceName", ((TextView) info.targetView).getText().toString());
		switch(item.getItemId()) {
		case R.id.device_delete: showDialog(DeleteDeviceDialogBuilder.DIALOG_ID); break;
		case R.id.device_modify: showDialog(ModifyDeviceDialogBuilder.DIALOG_ID); break;
		} return super.onContextItemSelected(item);
	}

	@Override
	protected void buildListAdapter(Object[] result) {
		// TODO Auto-generated method stub
		if (result.length == 0) Toast.makeText(this, R.string.error_device_code_is_not_existed, Toast.LENGTH_LONG).show();
		ArrayList<HashMap<String, Device>> items = new ArrayList<HashMap<String, Device>>();
		for (int i=0; i<result.length; i++) {
			HashMap<String, Device> item = new HashMap<String, Device>();
			item.put("device", (Device) result[i]); items.add(item);
		}	
		SimpleAdapter adapter = new SimpleAdapter(this, items, android.R.layout.simple_list_item_checked, new String[]{"device"}, new int[]{android.R.id.text1});
        adapter.setViewBinder(this); setListAdapter(adapter);
	}
	
	@Override
	public boolean setViewValue(View view, Object data, String textRepresentation) {
		// TODO Auto-generated method stub
		Device device = (Device) data; view.setTag(Long.valueOf(device.getId()).intValue());
		((TextView) view).setText(device.getName()); return true;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if (scanResult != null) {((FormEditText) builder.getDialog().findViewById(R.id.device_code_input)).setText(scanResult.getContents());}
	}

}
