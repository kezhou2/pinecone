/**
 * 
 */
package com.tenline.pinecone.mobile.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 
 * @author Bill
 *
 */
public class LoginActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		Log.i(getClass().getSimpleName(), "onCreate");
        setContentView(R.layout.login);
        findViewById(R.id.login).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Log.i(getClass().getSimpleName(), "onClick");
				startActivity(new Intent(DeviceActivity.ACTIVITY_ACTION));
		    	finish();
			}
        	
        });
        findViewById(R.id.register).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Log.i(getClass().getSimpleName(), "onClick");
				startActivity(new Intent(RegisterActivity.ACTIVITY_ACTION));
			}
        	
        });
    }

}

