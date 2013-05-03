/**
 * 
 */
package com.tenline.pinecone.mobile.android;

import org.codehaus.jackson.map.ObjectMapper;

import com.tenline.pinecone.mobile.android.service.ChannelService;
import com.tenline.pinecone.mobile.android.service.ServiceConnectionHelper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * @author Bill
 *
 */
public abstract class AbstractMessageActivity extends AbstractListActivity {

	protected ObjectMapper mapper = new ObjectMapper();
	
	protected ServiceConnectionHelper channelHelper = new ServiceConnectionHelper();
	
	protected ChannelService getChannelService() {return (ChannelService) channelHelper.getService();}
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!channelHelper.isBound()) {bindService(new Intent(this, ChannelService.class), channelHelper, Context.BIND_AUTO_CREATE);}
	}
	
	@Override
    protected void onDestroy() {
        super.onDestroy();
        if (channelHelper.isBound()) {unbindService(channelHelper); channelHelper.setBound(false);}
    }
	
	@Override
    public boolean onPrepareOptionsMenu(Menu menu) {
    	menu.setGroupVisible(R.id.device_items, false); return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {case R.id.user_logout: new LogoutTask(this).execute(); break;}
		return super.onOptionsItemSelected(item);
	}

}