/**
 * 
 */
package com.tenline.pinecone.mobile.android.view;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;

/**
 * @author Bill
 *
 */
public abstract class AbstractDialogBuilder extends Builder {

	protected AlertDialog dialog;
	
	/**
	 * 
	 * @param context
	 */
	public AbstractDialogBuilder(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		setCancelable(false);
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
