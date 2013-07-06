/**
 * 
 */
package com.tenline.pinecone.mobile.android.view;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.tenline.pinecone.mobile.android.R;
import com.tenline.pinecone.mobile.android.VariableActivity;

/**
 * @author Bill
 *
 */
public class QueryVariableDialogBuilder extends AbstractDialogBuilder {

	public static final int DIALOG_ID = 4;
	
	/**
	 * 
	 * @param activity
	 */
	public QueryVariableDialogBuilder(VariableActivity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		View view = activity.getLayoutInflater().inflate(R.layout.query_variable, null);
//		DatePicker variableQueryDate = (DatePicker) view.findViewById(R.id.variable_query_date);
		((Button) view.findViewById(R.id.app_ok)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				
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
		setTitle(activity.getIntent().getStringExtra("variableName"));
		setIcon(android.R.drawable.ic_menu_search);
		setDialog(create());
	}

}
