/**
 * 
 */
package com.tenline.pinecone.mobile.android.receiver;

import com.tenline.pinecone.mobile.android.R;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

/**
 * @author Bill
 *
 */
public class NetworkReceiver extends BroadcastReceiver {
	
	/**
	 * Whether network is turned off or not
	 */
	private boolean isTurnOff;

	/**
	 * 
	 */
	public NetworkReceiver() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		boolean turnOff = manager.getActiveNetworkInfo() == null ? true : false;
		if (turnOff != isTurnOff) {
			if (turnOff) {Toast.makeText(context, R.string.network_turn_off, Toast.LENGTH_LONG).show();}
			else {Toast.makeText(context, R.string.network_turn_on, Toast.LENGTH_LONG).show();}
			isTurnOff = turnOff;
		}
	}

}
