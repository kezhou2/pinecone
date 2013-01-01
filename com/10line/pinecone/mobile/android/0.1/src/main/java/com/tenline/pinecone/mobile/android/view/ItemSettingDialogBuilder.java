/**
 * 
 */
package com.tenline.pinecone.mobile.android.view;

import java.util.HashMap;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.tenline.pinecone.mobile.android.ItemActivity;
import com.tenline.pinecone.mobile.android.R;
import com.tenline.pinecone.mobile.android.VariableActivity;

/**
 * @author Bill
 *
 */
public class ItemSettingDialogBuilder extends AbstractBuilder {

	public static final int DIALOG_ID = 1;
	
	/**
	 * 
	 * @param activity
	 */
	public ItemSettingDialogBuilder(final ItemActivity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		setPositiveButton(R.string.app_ok, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				HashMap<String, String> attributes = new HashMap<String, String>();
				attributes.put("var_id", activity.getIntent().getStringExtra("variableId"));
				attributes.put("var_value", activity.getIntent().getStringExtra("itemValue"));
				VariableActivity.getInstance().onPublish(attributes); dialog.cancel();
			}
			
		});
		setNegativeButton(R.string.app_cancel, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
			
		});
		setCancelable(false);
		setMessage(activity.getString(R.string.item_setting) + " " + activity.getIntent().getStringExtra("itemValue") + "?");
		setTitle(activity.getIntent().getStringExtra("variableName"));
		setIcon(android.R.drawable.ic_menu_manage);
		setDialog(create());
	}

}
