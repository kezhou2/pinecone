/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.controllers;

import java.util.Collection;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.widget.Info;
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
	private FriendServiceAsync service = Registry.get(FriendService.class.getName());

	/**
	 * 
	 */
	public FriendController() {
		registerEventTypes(FriendEvents.GET_BY_USER);
		registerEventTypes(FriendEvents.GET_REQUESTS);
		registerEventTypes(FriendEvents.CHECK);
		registerEventTypes(FriendEvents.ADD);
		registerEventTypes(FriendEvents.DELETE);
		registerEventTypes(FriendEvents.SETTING);
	}

	@Override
	public void handleEvent(AppEvent event) {
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
			} else if (event.getType().equals(FriendEvents.SETTING)) {
				setting(event);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param event
	 * @throws Exception
	 */
	private void getByUser(final AppEvent event) throws Exception {
		service.show("isDecided==true&&receiver.id=='"+((User)Registry.get(User.class.getName())).getId()+"'", 
				new AsyncCallback<Collection<Friend>>() {

			@Override
			public void onFailure(Throwable caught) {
				Info.display("", caught.getMessage());
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Collection<Friend> result) {
				final Collection<Friend> temp = result;
				service.show("isDecided==true&&sender.id=='"+((User)Registry.get(User.class.getName())).getId()+"'", 
						new AsyncCallback<Collection<Friend>>() {

					@Override
					public void onFailure(Throwable caught) {
						Info.display("", caught.getMessage());
						caught.printStackTrace();
					}

					@Override
					public void onSuccess(Collection<Friend> result) {
						result.addAll(temp);
						forwardToView(view, event.getType(),result);
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
		String filter = "isDecided==false&&receiver.id=='"+((User)Registry.get(User.class.getName())).getId()+"'";
		service.show(filter, new AsyncCallback<Collection<Friend>>() {

			@Override
			public void onFailure(Throwable caught) {
				Info.display("", caught.getMessage());
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Collection<Friend> result) {
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
		String filter = "sender.id=='"+((User)Registry.get(User.class.getName())).getId()+"'";
		service.show(filter, new AsyncCallback<Collection<Friend>>() {

			@Override
			public void onFailure(Throwable caught) {
				Info.display("", caught.getMessage());
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Collection<Friend> result) {
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
		System.out.println("FriendController add");
		Friend friend = new Friend();
		friend.setReceiver((User) event.getData("receiver"));
		friend.setSender((User) Registry.get(User.class.getName()));
		friend.setType((String) event.getData("type"));
		service.create(friend, new AsyncCallback<Friend>() {

			@Override
			public void onFailure(Throwable caught) {
				Info.display("", caught.getMessage());
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Friend result) {
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
		System.out.println("FriendController delete: id="+event.getData("id"));
		String filter = event.getData("id");
		service.delete(filter, new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				Info.display("", caught.getMessage());
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Boolean result) {
				System.out.println("FriendController delete:"+result);
				forwardToView(view, event.getType(), result);
			}
			
		});
	}
	
	/**
	 * 
	 * @param event
	 * @throws Exception
	 */
	private void setting(final AppEvent event) throws Exception {
		Boolean isDecided = event.getData("isDecided");
		String type = event.getData("type");
		Friend friend = event.getData("friend");
		if (isDecided != null) friend.setDecided(isDecided);
		if (type != null) friend.setType(type);
		service.update(friend, new AsyncCallback<Friend>() {

			@Override
			public void onFailure(Throwable caught) {
				Info.display("", caught.getMessage());
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Friend result) {
				forwardToView(view, event.getType(), result);
			}
			
		});
	}
	
}
