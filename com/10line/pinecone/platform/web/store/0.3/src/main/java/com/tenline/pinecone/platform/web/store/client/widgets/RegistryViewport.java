package com.tenline.pinecone.platform.web.store.client.widgets;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.Validator;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayoutData;
import com.google.gwt.user.client.ui.Image;
import com.tenline.pinecone.platform.web.store.client.Images;
import com.tenline.pinecone.platform.web.store.client.Messages;
import com.tenline.pinecone.platform.web.store.client.events.LoginViewEvents;
import com.tenline.pinecone.platform.web.store.client.events.RegistryViewEvents;

public class RegistryViewport extends Viewport {
	
	public RegistryViewport() {
		setLayout(new BorderLayout());
		
		LayoutContainer layoutContainer = new LayoutContainer();
		layoutContainer.setBorders(true);
		layoutContainer.setLayout(new BorderLayout());
		
		LayoutContainer layoutContainer_4 = new LayoutContainer();
		layoutContainer_4.setLayout(new FitLayout());
		
		
		Image image = ((Images) Registry.get(Images.class.getName())).logo().createImage();
		layoutContainer_4.add(image, new FitData(20, 8, 20, 8));
		layoutContainer.add(layoutContainer_4, new BorderLayoutData(LayoutRegion.WEST));
		add(layoutContainer, new BorderLayoutData(LayoutRegion.NORTH, 100.0f));
		
		LayoutContainer layoutContainer_2 = new LayoutContainer();
		layoutContainer_2.setLayout(new FitLayout());
		
		LayoutContainer layoutContainer_1 = new LayoutContainer();
		FormLayout fl_layoutContainer_1 = new FormLayout();
		fl_layoutContainer_1.setLabelAlign(LabelAlign.RIGHT);
		layoutContainer_1.setLayout(fl_layoutContainer_1);
		
		LayoutContainer layoutContainer_3 = new LayoutContainer();
		layoutContainer_3.setLayout(new FitLayout());
		
		Text registryText = new Text(((Messages) Registry.get(Messages.class.getName())).registerViewTitle());
		layoutContainer_3.add(registryText);
		FormData fd_layoutContainer_3 = new FormData("100%");
		fd_layoutContainer_3.setMargins(new Margins(20, 20, 20, 20));
		layoutContainer_1.add(layoutContainer_3, fd_layoutContainer_3);
		
		final TextField<String> txtfldName = new TextField<String>();
		FormData fd_txtfldNewTextfield = new FormData("50%");
		fd_txtfldNewTextfield.setMargins(new Margins(40, 0, 10, 0));
		layoutContainer_1.add(txtfldName, fd_txtfldNewTextfield);
		txtfldName.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).account());
		
		final TextField<String> txtfldPassword = new TextField<String>();
		FormData fd_txtfldPassword = new FormData("50%");
		fd_txtfldPassword.setMargins(new Margins(20, 0, 10, 0));
		layoutContainer_1.add(txtfldPassword, fd_txtfldPassword);
		txtfldPassword.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).password());
		
		final TextField<String> txtfldConfirmPwd = new TextField<String>();
		FormData fd_txtfldNewTextfield_1 = new FormData("50%");
		fd_txtfldNewTextfield_1.setMargins(new Margins(20, 0, 10, 0));
		layoutContainer_1.add(txtfldConfirmPwd, fd_txtfldNewTextfield_1);
		txtfldConfirmPwd.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).confirmation());
		txtfldConfirmPwd.setValidator(new Validator(){

			@Override
			public String validate(Field<?> field, String value) {
				if(txtfldPassword.getValue().equals(txtfldConfirmPwd.getValue())){
					return null;
				}else{
					return ((Messages) Registry.get(Messages.class.getName())).confirmationFailureWarning();
				}
			}});
		
		final TextField<String> txtfldEmail = new TextField<String>();
		FormData fd_txtfldEmail = new FormData("50%");
		fd_txtfldEmail.setMargins(new Margins(20, 0, 10, 0));
		layoutContainer_1.add(txtfldEmail, fd_txtfldEmail);
		txtfldEmail.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).email());
		
		LayoutContainer layoutContainer_5 = new LayoutContainer();
		layoutContainer_5.setLayout(new HBoxLayout());
		
		Button btnCreate = new Button("Create Account");
		btnCreate.addListener(Events.Select, new Listener<ButtonEvent>() {
			public void handleEvent(ButtonEvent e) {
				System.out.println("step 1.");
				AppEvent appEvent = new AppEvent(RegistryViewEvents.CREATE_USER);
				if(txtfldName.isValid() && txtfldPassword.isValid() && txtfldConfirmPwd.isValid() && txtfldEmail.isValid()){
					System.out.println("step 2.");
					appEvent.setData("name", txtfldName.getValue());
					appEvent.setData("password", txtfldPassword.getValue());
					appEvent.setData("email", txtfldEmail.getValue());
					Dispatcher.get().dispatch(appEvent);
				}
			}
		});
		layoutContainer_5.add(btnCreate, new HBoxLayoutData(0, 10, 0, 20));
		FormData fd_layoutContainer_5 = new FormData("100%");
		fd_layoutContainer_5.setMargins(new Margins(20, 0, 10, 0));
		
		Button btnCancel = new Button("Cancel");
		btnCancel.addListener(Events.Select, new Listener<ButtonEvent>() {
			public void handleEvent(ButtonEvent e) {
				AppEvent appEvent = new AppEvent(LoginViewEvents.INIT_LOGIN);
				Dispatcher.get().dispatch(appEvent);
			}
		});
		layoutContainer_5.add(btnCancel, new HBoxLayoutData(0, 0, 0, 10));
		layoutContainer_1.add(layoutContainer_5, fd_layoutContainer_5);
		layoutContainer_2.add(layoutContainer_1, new FitData(50, 150, 0, 150));
		add(layoutContainer_2, new BorderLayoutData(LayoutRegion.CENTER));
		layoutContainer_2.setBorders(false);
	}
}
