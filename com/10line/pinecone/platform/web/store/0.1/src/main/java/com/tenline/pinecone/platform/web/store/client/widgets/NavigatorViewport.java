/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.widgets;

import java.util.ArrayList;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.IconAlign;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.extjs.gxt.ui.client.widget.menu.SeparatorMenuItem;
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
public abstract class NavigatorViewport extends AbstractViewport {

	protected AccountButton accountButton = new AccountButton();
	
	/**
	 * 
	 */
	public NavigatorViewport() {
		super();
		// TODO Auto-generated constructor stub
		accountButton.getMenu().add(new IdentityItem());
		accountButton.getMenu().add(new SeparatorMenuItem());
		accountButton.getMenu().add(new InvitationItem());
		accountButton.getMenu().add(new SeparatorMenuItem());
		accountButton.getMenu().add(new LogoutItem());
	}
	
	/**
	 * 
	 */
	public void updateIdentityToOwner() {
		BeanModel owner = Registry.get(Store.CURRENT_OWNER);
		MenuItem item = (MenuItem) accountButton.getMenu().getItem(0);
		item.setText(owner.get("name").toString());
		item.setTitle(owner.get("email").toString());
	}
	
	/**
	 * 
	 */
	public void updateIdentityToViewer() {
		BeanModel viewer = Registry.get(Store.CURRENT_VIEWER);
		MenuItem item = (MenuItem) accountButton.getMenu().getItem(0);
		item.setText(viewer.get("name").toString());
		item.setTitle(viewer.get("email").toString());
	}
	
	/**
	 * 
	 * @param number
	 */
	public void updateFriendInvitation(String number) {
		MenuItem item = (MenuItem) accountButton.getMenu().getItem(2);
		item.setText(((Messages) Registry.get(Messages.class.getName())).friendInvitation()+" ("+number+")");
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	protected class HomeButton extends Button {
		
		protected HomeButton() { 
			setWidth(60);
			setHeight(50);
			setIconAlign(IconAlign.TOP);
			setIcon(((Images) Registry.get(Images.class.getName())).home());
			setText(((Messages) Registry.get(Messages.class.getName())).Home());
			addSelectionListener(new SelectionListener<ButtonEvent>() {

				@Override
				public void componentSelected(ButtonEvent event) {
					// TODO Auto-generated method stub
					Registry.unregister(Store.CURRENT_VIEWER);
					Registry.register(Store.CURRENT_VIEWER, Registry.get(Store.CURRENT_OWNER));
					BeanModel viewer = (BeanModel) Registry.get(Store.CURRENT_VIEWER);
					ArrayList<String> application = new ArrayList<String>();
					application.add(Application.class.getName());
					application.add("user.id=='" + viewer.get("id") + "'");
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
	protected class ApplicationButton extends Button {
		
		protected ApplicationButton() { 
			setWidth(60);
			setHeight(50);
			setIconAlign(IconAlign.TOP);
			setIcon(((Images) Registry.get(Images.class.getName())).application());
			setText(((Messages) Registry.get(Messages.class.getName())).application());
			addSelectionListener(new SelectionListener<ButtonEvent>() {

				@Override
				public void componentSelected(ButtonEvent event) {
					// TODO Auto-generated method stub
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
	protected class FriendButton extends Button {
		
		protected FriendButton() {
			setWidth(60);
			setHeight(50);
			setIconAlign(IconAlign.TOP);
			setIcon(((Images) Registry.get(Images.class.getName())).friend());
			setText(((Messages) Registry.get(Messages.class.getName())).friend());
			addSelectionListener(new SelectionListener<ButtonEvent>() {

				@Override
				public void componentSelected(ButtonEvent event) {
					// TODO Auto-generated method stub
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
	private class AccountButton extends Button {
		
		private AccountButton() {
			setWidth(60);
			setHeight(50);
			setIconAlign(IconAlign.TOP);
			setIcon(((Images) Registry.get(Images.class.getName())).account());
			setText(((Messages) Registry.get(Messages.class.getName())).account());
			setMenu(new Menu());
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class IdentityItem extends MenuItem {
		
		private IdentityItem() {
//			setIcon(((Images) Registry.get(Images.class.getName())).user());
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class InvitationItem extends MenuItem {
		
		private InvitationItem() {
			setIcon(((Images) Registry.get(Images.class.getName())).invitation());
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class LogoutItem extends MenuItem {
		
		private LogoutItem() {
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

}
