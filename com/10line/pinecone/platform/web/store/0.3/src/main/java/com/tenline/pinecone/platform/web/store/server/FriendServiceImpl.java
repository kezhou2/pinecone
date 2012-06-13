/**
 * 
 */
package com.tenline.pinecone.platform.web.store.server;

import java.util.Collection;

import com.tenline.pinecone.platform.model.Friend;
import com.tenline.pinecone.platform.sdk.FriendAPI;
import com.tenline.pinecone.platform.sdk.development.APIResponse;
import com.tenline.pinecone.platform.web.store.client.services.FriendService;

/**
 * @author Bill
 *
 */
@SuppressWarnings("serial")
public class FriendServiceImpl extends AbstractService implements FriendService {
	
	private FriendAPI friendAPI;

	/**
	 * 
	 */
	public FriendServiceImpl() {
		// TODO Auto-generated constructor stub
		friendAPI = new FriendAPI(HOST, PORT, CONTEXT);
	}

	@Override
	public boolean delete(String id) throws Exception {
		// TODO Auto-generated method stub
		APIResponse response = friendAPI.delete(id);
		if (response.isDone()) return true;
		else return false;
	}

	@Override
	public Friend create(Friend friend) throws Exception {
		// TODO Auto-generated method stub
		APIResponse response = friendAPI.create(friend);
		if (response.isDone()) return (Friend) response.getMessage();
		else return null;
	}

	@Override
	public Friend update(Friend friend) throws Exception {
		// TODO Auto-generated method stub
		APIResponse response = friendAPI.update(friend);
		if (response.isDone()) return (Friend) response.getMessage();
		else return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Friend> show(String filter) throws Exception {
		// TODO Auto-generated method stub
		APIResponse response = friendAPI.show(filter);
		if (response.isDone()) return (Collection<Friend>) response.getMessage();
		else return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Friend> showByReceiver(String filter) throws Exception {
		// TODO Auto-generated method stub
		APIResponse response = friendAPI.showByReceiver(filter);
		if (response.isDone()) return (Collection<Friend>) response.getMessage();
		else return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Friend> showBySender(String filter) throws Exception {
		// TODO Auto-generated method stub
		APIResponse response = friendAPI.showBySender(filter);
		if (response.isDone()) return (Collection<Friend>) response.getMessage();
		else return null;
	}

}
