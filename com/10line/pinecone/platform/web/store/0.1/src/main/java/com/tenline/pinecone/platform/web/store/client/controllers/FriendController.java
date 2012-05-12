/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.controllers;

import java.util.Collection;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.tenline.pinecone.platform.model.Friend;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.store.client.events.FriendEvents;
import com.tenline.pinecone.platform.web.store.client.services.FriendService;
import com.tenline.pinecone.platform.web.store.client.services.FriendServiceAsync;
import com.tenline.pinecone.platform.web.store.client.views.FriendView;

/**
 * @author Bill
 *
 */
public class FriendController extends Controller {
	
	private FriendView view = new FriendView(this);

	/**
	 * 
	 */
	public FriendController() {
		// TODO Auto-generated constructor stub
		registerEventTypes(FriendEvents.GET_BY_USER);
		registerEventTypes(FriendEvents.GET_REQUESTS);
		registerEventTypes(FriendEvents.CHECK);
		registerEventTypes(FriendEvents.ADD);
		registerEventTypes(FriendEvents.DELETE);
		registerEventTypes(FriendEvents.AGREE);
		registerEventTypes(FriendEvents.DISAGREE);
	}

	@Override
	public void handleEvent(AppEvent event) {
		// TODO Auto-generated method stub
		try {
			if (event.getType().equals(FriendEvents.GET_BY_USER)) {
				getByUser(event);
			} else if (event.getType().equals(FriendEvents.GET_REQUESTS)) {
				getRequests(event);
			} else if (event.getType().equals(FriendEvents.CHECK)) {
				check(event);
			} else if (event.getType().equals(FriendEvents.ADD)) {
				add(event);
			} else if (event.getType().equals(FriendEvents.DELETE)) {
				delete(event);
			} else if (event.getType().equals(FriendEvents.AGREE)) {
				agree(event);
			} else if (event.getType().equals(FriendEvents.DISAGREE)) {
				disagree(event);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param event
	 * @throws Exception
	 */
	private void getByUser(final AppEvent event) throws Exception {
		final String filter = "isDecided==true&&";
		final FriendServiceAsync friendService = Registry.get(FriendService.class.getName());
		friendService.show(filter + "receiver.id=='"+event.getData("id").toString()+"'", new AsyncCallback<Collection<Friend>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Collection<Friend> result) {
				// TODO Auto-generated method stub
				final Collection<Friend> temp = result;
				friendService.show(filter + "sender.id=='"+event.getData("id").toString()+"'", new AsyncCallback<Collection<Friend>>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						caught.printStackTrace();
					}

					@Override
					public void onSuccess(Collection<Friend> result) {
						// TODO Auto-generated method stub
						result.addAll(temp);
						forwardToView(view, event.getType(), result);
					}
					
				});
			}
			
		});
	}
	
	/**
	 * 
	 * @param event
	 * @throws Exception
	 */
	private void getRequests(final AppEvent event) throws Exception {
		String filter = "isDecided==false&&receiver.id=='"+event.getData("id").toString()+"'";
		FriendServiceAsync friendService = Registry.get(FriendService.class.getName());
		friendService.show(filter, new AsyncCallback<Collection<Friend>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Collection<Friend> result) {
				// TODO Auto-generated method stub
				forwardToView(view, event.getType(), result);
			}
			
		});
	}
	
	/**
	 * 
	 * @param event
	 * @throws Exception
	 */
	private void check(final AppEvent event) throws Exception {
		String filter = "sender.id=='"+event.getData("id").toString()+"'";
		FriendServiceAsync friendService = Registry.get(FriendService.class.getName());
		friendService.show(filter, new AsyncCallback<Collection<Friend>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Collection<Friend> result) {
				// TODO Auto-generated method stub
				forwardToView(view, event.getType(), result);
			}
			
		});
	}
	
	/**
	 * 
	 * @param event
	 * @throws Exception
	 */
	private void add(final AppEvent event) throws Exception {
		Friend friend = new Friend();
		friend.setReceiver((User) event.getData("receiver"));
		friend.setSender((User) event.getData("sender"));
		friend.setType((String) event.getData("type"));
		FriendServiceAsync friendService = Registry.get(FriendService.class.getName());
		friendService.create(friend, new AsyncCallback<Friend>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Friend result) {
				// TODO Auto-generated method stub
				forwardToView(view, event.getType(), result);
			}
			
		});
	}
	
	/**
	 * 
	 * @param event
	 * @throws Exception
	 */
	private void delete(final AppEvent event) throws Exception {
		String filter = "id=='"+event.getData("id").toString()+"'";
		FriendServiceAsync friendService = Registry.get(FriendService.class.getName());
		friendService.delete(filter, new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Boolean result) {
				// TODO Auto-generated method stub
				forwardToView(view, event.getType(), result);
			}
			
		});
	}
	
	/**
	 * 
	 * @param event
	 * @throws Exception
	 */
	private void agree(final AppEvent event) throws Exception {
		Friend friend = event.getData("friend");
		friend.setDecided(true);
		FriendServiceAsync friendService = Registry.get(FriendService.class.getName());
		friendService.update(friend, new AsyncCallback<Friend>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Friend result) {
				// TODO Auto-generated method stub
				forwardToView(view, event.getType(), result);
			}
			
		});
	}
	
	/**
	 * 
	 * @param event
	 * @throws Exception
	 */
	private void disagree(final AppEvent event) throws Exception {
		FriendServiceAsync friendService = Registry.get(FriendService.class.getName());
		friendService.delete("id=='"+event.getData("id").toString()+"'", new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Boolean result) {
				// TODO Auto-generated method stub
				forwardToView(view, event.getType(), result);
			}
			
		});
	}

}
