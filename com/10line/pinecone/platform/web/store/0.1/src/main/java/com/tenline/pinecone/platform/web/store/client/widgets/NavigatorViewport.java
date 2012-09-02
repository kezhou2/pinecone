/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.widgets;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.IconAlign;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
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
import com.tenline.pinecone.platform.web.store.client.controllers.ModelController;

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
	public void updateIdentity() {
		BeanModel owner = Registry.get(Store.CURRENT_OWNER);
		MenuItem identityItem = (MenuItem) accountButton.getMenu().getItem(0);
		identityItem.setText(owner.get("name").toString());
		identityItem.setTitle(owner.get("email").toString());
	}
	
	/**
	 * 
	 * @param invitations
	 */
	public void updateInvitation(List<BeanModel> invitations) {
		MenuItem invitationItem = (MenuItem) accountButton.getMenu().getItem(2);
		String text = ((Messages) Registry.get(Messages.class.getName())).invitation();
		invitationItem.setText(text + " (" + invitations.size() + ")");
		if (invitationItem.getSubMenu().removeAll()) {
			for (BeanModel invitation : invitations) {
				BeanModel sender = invitation.get("sender");
				invitationItem.getSubMenu().add(new MenuItem(sender.get("name").toString()));
			}		
		}
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
					HomeViewport view = Registry.get(HomeViewport.class.getName());
					view.updateIdentity();
					view.updateToRootPanel();

					Registry.register(Store.CURRENT_VIEWER, Registry.get(Store.CURRENT_OWNER));
					BeanModel viewer = (BeanModel) Registry.get(Store.CURRENT_VIEWER);
					ArrayList<String> model = new ArrayList<String>();
					model.add(Application.class.getName()); model.add("user.id=='" + viewer.get("id") + "'");
					ModelController.show(HomeViewport.VIEWER_GET_APPLICATIONS, model, view);
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
					ApplicationViewport view = Registry.get(ApplicationViewport.class.getName());
					view.updateIdentity();
					view.updateToRootPanel();
					
					ArrayList<String> model = new ArrayList<String>();
					model.add(Consumer.class.getName()); model.add("all");
					ModelController.show(ApplicationViewport.OWNER_GET_ALL_CONSUMERS, model, view);
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
					FriendViewport view = Registry.get(FriendViewport.class.getName());
					view.updateIdentity();
					view.updateToRootPanel();
					
					BeanModel owner = Registry.get(Store.CURRENT_OWNER);
					ArrayList<String> model = new ArrayList<String>();
					model.add(User.class.getName()); model.add("id!='" + owner.get("id") + "'");
					ModelController.show(FriendViewport.OWNER_GET_ALL_OTHER_USERS, model, view);
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
			setSubMenu(new Menu());
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
				public void componentSelected(MenuEvent event) {
					// TODO Auto-generated method stub
					Registry.unregister(Store.CURRENT_OWNER);
					Registry.unregister(Store.CURRENT_VIEWER);
					LoginViewport view = Registry.get(LoginViewport.class.getName());
					view.updateToRootPanel();
				}
				
			});
		}
		
	}

}
