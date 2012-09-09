/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.views;

import java.util.Collection;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.mvc.View;
import com.tenline.pinecone.platform.model.Mail;
import com.tenline.pinecone.platform.web.store.client.events.MailEvents;
import com.tenline.pinecone.platform.web.store.client.widgets.HomeViewport;
import com.tenline.pinecone.platform.web.store.client.widgets.MailListViewport;

/**
 * @author Bill
 *
 */
public class MailView extends View {

	/**
	 * @param controller
	 */
	public MailView(Controller controller) {
		super(controller);
	}

	@Override
	protected void handleEvent(AppEvent event) {
		try {
			if (event.getType().equals(MailEvents.GET_UNREAD_COUNT)) {
				onGetUnreadMailCount(event);	
			} else if (event.getType().equals(MailEvents.GET_UNREAD)) {
				onGetUnreadMail(event);	
			} else if (event.getType().equals(MailEvents.SETTING)) {
				onSetting(event);	
			} else if (event.getType().equals(MailEvents.SEND)) {
					
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void onGetUnreadMailCount(AppEvent event){
		HomeViewport view = (HomeViewport)Registry.get(HomeViewport.class.getName());
		Collection<Mail> userMails = (Collection<Mail>)event.getData();
		view.loadMails(userMails.size());
	}
	
	@SuppressWarnings("unchecked")
	public void onGetUnreadMail(AppEvent event){
		MailListViewport view = (MailListViewport)Registry.get(MailListViewport.class.getName());
		Collection<Mail> mails = (Collection<Mail>)event.getData();
		view.loadMails(mails);
	}
	
	public void onSetting(AppEvent event){
		AppEvent appEvent = new AppEvent(MailEvents.GET_UNREAD);
		Dispatcher.get().dispatch(appEvent);
	}

}
