package com.tenline.pinecone.platform.web.store.client.widgets;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.CenterLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.tenline.pinecone.platform.web.store.client.Messages;
import com.tenline.pinecone.platform.web.store.client.events.UserEvents;
import com.tenline.pinecone.platform.web.store.client.events.WidgetEvents;

public class RegisterViewport extends AbstractViewport {

	public RegisterViewport() {
		super();
		LayoutContainer registerFormContainer = new LayoutContainer(
				new CenterLayout());
		registerFormContainer.add(new RegisterForm());
		body.add(registerFormContainer, new BorderLayoutData(
				LayoutRegion.CENTER));
	}

	private class RegisterForm extends FormPanel {

		private RegisterForm() {
			this.setSize("300", "170");
			setHeaderVisible(false);
			setLabelSeparator("");
			this.setLabelWidth(60);

			final TextField<String> nameField = new TextField<String>();
			nameField.setAllowBlank(false);
			nameField.setValue("Anonymous");
			nameField.setEmptyText(((Messages) Registry.get(Messages.class
					.getName())).RegistryViewport_nameBlankWarning());
			nameField.setFieldLabel(((Messages) Registry.get(Messages.class
					.getName())).RegistryViewport_name());
			nameField
					.setRegex("\\w+([-+.]\\w+)*");
			nameField.getMessages().setBlankText(
					((Messages) Registry.get(Messages.class.getName()))
							.RegistryViewport_nameBlankWarning());
			nameField.getMessages().setRegexText(
					((Messages) Registry.get(Messages.class.getName()))
							.RegistryViewport_nameRegexWarning());
			
			final TextField<String> mailField = new TextField<String>();
			mailField.setAllowBlank(false);
			mailField.setEmptyText(((Messages) Registry.get(Messages.class
					.getName())).RegistryViewport_mailBlankWarning());
			mailField.setFieldLabel(((Messages) Registry.get(Messages.class
					.getName())).RegistryViewport_mail());
			mailField
					.setRegex("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
			mailField.getMessages().setBlankText(
					((Messages) Registry.get(Messages.class.getName()))
							.RegistryViewport_mailBlankWarning());
			mailField.getMessages().setRegexText(
					((Messages) Registry.get(Messages.class.getName()))
							.RegistryViewport_mailRegexWarning());

			final TextField<String> passwordField = new TextField<String>();
			passwordField.setPassword(true);
			passwordField.setAllowBlank(false);
			passwordField.setFieldLabel(((Messages) Registry.get(Messages.class
					.getName())).RegistryViewport_password());
			passwordField.getMessages().setBlankText(
					((Messages) Registry.get(Messages.class.getName()))
							.RegistryViewport_passwordBlankWarning());

			final TextField<String> confirmationField = new TextField<String>();
			confirmationField.setPassword(true);
			confirmationField.setAllowBlank(false);
			confirmationField.setFieldLabel(((Messages) Registry
					.get(Messages.class.getName())).RegistryViewport_confirmation());
			confirmationField.getMessages().setBlankText(
					((Messages) Registry.get(Messages.class.getName()))
							.RegistryViewport_confirmationBlankWarning());

			final Button submitButton = new Button(
					((Messages) Registry.get(Messages.class.getName()))
							.RegistryViewport_submit());
			final Button cancelButton = new Button(
					((Messages) Registry.get(Messages.class.getName()))
							.RegistryViewport_cancel());
			submitButton
					.addSelectionListener(new SelectionListener<ButtonEvent>() {

						@Override
						public void componentSelected(ButtonEvent event) {
							if (RegisterForm.this.isValid()) {
								AppEvent appEvent = new AppEvent(
										UserEvents.REGISTER);
								appEvent.setData("name",
										nameField.getValue());
								appEvent.setData("email",
										mailField.getValue());
								appEvent.setData("password",
										passwordField.getValue());
								Dispatcher.get().dispatch(appEvent);
							}
						}

					});
			cancelButton
					.addSelectionListener(new SelectionListener<ButtonEvent>() {

						@Override
						public void componentSelected(ButtonEvent event) {
							// TODO Auto-generated method stub
							Dispatcher
									.get()
									.dispatch(
											new AppEvent(
													WidgetEvents.UPDATE_LOGIN_TO_PANEL));
						}

					});

			
			FormData formData = new FormData("85%");
			formData.setMargins(new Margins(10, 0, 10, 0));
			
			add(mailField,formData);
			add(passwordField,formData);
			add(confirmationField,formData);
			addButton(submitButton);
			addButton(cancelButton);
		}

	}
}
