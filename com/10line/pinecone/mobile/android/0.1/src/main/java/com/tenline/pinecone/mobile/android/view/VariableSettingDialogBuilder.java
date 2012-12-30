/**
 * 
 */
package com.tenline.pinecone.mobile.android.view;

import com.tenline.pinecone.mobile.android.R;
import com.tenline.pinecone.mobile.android.VariableActivity;

/**
 * @author Bill
 *
 */
public class VariableSettingDialogBuilder extends AbstractBuilder {

	public static final int DIALOG_ID = 1;
	
	/**
	 * 
	 * @param activity
	 */
	public VariableSettingDialogBuilder(VariableActivity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
//		setCancelable(false);
		setTitle(R.string.variable_setting);
		setIcon(android.R.drawable.ic_menu_info_details);
		setDialog(create());
	}

}
