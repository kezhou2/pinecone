/**
 * 
 */
package com.tenline.pinecone.mobile.android;

import java.util.ArrayList;
import java.util.HashMap;

import org.codehaus.jackson.node.ObjectNode;

import com.tenline.pinecone.mobile.android.view.ItemSettingDialogBuilder;
import com.tenline.pinecone.platform.model.Item;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

/**
 * @author Bill
 *
 */
public class ItemActivity extends AbstractMessageActivity {

	public static final String ACTIVITY_ACTION = "com.tenline.pinecone.mobile.android.item";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		doInitListViewTask("/variable/" + getIntent().getStringExtra("variableId") + "/items");
		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			@SuppressWarnings("deprecation")
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				if (((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() == null) {
					Toast.makeText(getApplicationContext(), R.string.network_turn_off, Toast.LENGTH_LONG).show();
				} else {
					getIntent().putExtra("itemValue", ((TextView) view.findViewById(R.id.item_value)).getText());
					showDialog(ItemSettingDialogBuilder.DIALOG_ID);
				}
			}
			
		});
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
				ObjectNode node = mapper.createObjectNode();
				node.put("id", params[0]); node.put("value", params[1]);
				getChannelService().publish("pinecone@device." + params[2] + ".subscribe", mapper.writeValueAsString(node)); return true;
			} catch (Exception e) {Log.e(getClass().getSimpleName(), e.getMessage()); return false;}
		}
		
	}
	
	@Override
	@SuppressWarnings("deprecation")
	protected void onPrepareDialog(final int id, final Dialog dialog) {
		super.onPrepareDialog(id, dialog);
		switch(id) {
		case ItemSettingDialogBuilder.DIALOG_ID:
			((AlertDialog) dialog).setMessage(getString(R.string.item_setting) + " " + getIntent().getStringExtra("itemValue") + "?"); break;
		}
	}
	
	@Override
	@SuppressWarnings("deprecation")
	protected Dialog onCreateDialog(int id) {
		switch(id) {
		case ItemSettingDialogBuilder.DIALOG_ID:
			return new ItemSettingDialogBuilder(this).getDialog();
		}
		return super.onCreateDialog(id);
	}

	@Override
	protected void buildListAdapter(Object[] result) {
		// TODO Auto-generated method stub
		ArrayList<HashMap<String, Item>> items = new ArrayList<HashMap<String, Item>>();
		for (int i=0; i<result.length; i++) {
			HashMap<String, Item> item = new HashMap<String, Item>();
			item.put("item", (Item) result[i]); items.add(item);
		}	
		SimpleAdapter adapter = new SimpleAdapter(this, items, R.layout.item_item, new String[]{"item"}, new int[]{R.id.item});
        adapter.setViewBinder(this); setListAdapter(adapter);
	}

	@Override
	public boolean setViewValue(View view, Object data, String textRepresentation) {
		// TODO Auto-generated method stub
		Item item = (Item) data; ((TextView) view.findViewById(R.id.item_value)).setText(item.getValue()); return true;
	}

}
