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
import com.extjs.gxt.ui.client.mvc.View;
import com.tenline.pinecone.platform.model.Application;
import com.tenline.pinecone.platform.model.Friend;
import com.tenline.pinecone.platform.web.store.client.events.ApplicationEvents;
import com.tenline.pinecone.platform.web.store.client.widgets.HomeViewport;

/**
 * @author Bill
 *
 */
public class ApplicationView extends View {

	/**use to generate Application BeanModel*/
	private BeanModelFactory applicationFactory = BeanModelLookup.get().getFactory(Application.class);
	
	/**
	 * @param controller
	 */
	public ApplicationView(Controller controller) {
		super(controller);
	}

	@Override
	protected void handleEvent(AppEvent event) {
		try {
			if (event.getType().equals(ApplicationEvents.GET_BY_USER)) {
				onGetByUser(event);	
			} else if(event.getType().equals(ApplicationEvents.SETTING)) {
				onSetting(event);	
			} else if(event.getType().equals(ApplicationEvents.INSTALL)) {
				
			} else if(event.getType().equals(ApplicationEvents.UNINSTALL)) {
				onUninstall(event);	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void onGetByUser(AppEvent event) throws Exception{
		HomeViewport view = (HomeViewport)Registry.get(HomeViewport.class.getName());
		Collection<Application> userApps = (Collection<Application>)event.getData();
		
		Collection<BeanModel> models = new ArrayList<BeanModel>();
		for(Application app:userApps){
			BeanModel bm = applicationFactory.createModel(app);
//			System.out.println("***********************************");
//			for(String key:bm.getPropertyNames()){
//				System.out.println(key+":"+bm.get(key));
//			}
//			
//			System.out.println("&&&app consumer"+app.getConsumer());
			bm.set("thisconsumer", app.getConsumer());
			bm.set("thisapplication", app);
			models.add(bm);
		}
		
		
		view.loadApps(models);
	}
	
	@SuppressWarnings("unchecked")
	private void onSetting(AppEvent event) throws Exception{
		System.out.println("ApplicationView onSetting");
		HomeViewport view = (HomeViewport)Registry.get(HomeViewport.class.getName());
		Collection<Application> userApps = (Collection<Application>)event.getData();
		
		Collection<BeanModel> models = new ArrayList<BeanModel>();
		for(Application app:userApps){
			BeanModel bm = applicationFactory.createModel(app);
			bm.set("thisconsumer", app.getConsumer());
			bm.set("thisapplication", app);
			models.add(bm);
		}
		view.loadApps(models);
	}

	@SuppressWarnings("unchecked")
	private void onUninstall(AppEvent event) throws Exception{
		HomeViewport view = (HomeViewport)Registry.get(HomeViewport.class.getName());
		Collection<Application> userApps = (Collection<Application>)event.getData();
		
		Collection<BeanModel> models = new ArrayList<BeanModel>();
		for(Application app:userApps){
			BeanModel bm = applicationFactory.createModel(app);
			bm.set("thisconsumer", app.getConsumer());
			bm.set("thisapplication", app);
			models.add(bm);
		}
		view.loadApps(models);
	}
}
