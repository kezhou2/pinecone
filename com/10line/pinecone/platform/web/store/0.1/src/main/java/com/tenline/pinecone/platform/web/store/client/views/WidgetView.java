/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.views;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.mvc.View;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.tenline.pinecone.platform.model.Application;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.store.client.events.ConsumerEvents;
import com.tenline.pinecone.platform.web.store.client.events.WidgetEvents;
import com.tenline.pinecone.platform.web.store.client.widgets.AppStoreViewport;
import com.tenline.pinecone.platform.web.store.client.widgets.ConsumerShowViewport;
import com.tenline.pinecone.platform.web.store.client.widgets.FriendViewport;
import com.tenline.pinecone.platform.web.store.client.widgets.HomeViewport;
import com.tenline.pinecone.platform.web.store.client.widgets.LoginViewport;
import com.tenline.pinecone.platform.web.store.client.widgets.MailListViewport;
import com.tenline.pinecone.platform.web.store.client.widgets.RegisterViewport;
import com.tenline.pinecone.platform.web.store.client.widgets.SendMailViewport;

/**
 * @author Bill
 *
 */
public class WidgetView extends View {

	/**
	 * @param controller
	 */
	public WidgetView(Controller controller) {
		super(controller);
	}

	@Override
	protected void handleEvent(AppEvent event) {
		try {
			if (event.getType().equals(WidgetEvents.UPDATE_LOGIN_TO_PANEL)) {
				updateLoginToPanel(event);
			} else if (event.getType().equals(WidgetEvents.UPDATE_REGISTER_TO_PANEL)) {
				updateRegisterToPanel(event);
			}else if (event.getType().equals(WidgetEvents.UPDATE_USERHOME_TO_PANEL)) {
				updateUserHomeToPanel(event);
			}else if (event.getType().equals(WidgetEvents.UPDATE_FRIENDS_MANAGE_TO_PANEL)) {
				updateFriendManageToPanel(event);
			}else if (event.getType().equals(WidgetEvents.UPDATE_SEND_MAIL_TO_PANEL)) {
				updateSendMailToPanel(event);
			}else if (event.getType().equals(WidgetEvents.UPDATE_MAX_PORTLET_TO_PANEL)) {
				updateMaxPortletToPanel(event);
			}else if (event.getType().equals(WidgetEvents.UPDATE_MAIL_LIST_TO_PANEL)) {
				updateMailListToPanel(event);
			}else if (event.getType().equals(WidgetEvents.UPDATE_APP_STORE_TO_PANEL)) {
				updateAppStoreToPanel(event);
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
	private void updateLoginToPanel(AppEvent event) throws Exception {
		System.out.println("WidgetView updateLoginToPanel");
		RootPanel.get().clear();
		RootPanel.get().add((Widget) Registry.get(LoginViewport.class.getName()));
	}
	
	/**
	 * 
	 * @param event
	 * @throws Exception
	 */
	private void updateRegisterToPanel(AppEvent event) throws Exception {
		System.out.println("WidgetView updateRegisterToPanel");
		RootPanel.get().clear();
		RootPanel.get().add((Widget) Registry.get(RegisterViewport.class.getName()));
	}
	
	/**
	 * 
	 * @param event
	 * @throws Exception
	 */
	private void updateUserHomeToPanel(AppEvent event) throws Exception {
		System.out.println("WidgetView updateUserHomeToPanel");
		RootPanel.get().clear();
		HomeViewport hv = Registry.get(HomeViewport.class.getName());
		hv.loadInfo();
		RootPanel.get().add(hv);
	}
	
	private void updateFriendManageToPanel(AppEvent event) throws Exception{
		System.out.println("WidgetView updateFriendManageToPanel");
		RootPanel.get().clear();
		FriendViewport fv = Registry.get(FriendViewport.class.getName());
		fv.loadInfo();
		RootPanel.get().add(fv);
	}
	
	private void updateSendMailToPanel(AppEvent event) throws Exception{
		User receiver = (User)event.getData("friend");
		RootPanel.get().clear();
		SendMailViewport smv = Registry.get(SendMailViewport.class.getName());
		smv.loadContactInfo(receiver);
		RootPanel.get().add(smv);
	}
	
	private void updateMaxPortletToPanel(AppEvent event) throws Exception{
		Application application = (Application)event.getData("application");
		ConsumerShowViewport cv = (ConsumerShowViewport)Registry.get(ConsumerShowViewport.class.getName());
		cv.loadConsumer(application);
		RootPanel.get().clear();
		RootPanel.get().add(cv);
	}

	private void updateMailListToPanel(AppEvent event) throws Exception{
		MailListViewport mv = (MailListViewport)Registry.get(MailListViewport.class.getName());
		RootPanel.get().clear();
		RootPanel.get().add(mv);
		mv.loadInfo();
	}
	
	private void updateAppStoreToPanel(AppEvent event) throws Exception{
		AppStoreViewport asv = (AppStoreViewport)Registry.get(AppStoreViewport.class.getName());
		RootPanel.get().clear();
		RootPanel.get().add(asv);
		AppEvent appEvent = new AppEvent(ConsumerEvents.GET_ALL);
		Dispatcher.get().dispatch(appEvent);
	}
}
