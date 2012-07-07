/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.views;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelFactory;
import com.extjs.gxt.ui.client.data.BeanModelLookup;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.mvc.View;
import com.google.gwt.user.client.ui.RootPanel;
import com.tenline.pinecone.platform.model.Consumer;
import com.tenline.pinecone.platform.web.store.client.Images;
import com.tenline.pinecone.platform.web.store.client.events.ConsumerEvents;
import com.tenline.pinecone.platform.web.store.client.events.WidgetEvents;
import com.tenline.pinecone.platform.web.store.client.widgets.AppStoreViewport;

/**
 * @author Bill
 *
 */
public class ConsumerView extends View {
	
	private Images images = (Images) Registry.get(Images.class.getName());
	/**
	 * 创建Consumer转换为BeanMode的工厂实例
	 * @author lue
	 */
	private BeanModelFactory consumerFactory = BeanModelLookup.get().getFactory(Consumer.class);
	
	/**
	 * @param controller
	 */
	public ConsumerView(Controller controller) {
		super(controller);
	}

	@Override
	protected void handleEvent(AppEvent event) {
		//add by lue at 2012年6月22日17:06:52
		if(event.getType().equals(ConsumerEvents.GET_ALL)){
			onGetAll(event);
		}else if(event.getType().equals(ConsumerEvents.GET_BY_CATEGORY)){
			onGetByCategory(event);
		}else if(event.getType().equals(ConsumerEvents.GET_BY_NAME)) {
			onGetByName(event);
		}else if(event.getType().equals(ConsumerEvents.REGISTER)) {
			System.out.println(event.getData().getClass());
			//TODO
			AppEvent ap = new AppEvent(WidgetEvents.UPDATE_APP_STORE_TO_PANEL);
			Dispatcher.get().dispatch(ap);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void onGetAll(AppEvent event){
		RootPanel.get().clear();
		AppStoreViewport appStoreViewPort = Registry.get(AppStoreViewport.class.getName());
		RootPanel.get().add(appStoreViewPort);
		List<BeanModel> data = new ArrayList<BeanModel>();
		if(event.getData() != null) {
			for(Consumer c : (Collection<Consumer>)event.getData()) {
				BeanModel bean = consumerFactory.createModel(c);
				bean.set("detail", c.getName());
				//TODO
				try{
				bean.set("detail", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Sed metus nibh, sodales a, porta at, vulputate eget, dui. Pellentesque ut nisl. Maecenas tortor turpis, interdum non, sodales non, iaculis ac, lacus. Vestibulum auctor, tortor quis iaculis malesuada, libero lectus bibendum purus, sit amet tincidunt quam turpis vel lacus. In pellentesque nisl non sem. Suspendisse nunc sem, pretium eget, cursus a, fringilla vel, urna.<br/><br/>Aliquam commodo ullamcorper erat. Nullam vel justo in neque porttitor laoreet. Aenean lacus dui, consequat eu, adipiscing eget, nonummy non, nisi. Morbi nunc est, dignissim non, ornare sed, luctus eu, massa. Vivamus eget quam. Vivamus tincidunt diam nec urna. Curabitur velit.");
				bean.set("icons", images.consumerIcon());
				bean.set("consumer", c);
				data.add(bean);
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
			System.out.println(((ArrayList<Consumer>)event.getData()).size());
		}
		appStoreViewPort.setAppGridData(data);
	}
	
	@SuppressWarnings("unchecked")
	public void onGetByCategory(AppEvent event){
		AppStoreViewport appStoreViewPort = Registry.get(AppStoreViewport.class.getName());
		List<BeanModel> data = new ArrayList<BeanModel>();
		if(event.getData() != null) {
			for(Consumer c : (Collection<Consumer>)event.getData()) {
				BeanModel bean = consumerFactory.createModel(c);
				bean.set("detail", c.getName());
				try{
				bean.set("detail", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Sed metus nibh, sodales a, porta at, vulputate eget, dui. Pellentesque ut nisl. Maecenas tortor turpis, interdum non, sodales non, iaculis ac, lacus. Vestibulum auctor, tortor quis iaculis malesuada, libero lectus bibendum purus, sit amet tincidunt quam turpis vel lacus. In pellentesque nisl non sem. Suspendisse nunc sem, pretium eget, cursus a, fringilla vel, urna.<br/><br/>Aliquam commodo ullamcorper erat. Nullam vel justo in neque porttitor laoreet. Aenean lacus dui, consequat eu, adipiscing eget, nonummy non, nisi. Morbi nunc est, dignissim non, ornare sed, luctus eu, massa. Vivamus eget quam. Vivamus tincidunt diam nec urna. Curabitur velit.");
				bean.set("icons", images.consumerIcon());
				bean.set("consumer", c);
				data.add(bean);
			}catch(Exception ex){
				ex.printStackTrace();
			}
			}
			System.out.println(((ArrayList<Consumer>)event.getData()).size());
		}
		appStoreViewPort.setAppGridData(data);
	}
	
	@SuppressWarnings("unchecked")
	public void onGetByName(AppEvent event){
		AppStoreViewport appStoreViewPort = Registry.get(AppStoreViewport.class.getName());
		List<BeanModel> data = new ArrayList<BeanModel>();
		if(event.getData() != null) {
			for(Consumer c : (Collection<Consumer>)event.getData()) {
				BeanModel bean = consumerFactory.createModel(c);
				bean.set("detail", c.getName());
				//TODO
				try{
				bean.set("detail", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Sed metus nibh, sodales a, porta at, vulputate eget, dui. Pellentesque ut nisl. Maecenas tortor turpis, interdum non, sodales non, iaculis ac, lacus. Vestibulum auctor, tortor quis iaculis malesuada, libero lectus bibendum purus, sit amet tincidunt quam turpis vel lacus. In pellentesque nisl non sem. Suspendisse nunc sem, pretium eget, cursus a, fringilla vel, urna.<br/><br/>Aliquam commodo ullamcorper erat. Nullam vel justo in neque porttitor laoreet. Aenean lacus dui, consequat eu, adipiscing eget, nonummy non, nisi. Morbi nunc est, dignissim non, ornare sed, luctus eu, massa. Vivamus eget quam. Vivamus tincidunt diam nec urna. Curabitur velit.");
				bean.set("icons", images.consumerIcon());
				bean.set("consumer", c);
				data.add(bean);
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
			System.out.println(((ArrayList<Consumer>)event.getData()).size());
		}
		appStoreViewPort.setAppGridData(data);
	}

}
