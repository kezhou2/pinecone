/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.controllers;

import java.util.Collection;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.tenline.pinecone.platform.model.Application;
import com.tenline.pinecone.platform.model.Consumer;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.store.client.events.ApplicationEvents;
import com.tenline.pinecone.platform.web.store.client.services.ApplicationService;
import com.tenline.pinecone.platform.web.store.client.services.ApplicationServiceAsync;
import com.tenline.pinecone.platform.web.store.client.views.ApplicationView;

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
		// TODO Auto-generated constructor stub
		registerEventTypes(ApplicationEvents.GET_BY_USER);
		registerEventTypes(ApplicationEvents.INSTALL);
		registerEventTypes(ApplicationEvents.UNINSTALL);
		registerEventTypes(ApplicationEvents.SETTING);
	}

	@Override
	public void handleEvent(AppEvent event) {
		// TODO Auto-generated method stub
		try {
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
		String filter = "id=='"+((User) Registry.get(User.class.getName())).getId()+"'";
		service.showByUser(filter, new AsyncCallback<Collection<Application>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Collection<Application> result) {
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
	private void install(final AppEvent event) throws Exception {
		Application application = new Application();
		application.setConsumer((Consumer) event.getData("consumer"));
		application.setDefault(false);
		application.setStatus(Application.CLOSED);
		application.setUser((User) Registry.get(User.class.getName()));
		service.create(application, new AsyncCallback<Application>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Application result) {
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
	private void uninstall(final AppEvent event) throws Exception {
		String filter = "id=='"+event.getData("id").toString()+"'";
		service.delete(filter, new AsyncCallback<Boolean>() {

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
	private void setting(final AppEvent event) throws Exception {
		Boolean isDefault = event.getData("default");
		String status = event.getData("status");
		Application application = event.getData("application");
		if (isDefault != null) application.setDefault(isDefault);
		if (status != null) application.setStatus(status);
		service.update(application, new AsyncCallback<Application>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Application result) {
				// TODO Auto-generated method stub
				forwardToView(view, event.getType(), result);
			}
			
		});
	}

}
