/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.controllers;

import java.util.Collection;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.tenline.pinecone.platform.model.Mail;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.store.client.events.MailEvents;
import com.tenline.pinecone.platform.web.store.client.services.MailService;
import com.tenline.pinecone.platform.web.store.client.services.MailServiceAsync;
import com.tenline.pinecone.platform.web.store.client.views.MailView;

/**
 * @author Bill
 *
 */
public class MailController extends Controller {

	private MailView view = new MailView(this);
	private MailServiceAsync service = Registry.get(MailService.class.getName());
	
	/**
	 * 
	 */
	public MailController() {
		// TODO Auto-generated constructor stub
		registerEventTypes(MailEvents.GET_UNREAD);
		registerEventTypes(MailEvents.SEND);
		registerEventTypes(MailEvents.SETTING);
	}

	@Override
	public void handleEvent(AppEvent event) {
		// TODO Auto-generated method stub
		try {
			if (event.getType().equals(MailEvents.GET_UNREAD)) {
				getUnread(event);
			} else if (event.getType().equals(MailEvents.SEND)) {
				send(event);
			} else if (event.getType().equals(MailEvents.SETTING)) {
				setting(event);
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
	private void getUnread(final AppEvent event) throws Exception {
		String filter = "isRead==false&&receiver.id=='"+((User) Registry.get(User.class.getName())).getId()+"'";
		service.show(filter, new AsyncCallback<Collection<Mail>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Collection<Mail> result) {
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
	private void send(final AppEvent event) throws Exception {
		Mail mail = new Mail();
		mail.setContent((String) event.getData("content"));
		mail.setReceiver((User) event.getData("receiver"));
		mail.setSender((User) Registry.get(User.class.getName()));
		mail.setTitle((String) event.getData("title"));
		service.create(mail, new AsyncCallback<Mail>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Mail result) {
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
	private void setting(final AppEvent event) throws Exception {
		String content = event.getData("content");
		String title = event.getData("title");
		Boolean isRead = event.getData("isRead");
		Mail mail = event.getData("mail");
		if (content != null) mail.setContent(content);
		if (isRead != null) mail.setRead(isRead);
		if (title != null) mail.setTitle(title);
		service.update(mail, new AsyncCallback<Mail>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Mail result) {
				// TODO Auto-generated method stub
				forwardToView(view, event.getType(), result);
			}
			
		});
	}

}
