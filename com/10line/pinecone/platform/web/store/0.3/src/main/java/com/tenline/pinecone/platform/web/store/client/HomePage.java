package com.tenline.pinecone.platform.web.store.client;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.HtmlContainer;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FillData;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.extjs.gxt.ui.client.widget.layout.FitData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout.HBoxLayoutAlign;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayoutData;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.extjs.gxt.ui.client.widget.layout.TableData;
import com.extjs.gxt.ui.client.widget.layout.TableLayout;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayout.VBoxLayoutAlign;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayoutData;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.store.client.window.RegisterWindow;
import com.tenline.pinecone.platform.web.store.shared.UserInfo;

public class HomePage extends Viewport {
	public HomePage() {
//		setSize("1000", "800");
//		setHeaderVisible(false);
		setEnableScroll(true);
//		setSize("auto", "auto");
		FitLayout fitLayout = new FitLayout();
		fitLayout.setExtraStyle("m-homepage-background");
		setLayout(fitLayout);
		
		LayoutContainer contentPanel = new LayoutContainer();
//		contentPanel.setHeaderVisible(false);
		FitLayout fl_contentPanel = new FitLayout();
		fl_contentPanel.setExtraStyle("m-homepage-background");
		contentPanel.setLayout(fl_contentPanel);
		
		LayoutContainer layoutContainer_1 = new LayoutContainer();
		VBoxLayout vbl_layoutContainer_1 = new VBoxLayout();
		vbl_layoutContainer_1.setVBoxLayoutAlign(VBoxLayoutAlign.CENTER);
		layoutContainer_1.setLayout(vbl_layoutContainer_1);
		
		LayoutContainer layoutContainer_2 = new LayoutContainer();
		BorderLayout bl_layoutContainer_2 = new BorderLayout();
		bl_layoutContainer_2.setExtraStyle("m-homepage-main");
		layoutContainer_2.setLayout(bl_layoutContainer_2);
		
		LayoutContainer layoutContainer_3 = new LayoutContainer();
		layoutContainer_3.setLayout(new BorderLayout());
		
		LayoutContainer layoutContainer_4 = new LayoutContainer();
		FitLayout fl_layoutContainer_4 = new FitLayout();
		fl_layoutContainer_4.setExtraStyle("m-homepage-main-middle");
		layoutContainer_4.setLayout(fl_layoutContainer_4);
		
		LayoutContainer layoutContainer_19 = new LayoutContainer();
		HBoxLayout hbl_layoutContainer_19 = new HBoxLayout();
		hbl_layoutContainer_19.setHBoxLayoutAlign(HBoxLayoutAlign.MIDDLE);
		layoutContainer_19.setLayout(hbl_layoutContainer_19);
		
		HtmlContainer htmlContainer = new HtmlContainer("" +
				"<div class='welcome_title_1'>" +
					"<img title='' alt='' src='../img/welcome_title_1.png'>" +
					"<p class='welcome_title_description'>在这里，我们将带你进入有趣的物联网世界!</p>"+
				"</div>" +
				"");
		layoutContainer_19.add(htmlContainer, new HBoxLayoutData(0, 0, 0, 20));
		htmlContainer.setSize("400", "200");
		layoutContainer_4.add(layoutContainer_19, new FitData(0, 15, 0, 15));
		layoutContainer_3.add(layoutContainer_4, new BorderLayoutData(LayoutRegion.SOUTH, 250.0f));
		
		LayoutContainer layoutContainer_5 = new LayoutContainer();
		layoutContainer_5.setLayout(new FitLayout());
		
		Image image_1 = new Image("../img/logo-1.png");
		image_1.setStyleName("m-homepage-main-logo");
		layoutContainer_5.add(image_1, new FitData(15, 25, 20, 25));
		layoutContainer_3.add(layoutContainer_5, new BorderLayoutData(LayoutRegion.WEST, 250.0f));
		
		LayoutContainer layoutContainer_6 = new LayoutContainer();
		layoutContainer_6.setLayout(new FillLayout(Orientation.HORIZONTAL));
		
		HTML htmlNewHtml = new HTML("<a href='#'>主 页</a>", true);
		htmlNewHtml.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		layoutContainer_6.add(htmlNewHtml);
		BorderLayoutData bld_layoutContainer_6 = new BorderLayoutData(LayoutRegion.CENTER);
		bld_layoutContainer_6.setMargins(new Margins(50, 80, 10, 140));
		
		HTML htmlNewHtml_1 = new HTML("<a href='#'>服  务</a>", true);
		htmlNewHtml_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		layoutContainer_6.add(htmlNewHtml_1);
		
		HTML htmlNewHtml_2 = new HTML("<a href='#'>关  于</a>", true);
		htmlNewHtml_2.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		layoutContainer_6.add(htmlNewHtml_2);
		
		HTML htmlNewHtml_3 = new HTML("<a href='#'>联  系</a>", true);
		htmlNewHtml_3.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		layoutContainer_6.add(htmlNewHtml_3);
		layoutContainer_3.add(layoutContainer_6, bld_layoutContainer_6);
		layoutContainer_2.add(layoutContainer_3, new BorderLayoutData(LayoutRegion.NORTH, 340.0f));
		
		LayoutContainer layoutContainer_7 = new LayoutContainer();
		FillLayout fl_layoutContainer_7 = new FillLayout(Orientation.HORIZONTAL);
		fl_layoutContainer_7.setExtraStyle("m-homepage-center");
		layoutContainer_7.setLayout(fl_layoutContainer_7);
		BorderLayoutData bld_layoutContainer_7 = new BorderLayoutData(LayoutRegion.CENTER);
		bld_layoutContainer_7.setMargins(new Margins(0, 0, 0, 0));
		
		LayoutContainer layoutContainer_8 = new LayoutContainer();
		VBoxLayout vbl_layoutContainer_8 = new VBoxLayout();
		vbl_layoutContainer_8.setExtraStyle("m-homepage-logon");
		vbl_layoutContainer_8.setVBoxLayoutAlign(VBoxLayoutAlign.CENTER);
		layoutContainer_8.setLayout(vbl_layoutContainer_8);
		
		LayoutContainer layoutContainer_10 = new LayoutContainer();
		BorderLayout bl_layoutContainer_10 = new BorderLayout();
		layoutContainer_10.setLayout(bl_layoutContainer_10);
		
		LayoutContainer layoutContainer_11 = new LayoutContainer();
		RowLayout rl_layoutContainer_11 = new RowLayout(Orientation.HORIZONTAL);
		layoutContainer_11.setLayout(rl_layoutContainer_11);
		
		Image image_2 = new Image("../img/logon.png");
		layoutContainer_11.add(image_2, new RowData(45.0, 45.0, new Margins(0, 0, 0, 0)));
		image_2.setSize("32", "32");
		BorderLayoutData bld_layoutContainer_11 = new BorderLayoutData(LayoutRegion.NORTH, 50.0f);
		bld_layoutContainer_11.setMargins(new Margins(5, 10, 0, 10));
		
		HTML htmlNewHtml_4 = new HTML("登陆", true);
		htmlNewHtml_4.setStyleName("m-homepage-title");
		layoutContainer_11.add(htmlNewHtml_4, new RowData(100.0, 40.0, new Margins(10, 0, 0, 10)));
		layoutContainer_10.add(layoutContainer_11, bld_layoutContainer_11);
		
		LayoutContainer layoutContainer_12 = new LayoutContainer();
		layoutContainer_12.setLayout(new TableLayout(5));
		BorderLayoutData bld_layoutContainer_12 = new BorderLayoutData(LayoutRegion.CENTER);
		bld_layoutContainer_12.setMargins(new Margins(5, 5, 5, 5));
		
		Label label = new Label("用户名");
		label.setStyleName("m-homepage-logon-text");
		TableData td_label = new TableData();
		td_label.setWidth("80");
		td_label.setPadding(5);
		layoutContainer_12.add(label, td_label);
		label.setSize("100", "35");
		
		final TextField<String> userTextfield = new TextField<String>();
		TableData td_txtfldNewTextfield = new TableData();
		td_txtfldNewTextfield.setPadding(5);
		td_txtfldNewTextfield.setColspan(4);
		layoutContainer_12.add(userTextfield, td_txtfldNewTextfield);
		userTextfield.setSize("180", "30");
		userTextfield.setFieldLabel("New TextField");
		
		Label label_1 = new Label("密码");
		label_1.setStyleName("m-homepage-logon-text");
		TableData td_label_1 = new TableData();
		td_label_1.setWidth("80");
		td_label_1.setPadding(5);
		layoutContainer_12.add(label_1, td_label_1);
		
		final TextField<String> pwdTextfield = new TextField<String>();
		pwdTextfield.setPassword(true);
		TableData td_txtfldNewTextfield_1 = new TableData();
		td_txtfldNewTextfield_1.setPadding(5);
		td_txtfldNewTextfield_1.setColspan(4);
		layoutContainer_12.add(pwdTextfield, td_txtfldNewTextfield_1);
		pwdTextfield.setSize("180", "30");
		pwdTextfield.setFieldLabel("New TextField");
		layoutContainer_12.add(new Text());
		layoutContainer_12.add(new Text());
		
		final Button loginButton = new Button("登陆");
		loginButton.addListener(Events.Select, new Listener<ButtonEvent>() {
			public void handleEvent(ButtonEvent e) {
				if(userTextfield.getValue().equals("") || 
						pwdTextfield.getValue().equals("")){
					return;
				}
					
				//TODO 登陆
				loginButton.setEnabled(false);
				EnvConfig.getPineconeService().login(userTextfield.getValue(), pwdTextfield.getValue(), 
						new AsyncCallback<User>(){

					@Override
					public void onFailure(Throwable caught) {
						MessageBox.info("错误", "访问后台用户登录服务失败", null);
						loginButton.setEnabled(true);
					}

					@Override
					public void onSuccess(User result) {
						if(result == null){
							MessageBox.info("错误", "用户登录信息错误", null);
							loginButton.setEnabled(true);
							return;
						}
						HomePage.this.removeFromParent();
						EnvConfig.setLoginUser(new UserInfo(result));
						RootPanel.get().add(new MainFrame(result));
						loginButton.setEnabled(true);
					}
					
				});
			}
		});
		TableData td_btnNewButton = new TableData();
		td_btnNewButton.setHorizontalAlign(HorizontalAlignment.RIGHT);
		layoutContainer_12.add(loginButton, td_btnNewButton);
		loginButton.setSize("50", "30");
		
		Button button = new Button("注册");
		button.addListener(Events.Select, new Listener<ButtonEvent>() {
			public void handleEvent(ButtonEvent e) {
				RegisterWindow rw = new RegisterWindow();
				rw.show();
				rw.center();
				
//				Demo w = new Demo();
//				w.show();
//				w.center();
			}
		});
		TableData td_button = new TableData();
		td_button.setHorizontalAlign(HorizontalAlignment.RIGHT);
		layoutContainer_12.add(button, td_button);
		button.setSize("50", "30");
		layoutContainer_12.add(new Text());
		layoutContainer_10.add(layoutContainer_12, bld_layoutContainer_12);
		layoutContainer_8.add(layoutContainer_10, new VBoxLayoutData(20, 0, 0, 0));
		layoutContainer_10.setSize("310", "185");
		layoutContainer_7.add(layoutContainer_8);
		
		LayoutContainer layoutContainer_9 = new LayoutContainer();
		layoutContainer_9.setLayout(new VBoxLayout());
		
		LayoutContainer layoutContainer_14 = new LayoutContainer();
		layoutContainer_14.setLayout(new RowLayout(Orientation.VERTICAL));
		
		LayoutContainer layoutContainer = new LayoutContainer();
		layoutContainer.setLayout(new RowLayout(Orientation.HORIZONTAL));
		
		Image image_3 = new Image("../img/news-reader.png");
		layoutContainer.add(image_3, new RowData(40.0, 40.0, new Margins()));
		
		HTML html = new HTML("新闻", true);
		html.setStyleName("m-homepage-title");
		layoutContainer.add(html, new RowData(60.0, 40.0, new Margins(0, 0, 0, 10)));
		layoutContainer_14.add(layoutContainer, new RowData(Style.DEFAULT, 40.0, new Margins()));
		
		LayoutContainer layoutContainer_15 = new LayoutContainer();
		layoutContainer_15.setLayout(new FitLayout());
		
		HTML htmlNewHtml_5 = new HTML("<a href='#'><newsLink>松果网应用平台上线</newsLink></a>", true);
		layoutContainer_15.add(htmlNewHtml_5, new FitData(0, 0, 0, 20));
		
		layoutContainer_14.add(layoutContainer_15);
		
		LayoutContainer layoutContainer_16 = new LayoutContainer();
		layoutContainer_16.setLayout(new FitLayout());
		
		HTML htmlNewHtml_6 = new HTML("<a href='#'><newsLink>新闻1</newsLink></a>", true);
		layoutContainer_16.add(htmlNewHtml_6, new FitData(0, 0, 0, 20));
		layoutContainer_14.add(layoutContainer_16);
		
		LayoutContainer layoutContainer_17 = new LayoutContainer();
		layoutContainer_17.setLayout(new FitLayout());
		
		HTML htmlNewHtml_7 = new HTML("<a href='#'><newsLink>新闻2</newsLink></a>", true);
		layoutContainer_17.add(htmlNewHtml_7, new FitData(0, 0, 0, 20));
		
		layoutContainer_14.add(layoutContainer_17);
		
		LayoutContainer layoutContainer_18 = new LayoutContainer();
		layoutContainer_18.setLayout(new FitLayout());
		
		HTML htmlNewHtml_8 = new HTML("<a href='#'><newsLink>新闻3</newsLink></a>", true);
		layoutContainer_18.add(htmlNewHtml_8, new FitData(0, 0, 0, 20));
		
		layoutContainer_14.add(layoutContainer_18);
		layoutContainer_9.add(layoutContainer_14, new VBoxLayoutData(20, 0, 0, 0));
		layoutContainer_14.setWidth("350");
		layoutContainer_7.add(layoutContainer_9, new FillData(0, 0, 0, 0));
		layoutContainer_2.add(layoutContainer_7, bld_layoutContainer_7);
		
		LayoutContainer layoutContainer_13 = new LayoutContainer();
		FitLayout fl_layoutContainer_13 = new FitLayout();
		fl_layoutContainer_13.setExtraStyle("m-homepage-right-reserved-border");
		layoutContainer_13.setLayout(fl_layoutContainer_13);
		
		Label lblNewLabel = new Label("Pinecone@Ten Line, All Right Reserved.");
		lblNewLabel.setStyleName("m-homepage-right-reserved");
		layoutContainer_13.add(lblNewLabel, new FitData(0, 15, 0, 15));
		layoutContainer_2.add(layoutContainer_13, new BorderLayoutData(LayoutRegion.SOUTH, 40.0f));
		layoutContainer_1.add(layoutContainer_2);
		layoutContainer_2.setSize("800", "640");
		contentPanel.add(layoutContainer_1, new FitData(0, 0, 0, 0));
		add(contentPanel, new FitData(0, 0, 0, 0));
	}
	
}
