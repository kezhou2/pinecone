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
import com.tenline.pinecone.platform.model.Category;
import com.tenline.pinecone.platform.model.Consumer;
import com.tenline.pinecone.platform.web.store.client.Messages;
import com.tenline.pinecone.platform.web.store.client.events.ConsumerEvents;
import com.tenline.pinecone.platform.web.store.client.services.ConsumerService;
import com.tenline.pinecone.platform.web.store.client.services.ConsumerServiceAsync;
import com.tenline.pinecone.platform.web.store.client.views.ConsumerView;
import com.tenline.pinecone.platform.web.store.client.widgets.AbstractViewport;

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
		registerEventTypes(ConsumerEvents.GET_ALL);
		registerEventTypes(ConsumerEvents.GET_BY_NAME);
	}

	@Override
	public void handleEvent(AppEvent event) {
		try {
			mask();
			
			if (event.getType().equals(ConsumerEvents.GET_BY_CATEGORY)) {
				getByCategory(event);
			} else if (event.getType().equals(ConsumerEvents.REGISTER)) {
				register(event);
			} else if (event.getType().equals(ConsumerEvents.UNREGISTER)) {
				unregister(event);
			} else if (event.getType().equals(ConsumerEvents.SETTING)) {
				setting(event);
			} else if (event.getType().equals(ConsumerEvents.GET_ALL)) {
				getAll(event);
			} else if (event.getType().equals(ConsumerEvents.GET_BY_NAME)) {
				getByName(event);
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
	private void getByCategory(final AppEvent event) throws Exception {
		service.showByCategory("id=='"+event.getData("id").toString()+"'", new AsyncCallback<Collection<Consumer>>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				unmask();
			}

			@Override
			public void onSuccess(Collection<Consumer> result) {
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
	private void register(final AppEvent event) throws Exception {
		Consumer consumer = new Consumer();
		consumer.setCategory((Category) event.getData("category"));
		consumer.setConnectURI((String) event.getData("connectURI"));
		consumer.setName((String) event.getData("name"));
		consumer.setAlias((String) event.getData("alias"));
		consumer.setVersion((String) event.getData("version"));
		Object obj = event.getData("icon");
		byte[] icon = null;
		if(obj instanceof byte[]){
			icon = (byte[])obj;
			consumer.setIcon(icon);
		}
		
		service.create(consumer, new AsyncCallback<Consumer>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				unmask();
			}

			@Override
			public void onSuccess(Consumer result) {
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
	private void unregister(final AppEvent event) throws Exception {
		service.delete("id=='"+event.getData("id").toString()+"'", new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				unmask();
			}

			@Override
			public void onSuccess(Boolean result) {
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
		String connectURI = event.getData("connectURI");
		String name = event.getData("name");
		Consumer consumer = event.getData("consumer");
		if (connectURI != null) consumer.setConnectURI(connectURI);
		if (name != null) consumer.setName(name);
		service.update(consumer, new AsyncCallback<Consumer>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				unmask();
			}

			@Override
			public void onSuccess(Consumer result) {
				unmask();
				forwardToView(view, event.getType(), result);
			}
			
		});
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
				unmask();
			}

			@Override
			public void onSuccess(Collection<Consumer> result) {
				unmask();
				forwardToView(view, ConsumerEvents.GET_ALL, result);
			}
		});
	}
	
	/**
	 * @author lue
	 * @param event
	 */
	private void getByName(AppEvent event) {
		service.show("name=='"+event.getData("name")+"'", new AsyncCallback<Collection<Consumer>>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				unmask();
			}

			@Override
			public void onSuccess(Collection<Consumer> result) {
				unmask();
				forwardToView(view, ConsumerEvents.GET_ALL, result);
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
