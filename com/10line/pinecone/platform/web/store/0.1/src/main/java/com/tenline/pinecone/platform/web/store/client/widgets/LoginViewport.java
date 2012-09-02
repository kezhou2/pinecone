/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.widgets;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayoutData;
import com.google.gwt.user.client.ui.RootPanel;
import com.tenline.pinecone.platform.model.Application;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.store.client.Messages;
import com.tenline.pinecone.platform.web.store.client.Store;
import com.tenline.pinecone.platform.web.store.client.controllers.ModelController;

/**
 * @author Bill
 *
 */
public class LoginViewport extends AbstractViewport {
	
	public static final String USER_LOGIN_TO_SITE = "user.login.to.site";

	/**
	 * 
	 */
	public LoginViewport() {
		super();
		// TODO Auto-generated constructor stub
		LayoutContainer centerContainer = new LayoutContainer(new FitLayout());
		centerContainer.add(new VideoDemo(), new FitData(20));
		body.add(centerContainer, new BorderLayoutData(LayoutRegion.CENTER));
		LayoutContainer eastContainer = new LayoutContainer(new VBoxLayout());
		eastContainer.add(new UserForm(), new VBoxLayoutData(new Margins(20)));
		body.add(eastContainer, new BorderLayoutData(LayoutRegion.EAST, 350));
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class VideoDemo extends ContentPanel {
		
		private VideoDemo() {
			setHeading(((Messages) Registry.get(Messages.class.getName())).videoDemo());
			setUrl("http://player.youku.com/player.php/sid/XMzMzNjU3Mzgw/v.swf");
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class UserForm extends FormPanel {
		
		private UserForm() {
			setLabelSeparator("");
			setLabelWidth(30);
			setHeading(((Messages) Registry.get(Messages.class.getName())).login());
			
			final TextField<String> accountField = new TextField<String>();
			accountField.setAllowBlank(false);
			accountField.setEmptyText(((Messages) Registry.get(Messages.class.getName())).accountEmptyText());
			accountField.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).account());
			accountField.setRegex("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
			accountField.getMessages().setBlankText(((Messages) Registry.get(Messages.class.getName())).accountBlankWarning());
			accountField.getMessages().setRegexText(((Messages) Registry.get(Messages.class.getName())).accountRegexWarning());
			
			final TextField<String> passwordField = new TextField<String>();
			passwordField.setPassword(true);
			passwordField.setAllowBlank(false);		
			passwordField.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).password());
			passwordField.getMessages().setBlankText(((Messages) Registry.get(Messages.class.getName())).passwordBlankWarning());	
			
			final Button loginButton = new Button(((Messages) Registry.get(Messages.class.getName())).login());
			final Button registerButton = new Button(((Messages) Registry.get(Messages.class.getName())).register());
			loginButton.addSelectionListener(new SelectionListener<ButtonEvent>() {

				@Override
				public void componentSelected(ButtonEvent event) {
					// TODO Auto-generated method stub
					if (UserForm.this.isValid()) {
						ArrayList<String> model = new ArrayList<String>();
						model.add(User.class.getName()); 
						model.add("email=='" + accountField.getValue() + "'&&password=='" + passwordField.getValue() + "'");
						ModelController.show(USER_LOGIN_TO_SITE, model, LoginViewport.this);
					}
				}
				
			});
			registerButton.addSelectionListener(new SelectionListener<ButtonEvent>() {

				@Override
				public void componentSelected(ButtonEvent event) {
					// TODO Auto-generated method stub
					RegisterViewport view = Registry.get(RegisterViewport.class.getName());
					view.updateToRootPanel();
				}
				
			});
			
			add(accountField);
			add(passwordField);
			addButton(loginButton);
			addButton(registerButton);
		}
		
	}
	
	/**
	 * 
	 */
	public void showErrorDialog() {
		RootPanel.get().clear();
		String title = ((Messages) Registry.get(Messages.class.getName())).loginErrorTitle();
		String msg = ((Messages) Registry.get(Messages.class.getName())).loginErrorMessage();
		MessageBox.info(title, msg, new Listener<MessageBoxEvent>() {

			@Override
			public void handleEvent(MessageBoxEvent event) {
				// TODO Auto-generated method stub
				LoginViewport.this.updateToRootPanel();
			}
			
		});
	}

	@Override
	public void handleViewCallback(AppEvent event) {
		// TODO Auto-generated method stub
		if (event.getData("type").equals(USER_LOGIN_TO_SITE)) {
			List<BeanModel> users = event.getData("model");
			if (users.size() == 1) {
				Registry.register(Store.CURRENT_OWNER, users.get(0));
				Registry.register(Store.CURRENT_VIEWER, users.get(0));
				
				HomeViewport view = Registry.get(HomeViewport.class.getName());
				view.updateIdentity();
				view.updateToRootPanel();
				
				BeanModel viewer = (BeanModel) Registry.get(Store.CURRENT_VIEWER);
				ArrayList<String> model = new ArrayList<String>();
				model.add(Application.class.getName()); 
				model.add("user.id=='" + viewer.get("id") + "'");
				ModelController.show(HomeViewport.VIEWER_GET_APPLICATIONS, model, view);
			} else { showErrorDialog(); }
		}
	}

}
