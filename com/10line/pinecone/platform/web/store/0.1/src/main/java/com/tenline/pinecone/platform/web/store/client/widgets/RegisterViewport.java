package com.tenline.pinecone.platform.web.store.client.widgets;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.HtmlContainer;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.BoxLayout.BoxLayoutPack;
import com.extjs.gxt.ui.client.widget.layout.FitData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayoutData;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.tenline.pinecone.platform.web.store.client.Images;
import com.tenline.pinecone.platform.web.store.client.Messages;
import com.tenline.pinecone.platform.web.store.client.events.UserEvents;
import com.tenline.pinecone.platform.web.store.client.events.WidgetEvents;

public class RegisterViewport extends AbstractViewport {

	public RegisterViewport() {
		super();
		LayoutContainer registerFormContainer = new LayoutContainer(
				new FitLayout());
		registerFormContainer.add(new RegisterForm(),new FitData(10,10,10,10));
		registerFormContainer.setBorders(true);
		registerFormContainer.setStyleName("registerviewport-input-panel");
		
		Text title = new Text(((Messages) Registry.get(Messages.class.getName())).RegistryViewport_title());
		title.addStyleName("registerviewport-title");
		BorderLayoutData titleBld = new BorderLayoutData(LayoutRegion.NORTH, 40);
		titleBld.setMargins(new Margins(6,60,0,60));
		body.add(title,titleBld);
		
		IntroducePanel infoPanel = new IntroducePanel();
		BorderLayoutData infoBld = new BorderLayoutData(LayoutRegion.WEST, 550);
		infoBld.setMargins(new Margins(5,10,10,10));
		body.add(infoPanel, infoBld);
		
		BorderLayoutData registerBld = new BorderLayoutData(LayoutRegion.CENTER);
		registerBld.setMargins(new Margins(10,60,10,10));
		body.add(registerFormContainer, registerBld);
	}
	
	public class IntroducePanel extends ContentPanel {
		
		private IntroducePanel(){
			setHeaderVisible(false);
			setBodyBorder(false);
			setLayout(new RowLayout(Orientation.VERTICAL));
			
			RowData rd1 = new RowData(Style.DEFAULT, Style.DEFAULT, new Margins(10, 10, 10, 40));
			this.add(createInfoPanel(
					((Images) Registry.get(Images.class.getName())).internetOfThing().createImage(),
					((Messages) Registry.get(Messages.class.getName())).RegistryViewport_title_internetOfThings(),
					((Messages) Registry.get(Messages.class.getName())).RegistryViewport_description_internetOfThings()
					,"500px","179px"
			), rd1);
			RowData rd2 = new RowData(Style.DEFAULT, Style.DEFAULT, new Margins(10, 10, 10, 40));
			this.add(createInfoPanel(
					((Images) Registry.get(Images.class.getName())).socialNetworking().createImage(),
					((Messages) Registry.get(Messages.class.getName())).RegistryViewport_title_socialNetworking(),
					((Messages) Registry.get(Messages.class.getName())).RegistryViewport_description_socialNetworking()
					,"500px","138px"
			), rd2);
			RowData rd3 = new RowData(Style.DEFAULT, Style.DEFAULT, new Margins(10, 10, 10, 40));
			this.add(createInfoPanel(
					((Images) Registry.get(Images.class.getName())).funThing().createImage(),
					((Messages) Registry.get(Messages.class.getName())).RegistryViewport_title_funThing(),
					((Messages) Registry.get(Messages.class.getName())).RegistryViewport_description_funThing()
					,"500px","135px"
			), rd3);
		}
		
		private LayoutContainer createInfoPanel(Image image, String title, String description, String width,String height){
			LayoutContainer container = new LayoutContainer();
			container.setLayout(new BorderLayout());
			
			LayoutContainer headerImageContainer = new LayoutContainer();
			headerImageContainer.setLayout(new FitLayout());
			
			headerImageContainer.add(image,new FitData(0, 15, 0, 15));
			BorderLayoutData imgBld = new BorderLayoutData(LayoutRegion.WEST, 250.0f);
			imgBld.setMargins(new Margins(0,0,0,0));
			image.addStyleName("img-border");
			container.add(headerImageContainer, imgBld);
			
			LayoutContainer infoContainer = new LayoutContainer();
			infoContainer.setLayout(new FitLayout());
			infoContainer.setBorders(false);
			
			HtmlContainer htmlContainer = new HtmlContainer("<h2 class='header-title'>"+
					title+
					"</h2><p class='header-description'>"+
					description+
					"</p>");
			infoContainer.add(htmlContainer,new FitData(10, 10, 10, 10));
			container.add(infoContainer, new BorderLayoutData(LayoutRegion.CENTER));
			container.setSize(width, height);
			container.addStyleName("abstractviewport-background");
			
			return container;
		}
	}

