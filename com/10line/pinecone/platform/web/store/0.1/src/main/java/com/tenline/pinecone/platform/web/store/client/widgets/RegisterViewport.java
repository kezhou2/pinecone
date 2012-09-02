/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.widgets;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelLookup;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FillData;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.tenline.pinecone.platform.model.Consumer;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.store.client.Messages;
import com.tenline.pinecone.platform.web.store.client.controllers.ModelController;

/**
 * @author Bill
 *
 */
public class RegisterViewport extends AbstractViewport {
	
	public static final String USER_REGISTER_TO_SITE = "user.register.to.site";
	public static final String CONSUMER_REGISTER_TO_SITE = "consumer.register.to.site";

	/**
	 * 
	 */
	public RegisterViewport() {
		super();
		// TODO Auto-generated constructor stub
		LayoutContainer centerContainer = new LayoutContainer(new FillLayout(Orientation.VERTICAL));
		centerContainer.add(new UserRegisterForm(), new FillData(20, 150, 20, 150));
		centerContainer.add(new ConsumerRegisterForm(), new FillData(0, 150, 20, 150));
		body.add(centerContainer, new BorderLayoutData(LayoutRegion.CENTER));
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class UserRegisterForm extends FormPanel {
		
		private UserRegisterForm() {
			setLabelSeparator("");
			setLabelWidth(30);
			setHeading(((Messages) Registry.get(Messages.class.getName())).userRegister());
			
			final TextField<String> accountField = new TextField<String>();
			accountField.setAllowBlank(false);
			accountField.setEmptyText(((Messages) Registry.get(Messages.class.getName())).accountEmptyText());
			accountField.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).account());
			accountField.setRegex("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
			accountField.getMessages().setBlankText(((Messages) Registry.get(Messages.class.getName())).accountBlankWarning());
			accountField.getMessages().setRegexText(((Messages) Registry.get(Messages.class.getName())).accountRegexWarning());
			
			final TextField<String> nameField = new TextField<String>();
			nameField.setAllowBlank(false);
			nameField.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).name());
			nameField.getMessages().setBlankText(((Messages) Registry.get(Messages.class.getName())).nameBlankWarning());
			
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
					if (UserRegisterForm.this.isValid()) {
						BeanModel model = BeanModelLookup.get().getFactory(User.class).createModel(new User());
						model.set("email", accountField.getValue());
						model.set("name", nameField.getValue());
						model.set("password", passwordField.getValue());
						ModelController.create(USER_REGISTER_TO_SITE, model, RegisterViewport.this);
					}
				}
				
			});
			cancelButton.addSelectionListener(new SelectionListener<ButtonEvent>() {

				@Override
				public void componentSelected(ButtonEvent event) {
					// TODO Auto-generated method stub
					LoginViewport view = Registry.get(LoginViewport.class.getName());
					view.updateToRootPanel();
				}
				
			});
			
			add(accountField);
			add(nameField);
			add(passwordField);
			add(confirmationField);
			addButton(submitButton);
			addButton(cancelButton);
		}
		
	}
	
	/**
	 * 
	 * @author Bill
	 *
	 */
	private class ConsumerRegisterForm extends FormPanel {
		
		private ConsumerRegisterForm() {
			setLabelSeparator("");
			setLabelWidth(30);
			setHeading(((Messages) Registry.get(Messages.class.getName())).applicationRegister());
			
			final TextField<String> nameField = new TextField<String>();
			nameField.setAllowBlank(false);
			nameField.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).name());
			nameField.getMessages().setBlankText(((Messages) Registry.get(Messages.class.getName())).nameBlankWarning());
			
			final TextField<String> urlField = new TextField<String>();
			urlField.setAllowBlank(false);
			urlField.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).url());
			urlField.setRegex("[a-zA-z]+://[^\\s]*");
			urlField.getMessages().setBlankText(((Messages) Registry.get(Messages.class.getName())).urlBlankWarning());
			urlField.getMessages().setRegexText(((Messages) Registry.get(Messages.class.getName())).urlRegexWarning());
			
			final TextField<String> versionField = new TextField<String>();
			versionField.setAllowBlank(false);
			versionField.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).version());
			versionField.getMessages().setBlankText(((Messages) Registry.get(Messages.class.getName())).versionBlankWarning());
			
			final Button submitButton = new Button(((Messages) Registry.get(Messages.class.getName())).submit());
			final Button cancelButton = new Button(((Messages) Registry.get(Messages.class.getName())).cancel());
			submitButton.addSelectionListener(new SelectionListener<ButtonEvent>() {

				@Override
				public void componentSelected(ButtonEvent event) {
					// TODO Auto-generated method stub
					if (ConsumerRegisterForm.this.isValid()) {
						BeanModel model = BeanModelLookup.get().getFactory(Consumer.class).createModel(new Consumer());
						model.set("connectURI", urlField.getValue());
						model.set("name", nameField.getValue());
						model.set("version", versionField.getValue());
						ModelController.create(CONSUMER_REGISTER_TO_SITE, model, RegisterViewport.this);
					}
				}
				
			});
			cancelButton.addSelectionListener(new SelectionListener<ButtonEvent>() {

				@Override
				public void componentSelected(ButtonEvent event) {
					// TODO Auto-generated method stub
					LoginViewport view = Registry.get(LoginViewport.class.getName());
					view.updateToRootPanel();
				}
				
			});
			
			add(nameField);
			add(urlField);
			add(versionField);
			addButton(submitButton);
			addButton(cancelButton);
		}
		
	}

	@Override
	public void handleViewCallback(AppEvent event) {
		// TODO Auto-generated method stub
		if (event.getData("type").equals(CONSUMER_REGISTER_TO_SITE) ||
			event.getData("type").equals(USER_REGISTER_TO_SITE)) {
			LoginViewport view = Registry.get(LoginViewport.class.getName());
			view.updateToRootPanel();	
		}
	}

}
