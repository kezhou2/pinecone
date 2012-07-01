/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.controllers;

import java.util.Collection;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.data.BaseListLoadResult;
import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.BaseLoader;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelFactory;
import com.extjs.gxt.ui.client.data.BeanModelReader;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.ListLoader;
import com.extjs.gxt.ui.client.data.LoadEvent;
import com.extjs.gxt.ui.client.data.Loader;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.LoadListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.tenline.pinecone.platform.model.Friend;
import com.tenline.pinecone.platform.web.store.client.Store;
import com.tenline.pinecone.platform.web.store.client.events.FriendEvents;
import com.tenline.pinecone.platform.web.store.client.services.FriendService;
import com.tenline.pinecone.platform.web.store.client.services.FriendServiceAsync;
import com.tenline.pinecone.platform.web.store.client.views.FriendView;

/**
 * @author Bill
 *
 */
public class FriendController extends Controller {
	
	private FriendView view = new FriendView(this);
	private FriendServiceAsync service = Registry.get(FriendService.class.getName());
	
	private BeanModelReader reader = Registry.get(BeanModelReader.class.getName());
	private BeanModelFactory factory = Registry.get(Store.FRIEND_MODEL_FACTORY);
	
	/**
	 * 
	 */
	public FriendController() {
		// TODO Auto-generated constructor stub
		registerEventTypes(FriendEvents.SEND_INVITATION);
		registerEventTypes(FriendEvents.BREAK_OFF_RELATION);
		registerEventTypes(FriendEvents.SETTING);
		registerEventTypes(FriendEvents.GET_BY_SENDER);
		registerEventTypes(FriendEvents.GET_BY_RECEIVER);
		registerEventTypes(FriendEvents.GET_INVITATIONS);
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class CreateProxy extends RpcProxy<Friend> {

		@Override
		protected void load(Object loadConfig, AsyncCallback<Friend> callback) {
			// TODO Auto-generated method stub
			service.create((Friend) loadConfig, callback);
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class DeleteProxy extends RpcProxy<Boolean> {

		@Override
		protected void load(Object loadConfig, AsyncCallback<Boolean> callback) {
			// TODO Auto-generated method stub
			service.delete((String) loadConfig, callback);
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class UpdateProxy extends RpcProxy<Friend> {

		@Override
		protected void load(Object loadConfig, AsyncCallback<Friend> callback) {
			// TODO Auto-generated method stub
			service.update((Friend) loadConfig, callback);
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class ShowBySenderProxy extends RpcProxy<Collection<Friend>> {

		@Override
		protected void load(Object loadConfig, AsyncCallback<Collection<Friend>> callback) {
			// TODO Auto-generated method stub
			service.showBySender((String) loadConfig, callback);
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class ShowByReceiverProxy extends RpcProxy<Collection<Friend>> {

		@Override
		protected void load(Object loadConfig, AsyncCallback<Collection<Friend>> callback) {
			// TODO Auto-generated method stub
			service.showByReceiver((String) loadConfig, callback);
		}
		
	}

	@Override
	public void handleEvent(AppEvent event) {
		// TODO Auto-generated method stub
		try {
			BeanModel model = event.getData();
			if (event.getType().equals(FriendEvents.SEND_INVITATION)) {
				create(event, model);
			} else if (event.getType().equals(FriendEvents.BREAK_OFF_RELATION)) {
				delete(event, model);
			} else if (event.getType().equals(FriendEvents.SETTING)) {
				update(event, model);
			} else if (event.getType().equals(FriendEvents.GET_BY_SENDER)) {
				showBySender(event, model);
			} else if (event.getType().equals(FriendEvents.GET_BY_RECEIVER)) {
				showByReceiver(event, model);
			} else if (event.getType().equals(FriendEvents.GET_INVITATIONS)) {
				showByReceiver(event, model);
			} 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param event
	 * @param model
	 * @throws Exception
	 */
	private void create(final AppEvent event, BeanModel model) throws Exception {
		Loader<Friend> loader = new BaseLoader<Friend>(new CreateProxy());
		loader.addLoadListener(new LoadListener() {
			
			@Override
			public void loaderLoad(LoadEvent loadEvent) {
				forwardToView(view, event.getType(), factory.createModel(loadEvent.getData()));
			}
			
		});
		loader.load(model.getBean());
	}
	
	/**
	 * 
	 * @param event
	 * @param model
	 * @throws Exception
	 */
	private void delete(final AppEvent event, BeanModel model) throws Exception {
		Loader<Boolean> loader = new BaseLoader<Boolean>(new DeleteProxy());
		loader.addLoadListener(new LoadListener() {
			
			@Override
			public void loaderLoad(LoadEvent loadEvent) {
				forwardToView(view, event.getType(), loadEvent.getData());
			}
			
		});
		loader.load("id=='" + model.get("id") + "'");
	}
	
	/**
	 * 
	 * @param event
	 * @param model
	 * @throws Exception
	 */
	private void update(final AppEvent event, BeanModel model) throws Exception {
		Loader<Friend> loader = new BaseLoader<Friend>(new UpdateProxy());
		loader.addLoadListener(new LoadListener() {
			
			@Override
			public void loaderLoad(LoadEvent loadEvent) {
				forwardToView(view, event.getType(), factory.createModel(loadEvent.getData()));
			}
			
		});
		loader.load(model.getBean());
	}
	
	/**
	 * 
	 * @param event
	 * @param model
	 * @throws Exception
	 */
	private void showBySender(final AppEvent event, BeanModel model) throws Exception {
		ListLoader<ListLoadResult<BeanModel>> loader = new BaseListLoader<ListLoadResult<BeanModel>>(new ShowBySenderProxy(), reader);
		loader.addLoadListener(new LoadListener() {
			
			@Override
			public void loaderLoad(LoadEvent loadEvent) {
				BaseListLoadResult<BeanModel> result = loadEvent.getData();
				forwardToView(view, event.getType(), result.getData());
			}
			
		});
		loader.load("id=='" + model.get("id") + "'");
	}
	
	/**
	 * 
	 * @param event
	 * @param model
	 * @throws Exception
	 */
	private void showByReceiver(final AppEvent event, BeanModel model) throws Exception {
		ListLoader<ListLoadResult<BeanModel>> loader = new BaseListLoader<ListLoadResult<BeanModel>>(new ShowByReceiverProxy(), reader);
		loader.addLoadListener(new LoadListener() {
			
			@Override
			public void loaderLoad(LoadEvent loadEvent) {
				BaseListLoadResult<BeanModel> result = loadEvent.getData();
				forwardToView(view, event.getType(), result.getData());
			}
			
		});
		loader.load("id=='" + model.get("id") + "'");
	}
	
}
