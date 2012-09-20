/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.views;

import java.util.ArrayList;
import java.util.Collection;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelFactory;
import com.extjs.gxt.ui.client.data.BeanModelLookup;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.mvc.View;
import com.tenline.pinecone.platform.model.Mail;
import com.tenline.pinecone.platform.web.store.client.controllers.MailController;
import com.tenline.pinecone.platform.web.store.client.events.MailEvents;
import com.tenline.pinecone.platform.web.store.client.widgets.HomeViewport;
import com.tenline.pinecone.platform.web.store.client.widgets.MailListViewport;
import com.tenline.pinecone.platform.web.store.client.widgets.ReadMailViewport;

/**
 * @author Bill
 *
 */
public class MailView extends View {

	private BeanModelFactory mailFactory = BeanModelLookup.get().getFactory(Mail.class);
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
					
			} else if (event.getType().equals(MailEvents.READ)) {
				onRead(event);	
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
		Collection<BeanModel> models = new ArrayList<BeanModel>();
		for(Mail m:mails){
			BeanModel bm = mailFactory.createModel(m);
			bm.set(MailController.MAIL_INSTANCE, m);
			models.add(bm);
		}
		view.loadMails(models);
	}
	
	public void onSetting(AppEvent event){
		AppEvent appEvent = new AppEvent(MailEvents.GET_UNREAD);
		Dispatcher.get().dispatch(appEvent);
	}
	
	public void onRead(AppEvent event){
		ReadMailViewport view = (ReadMailViewport)Registry.get(ReadMailViewport.class.getName());
		Mail mail = (Mail)event.getData();
		BeanModel bm = mailFactory.createModel(mail);
		bm.set(MailController.MAIL_INSTANCE, mail);
		view.loadMail(bm);
	}

}
