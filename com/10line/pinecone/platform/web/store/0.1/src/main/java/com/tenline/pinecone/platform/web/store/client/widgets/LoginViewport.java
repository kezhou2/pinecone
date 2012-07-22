/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.widgets;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.Validator;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.BoxLayout.BoxLayoutPack;
import com.extjs.gxt.ui.client.widget.layout.FitData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayoutData;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.ui.Button;
import com.tenline.pinecone.platform.web.store.client.Messages;
import com.tenline.pinecone.platform.web.store.client.events.UserEvents;
import com.tenline.pinecone.platform.web.store.client.events.WidgetEvents;

/**
 * @author Bill
 *
 */
public class LoginViewport extends AbstractViewport {
	
	private MainPanel mainPanel;
	
	public LoginViewport() {
		mainPanel = new MainPanel();
		BorderLayoutData bld = new BorderLayoutData(LayoutRegion.CENTER);
		bld.setMargins(new Margins(0,10,0,10));
		body.add(mainPanel, bld);
	}
	
	public void logout(){
		mainPanel.logout();
	}
	
	private class MainPanel extends ContentPanel{
		
		public final String videoUrl = "http://player.youku.com/player.php/sid/XMzMzNjU3Mzgw/v.swf";
//		public final String videoUrl = "";
		
		private TextField<String> nameText;
		private TextField<String> pwdText;
		
		public MainPanel(){
			setLayout(new BorderLayout());
			setBorders(false);
			setBodyBorder(false);
			setHeaderVisible(false);
			this.setBodyStyleName("loginviewport-background");
			
			LayoutContainer loginLayoutContainer = new LayoutContainer();
			loginLayoutContainer.setLayout(new BorderLayout());
			loginLayoutContainer.setBorders(true);
			loginLayoutContainer.addStyleName("loginviewport-input-panel");
			
			LayoutContainer loginHeader = new LayoutContainer();
			loginHeader.addStyleName("loginviewport-title-panel");
			BorderLayoutData headerBld = new BorderLayoutData(LayoutRegion.NORTH,50);
			loginLayoutContainer.add(loginHeader,headerBld);
			Text titleText = new Text(((Messages) Registry.get(Messages.class.getName())).LoginViewport_title());
			loginHeader.setLayout(new HBoxLayout());
			loginHeader.add(titleText,new HBoxLayoutData(8, 0, 5, 10));
			loginHeader.setBorders(true);
			titleText.addStyleName("loginviewport-title-text");
			
			LayoutContainer formLayoutContainer = new LayoutContainer();
			BorderLayoutData bld = new BorderLayoutData(LayoutRegion.CENTER);
			loginLayoutContainer.add(formLayoutContainer, bld);
	
			FormLayout fl_layoutContainer_1 = new FormLayout();
			fl_layoutContainer_1.setLabelSeparator("");
			fl_layoutContainer_1.setLabelAlign(LabelAlign.RIGHT);
			formLayoutContainer.setLayout(fl_layoutContainer_1);
			
			Text txtLoginToContinue = new Text(((Messages) Registry.get(Messages.class.getName())).LoginViewport_loginToContinue());
			FormData fd_txtLoginToContinue = new FormData("100%");
			fd_txtLoginToContinue.setMargins(new Margins(30, 0, 0, 20));
			formLayoutContainer.add(txtLoginToContinue, fd_txtLoginToContinue);
			//style
			txtLoginToContinue.addStyleName("loginviewport-tooltip-text-yellow");
			
			nameText = new TextField<String>();
			FormData fd_txtfldName = new FormData("90%");
			fd_txtfldName.setMargins(new Margins(20, 10, 10, 0));
			formLayoutContainer.add(nameText, fd_txtfldName);
			nameText.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).LoginViewport_account());
			nameText.setAllowBlank(false);
			//style
			nameText.setLabelStyle("font-size: 18px;font-weight: bold;line-height: 18px;margin-bottom: 10px;color: #4D5762;	position: relative;	word-spacing: -0.1em;");
			nameText.setHeight("30px");
			nameText.setValidator(new Validator(){

				@Override
				public String validate(Field<?> field, String value) {
					return null;
				}
				
			});
			nameText.setValidateOnBlur(true);
			
