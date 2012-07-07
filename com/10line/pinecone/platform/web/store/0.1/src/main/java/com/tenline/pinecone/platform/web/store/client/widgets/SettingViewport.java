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
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FileUploadField;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.MultiField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.CenterLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.store.client.Messages;
import com.tenline.pinecone.platform.web.store.client.events.UserEvents;
import com.tenline.pinecone.platform.web.store.client.events.WidgetEvents;

/**
 * @author liugy
 *
 */
public class SettingViewport extends AbstractViewport {
	
	private RegisterForm form;
	
	public SettingViewport() {
		super();
		LayoutContainer registerFormContainer = new LayoutContainer(
				new CenterLayout());
		form = new RegisterForm();
		registerFormContainer.add(form);
		body.add(registerFormContainer, new BorderLayoutData(
				LayoutRegion.CENTER));
	}
	
	public void loadInfo(){
		form.loadInfo();
	}

	private class RegisterForm extends FormPanel {
		
		private String password;

		private TextField<String> nameField;
		private TextField<String> mailField;
		private TextField<String> nowPasswordField;
		private TextField<String> passwordField;
		private TextField<String> confirmationField;
		private TextField<String> phoneField;
		private FileUploadField uploadfield;
		
		private RegisterForm() {
			this.setSize("400", "350");
			setHeaderVisible(false);
			setLabelSeparator("");
			this.setLabelWidth(60);

			nameField = new TextField<String>();
			nameField.setAllowBlank(false);
			nameField.setEmptyText(((Messages) Registry.get(Messages.class
					.getName())).SettingViewport_nameEmptyText());
			nameField.setFieldLabel(((Messages) Registry.get(Messages.class
					.getName())).SettingViewport_name());
			nameField
					.setRegex("\\w+([-+.]\\w+)*");
			nameField.getMessages().setBlankText(
					((Messages) Registry.get(Messages.class.getName()))
							.SettingViewport_nameBlankWarning());
			nameField.getMessages().setRegexText(
					((Messages) Registry.get(Messages.class.getName()))
							.SettingViewport_nameRegexWarning());
			
			mailField = new TextField<String>();
			mailField.setAllowBlank(false);
			mailField.setEmptyText(((Messages) Registry.get(Messages.class
					.getName())).RegistryViewport_mailEmptyText());
			mailField.setFieldLabel(((Messages) Registry.get(Messages.class
					.getName())).SettingViewport_mail());
			mailField
					.setRegex("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
			mailField.getMessages().setBlankText(
					((Messages) Registry.get(Messages.class.getName()))
							.SettingViewport_mailBlankWarning());
			mailField.getMessages().setRegexText(
					((Messages) Registry.get(Messages.class.getName()))
							.SettingViewport_mailRegexWarning());
			
			nowPasswordField = new TextField<String>();
			nowPasswordField.setPassword(true);
			nowPasswordField.setAllowBlank(false);
			nowPasswordField.setFieldLabel(((Messages) Registry.get(Messages.class
					.getName())).SettingViewport_nowPassword());
			nowPasswordField.getMessages().setBlankText(
					((Messages) Registry.get(Messages.class.getName()))
							.SettingViewport_nowPasswordBlankWarning());

			passwordField = new TextField<String>();
			passwordField.setPassword(true);
			passwordField.setAllowBlank(false);
			passwordField.setFieldLabel(((Messages) Registry.get(Messages.class
					.getName())).SettingViewport_password());
			passwordField.getMessages().setBlankText(
					((Messages) Registry.get(Messages.class.getName()))
							.SettingViewport_passwordBlankWarning());

			confirmationField = new TextField<String>();
			confirmationField.setPassword(true);
			confirmationField.setAllowBlank(false);
			confirmationField.setFieldLabel(((Messages) Registry
					.get(Messages.class.getName())).SettingViewport_confirmation());
			confirmationField.getMessages().setBlankText(
					((Messages) Registry.get(Messages.class.getName()))
							.SettingViewport_confirmationBlankWarning());
			
			phoneField = new TextField<String>();
			phoneField.setAllowBlank(false);
			phoneField.setEmptyText(((Messages) Registry.get(Messages.class
					.getName())).SettingViewport_phoneEmptyText());
			phoneField.setFieldLabel(((Messages) Registry.get(Messages.class
					.getName())).SettingViewport_phone());

			final Button submitButton = new Button(
					((Messages) Registry.get(Messages.class.getName()))
							.RegistryViewport_submit());
			final Button cancelButton = new Button(
					((Messages) Registry.get(Messages.class.getName()))
							.RegistryViewport_cancel());
			
			
			MultiField multifield = new MultiField();
			uploadfield = new FileUploadField();
			multifield.add(uploadfield);
			uploadfield.setFieldLabel("Upload");
			multifield.setFieldLabel("Portrait");
			
			submitButton
					.addSelectionListener(new SelectionListener<ButtonEvent>() {

						@Override
						public void componentSelected(ButtonEvent event) {
							if (RegisterForm.this.isValid()) {
								AppEvent appEvent = new AppEvent(
										UserEvents.SETTING);
								appEvent.setData("name",
										nameField.getValue());
								appEvent.setData("email",
										mailField.getValue());
								
								if(password.equals(nowPasswordField.getValue())){
									if(passwordField.getValue().equals(confirmationField.getValue())){
										appEvent.setData("password",
												passwordField.getValue());
									}else{
										MessageBox.alert("Warning", 
												"Confirmation is failure.", null);
									}
								}else{
									MessageBox.alert("Warning", 
											"Password is wrong.", null);
								}
								
								appEvent.setData("phone",
										phoneField.getValue());
								//TODO
//								appEvent.setData("avatar", uploadfield.get);
								
								Dispatcher.get().dispatch(appEvent);
							}
						}

					});
			cancelButton
					.addSelectionListener(new SelectionListener<ButtonEvent>() {

						@Override
						public void componentSelected(ButtonEvent event) {
							Dispatcher
									.get()
									.dispatch(
											new AppEvent(
													WidgetEvents.UPDATE_USERHOME_TO_PANEL));
						}

					});

			
			FormData formData = new FormData("85%");
			formData.setMargins(new Margins(10, 0, 10, 0));
			
			add(nameField,formData);
			add(mailField,formData);
			add(nowPasswordField,formData);
			add(passwordField,formData);
			add(confirmationField,formData);
			add(phoneField,formData);
			add(multifield,formData);
			addButton(submitButton);
			addButton(cancelButton);
		}
		
		public void loadInfo(){
			User user = (User)Registry.get(User.class.getName());
			if(user != null){
				password = user.getPassword();
				nameField.setValue(user.getName());
				mailField.setValue(user.getEmail());
				phoneField.setValue(user.getPhone());
			}
		}

	}
}
