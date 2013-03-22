/**
 * 
 */
package com.tenline.pinecone.mobile.android;

import org.codehaus.jackson.map.ObjectMapper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.tenline.pinecone.mobile.android.service.ChannelService;
import com.tenline.pinecone.mobile.android.service.ServiceConnectionHelper;

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
		if (!channelHelper.isBound()) bindService(new Intent(this, ChannelService.class), channelHelper, Context.BIND_AUTO_CREATE);
	}
	
	@Override
    protected void onDestroy() {
        super.onDestroy();
        if (channelHelper.isBound()) {unbindService(channelHelper); channelHelper.setBound(false);}
    }
	
}