			pwdText = new TextField<String>();
			pwdText.setPassword(true);
			FormData fd_txtfldPassword = new FormData("90%");
			fd_txtfldPassword.setMargins(new Margins(20, 10, 10, 0));
			formLayoutContainer.add(pwdText, fd_txtfldPassword);
			pwdText.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).LoginViewport_password());
			BorderLayoutData bld_layoutContainer_1 = new BorderLayoutData(LayoutRegion.EAST, 400.0f);
			bld_layoutContainer_1.setMargins(new Margins(10, 20, 10, 10));
			pwdText.setAllowBlank(false);
			//style
			pwdText.setLabelStyle("font-size: 18px;font-weight: bold;line-height: 18px;margin-bottom: 10px;color: #4D5762;	position: relative;	word-spacing: -0.1em;");
			pwdText.setHeight("30px");
			
			LayoutContainer layoutContainer_3 = new LayoutContainer();
			HBoxLayout hbl_layoutContainer_3 = new HBoxLayout();
			hbl_layoutContainer_3.setPack(BoxLayoutPack.END);
			layoutContainer_3.setLayout(hbl_layoutContainer_3);
			
			Button loginButton = new Button(((Messages) Registry.get(Messages.class.getName())).LoginViewport_login());
			layoutContainer_3.add(loginButton, new HBoxLayoutData(0, 0, 0, 0));
			loginButton.addMouseUpHandler(new MouseUpHandler() {
				public void onMouseUp(MouseUpEvent event) {
					AppEvent appEvent = new AppEvent(UserEvents.LOGIN);
					appEvent.setData("email", nameText.getValue());
					appEvent.setData("password", pwdText.getValue());
					Dispatcher.get().dispatch(appEvent);
				}
			});
			//style
			loginButton.setStyleName("btn-green");
			loginButton.setSize("60px", "30px");
			
			FormData fd_layoutContainer_3 = new FormData("100%");
			fd_layoutContainer_3.setMargins(new Margins(0, 50, 0, 50));
			formLayoutContainer.add(layoutContainer_3, fd_layoutContainer_3);
			
			Text txtRegister = new Text(((Messages) Registry.get(Messages.class.getName())).LoginViewport_tipsRegister());
			FormData fd_txtRegister = new FormData("100%");
			fd_txtRegister.setMargins(new Margins(30, 0, 10, 20));
			txtRegister.addStyleName("loginviewport-tooltip-text-blue");
			formLayoutContainer.add(txtRegister, fd_txtRegister);
			
			LayoutContainer layoutContainer_4 = new LayoutContainer();
			HBoxLayout hbl_layoutContainer_4 = new HBoxLayout();
			hbl_layoutContainer_4.setPack(BoxLayoutPack.END);
			layoutContainer_4.setLayout(hbl_layoutContainer_4);
				
			Button regButton = new Button(((Messages) Registry.get(Messages.class.getName())).LoginViewport_register());
			layoutContainer_4.add(regButton, new HBoxLayoutData(0, 0, 0, 10));
			regButton.addMouseUpHandler(new MouseUpHandler() {
				public void onMouseUp(MouseUpEvent event) {
					AppEvent appEvent = new AppEvent(WidgetEvents.UPDATE_REGISTER_TO_PANEL);
					appEvent.setHistoryEvent(true);
					Dispatcher.get().dispatch(appEvent);
				}
			});
			//style
			regButton.setStyleName("btn-green");
			regButton.setSize("60px", "30px");
				
			FormData fd_layoutContainer_4 = new FormData("100%");
			fd_layoutContainer_4.setMargins(new Margins(0, 50, 0, 50));
			formLayoutContainer.add(layoutContainer_4, fd_layoutContainer_4);
			
			add(loginLayoutContainer, bld_layoutContainer_1);
			
			LayoutContainer videoLayoutContainer = new LayoutContainer();
			videoLayoutContainer.setLayout(new FitLayout());
			
			ContentPanel videoContentpanel = new ContentPanel();
			videoContentpanel.setHeaderVisible(false);
			videoContentpanel.setHeading("Video");
			videoContentpanel.setCollapsible(true);
			videoLayoutContainer.add(videoContentpanel, new FitData(15,15,15,15));
			videoLayoutContainer.add(videoContentpanel);
			videoContentpanel.setUrl(videoUrl);
			add(videoLayoutContainer, new BorderLayoutData(LayoutRegion.CENTER));
			videoLayoutContainer.setBorders(false);
			videoContentpanel.setBodyBorder(false);
			videoContentpanel.addStyleName("video-panel");
		}
		
		public void logout(){
			nameText.setValue("");
			pwdText.setValue("");
		}
	}

}
