/**
 * 
 */
package com.tenline.pinecone.mobile.android;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * 
 * @author Bill
 *
 */
public class MainActivity extends Activity {

    private static String TAG = "android";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
        setContentView(R.layout.main);
    }

}

