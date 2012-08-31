/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.views;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.mvc.View;
import com.google.gwt.user.client.ui.RootPanel;
import com.tenline.pinecone.platform.model.Application;
import com.tenline.pinecone.platform.model.Friend;
import com.tenline.pinecone.platform.web.store.client.Store;
import com.tenline.pinecone.platform.web.store.client.events.ModelEvents;
import com.tenline.pinecone.platform.web.store.client.widgets.ApplicationViewport;
import com.tenline.pinecone.platform.web.store.client.widgets.FriendViewport;
import com.tenline.pinecone.platform.web.store.client.widgets.HomeViewport;
import com.tenline.pinecone.platform.web.store.client.widgets.LoginViewport;
import com.tenline.pinecone.platform.web.store.client.widgets.NavigatorViewport;

/**
 * @author Bill
 *
 */
public class ModelView extends View {

	/**
	 * @param controller
	 */
	public ModelView(Controller controller) {
		super(controller);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void handleEvent(AppEvent event) {
		// TODO Auto-generated method stub
		if (event.getType().equals(ModelEvents.REGISTER_USER)) {
			registerUser();
		} else if (event.getType().equals(ModelEvents.REGISTER_CONSUMER)) {
			registerConsumer();
		} else if (event.getType().equals(ModelEvents.LOGIN_USER)) {
			loginUser(event);
		} else if (event.getType().equals(ModelEvents.LOGOUT_USER)) {
			logoutUser();
		} else if (event.getType().equals(ModelEvents.GET_ALL_USER)) {
			getAllUser(event);
		} else if (event.getType().equals(ModelEvents.GET_ALL_CONSUMER)) {
			getAllConsumer(event);
		} else if (event.getType().equals(ModelEvents.GET_APPLICATION_BY_USER)) {
			getApplicationByUser(event);
		} else if (event.getType().equals(ModelEvents.GET_FRIEND_BY_RECEIVER)) {
			getFriendByReceiver(event);
		} else if (event.getType().equals(ModelEvents.GET_FRIEND_BY_SENDER)) {
			getFriendBySender(event);
		} else if (event.getType().equals(ModelEvents.GET_INVITATION_BY_RECEIVER)) {
			getInvitationByReceiver(event);
		}
	}
	
	/**
	 * 
	 * @param event
	 */
	private void updateConsumerToGrid(AppEvent event) {
		ApplicationViewport view = Registry.get(ApplicationViewport.class.getName());
		List<BeanModel> consumers = event.getData();
		for (BeanModel consumer : consumers) {
			view.updateConsumerToGrid(consumer);	
		}
	}
	
	/**
	 * 
	 * @param event
	 */
	private void updateUserToGrid(AppEvent event) {
		FriendViewport view = Registry.get(FriendViewport.class.getName());
		List<BeanModel> users = event.getData();
		for (BeanModel user : users) {
			if (!user.get("id").equals(((BeanModel) Registry.get(Store.CURRENT_OWNER)).get("id"))){
				view.updateUserToGrid(user);	
			}
		}
	}
	
	/**
	 * 
	 * @param event
	 */
	private void updateApplicationToList(AppEvent event) {
		HomeViewport view = Registry.get(HomeViewport.class.getName());
		List<BeanModel> applications = event.getData();
		for (BeanModel application : applications) {
			view.updateApplicationToList((BeanModel) application.get("consumer"));
			if (applications.indexOf(application) == 0) {
				view.updateApplicationToConsole((BeanModel) application.get("consumer"));
			}
		}
	}
	
	/**
	 * 
	 * @param event
	 * @param role
	 */
	private void updateFriendToList(AppEvent event, String role) {
		HomeViewport view = Registry.get(HomeViewport.class.getName());
		List<BeanModel> friends = event.getData();
		for (BeanModel friend : friends) {
			if (friend.get("decided")) {
				view.updateFriendToList((BeanModel) friend.get(role));	
			}	
		}
	}
	
	/**
	 * 
	 */
	private void registerUser() {
		LoginViewport view = Registry.get(LoginViewport.class.getName());
		view.updateToRootPanel();
	}
	
	/**
	 * 
	 */
	private void registerConsumer() {
		LoginViewport view = Registry.get(LoginViewport.class.getName());
		view.updateToRootPanel();
	}
	
	/**
	 * 
	 * @param event
	 */
	private void loginUser(AppEvent event) {
		List<BeanModel> models = event.getData();
		if (models.size() == 1) {
			BeanModel model = models.get(0);
			Registry.register(Store.CURRENT_OWNER, model);
			Registry.register(Store.CURRENT_VIEWER, model);
			ArrayList<String> application = new ArrayList<String>();
			application.add(Application.class.getName());
			application.add("user.id=='" + model.get("id") + "'");
			Dispatcher.get().dispatch(ModelEvents.GET_APPLICATION_BY_USER, application);
		} else {
			LoginViewport view = Registry.get(LoginViewport.class.getName());
			view.showErrorDialog();
		}
	}
	
	/**
	 * 
	 */
	private void logoutUser() {
		Registry.unregister(Store.CURRENT_OWNER);
		Registry.unregister(Store.CURRENT_VIEWER);
		LoginViewport view = Registry.get(LoginViewport.class.getName());
		view.updateToRootPanel();
	}
	
	/**
	 * 
	 * @param event
	 */
	private void getAllUser(AppEvent event) {
		updateUserToGrid(event);
		FriendViewport view = Registry.get(FriendViewport.class.getName());
		view.updateIdentityToOwner();
		view.updateToRootPanel();
	}
	
	/**
	 * 
	 * @param event
	 */
	private void getAllConsumer(AppEvent event) {
		updateConsumerToGrid(event);
		ApplicationViewport view = Registry.get(ApplicationViewport.class.getName());
		view.updateIdentityToOwner();
		view.updateToRootPanel();
	}
	
	/**
	 * 
	 * @param event
	 */
	private void getApplicationByUser(AppEvent event) {
		updateApplicationToList(event);
		BeanModel model = Registry.get(Store.CURRENT_VIEWER);
		ArrayList<String> friend = new ArrayList<String>();
		friend.add(Friend.class.getName());
		friend.add("sender.id=='" + model.get("id") + "'");
		Dispatcher.get().dispatch(ModelEvents.GET_FRIEND_BY_SENDER, friend);
	}
	
	/**
	 * 
	 * @param event
	 */
	private void getFriendByReceiver(AppEvent event) {
		updateFriendToList(event, "sender");
		HomeViewport view = Registry.get(HomeViewport.class.getName());
		view.updateIdentityToViewer();
		view.updateToRootPanel();
	}
	
	/**
	 * 
	 * @param event
	 */
	private void getFriendBySender(AppEvent event) {
		updateFriendToList(event, "receiver");
		BeanModel model = Registry.get(Store.CURRENT_VIEWER);
		ArrayList<String> friend = new ArrayList<String>();
		friend.add(Friend.class.getName());
		friend.add("receiver.id=='" + model.get("id") + "'");
		Dispatcher.get().dispatch(ModelEvents.GET_FRIEND_BY_RECEIVER, friend);
	}
	
	/**
	 * 
	 * @param event
	 */
	private void getInvitationByReceiver(AppEvent event) {
		List<BeanModel> models = event.getData();
		NavigatorViewport view = (NavigatorViewport) RootPanel.get().getWidget(0);
		view.updateFriendInvitation(String.valueOf(models.size()));
	}

}
