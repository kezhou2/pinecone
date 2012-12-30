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
public abstract class AbstractBuilder extends Builder {

	protected AlertDialog dialog;
	
	/**
	 * 
	 * @param context
	 */
	public AbstractBuilder(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
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
