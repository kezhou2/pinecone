/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.controllers;

import java.util.Collection;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.tenline.pinecone.platform.model.Category;
import com.tenline.pinecone.platform.model.Consumer;
import com.tenline.pinecone.platform.web.store.client.events.ConsumerEvents;
import com.tenline.pinecone.platform.web.store.client.services.ConsumerService;
import com.tenline.pinecone.platform.web.store.client.services.ConsumerServiceAsync;
import com.tenline.pinecone.platform.web.store.client.views.ConsumerView;

/**
 * @author Bill
 *
 */
public class ConsumerController extends Controller {

	private ConsumerView view = new ConsumerView(this);
	private ConsumerServiceAsync service = Registry.get(ConsumerService.class.getName());
	
	/**
	 * 
	 */
	public ConsumerController() {
		registerEventTypes(ConsumerEvents.GET_BY_CATEGORY);
		registerEventTypes(ConsumerEvents.REGISTER);
		registerEventTypes(ConsumerEvents.UNREGISTER);
		registerEventTypes(ConsumerEvents.SETTING);
		//add by lue at 2012年6月22日16:56:40
		registerEventTypes(ConsumerEvents.GET_ALL);
	}

	@Override
	public void handleEvent(AppEvent event) {
		try {
			if (event.getType().equals(ConsumerEvents.GET_BY_CATEGORY)) {
				getByCategory(event);
			} else if (event.getType().equals(ConsumerEvents.REGISTER)) {
				register(event);
			} else if (event.getType().equals(ConsumerEvents.UNREGISTER)) {
				unregister(event);
			} else if (event.getType().equals(ConsumerEvents.SETTING)) {
				setting(event);
			} 
			//add by lue at 2012年6月22日16:56:40
			else if(event.getType().equals(ConsumerEvents.GET_ALL)) {
				getAll(event);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取所有Consumer
	 * @author lue
	 * @param event
	 */
	private void getAll(AppEvent event) throws Exception{
		service.show("all", new AsyncCallback<Collection<Consumer>>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				try {
					throw caught;
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onSuccess(Collection<Consumer> result) {
				forwardToView(view, ConsumerEvents.GET_ALL, result);
			}
		});
	}

	/**
	 * 
	 * @param event
	 * @throws Exception
	 */
	private void getByCategory(final AppEvent event) throws Exception {
		service.showByCategory("id=='"+event.getData("id").toString()+"'", new AsyncCallback<Collection<Consumer>>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				try {
					throw caught;
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onSuccess(Collection<Consumer> result) {
				forwardToView(view, event.getType(), result);
			}
			
		});
	}
	
	/**
	 * 
	 * @param event
	 * @throws Exception
	 */
	private void register(final AppEvent event) throws Exception {
		Consumer consumer = new Consumer();
		consumer.setCategory((Category) event.getData("category"));
		consumer.setConnectURI((String) event.getData("connectURI"));
		consumer.setName((String) event.getData("name"));
		service.create(consumer, new AsyncCallback<Consumer>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Consumer result) {
				forwardToView(view, event.getType(), result);
			}
			
		});
	}
	
	/**
	 * 
	 * @param event
	 * @throws Exception
	 */
	private void unregister(final AppEvent event) throws Exception {
		service.delete("id=='"+event.getData("id").toString()+"'", new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Boolean result) {
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
		String connectURI = event.getData("connectURI");
		String name = event.getData("name");
		Consumer consumer = event.getData("consumer");
		if (connectURI != null) consumer.setConnectURI(connectURI);
		if (name != null) consumer.setName(name);
		service.update(consumer, new AsyncCallback<Consumer>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Consumer result) {
				forwardToView(view, event.getType(), result);
			}
			
		});
	}

}
