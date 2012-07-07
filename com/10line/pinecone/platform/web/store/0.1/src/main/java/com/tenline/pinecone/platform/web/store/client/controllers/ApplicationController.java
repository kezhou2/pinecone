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
import com.tenline.pinecone.platform.model.Application;
import com.tenline.pinecone.platform.model.Consumer;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.store.client.Messages;
import com.tenline.pinecone.platform.web.store.client.events.ApplicationEvents;
import com.tenline.pinecone.platform.web.store.client.services.ApplicationService;
import com.tenline.pinecone.platform.web.store.client.services.ApplicationServiceAsync;
import com.tenline.pinecone.platform.web.store.client.views.ApplicationView;
import com.tenline.pinecone.platform.web.store.client.widgets.AbstractViewport;

/**
 * @author Bill
 *
 */
public class ApplicationController extends Controller {

	private ApplicationView view = new ApplicationView(this);
	private ApplicationServiceAsync service = Registry.get(ApplicationService.class.getName());

	/**
	 * 
	 */
	public ApplicationController() {
		registerEventTypes(ApplicationEvents.GET_BY_USER);
		registerEventTypes(ApplicationEvents.INSTALL);
		registerEventTypes(ApplicationEvents.UNINSTALL);
		registerEventTypes(ApplicationEvents.SETTING);
	}

	@Override
	public void handleEvent(AppEvent event) {
		try {
			mask();
			
			if (event.getType().equals(ApplicationEvents.GET_BY_USER)) {
				getByUser(event);
			} else if (event.getType().equals(ApplicationEvents.INSTALL)) {
				install(event);
			} else if (event.getType().equals(ApplicationEvents.UNINSTALL)) {
				uninstall(event);
			} else if (event.getType().equals(ApplicationEvents.SETTING)) {
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
	private void getByUser(final AppEvent event) throws Exception {
		String filter = "id=='"+((User) Registry.get(User.class.getName())).getId()+"'";
		service.showByUser(filter, new AsyncCallback<Collection<Application>>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				unmask();
			}

			@Override
			public void onSuccess(Collection<Application> result) {
				unmask();
				
				forwardToView(view, event.getType(), result);
				Registry.register("MyApps", result);
			}
			
		});
	}
	
	/**
	 * 
	 * @param event
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void install(final AppEvent event) throws Exception {
		Consumer consumer = (Consumer) event.getData("consumer");
		Collection<Application> list = (Collection<Application>)Registry.get("MyApps");
		for(Application a:list){
			if(a.getConsumer().getId().equals(consumer.getId())){
				unmask();
				return;
			}
		}
		
		Application application = new Application();
		application.setConsumer((Consumer) event.getData("consumer"));
		application.setDefault(false);
		application.setStatus(Application.CLOSED);
		application.setUser((User) Registry.get(User.class.getName()));
		service.create(application, new AsyncCallback<Application>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				unmask();
			}

			@Override
			public void onSuccess(Application result) {
				unmask();
				forwardToView(view, event.getType(), result);
				((Collection<Application>)Registry.get("MyApps")).add(result);
			}
			
		});
	}
	
	/**
	 * 
	 * @param event
	 * @throws Exception
	 */
	private void uninstall(final AppEvent event) throws Exception {
		System.out.println("ApplicationController uninstall:"+event.getData("id").toString());
		String filter = event.getData("id").toString();
		service.delete(filter, new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				unmask();
			}

			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(Boolean result) {
				System.out.println("result:"+result);
//				forwardToView(view, event.getType(), result);
				Collection<Application> list = (Collection<Application>)Registry.get("MyApps");
				for(Application a:list){
					if(a.getId().equals(event.getData("id").toString())){
						list.remove(a);
						unmask();
						forwardToView(view, event.getType(), list);
						break;
					}
				}
				unmask();
			}
			
		});
//		}
	}
	
	/**
	 * 
	 * @param event
	 * @throws Exception
	 */
	private void setting(final AppEvent event) throws Exception {
		Boolean isDefault = event.getData("default");
		String status = event.getData("status");
		Application application = event.getData("application");
		if (isDefault != null) application.setDefault(isDefault);
		if (status != null) application.setStatus(status);
		service.update(application, new AsyncCallback<Application>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				unmask();
			}

			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(Application result) {
				Collection<Application> list = (Collection<Application>)Registry.get("MyApps");
				unmask();
				forwardToView(view, event.getType(), list);
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
