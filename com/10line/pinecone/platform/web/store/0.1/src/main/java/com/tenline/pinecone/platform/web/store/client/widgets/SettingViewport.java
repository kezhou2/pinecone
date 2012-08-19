/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.widgets;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.ButtonBar;
import com.extjs.gxt.ui.client.widget.form.FileUploadField;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.MultiField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.BoxLayout.BoxLayoutPack;
import com.extjs.gxt.ui.client.widget.layout.CardLayout;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.extjs.gxt.ui.client.widget.layout.FitData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout.HBoxLayoutAlign;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayoutData;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.ui.Button;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.store.client.Messages;
import com.tenline.pinecone.platform.web.store.client.events.UserEvents;
import com.tenline.pinecone.platform.web.store.client.events.WidgetEvents;

/**
 * @author liugy
 *
 */
public class SettingViewport extends AbstractViewport {
	
	private MainPanel mainPanel;
	
	public SettingViewport() {
		super();

		mainPanel = new MainPanel();
		BorderLayoutData bld = new BorderLayoutData(LayoutRegion.CENTER);
		bld.setMargins(new Margins(10,10,10,10));
		body.add(mainPanel, bld);
	}
	
	public void loadInfo(){
		mainPanel.loadInfo();
	}
	
	private class MainPanel extends ContentPanel{
		private ContentPanel container;
		private CardLayout layout;
		
		/**User Setting widget*/
		private FormPanel userSettingPanel = null;
		private String password;
		private TextField<String> nameField;
		private TextField<String> mailField;
		private TextField<String> nowPasswordField;
		private TextField<String> passwordField;
		private TextField<String> confirmationField;
		private TextField<String> phoneField;
		private FileUploadField uploadfield;
		
		public MainPanel(){
			this.setHeaderVisible(false);
			this.setLayout(new BorderLayout());
			this.setBodyStyleName("abstractviewport-background");
			this.setBodyBorder(false);
			this.setBorders(false);
			
			BorderLayoutData toolbarbld = new BorderLayoutData(LayoutRegion.NORTH,40);
			toolbarbld.setMargins(new Margins(10,0,10,10));
			LayoutContainer toolbarlc = new LayoutContainer();
			add(toolbarlc, toolbarbld);
			toolbarlc.addStyleName("abstractviewport-background");
			
			HBoxLayout hbl_toolbarLayoutContainer = new HBoxLayout();
			hbl_toolbarLayoutContainer.setPack(BoxLayoutPack.START);
			hbl_toolbarLayoutContainer.setHBoxLayoutAlign(HBoxLayoutAlign.STRETCHMAX);
			toolbarlc.setLayout(hbl_toolbarLayoutContainer);
			
			Button btnBack = new Button(((Messages) Registry.get(Messages.class.getName())).MailListViewport_button_back());
			toolbarlc.add(btnBack,new HBoxLayoutData(0, 2, 0, 2));
			btnBack.addMouseUpHandler(new MouseUpHandler() {
				public void onMouseUp(MouseUpEvent event) {
					AppEvent appEvent = new AppEvent(WidgetEvents.UPDATE_USERHOME_TO_PANEL);
					appEvent.setHistoryEvent(true);
					Dispatcher.get().dispatch(appEvent);
				}

			});
			btnBack.setHTML("<img class='btn-img-left' src='../images/icons/back.png'>"+
					((Messages) Registry.get(Messages.class.getName())).HomeViewport_title()+
					"</img>");
			btnBack.setHeight("32px");
			btnBack.setStyleName("abstractviewport-btn");
			
			container = new ContentPanel();
			container.setHeading(((Messages) Registry.get(Messages.class.getName())).SettingViewport_title());
			container.getHeader().addStyleName("header-title");
			container.getHeader().addStyleName("appstoreviewport-panel-header");
			container.addStyleName("appstoreviewport-panel");
			container.setBorders(false);
			BorderLayoutData centerlc = new BorderLayoutData(LayoutRegion.CENTER);
			centerlc.setMargins(new Margins(0,20,15,20));
			add(container,centerlc);
			
			layout = new CardLayout();
			container.setLayout(layout);
			container.setBodyBorder(false);
			container.setBorders(false);
			container.add(createUserSettingPanel(), new FitData(0,0,15,0));
			layout.setActiveItem(createUserSettingPanel());
		}
		
