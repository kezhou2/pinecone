/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.widgets;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.CenterLayout;
import com.tenline.pinecone.platform.web.store.client.Images;
import com.tenline.pinecone.platform.web.store.client.Messages;
import com.tenline.pinecone.platform.web.store.client.events.UserEvents;

/**
 * @author Bill
 *
 */
public class LoginViewport extends Viewport {

	/**
	 * 
	 */
	public LoginViewport() {
		// TODO Auto-generated constructor stub
		setLayout(new BorderLayout());
		add(new Body(), new BorderLayoutData(LayoutRegion.CENTER));
		add(new Footer(), new BorderLayoutData(LayoutRegion.SOUTH, 50));
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class Body extends ContentPanel {
		
		private Body() {
			setIcon(((Images) Registry.get(Images.class.getName())).logo());
			setLayout(new BorderLayout());
			add(new VideoDemo(), new BorderLayoutData(LayoutRegion.CENTER));
			add(new UserForm(), new BorderLayoutData(LayoutRegion.EAST, 350));
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class VideoDemo extends ContentPanel {
		
		private VideoDemo() {
			setHeaderVisible(false);
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
			setHeaderVisible(false);
			setLabelSeparator("");
			
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
			
			final TextField<String> confirmationField = new TextField<String>();
			confirmationField.setPassword(true);
			confirmationField.setAllowBlank(false);
			confirmationField.setVisible(false);
			confirmationField.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).confirmation());
			confirmationField.getMessages().setBlankText(((Messages) Registry.get(Messages.class.getName())).confirmationBlankWarning());
			
			final Button loginButton = new Button(((Messages) Registry.get(Messages.class.getName())).login());
			final Button registerButton = new Button(((Messages) Registry.get(Messages.class.getName())).register());
			final Button submitButton = new Button(((Messages) Registry.get(Messages.class.getName())).submit());
			final Button cancelButton = new Button(((Messages) Registry.get(Messages.class.getName())).cancel());
			loginButton.addSelectionListener(new SelectionListener<ButtonEvent>() {

				@Override
				public void componentSelected(ButtonEvent event) {
					// TODO Auto-generated method stub
					if (UserForm.this.isValid()) {
						AppEvent appEvent = new AppEvent(UserEvents.LOGIN);
						appEvent.setData("email", accountField.getValue());
						appEvent.setData("password", passwordField.getValue());
						Dispatcher.get().dispatch(appEvent);
					}
				}
				
			});
			submitButton.setVisible(false);
			submitButton.addSelectionListener(new SelectionListener<ButtonEvent>() {

				@Override
				public void componentSelected(ButtonEvent event) {
					// TODO Auto-generated method stub
					if (UserForm.this.isValid()) {
						AppEvent appEvent = new AppEvent(UserEvents.REGISTER);
						appEvent.setData("email", accountField.getValue());
						appEvent.setData("password", passwordField.getValue());
						Dispatcher.get().dispatch(appEvent);
					}
				}
				
			});
			cancelButton.setVisible(false);
			cancelButton.addSelectionListener(new SelectionListener<ButtonEvent>() {

				@Override
				public void componentSelected(ButtonEvent event) {
					// TODO Auto-generated method stub
					confirmationField.setVisible(false);
					submitButton.setVisible(false);
					cancelButton.setVisible(false);
					loginButton.setVisible(true);
					registerButton.setVisible(true);
				}
				
			});
			registerButton.addSelectionListener(new SelectionListener<ButtonEvent>() {

				@Override
				public void componentSelected(ButtonEvent event) {
					// TODO Auto-generated method stub
					confirmationField.setVisible(true);
					submitButton.setVisible(true);
					cancelButton.setVisible(true);
					loginButton.setVisible(false);
					registerButton.setVisible(false);
				}
				
			});
			
			add(accountField);
			add(passwordField);
			add(confirmationField);
			add(loginButton);
			add(registerButton);
			add(submitButton);
			add(cancelButton);
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class Footer extends ContentPanel {
		
		private Footer() {
			setHeaderVisible(false);
			setLayout(new CenterLayout());
			add(new LabelField(((Messages) Registry.get(Messages.class.getName())).copyright()));
		}
		
	}

}
