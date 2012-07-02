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
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.form.TextField;
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
	
	public LoginViewport() {
		MainPanel mainPanel = new MainPanel();
		body.add(mainPanel, new BorderLayoutData(LayoutRegion.CENTER));
	}
	
	private class MainPanel extends ContentPanel{
		
//		public final String videoUrl = "http://player.youku.com/player.php/sid/XMzMzNjU3Mzgw/v.swf";
		public final String videoUrl = "";
		
		public MainPanel(){
			setLayout(new BorderLayout());
			setBorders(false);
			setHeaderVisible(false);
			
			LayoutContainer loginLayoutContainer = new LayoutContainer();
			loginLayoutContainer.setLayout(new FitLayout());
			LayoutContainer layoutContainer_1 = new LayoutContainer();
			loginLayoutContainer.add(layoutContainer_1, new FitData(0, 0, 0, 0));
			FormLayout fl_layoutContainer_1 = new FormLayout();
			fl_layoutContainer_1.setLabelSeparator("");
			fl_layoutContainer_1.setLabelAlign(LabelAlign.RIGHT);
			layoutContainer_1.setLayout(fl_layoutContainer_1);
			
			Text txtLoginToContinue = new Text(((Messages) Registry.get(Messages.class.getName())).LoginViewport_loginToContinue());
			FormData fd_txtLoginToContinue = new FormData("100%");
			fd_txtLoginToContinue.setMargins(new Margins(50, 0, 0, 40));
			layoutContainer_1.add(txtLoginToContinue, fd_txtLoginToContinue);
			//style
//			txtLoginToContinue.addStyleName("LoginViewMessage");
			txtLoginToContinue.setStyleAttribute("font-size", "21px");;
			txtLoginToContinue.setStyleAttribute("font-weight", "bold");
			txtLoginToContinue.setStyleAttribute("line-height", "28px");
			txtLoginToContinue.setStyleAttribute("margin-bottom", "18px");
			txtLoginToContinue.setStyleAttribute("color", "#4D5762");
			txtLoginToContinue.setStyleAttribute("position", "relative");
			txtLoginToContinue.setStyleAttribute("word-spacing", "-0.1em");
			
			final TextField<String> nameText = new TextField<String>();
			FormData fd_txtfldName = new FormData("80%");
			fd_txtfldName.setMargins(new Margins(20, 10, 10, 10));
			layoutContainer_1.add(nameText, fd_txtfldName);
			nameText.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).LoginViewport_account());
			nameText.setAllowBlank(false);
			//style
			nameText.setLabelStyle("font-size: 18px;font-weight: bold;line-height: 18px;margin-bottom: 10px;color: #4D5762;	position: relative;	word-spacing: -0.1em;");
			nameText.setHeight("30px");
			
			final TextField<String> pwdText = new TextField<String>();
			pwdText.setPassword(true);
			FormData fd_txtfldPassword = new FormData("80%");
			fd_txtfldPassword.setMargins(new Margins(20, 10, 10, 10));
			layoutContainer_1.add(pwdText, fd_txtfldPassword);
			pwdText.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).LoginViewport_password());
			BorderLayoutData bld_layoutContainer_1 = new BorderLayoutData(LayoutRegion.EAST, 400.0f);
			bld_layoutContainer_1.setMargins(new Margins(0, 0, 0, 0));
			pwdText.setAllowBlank(false);
			//style
			pwdText.setLabelStyle("font-size: 18px;font-weight: bold;line-height: 18px;margin-bottom: 10px;color: #4D5762;	position: relative;	word-spacing: -0.1em;");
			pwdText.setHeight("26px");
			
			LayoutContainer layoutContainer_3 = new LayoutContainer();
			HBoxLayout hbl_layoutContainer_3 = new HBoxLayout();
			hbl_layoutContainer_3.setPack(BoxLayoutPack.END);
			layoutContainer_3.setLayout(hbl_layoutContainer_3);
			
			Button loginButton = new Button(((Messages) Registry.get(Messages.class.getName())).LoginViewport_login());
			layoutContainer_3.add(loginButton, new HBoxLayoutData(0, 10, 0, 0));
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
			
			Button regButton = new Button(((Messages) Registry.get(Messages.class.getName())).LoginViewport_logout());
			layoutContainer_3.add(regButton, new HBoxLayoutData(0, 0, 0, 10));
			FormData fd_layoutContainer_3 = new FormData("100%");
			fd_layoutContainer_3.setMargins(new Margins(0, 90, 0, 50));
			layoutContainer_1.add(layoutContainer_3, fd_layoutContainer_3);
			add(loginLayoutContainer, bld_layoutContainer_1);
			
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
			
			LayoutContainer videoLayoutContainer = new LayoutContainer();
			videoLayoutContainer.setLayout(new FitLayout());
			
			ContentPanel VideoContentpanel = new ContentPanel();
			VideoContentpanel.setHeaderVisible(false);
			VideoContentpanel.setHeading("Video");
			VideoContentpanel.setCollapsible(true);
			videoLayoutContainer.add(VideoContentpanel, new FitData(10,10,10,10));
			videoLayoutContainer.add(VideoContentpanel);
			VideoContentpanel.setUrl(videoUrl);
			add(videoLayoutContainer, new BorderLayoutData(LayoutRegion.CENTER));
			videoLayoutContainer.setBorders(false);
		}
	}

}
