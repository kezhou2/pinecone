/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.controllers;

import java.util.Collection;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.tenline.pinecone.platform.model.Mail;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.store.client.Messages;
import com.tenline.pinecone.platform.web.store.client.events.MailEvents;
import com.tenline.pinecone.platform.web.store.client.services.MailService;
import com.tenline.pinecone.platform.web.store.client.services.MailServiceAsync;
import com.tenline.pinecone.platform.web.store.client.views.MailView;
import com.tenline.pinecone.platform.web.store.client.widgets.AbstractViewport;

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
		registerEventTypes(MailEvents.GET_UNREAD_COUNT);
		registerEventTypes(MailEvents.GET_UNREAD);
		registerEventTypes(MailEvents.SEND);
		registerEventTypes(MailEvents.SETTING);
	}

	@Override
	public void handleEvent(AppEvent event) {
		
		try {
			mask();
			
			if (event.getType().equals(MailEvents.GET_UNREAD_COUNT)) {
				getUnread(event);
			} else if (event.getType().equals(MailEvents.GET_UNREAD)) {
				getUnread(event);
			} else if (event.getType().equals(MailEvents.SEND)) {
				send(event);
			} else if (event.getType().equals(MailEvents.SETTING)) {
				setting(event);
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
	private void getUnread(final AppEvent event) throws Exception {
		String filter = "isRead==false&&receiver.id=='"+((User) Registry.get(User.class.getName())).getId()+"'";
		service.show(filter, new AsyncCallback<Collection<Mail>>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				unmask();
			}

			@Override
			public void onSuccess(Collection<Mail> result) {
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
	private void send(final AppEvent event) throws Exception {
		Mail mail = new Mail();
		mail.setContent((String) event.getData("content"));
		
		User receiver = null;
		if(event.getData("receiver") instanceof User){
			receiver = (User)event.getData("receiver");
		}else if(event.getData("receiver") instanceof String){
			
		}
		
		mail.setReceiver((User) event.getData("receiver"));
		mail.setSender((User) Registry.get(User.class.getName()));
		mail.setTitle((String) event.getData("title"));
		service.create(mail, new AsyncCallback<Mail>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				unmask();
			}

			@Override
			public void onSuccess(Mail result) {
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
				caught.printStackTrace();
				unmask();
			}

			@Override
			public void onSuccess(Mail result) {
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
