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
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.button.Button;
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
import com.google.gwt.user.client.ui.Image;
import com.tenline.pinecone.platform.web.store.client.Images;
import com.tenline.pinecone.platform.web.store.client.Messages;
import com.tenline.pinecone.platform.web.store.client.events.LoginViewEvents;

/**
 * @author Bill
 *
 */
public class LoginViewport extends Viewport {
	
//	public final String videoUrl = "http://player.youku.com/player.php/sid/XMzMzNjU3Mzgw/v.swf";
	public final String videoUrl = "";
	
	public LoginViewport() {
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
		
		LayoutContainer eastLayoutContainer = new LayoutContainer();
		eastLayoutContainer.setLayout(new FitLayout());
		LayoutContainer layoutContainer_1 = new LayoutContainer();
		eastLayoutContainer.add(layoutContainer_1, new FitData(0, 0, 0, 0));
		FormLayout fl_layoutContainer_1 = new FormLayout();
		fl_layoutContainer_1.setLabelSeparator("");
		fl_layoutContainer_1.setLabelAlign(LabelAlign.RIGHT);
		layoutContainer_1.setLayout(fl_layoutContainer_1);
		
		final TextField<String> nameText = new TextField<String>();
		FormData fd_txtfldName = new FormData("80%");
		fd_txtfldName.setMargins(new Margins(50, 10, 10, 10));
		layoutContainer_1.add(nameText, fd_txtfldName);
		nameText.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).account());
		nameText.setAllowBlank(false);
		
		final TextField<String> pwdText = new TextField<String>();
		pwdText.setPassword(true);
		FormData fd_txtfldPassword = new FormData("80%");
		fd_txtfldPassword.setMargins(new Margins(10, 10, 10, 10));
		layoutContainer_1.add(pwdText, fd_txtfldPassword);
		pwdText.setFieldLabel(((Messages) Registry.get(Messages.class.getName())).password());
		BorderLayoutData bld_layoutContainer_1 = new BorderLayoutData(LayoutRegion.EAST, 400.0f);
		bld_layoutContainer_1.setMargins(new Margins(0, 0, 0, 0));
		pwdText.setAllowBlank(false);
		
		LayoutContainer layoutContainer_3 = new LayoutContainer();
		HBoxLayout hbl_layoutContainer_3 = new HBoxLayout();
		hbl_layoutContainer_3.setPack(BoxLayoutPack.END);
		layoutContainer_3.setLayout(hbl_layoutContainer_3);
		
		Button loginButton = new Button(((Messages) Registry.get(Messages.class.getName())).login());
		layoutContainer_3.add(loginButton, new HBoxLayoutData(0, 10, 0, 0));
		loginButton.addSelectionListener(new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent event) {
				AppEvent appEvent = new AppEvent(LoginViewEvents.USER_LOGIN);
				appEvent.setData("name", nameText.getValue());
				appEvent.setData("password", pwdText.getValue());
				Dispatcher.get().dispatch(appEvent);
			}
			
		});
		
		Button regButton = new Button(((Messages) Registry.get(Messages.class.getName())).register());
		layoutContainer_3.add(regButton, new HBoxLayoutData(0, 0, 0, 10));
		FormData fd_layoutContainer_3 = new FormData("100%");
		fd_layoutContainer_3.setMargins(new Margins(0, 90, 0, 50));
		layoutContainer_1.add(layoutContainer_3, fd_layoutContainer_3);
		add(eastLayoutContainer, bld_layoutContainer_1);
		
		regButton.addSelectionListener(new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent event) {
				AppEvent appEvent = new AppEvent(LoginViewEvents.GO_TO_REGISTRY);
				appEvent.setHistoryEvent(true);
				Dispatcher.get().dispatch(appEvent);
			}
		});
		
		LayoutContainer layoutContainer_2 = new LayoutContainer();
		layoutContainer_2.setLayout(new FitLayout());
		
		ContentPanel VideoContentpanel = new ContentPanel();
		VideoContentpanel.setHeaderVisible(false);
		VideoContentpanel.setHeading("Video");
		VideoContentpanel.setCollapsible(true);
		layoutContainer_2.add(VideoContentpanel);
		VideoContentpanel.setUrl(videoUrl);
		add(layoutContainer_2, new BorderLayoutData(LayoutRegion.CENTER));
		layoutContainer_2.setBorders(false);
	}

}
