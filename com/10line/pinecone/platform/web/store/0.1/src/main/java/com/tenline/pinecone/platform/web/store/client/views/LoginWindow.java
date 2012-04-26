/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.views;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.tenline.pinecone.platform.web.store.client.Messages;
import com.tenline.pinecone.platform.web.store.client.services.UserService;
import com.tenline.pinecone.platform.web.store.client.services.UserServiceAsync;

/**
 * @author Bill
 *
 */
public class LoginWindow extends Window {

	/**
	 * 
	 */
	public LoginWindow() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		setAutoHide(false);
		setResizable(false);
		setSize(350, 130);
		setHeading(((Messages) Registry.get(Messages.class.getName())).login());
		setLayout(new FitLayout());
		
		final FormPanel loginForm = new FormPanel();
		loginForm.setHeaderVisible(false);
		
		final TextField<String> emailField = new TextField<String>();
		emailField.setAllowBlank(false);
		emailField.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).email());
		emailField.setRegex("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		emailField.getMessages().setBlankText(((Messages) Registry.get(Messages.class.getName())).emailBlankWarning());
		emailField.getMessages().setRegexText(((Messages) Registry.get(Messages.class.getName())).emailRegexWarning());
		
		final TextField<String> passwordField = new TextField<String>();
		passwordField.setPassword(true);
		passwordField.setAllowBlank(false);		
		passwordField.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).password());
		passwordField.getMessages().setBlankText(((Messages) Registry.get(Messages.class.getName())).passwordBlankWarning());
		
		Button loginButton = new Button(((Messages) Registry.get(Messages.class.getName())).login());
		loginButton.addSelectionListener(new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent event) {
				// TODO Auto-generated method stub
				if (loginForm.isValid()) {
					UserServiceAsync userService = Registry.get(UserService.class.getName());
					userService.login(emailField.getValue(), passwordField.getValue(), new AsyncCallback<Boolean>() {

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							caught.printStackTrace();
						}

						@Override
						public void onSuccess(Boolean result) {
							// TODO Auto-generated method stub
							if (result) {
								LoginWindow.this.hide();
							}
						}
						
					});	
				}
			}
			
		});
		
		loginForm.add(emailField);
		loginForm.add(passwordField);
		loginForm.add(loginButton);
		
		add(loginForm);
	}

}