		private ContentPanel createUserSettingPanel(){
			if(userSettingPanel == null){
				String labelStyle = "color: #222;font-weight: bold;line-height:20px";
				
				userSettingPanel = new FormPanel();
				userSettingPanel.setBodyBorder(false);
				userSettingPanel.setBorders(false);
				userSettingPanel.setHeaderVisible(false);
				
				userSettingPanel.setLabelWidth(60);
				userSettingPanel.setLabelSeparator("");

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
				nameField.setLabelStyle(labelStyle);
				nameField.setHeight("30px");
				
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
				mailField.setLabelStyle(labelStyle);
				mailField.setHeight("30px");
				
				nowPasswordField = new TextField<String>();
				nowPasswordField.setPassword(true);
				nowPasswordField.setAllowBlank(false);
				nowPasswordField.setFieldLabel(((Messages) Registry.get(Messages.class
						.getName())).SettingViewport_nowPassword());
				nowPasswordField.getMessages().setBlankText(
						((Messages) Registry.get(Messages.class.getName()))
								.SettingViewport_nowPasswordBlankWarning());
				nowPasswordField.setLabelStyle(labelStyle);
				nowPasswordField.setHeight("30px");
				
				passwordField = new TextField<String>();
				passwordField.setPassword(true);
				passwordField.setAllowBlank(false);
				passwordField.setFieldLabel(((Messages) Registry.get(Messages.class
						.getName())).SettingViewport_password());
				passwordField.getMessages().setBlankText(
						((Messages) Registry.get(Messages.class.getName()))
								.SettingViewport_passwordBlankWarning());
				passwordField.setLabelStyle(labelStyle);
				passwordField.setHeight("30px");

				confirmationField = new TextField<String>();
				confirmationField.setPassword(true);
				confirmationField.setAllowBlank(false);
				confirmationField.setFieldLabel(((Messages) Registry
						.get(Messages.class.getName())).SettingViewport_confirmation());
				confirmationField.getMessages().setBlankText(
						((Messages) Registry.get(Messages.class.getName()))
								.SettingViewport_confirmationBlankWarning());
				confirmationField.setLabelStyle(labelStyle);
				confirmationField.setHeight("30px");
				
				phoneField = new TextField<String>();
				phoneField.setAllowBlank(false);
				phoneField.setEmptyText(((Messages) Registry.get(Messages.class
						.getName())).SettingViewport_phoneEmptyText());
				phoneField.setFieldLabel(((Messages) Registry.get(Messages.class
						.getName())).SettingViewport_phone());
				phoneField.setLabelStyle(labelStyle);
				phoneField.setHeight("30px");
				
				FileUploadField flpldfldNewFileuploadfield = new FileUploadField();
//				FormData fd_txtfldUpload = new FormData("90%");
//				fd_txtfldUpload.setMargins(new Margins(10, 0, 10, 0));
//				layoutContainer.add(flpldfldNewFileuploadfield,fd_txtfldUpload);
				flpldfldNewFileuploadfield.setHeight("30px");
				flpldfldNewFileuploadfield.setFieldLabel("New FileUploadField");
				flpldfldNewFileuploadfield.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).SettingViewport_portrait());
				flpldfldNewFileuploadfield.setLabelStyle(labelStyle);
//				flpldfldNewFileuploadfield.g
				
				final Button submitButton = new Button(
						((Messages) Registry.get(Messages.class.getName()))
								.RegistryViewport_submit());
				submitButton.setStyleName("abstractviewport-btn");
				submitButton.setSize("80px", "30px");
				
				submitButton.addMouseUpHandler(new MouseUpHandler() {
					public void onMouseUp(MouseUpEvent event) {
								if (userSettingPanel.isValid()) {
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
//									appEvent.setData("avatar", uploadfield.get);
									
									appEvent.setHistoryEvent(true);
									Dispatcher.get().dispatch(appEvent);
								}
							}

						});
				
				FormData formData = new FormData("100%");
				formData.setMargins(new Margins(10, 30, 10, 30));
				
				userSettingPanel.add(nameField,formData);
				userSettingPanel.add(mailField,formData);
				userSettingPanel.add(nowPasswordField,formData);
				userSettingPanel.add(passwordField,formData);
				userSettingPanel.add(confirmationField,formData);
				userSettingPanel.add(phoneField,formData);
				userSettingPanel.add(flpldfldNewFileuploadfield,formData);
				
				ButtonBar buttonBar = new ButtonBar();
				buttonBar.setAlignment(HorizontalAlignment.RIGHT);
				userSettingPanel.add(buttonBar,formData);
				
				LayoutContainer btnlc = new LayoutContainer();
				btnlc.setLayout(new FillLayout());
				btnlc.add(submitButton,new FitData(0,0,0,0));
				buttonBar.add(btnlc);
			}
			
			return userSettingPanel;
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
