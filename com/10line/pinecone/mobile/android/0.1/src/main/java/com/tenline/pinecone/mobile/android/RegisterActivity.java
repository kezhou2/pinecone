/**
 * 
 */
package com.tenline.pinecone.mobile.android;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * @author Bill
 *
 */
public class RegisterActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(getClass().getSimpleName(), "onCreate");
        setContentView(R.layout.register);
        findViewById(R.id.submit).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i(getClass().getSimpleName(), "onClick");
				finish();
			}
        	
        });
	}
	 
}