	private class RegisterForm extends FormPanel {

		private RegisterForm() {
			this.setSize("300", "170");
			setHeaderVisible(false);
			setLabelSeparator("");
			this.setLabelWidth(60);
			this.setLabelAlign(LabelAlign.TOP);
			this.setBodyBorder(false);

			String labelStyle = "color: #222;font-weight: bold;";
			
			final TextField<String> nameField = new TextField<String>();
			nameField.setAllowBlank(false);
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
			nameField.setLabelStyle(labelStyle);
			
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
			mailField.setLabelStyle(labelStyle);

			final TextField<String> passwordField = new TextField<String>();
			passwordField.setPassword(true);
			passwordField.setAllowBlank(false);
			passwordField.setFieldLabel(((Messages) Registry.get(Messages.class
					.getName())).RegistryViewport_password());
			passwordField.getMessages().setBlankText(
					((Messages) Registry.get(Messages.class.getName()))
							.RegistryViewport_passwordBlankWarning());
			passwordField.setLabelStyle(labelStyle);
			passwordField.setEmptyText(((Messages) Registry.get(Messages.class.getName())).RegistryViewport_passwordEmptyText());

			final TextField<String> confirmationField = new TextField<String>();
			confirmationField.setPassword(true);
			confirmationField.setAllowBlank(false);
			confirmationField.setFieldLabel(((Messages) Registry
					.get(Messages.class.getName())).RegistryViewport_confirmation());
			confirmationField.getMessages().setBlankText(
					((Messages) Registry.get(Messages.class.getName()))
							.RegistryViewport_confirmationBlankWarning());
			confirmationField.setLabelStyle(labelStyle);
			confirmationField.setEmptyText(((Messages) Registry.get(Messages.class.getName())).RegistryViewport_confirmationEmptyText());

			final Button submitButton = new Button(
					((Messages) Registry.get(Messages.class.getName()))
							.RegistryViewport_submit());
			submitButton.addStyleName("btn-green");
			submitButton.setSize("60px", "30");
			final Button cancelButton = new Button(
					((Messages) Registry.get(Messages.class.getName()))
							.RegistryViewport_cancel());
			cancelButton.addStyleName("btn-green");
			cancelButton.setSize("60px", "30");
			
			submitButton.addMouseUpHandler(new MouseUpHandler() {
				public void onMouseUp(MouseUpEvent event) {
							if (RegisterForm.this.isValid()) {
								AppEvent appEvent = new AppEvent(
										UserEvents.REGISTER);
								appEvent.setData("name",
										nameField.getValue());
								appEvent.setData("email",
										mailField.getValue());
								appEvent.setData("password",
										passwordField.getValue());
								appEvent.setHistoryEvent(true);
								Dispatcher.get().dispatch(appEvent);
							}
						}

					});
			cancelButton.addMouseUpHandler(new MouseUpHandler() {
				public void onMouseUp(MouseUpEvent event) {
					AppEvent appEvent = new AppEvent(WidgetEvents.UPDATE_LOGIN_TO_PANEL);
					appEvent.setHistoryEvent(true);
					Dispatcher.get().dispatch(appEvent);
				}

			});
			
			LayoutContainer buttonLayoutContainer = new LayoutContainer();
			HBoxLayout hbl_layoutContainer_4 = new HBoxLayout();
			hbl_layoutContainer_4.setPack(BoxLayoutPack.END);
			buttonLayoutContainer.setLayout(hbl_layoutContainer_4);
			buttonLayoutContainer.add(submitButton, new HBoxLayoutData(10, 0, 0, 10));
			buttonLayoutContainer.add(cancelButton, new HBoxLayoutData(10, 0, 0, 10));
			
			FormData formData = new FormData("100%");
			formData.setMargins(new Margins(4, 0, 10, 0));
			
			add(nameField,formData);
			add(mailField,formData);
			add(passwordField,formData);
			add(confirmationField,formData);
			add(buttonLayoutContainer,formData);
			
		}

	}
}
