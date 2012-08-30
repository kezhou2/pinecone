/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.widgets;

import java.util.ArrayList;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.CenterLayout;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.ui.RootPanel;
import com.tenline.pinecone.platform.model.Application;
import com.tenline.pinecone.platform.model.Consumer;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.store.client.Images;
import com.tenline.pinecone.platform.web.store.client.Messages;
import com.tenline.pinecone.platform.web.store.client.Store;
import com.tenline.pinecone.platform.web.store.client.events.ModelEvents;

/**
 * @author Bill
 *
 */
public abstract class AbstractViewport extends Viewport {
	
	protected Navigator navigator = new Navigator();
	
	protected Header header = new Header();
	protected Body body = new Body();
	protected Footer footer = new Footer();

	/**
	 * 
	 */
	public AbstractViewport() {
		// TODO Auto-generated constructor stub
		setLayout(new BorderLayout());
		add(header, new BorderLayoutData(LayoutRegion.NORTH, 60));
		add(body, new BorderLayoutData(LayoutRegion.CENTER));
		add(footer, new BorderLayoutData(LayoutRegion.SOUTH, 50));
	}
	
	/**
	 * 
	 */
	public void updateToRootPanel() {
		RootPanel.get().clear();
		RootPanel.get().add(this);
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	protected class HomeItem extends MenuItem {
		
		protected HomeItem() { 
			setIcon(((Images) Registry.get(Images.class.getName())).home());
			setText(((Messages) Registry.get(Messages.class.getName())).Home());
			addSelectionListener(new SelectionListener<MenuEvent>() {

				@Override
				public void componentSelected(MenuEvent event) {
					// TODO Auto-generated method stub
					HomeViewport view = Registry.get(HomeViewport.class.getName());
					view.updateToRootPanel();
					ArrayList<String> application = new ArrayList<String>();
					application.add(Application.class.getName());
					application.add("user.id=='" + ((BeanModel) Registry.get(Store.CURRENT_USER)).get("id") + "'");
					Dispatcher.get().dispatch(ModelEvents.GET_APPLICATION_BY_USER, application);
				}
				
			}); 
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	protected class ApplicationItem extends MenuItem {
		
		protected ApplicationItem() { 
			setIcon(((Images) Registry.get(Images.class.getName())).application());
			setText(((Messages) Registry.get(Messages.class.getName())).application());
			addSelectionListener(new SelectionListener<MenuEvent>() {

				@Override
				public void componentSelected(MenuEvent event) {
					// TODO Auto-generated method stub
					ApplicationViewport view = Registry.get(ApplicationViewport.class.getName());
					view.updateToRootPanel();
					ArrayList<String> model = new ArrayList<String>();
					model.add(Consumer.class.getName()); model.add("all");
					Dispatcher.get().dispatch(ModelEvents.GET_ALL_CONSUMER, model);
				}
				
			}); 
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	protected class FriendItem extends MenuItem {
		
		protected FriendItem() {
			setIcon(((Images) Registry.get(Images.class.getName())).friend());
			setText(((Messages) Registry.get(Messages.class.getName())).friend());
			addSelectionListener(new SelectionListener<MenuEvent>() {

				@Override
				public void componentSelected(MenuEvent event) {
					// TODO Auto-generated method stub
					FriendViewport view = Registry.get(FriendViewport.class.getName());
					view.updateToRootPanel();
					ArrayList<String> model = new ArrayList<String>();
					model.add(User.class.getName()); model.add("all");
					Dispatcher.get().dispatch(ModelEvents.GET_ALL_USER, model);
				}
				
			});
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	protected class LogoutItem extends MenuItem {
		
		protected LogoutItem() {
			setIcon(((Images) Registry.get(Images.class.getName())).logout());
			setText(((Messages) Registry.get(Messages.class.getName())).logout());
			addSelectionListener(new SelectionListener<MenuEvent>() {

				@Override
				public void componentSelected(MenuEvent ce) {
					// TODO Auto-generated method stub
					Dispatcher.get().dispatch(ModelEvents.LOGOUT_USER);
				}
				
			});
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	protected class Navigator extends Button {
		
		protected Navigator() {
			setWidth(40);
			setHeight(35);
			setIcon(((Images) Registry.get(Images.class.getName())).navigator());
			setMenu(new Menu());
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	protected class Header extends ToolBar {
		
		protected Header() {
			Button logo = new Button();
			logo.setIcon(((Images) Registry.get(Images.class.getName())).logo());
			logo.setWidth(260);
			logo.setHeight(55);
			add(logo);
			add(new FillToolItem());
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	protected class Body extends ContentPanel {
		
		protected Body() {
			setHeaderVisible(false);
			setLayout(new BorderLayout());
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	protected class Footer extends ToolBar {
		
		protected Footer() {
			setLayout(new CenterLayout());
			add(new LabelField(((Messages) Registry.get(Messages.class.getName())).copyright()));
		}
		
	}

}
