/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.services;

import java.util.Collection;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.tenline.pinecone.platform.model.Friend;

/**
 * @author Bill
 *
 */
@RemoteServiceRelativePath("friend/service")
public interface FriendService extends AbstractService {

	/**
	 * 
	 * @param friend
	 * @return
	 * @throws Exception
	 */
	public Friend create(Friend friend) throws Exception;
	
	/**
	 * 
	 * @param friend
	 * @return
	 * @throws Exception
	 */
	public Friend update(Friend friend) throws Exception;
	
	/**
	 * 
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	public Collection<Friend> show(String filter) throws Exception;
	
	/**
	 * 
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	public Collection<Friend> showByReceiver(String filter) throws Exception;

	/**
	 * 
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	public Collection<Friend> showBySender(String filter) throws Exception;
	
}
