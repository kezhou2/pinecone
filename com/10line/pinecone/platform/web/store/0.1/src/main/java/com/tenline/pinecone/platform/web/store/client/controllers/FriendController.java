/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.controllers;

import java.util.ArrayList;
import java.util.Collection;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.widget.Info;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.tenline.pinecone.platform.model.Friend;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.store.client.Messages;
import com.tenline.pinecone.platform.web.store.client.events.FriendEvents;
import com.tenline.pinecone.platform.web.store.client.services.FriendService;
import com.tenline.pinecone.platform.web.store.client.services.FriendServiceAsync;
import com.tenline.pinecone.platform.web.store.client.views.FriendView;
import com.tenline.pinecone.platform.web.store.client.widgets.AbstractViewport;

/**
 * @author Bill
 *
 */
public class FriendController extends Controller {
	
	public final static String FRIEND_INSTANCE = "friend_this";
	public final static String FRIEND_SENDER = "friend_sender";
	public final static String FRIEND_RECEIVER = "friend_receiver";
	public final static String FRIEND_OWNER = "friend_owner";
	public final static String FRIEND_FRIEND = "friend_friend";
	
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
		registerEventTypes(FriendEvents.INIT_MAIL_SENDER);
	}

	@Override
	public void handleEvent(AppEvent event) {
		try {
			mask();
			
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
			} else if (event.getType().equals(FriendEvents.INIT_MAIL_SENDER)) {
				initMailSender(event);
			} 
		} catch (Exception e) {
			e.printStackTrace();
			unmask();
		}
	}
	
	/**
	 * 
	 * @param event
	 * @throws Exception
	 */
	private void getByUser(final AppEvent event) throws Exception {
		//TODO decided 查询条件无效
//		service.show("decided==true&&receiver.id=='"+((User)Registry.get(User.class.getName())).getId()+"'", 
		service.show("receiver.id=='"+((User)Registry.get(User.class.getName())).getId()+"'",
				new AsyncCallback<Collection<Friend>>() {

			@Override
			public void onFailure(Throwable caught) {
				Info.display("getByUser", caught.getMessage());
				caught.printStackTrace();
				unmask();
			}

			@Override
			public void onSuccess(Collection<Friend> result) {
				 
//				System.out.println("Receiver*******************************");
//				for(Friend f:result){
//					System.out.println("id:"+f.getSender().getId());
//					System.out.println("name:"+f.getSender().getName());
//					System.out.println("password:"+f.getSender().getPassword());
//					System.out.println("email:"+f.getSender().getEmail());
//				}
				
				final Collection<Friend> temp = result;
				
				System.out.println("FiendController======getByUser temp size:"+temp.size());
//				service.show("decided==true&&sender.id=='"+((User)Registry.get(User.class.getName())).getId()+"'", 
				service.show("sender.id=='"+((User)Registry.get(User.class.getName())).getId()+"'", 
						new AsyncCallback<Collection<Friend>>() {

					@Override
					public void onFailure(Throwable caught) {
						Info.display("getByUser", caught.getMessage());
						caught.printStackTrace();
						unmask();
					}

					@Override
					public void onSuccess(Collection<Friend> result) {
						
//						System.out.println("sender*******************************");
//						for(Friend f:result){
//							System.out.println("id:"+f.getReceiver().getId());
//							System.out.println("name:"+f.getReceiver().getName());
//							System.out.println("password:"+f.getReceiver().getPassword());
//							System.out.println("email:"+f.getReceiver().getEmail());
//						}
						System.out.println("FiendController======getByUser result size:"+result.size());
						unmask();
						result.addAll(temp);
						
						Collection<Friend> data = new ArrayList<Friend>();
						for(Friend f:result){
							if(f.getDecided())
								data.add(f);
						}
						
						System.out.println("FiendController======getByUser size:"+temp.size());
						forwardToView(view, event.getType(),data);
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
	private void initMailSender(final AppEvent event) throws Exception {
		//TODO decided 查询条件无效 
		service.show("receiver.id=='"+((User)Registry.get(User.class.getName())).getId()+"'",
				new AsyncCallback<Collection<Friend>>() {

			@Override
			public void onFailure(Throwable caught) {
				Info.display("getByUser", caught.getMessage());
				caught.printStackTrace();
				unmask();
			}

			@Override
			public void onSuccess(Collection<Friend> result) {

				final Collection<Friend> temp = result;
//				service.show("decided==true&&sender.id=='"+((User)Registry.get(User.class.getName())).getId()+"'", 
				service.show("sender.id=='"+((User)Registry.get(User.class.getName())).getId()+"'", 
						new AsyncCallback<Collection<Friend>>() {

					@Override
					public void onFailure(Throwable caught) {
						Info.display("getByUser", caught.getMessage());
						caught.printStackTrace();
						unmask();
					}

					@Override
					public void onSuccess(Collection<Friend> result) {
						unmask();
						result.addAll(temp);
						
						Collection<Friend> data = new ArrayList<Friend>();
						for(Friend f:result){
							if(f.getDecided())
								data.add(f);
						}
						
						forwardToView(view, event.getType(),data);
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
		System.out.println("FriendController getRequests");
		//TODO decided 查询条件有问题
//		String filter = "decided=="+Friend.NO_DECIDED+"&&receiver.id=='"+((User)Registry.get(User.class.getName())).getId()+"'";
//		String filter = "decided==false&&receiver.id=='"+((User)Registry.get(User.class.getName())).getId()+"'";
		String filter = "receiver.id=='"+((User)Registry.get(User.class.getName())).getId()+"'";
//		System.out.println("FriendController getRequests filter:"+filter);
		service.show(filter, new AsyncCallback<Collection<Friend>>() {

			@Override
			public void onFailure(Throwable caught) {
				Info.display("getRequests", caught.getMessage());
				caught.printStackTrace();
				unmask();
			}

			@Override
			public void onSuccess(Collection<Friend> result) {
				unmask();
				Collection<Friend> data = new ArrayList<Friend>();
				for(Friend f:result){
					if(!f.getDecided())
						data.add(f);
				}
//				System.out.println("FriendController getRequests size:"+data.size());
				forwardToView(view, event.getType(), data);
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
				Info.display("check", caught.getMessage());
				caught.printStackTrace();
				unmask();
			}

			@Override
			public void onSuccess(Collection<Friend> result) {
				unmask();
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
		friend.setReceiver((User) event.getData(FRIEND_RECEIVER));
		friend.setSender((User) Registry.get(User.class.getName()));
		friend.setType((String) event.getData("type"));
		friend.setDecided(false);
		service.create(friend, new AsyncCallback<Friend>() {

			@Override
			public void onFailure(Throwable caught) {
				Info.display("add", caught.getMessage());
				caught.printStackTrace();
				unmask();
			}

			@Override
			public void onSuccess(Friend result) {
				unmask();
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
		Friend friend = event.getData(FRIEND_INSTANCE);
		service.delete(friend, new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				Info.display("delete", caught.getMessage());
				caught.printStackTrace();
				unmask();
			}

			@Override
			public void onSuccess(Boolean result) {
				unmask();
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
		System.out.println("FriendController setting");
		Boolean decided = event.getData("decided");
		System.out.println("######decide="+decided);
		String type = event.getData("type");
		Friend friend = event.getData(FRIEND_INSTANCE);
		if (decided != null) friend.setDecided(decided);
		if (type != null) friend.setType(type);
		service.update(friend, new AsyncCallback<Friend>() {

			@Override
			public void onFailure(Throwable caught) {
				Info.display("setting", caught.getMessage());
				caught.printStackTrace();
				unmask();
			}

			@Override
			public void onSuccess(Friend result) {
				unmask();
				forwardToView(view, event.getType(), result);
			}
			
		});
	}
	/**
	 * unmask the viewport
	 */
	private void unmask(){
		if(RootPanel.get().getWidgetCount() > 0){
			AbstractViewport av = (AbstractViewport)(RootPanel.get().getWidget(0));
			av.unmask();
		}
	}
	/**
	 * mask the viewport
	 */
	private void mask(){
		if(RootPanel.get().getWidgetCount() > 0){
			AbstractViewport av = (AbstractViewport)(RootPanel.get().getWidget(0));
			av.mask(((Messages) Registry.get(Messages.class.getName())).loadingInfo());
		}
	}
}
