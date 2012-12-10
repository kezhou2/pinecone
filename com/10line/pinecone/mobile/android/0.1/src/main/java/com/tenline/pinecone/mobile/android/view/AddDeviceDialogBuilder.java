/**
 * 
 */
package com.tenline.pinecone.mobile.android.view;

import com.tenline.pinecone.mobile.android.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * @author Bill
 *
 */
public class AddDeviceDialogBuilder extends Builder {
	
	public static final int DIALOG_ID = 0;
	private AlertDialog dialog;

	/**
	 * 
	 * @param context
	 */
	public AddDeviceDialogBuilder(final Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		View view = ((Activity) context).getLayoutInflater().inflate(R.layout.add_device, null);
		final FormEditText deviceCode = (FormEditText) view.findViewById(R.id.device_code_input);
		final FormEditText deviceName = (FormEditText) view.findViewById(R.id.device_name_input);
		((Button) view.findViewById(R.id.app_ok)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				if (deviceCode.testValidity() && deviceName.testValidity()) {
					Toast.makeText(context, R.string.device_add, Toast.LENGTH_LONG).show();
					dialog.cancel();
				}
			}
			
		});
		((Button) view.findViewById(R.id.app_cancel)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
			
		});
		setView(view);
		setCancelable(false);
		setTitle(R.string.device_add);
		setIcon(android.R.drawable.ic_menu_add);
		setDialog(create());
	}

	/**
	 * @return the dialog
	 */
	public AlertDialog getDialog() {
		return dialog;
	}

	/**
	 * @param dialog the dialog to set
	 */
	public void setDialog(AlertDialog dialog) {
		this.dialog = dialog;
	}

}
