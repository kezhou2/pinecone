/**
 * 
 */
package com.tenline.pinecone.mobile.android;

import java.util.ArrayList;

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
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initFetchTask("/variable/" + getIntent().getStringExtra("variableId") + "/items");
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
	protected void buildListAdapter(ArrayList<?> result) {
		// TODO Auto-generated method stub
		String[] items = new String[result.size()];   
        for (int i=0; i<result.size(); i++) {items[i] = ((Item) result.get(i)).getValue();}
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, items));	
	}

}
