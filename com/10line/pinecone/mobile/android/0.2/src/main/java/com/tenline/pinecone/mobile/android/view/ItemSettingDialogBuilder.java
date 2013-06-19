/**
 * 
 */
package com.tenline.pinecone.mobile.android.view;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.tenline.pinecone.mobile.android.ItemActivity;
import com.tenline.pinecone.mobile.android.R;

/**
 * @author Bill
 *
 */
public class ItemSettingDialogBuilder extends AbstractDialogBuilder {

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
				String variableId = activity.getIntent().getStringExtra("variableId");
				String variableValue = activity.getIntent().getStringExtra("itemValue");
				String deviceCode = activity.getIntent().getStringExtra("deviceCode");
				dialog.cancel(); activity.finish();
				if (activity.isFinishing()) {activity.new PublishToChannelTask().execute(variableId, variableValue, deviceCode);}
			}
			
		});
		setNegativeButton(R.string.app_cancel, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
			
		});
		setMessage(activity.getString(R.string.item_setting) + " " + activity.getIntent().getStringExtra("itemValue") + "?");
		setTitle(activity.getIntent().getStringExtra("variableName"));
		setIcon(android.R.drawable.ic_menu_manage);
		setDialog(create());
	}

}
