/**
 * 
 */
package com.tenline.pinecone.mobile.android;

import java.util.ArrayList;
import java.util.Arrays;

import com.tenline.pinecone.mobile.android.service.RESTService;
import com.tenline.pinecone.mobile.android.service.TaskFacade;
import com.tenline.pinecone.mobile.android.view.ItemSettingDialogBuilder;
import com.tenline.pinecone.platform.model.Item;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

/**
 * @author Bill
 *
 */
public class ItemActivity extends AbstractListActivity {

	public static final String ACTIVITY_ACTION = "com.tenline.pinecone.mobile.android.item";
	
	private static final String GET_ITEMS_WITH_VARIABLE = "getItemsWithVariable";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TaskFacade.initRESTTask(this, GET_ITEMS_WITH_VARIABLE, "/variable/" + getIntent().getStringExtra("variableId") + "/items");
		getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			@SuppressWarnings("deprecation")
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				getIntent().putExtra("itemValue", ((TextView) view).getText());
				Log.i(getClass().getSimpleName(), getIntent().getStringExtra("itemValue"));
				showDialog(ItemSettingDialogBuilder.DIALOG_ID);
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
	
	@Override
	@SuppressWarnings("deprecation")
	protected void onPrepareDialog(final int id, final Dialog dialog) {
		super.onPrepareDialog(id, dialog);
		switch(id) {
		case ItemSettingDialogBuilder.DIALOG_ID:
			((AlertDialog) dialog).setMessage(getString(R.string.item_setting) + " " + getIntent().getStringExtra("itemValue") + "?");
		}
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public Dialog onCreateDialog(int id) {
		switch(id) {
		case ItemSettingDialogBuilder.DIALOG_ID:
			return new ItemSettingDialogBuilder(this).getDialog();
		}
		return super.onCreateDialog(id);
	}

	@Override
	protected void buildListAdapter(Object[] result) {
		// TODO Auto-generated method stub
		String[] items = new String[result.length];   
        for (int i=0; i<result.length; i++) {items[i] = ((Item) result[i]).getValue();}
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, items));	
	}
	
	@Override
	public Object[] doInBackground(String... params) {
		// TODO Auto-generated method stub
		Object[] result = Arrays.asList(params).toArray();
		try {
			while (!helper.isBound()) {Thread.sleep(100);}
			RESTService service = (RESTService) helper.getService();
			if (result[0].equals(GET_ITEMS_WITH_VARIABLE)) {
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
			if (result[0].equals(GET_ITEMS_WITH_VARIABLE)) {
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
