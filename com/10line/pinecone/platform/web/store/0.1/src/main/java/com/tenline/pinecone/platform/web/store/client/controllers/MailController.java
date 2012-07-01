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
import com.tenline.pinecone.platform.model.Mail;
import com.tenline.pinecone.platform.web.store.client.Store;
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
	
	private BeanModelReader reader = Registry.get(BeanModelReader.class.getName());
	private BeanModelFactory factory = Registry.get(Store.MAIL_MODEL_FACTORY);
	
	/**
	 * 
	 */
	public MailController() {
		// TODO Auto-generated constructor stub
		registerEventTypes(MailEvents.GET_UNREAD);
		registerEventTypes(MailEvents.SEND);
		registerEventTypes(MailEvents.SETTING);
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class CreateProxy extends RpcProxy<Mail> {

		@Override
		protected void load(Object loadConfig, AsyncCallback<Mail> callback) {
			// TODO Auto-generated method stub
			service.create((Mail) loadConfig, callback);
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class UpdateProxy extends RpcProxy<Mail> {

		@Override
		protected void load(Object loadConfig, AsyncCallback<Mail> callback) {
			// TODO Auto-generated method stub
			service.update((Mail) loadConfig, callback);
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class ShowProxy extends RpcProxy<Collection<Mail>> {

		@Override
		protected void load(Object loadConfig, AsyncCallback<Collection<Mail>> callback) {
			// TODO Auto-generated method stub
			service.show((String) loadConfig, callback);
		}
		
	}

	@Override
	public void handleEvent(AppEvent event) {
		// TODO Auto-generated method stub
		try {
			BeanModel model = event.getData();
			if (event.getType().equals(MailEvents.GET_UNREAD)) {
				show(event, "isRead==false&&receiver.id=='" + model.get("id") +"'");
			} else if (event.getType().equals(MailEvents.SEND)) {
				create(event, model);
			} else if (event.getType().equals(MailEvents.SETTING)) {
				update(event, model);
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
		Loader<Mail> loader = new BaseLoader<Mail>(new CreateProxy());
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
	private void update(final AppEvent event, BeanModel model) throws Exception {
		Loader<Mail> loader = new BaseLoader<Mail>(new UpdateProxy());
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
	 * @param loadConfig
	 * @throws Exception
	 */
	private void show(final AppEvent event, Object loadConfig) throws Exception {
		ListLoader<ListLoadResult<BeanModel>> loader = new BaseListLoader<ListLoadResult<BeanModel>>(new ShowProxy(), reader);
		loader.addLoadListener(new LoadListener() {
			
			@Override
			public void loaderLoad(LoadEvent loadEvent) {
				BaseListLoadResult<BeanModel> result = loadEvent.getData();
				forwardToView(view, event.getType(), result.getData());
			}
			
		});
		loader.load(loadConfig);
	}
	
}
