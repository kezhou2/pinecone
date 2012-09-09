/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.widgets;

import org.apache.commons.codec.binary.Base64;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FormEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.ButtonBar;
import com.extjs.gxt.ui.client.widget.form.FileUploadField;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.FormPanel.Encoding;
import com.extjs.gxt.ui.client.widget.form.FormPanel.Method;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.BoxLayout.BoxLayoutPack;
import com.extjs.gxt.ui.client.widget.layout.CardLayout;
import com.extjs.gxt.ui.client.widget.layout.FillData;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.extjs.gxt.ui.client.widget.layout.FitData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout.HBoxLayoutAlign;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayoutData;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.server.Base64Utils;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.store.client.Images;
import com.tenline.pinecone.platform.web.store.client.Messages;
import com.tenline.pinecone.platform.web.store.client.events.UserEvents;
import com.tenline.pinecone.platform.web.store.client.events.WidgetEvents;

/**
 * @author liugy
 *
 */
public class SettingViewport extends AbstractViewport {
	
	private final String USER_URL="../images/icons/user.png";
	
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
		private Image portraitImg;
		
		private byte[] avatarArray = null;
		
		public final String FILE_UPLOAD_URL = "fileupload.html";
		
		public MainPanel(){
			this.setHeaderVisible(false);
			this.setLayout(new BorderLayout());
			this.setBodyStyleName("abstractviewport-background");
			this.setBodyBorder(false);
			this.setBorders(false);
			
			if(avatarArray == null){
				portraitImg = new Image(USER_URL);
			}
			
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
				
				final LayoutContainer uploadLc = new LayoutContainer();
				uploadLc.setLayout(new BorderLayout());
				uploadLc.setHeight("90px");
				uploadLc.addStyleName("abstractviewport-background");
				
				final FormPanel inputPanel = new FormPanel();
				inputPanel.setBodyBorder(false);
				inputPanel.setBorders(false);
				inputPanel.setHeaderVisible(false);
				BorderLayoutData inputPanelbld = new BorderLayoutData(LayoutRegion.WEST,320);
				inputPanelbld.setMargins(new Margins(0,0,0,0));
				uploadLc.add(inputPanel, inputPanelbld);
				inputPanel.setAction(GWT.getHostPageBaseURL()+FILE_UPLOAD_URL);
				inputPanel.setMethod(Method.POST);
				inputPanel.setEncoding(Encoding.MULTIPART);
				inputPanel.addListener(Events.Submit, new Listener<FormEvent>(){
					@Override
					public void handleEvent(FormEvent be) {
						String array = be.getResultHtml().replace("<pre style=\"word-wrap: break-word; white-space: pre-wrap;\">", "");
						array = array.replace("</pre>", "");
						String styleName = portraitImg.getStyleName();
						portraitImg.setUrl("data:unknown;base64,"+array);
						portraitImg.setStyleName(styleName);
//						avatarArray = Base64Utils.fromBase64(array);
					}
					
				});
				
				final FileUploadField uploadfield = new FileUploadField();
				uploadfield.setHeight("30px");
				uploadfield.setName("file");
				uploadfield.setFieldLabel("New FileUploadField");
				uploadfield.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).SettingViewport_portrait());
				uploadfield.setLabelStyle(labelStyle);
				
				inputPanel.add(uploadfield);
				
				ButtonBar bBar = new ButtonBar();
				bBar.setAlignment(HorizontalAlignment.RIGHT);
				FormData fData = new FormData("100%");
				fData.setMargins(new Margins(10, 10, 10, 10));
				inputPanel.add(bBar,fData);
				
				final Button sButton = new Button(((Messages) Registry.get(Messages.class.getName())).SettingViewport_upload());
				sButton.addMouseUpHandler(new MouseUpHandler() {
					public void onMouseUp(MouseUpEvent event) {
						if(!uploadfield.getValue().equals(""))
						inputPanel.submit();		
					}
				});
				sButton.setStyleName("abstractviewport-btn");
				sButton.setSize("80px", "30px");
				LayoutContainer llc = new LayoutContainer();
				llc.setLayout(new FillLayout());
				llc.add(sButton,new FitData(0,0,0,0));
				bBar.add(llc);
				
				LayoutContainer rlc = new LayoutContainer();
				rlc.setLayout(new BorderLayout());
				BorderLayoutData rlcbld = new BorderLayoutData(LayoutRegion.CENTER);
				rlcbld.setMargins(new Margins(0,0,0,0));
				uploadLc.add(rlc, rlcbld);
				rlc.addStyleName("abstractviewport-background");
				
				LayoutContainer frameLc = new LayoutContainer();
				frameLc.setLayout(new FitLayout());
				BorderLayoutData frameLcbld = new BorderLayoutData(LayoutRegion.WEST,70);
				frameLcbld.setMargins(new Margins(0,0,0,0));
				LayoutContainer portraitLc = new LayoutContainer();
				frameLc.add(portraitLc, frameLcbld);
				portraitLc.setLayout(new FitLayout());
				portraitLc.add(portraitImg);
				portraitLc.addStyleName("appstoreviewport-panel");
				BorderLayoutData portraitLcbld = new BorderLayoutData(LayoutRegion.WEST,70);
				portraitLcbld.setMargins(new Margins(10,10,10,10));
				rlc.add(frameLc, portraitLcbld);
				
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

									if(avatarArray != null){
										appEvent.setData("avatar", avatarArray);
									}
									
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
				FormData formData1 = new FormData("100%");
				formData1.setMargins(new Margins(0, 0, 0, 0));
				userSettingPanel.add(uploadLc,formData1);
				
				ButtonBar buttonBar = new ButtonBar();
				buttonBar.setAlignment(HorizontalAlignment.RIGHT);
				userSettingPanel.add(buttonBar,formData1);
				
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
				if(user.getAvatar() == null){
//					String base64 = Base64Utils.toBase64(user.getAvatar());
//					base64 = base64.replace('$', '+');
//					base64 = base64.replace('_', '/');
//					portraitImg.setUrl("data:unknown;base64,"+base64);
				}
			}
		}
	}
}
