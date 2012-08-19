/**
 * 
 */
package com.tenline.pinecone.platform.web.store.server;

import java.util.Collection;

import com.tenline.pinecone.platform.model.Entity;
import com.tenline.pinecone.platform.model.Friend;
import com.tenline.pinecone.platform.sdk.development.APIResponse;
import com.tenline.pinecone.platform.web.store.client.services.FriendService;

/**
 * @author Bill
 *
 */
@SuppressWarnings("serial")
public class FriendServiceImpl extends AbstractService implements FriendService {

	/**
	 * 
	 */
	public FriendServiceImpl() {
		super();
	}

	@Override
	public boolean delete(Entity entity) throws Exception {
		APIResponse response = modelAPI.delete(entity);
		if (response.isDone()) return true;
		else return false;
	}

	@Override
	public Friend create(Friend friend) throws Exception {
		APIResponse response = modelAPI.create(friend);
		if (response.isDone()) return (Friend) response.getMessage();
		else return null;
	}

	@Override
	public Friend update(Friend friend) throws Exception {
		APIResponse response = modelAPI.update(friend);
		if (response.isDone()) return (Friend) response.getMessage();
		else return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Friend> show(String filter) throws Exception {
		APIResponse response = modelAPI.show(Friend.class, filter);
		if (response.isDone()) return (Collection<Friend>) response.getMessage();
		else return null;
	}

//	@Override
//	@SuppressWarnings("unchecked")
//	public Collection<Friend> showByReceiver(String filter) throws Exception {
//		APIResponse response = friendAPI.showByReceiver(filter);
//		if (response.isDone()) return (Collection<Friend>) response.getMessage();
//		else return null;
//	}
//
//	@Override
//	@SuppressWarnings("unchecked")
//	public Collection<Friend> showBySender(String filter) throws Exception {
//		APIResponse response = friendAPI.showBySender(filter);
//		if (response.isDone()) return (Collection<Friend>) response.getMessage();
//		else return null;
//	}

}
