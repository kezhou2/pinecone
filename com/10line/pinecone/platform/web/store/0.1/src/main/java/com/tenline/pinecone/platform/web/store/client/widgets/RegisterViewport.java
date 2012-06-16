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
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.CenterLayout;
import com.tenline.pinecone.platform.web.store.client.Messages;
import com.tenline.pinecone.platform.web.store.client.events.UserEvents;
import com.tenline.pinecone.platform.web.store.client.events.WidgetEvents;

/**
 * @author Bill
 *
 */
public class RegisterViewport extends AbstractViewport {

	/**
	 * 
	 */
	public RegisterViewport() {
		super();
		// TODO Auto-generated constructor stub
		LayoutContainer registerFormContainer = new LayoutContainer(new CenterLayout());
		registerFormContainer.add(new RegisterForm());
		body.add(registerFormContainer, new BorderLayoutData(LayoutRegion.CENTER));
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class RegisterForm extends FormPanel {
		
		private RegisterForm() {
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
			confirmationField.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).confirmation());
			confirmationField.getMessages().setBlankText(((Messages) Registry.get(Messages.class.getName())).confirmationBlankWarning());
			
			final Button submitButton = new Button(((Messages) Registry.get(Messages.class.getName())).submit());
			final Button cancelButton = new Button(((Messages) Registry.get(Messages.class.getName())).cancel());
			submitButton.addSelectionListener(new SelectionListener<ButtonEvent>() {

				@Override
				public void componentSelected(ButtonEvent event) {
					// TODO Auto-generated method stub
					if (RegisterForm.this.isValid()) {
						AppEvent appEvent = new AppEvent(UserEvents.REGISTER);
						appEvent.setData("email", accountField.getValue());
						appEvent.setData("password", passwordField.getValue());
						Dispatcher.get().dispatch(appEvent);
					}
				}
				
			});
			cancelButton.addSelectionListener(new SelectionListener<ButtonEvent>() {

				@Override
				public void componentSelected(ButtonEvent event) {
					// TODO Auto-generated method stub
					Dispatcher.get().dispatch(new AppEvent(WidgetEvents.UPDATE_LOGIN_TO_PANEL));
				}
				
			});
			
			add(accountField);
			add(passwordField);
			add(confirmationField);
			addButton(submitButton);
			addButton(cancelButton);
		}
		
	}

}
