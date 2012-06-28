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
import com.extjs.gxt.ui.client.mvc.View;
import com.google.gwt.user.client.ui.RootPanel;
import com.tenline.pinecone.platform.model.Consumer;
import com.tenline.pinecone.platform.web.store.client.events.ConsumerEvents;
import com.tenline.pinecone.platform.web.store.client.widgets.AppStoreViewPort;

/**
 * @author Bill
 *
 */
public class ConsumerView extends View {
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
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void handleEvent(AppEvent event) {
		//add by lue at 2012年6月22日17:06:52
		if(event.getType().equals(ConsumerEvents.GET_ALL)
				||event.getType().equals(ConsumerEvents.GET_BY_CATEGORY)
				||event.getType().equals(ConsumerEvents.GET_ALL)) {
			updateAppStoreGrid(event);
		}else if(event.getType().equals(ConsumerEvents.REGISTER)) {
			System.out.println(event.getData().getClass());
		}
	}

	@SuppressWarnings("unchecked")
	private void updateAppStoreGrid(AppEvent event) {
		RootPanel.get().clear();
		AppStoreViewPort appStoreViewPort = Registry.get(AppStoreViewPort.class.getName());
		RootPanel.get().add(appStoreViewPort);
		List<BeanModel> data = new ArrayList<BeanModel>();
		if(event.getData() != null) {
			for(Consumer c : (Collection<Consumer>)event.getData()) {
				BeanModel bean = consumerFactory.createModel(c);
				bean.set("detail", c.getName());
				data.add(bean);
			}
//			System.out.println(event.getData().getClass());
			System.out.println(((ArrayList<Consumer>)event.getData()).size());
		}
		appStoreViewPort.setAppGridData(data);
	}

}
