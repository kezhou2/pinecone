/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.widgets;

import java.util.ArrayList;

import com.extjs.gxt.ui.client.GXT;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelLookup;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.tenline.pinecone.platform.model.Application;
import com.tenline.pinecone.platform.web.store.client.Messages;
import com.tenline.pinecone.platform.web.store.client.Store;
import com.tenline.pinecone.platform.web.store.client.events.ModelEvents;

/**
 * @author Bill
 *
 */
public class ApplicationViewport extends NavigatorViewport {
	
	private ListStore<BeanModel> consumerGridStore = new ListStore<BeanModel>();

	/**
	 * 
	 */
	public ApplicationViewport() {
		super();
		// TODO Auto-generated constructor stub
		header.add(new HomeButton());
		header.add(new FriendButton());
		header.add(accountButton);
		LayoutContainer centerContainer = new LayoutContainer(new FitLayout());
		centerContainer.add(new ConsumerPanel(), new FitData(20));
		body.add(centerContainer, new BorderLayoutData(LayoutRegion.CENTER));
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class ConsumerPanel extends ContentPanel {
		
		private ConsumerPanel() {
			setHeading(((Messages) Registry.get(Messages.class.getName())).application());
			setLayout(new FitLayout());
			ArrayList<ColumnConfig> config = new ArrayList<ColumnConfig>();
			config.add(new ColumnConfig("name", ((Messages) Registry.get(Messages.class.getName())).name(), 100));
			config.add(new ColumnConfig("connectURI", ((Messages) Registry.get(Messages.class.getName())).url(), 100));
			config.add(new ColumnConfig("version", ((Messages) Registry.get(Messages.class.getName())).version(), 100));
			config.add(new ColumnConfig("install", 100));
			config.get(3).setRenderer(new GridCellRenderer<BeanModel>() {

				@Override
				public Object render(final BeanModel model, String property,
						ColumnData config, int rowIndex, int colIndex,
						ListStore<BeanModel> store, Grid<BeanModel> grid) {
					// TODO Auto-generated method stub
					return new Button(((Messages) Registry.get(Messages.class.getName())).install(), new SelectionListener<ButtonEvent>() {

						@Override
						public void componentSelected(ButtonEvent event) {
							// TODO Auto-generated method stub
							BeanModel application = BeanModelLookup.get().getFactory(Application.class).createModel(new Application());
							application.set("status", Application.CLOSED);
							application.set("consumer", model.getBean());
							application.set("user", Registry.get(Store.CURRENT_OWNER));
							showInstallDialog(application);
						}
						
					});
				}
				
			});
			add(new ConsumerGrid(new ColumnModel(config)));
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class ConsumerGrid extends Grid<BeanModel> {
		
		private ConsumerGrid(ColumnModel model) {
			super(consumerGridStore, model);
			consumerGridStore.setModelComparer(new SimpleModelComparer<BeanModel>());
			getView().setAutoFill(true);
		}
		
	}
	
	/**
	 * 
	 * @param consumer
	 */
	public void updateConsumerToGrid(BeanModel consumer) {
		if (!consumerGridStore.contains(consumer)) {
			consumerGridStore.add(consumer);
			consumerGridStore.commitChanges();
		}
	}
	
	/**
	 * 
	 * @param model
	 */
	private void showInstallDialog(final BeanModel model) {
		MessageBox msgBox = new MessageBox();
		msgBox.setMessage(((Messages) Registry.get(Messages.class.getName())).applicationInstallMessage());
		msgBox.setTitle(((Messages) Registry.get(Messages.class.getName())).applicationInstallTitle());
		msgBox.setButtons(MessageBox.OKCANCEL);
		msgBox.setIcon(MessageBox.QUESTION);
		msgBox.addCallback(new Listener<MessageBoxEvent>() {

			@Override
			public void handleEvent(MessageBoxEvent msgEvent) {
				// TODO Auto-generated method stub
				if (msgEvent.getButtonClicked().getText().equals(GXT.MESSAGES.messageBox_ok())) {
					Dispatcher.get().dispatch(ModelEvents.INSTALL_APPLICATION, model);
				}
			}
			
		});
		msgBox.show();
	}

}
