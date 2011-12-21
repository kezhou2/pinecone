package com.tenline.pinecone.platform.web.store.client;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.VerticalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.TableData;
import com.extjs.gxt.ui.client.widget.layout.TableLayout;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.tenline.pinecone.platform.model.User;

public class RegisterWindow extends Window {
	
	private TextField<String> userNameTextfield;
	private TextField<String> mailTextfield;
	private TextField<String> passwordTextfield;
	private TextField<String> confirmPwdTextfield;
	
	private User user = new User();
	private LayoutContainer layoutContainer;
	private Text infoText;
	
	public RegisterWindow() {
		setHeading("<header1>用户注册</header1>");
		setSize("320", "335");
		FitLayout fitLayout = new FitLayout();
		fitLayout.setExtraStyle("header1");
		setLayout(fitLayout);
		
		LayoutContainer layoutContainer_4 = new LayoutContainer();
		TableLayout tl_layoutContainer_4 = new TableLayout(2);
		tl_layoutContainer_4.setCellSpacing(2);
		tl_layoutContainer_4.setCellPadding(5);
		layoutContainer_4.setLayout(tl_layoutContainer_4);
		
		Text txtNewText = new Text("用户名：");
		TableData td_txtNewText = new TableData();
		td_txtNewText.setVerticalAlign(VerticalAlignment.MIDDLE);
		td_txtNewText.setHorizontalAlign(HorizontalAlignment.RIGHT);
		td_txtNewText.setStyleName("m-registerwindow-text");
		layoutContainer_4.add(txtNewText, td_txtNewText);
		txtNewText.setSize("80", "20");
		
		userNameTextfield = new TextField<String>();
		layoutContainer_4.add(userNameTextfield);
		userNameTextfield.setSize("200", "30");
		userNameTextfield.setFieldLabel("New TextField");
		
		Text txtNewText_1 = new Text("邮箱：");
		TableData td_txtNewText_1 = new TableData();
		td_txtNewText_1.setHorizontalAlign(HorizontalAlignment.RIGHT);
		td_txtNewText_1.setStyleName("m-registerwindow-text");
		layoutContainer_4.add(txtNewText_1, td_txtNewText_1);
		txtNewText_1.setSize("80", "20");
		
		mailTextfield = new TextField<String>();
		layoutContainer_4.add(mailTextfield);
		mailTextfield.setSize("200", "30");
		mailTextfield.setFieldLabel("New TextField");
		
		final Button checkButton = new Button("检查是否可用");
		checkButton.addListener(Events.Select, new Listener<ButtonEvent>() {
			public void handleEvent(ButtonEvent e) {	
				if(checkAvailable()){
					if(user.getName() != null){
						checkButton.setEnabled(false);
						checkButton.setText("监测中...");
						EnvConfig.getPineconeService().
							isUserExist(user, new AsyncCallback<Boolean>(){
	
								@Override
								public void onFailure(Throwable caught) {
									MessageBox.info("错误", "调用后台服务查询用户是否存在失败", null);
									checkButton.setEnabled(true);
									checkButton.setText("检查是否可用");
								}
	
								@Override
								public void onSuccess(Boolean result) {
									if(result){
										infoText.setText("用户名已存在或者邮箱地址已经使用名");
									}else {
										infoText.setText("用户名和邮箱没有被使用");
									}
									checkButton.setEnabled(true);
									checkButton.setText("检查是否可用");
								}
							
						});
					}
				}
			}
		});
		layoutContainer_4.add(new Text());
		TableData td_checkButton = new TableData();
		td_checkButton.setHorizontalAlign(HorizontalAlignment.RIGHT);
		layoutContainer_4.add(checkButton, td_checkButton);
		checkButton.setHeight("30");
		
		Text text = new Text("设置密码：");
		TableData td_text = new TableData();
		td_text.setHorizontalAlign(HorizontalAlignment.RIGHT);
		td_text.setStyleName("m-registerwindow-text");
		layoutContainer_4.add(text, td_text);
		text.setSize("80", "20");
		
		passwordTextfield = new TextField<String>();
		passwordTextfield.setPassword(true);
		layoutContainer_4.add(passwordTextfield);
		passwordTextfield.setSize("200", "30");
		passwordTextfield.setFieldLabel("New TextField");
		
		Text text_1 = new Text("确认密码：");
		TableData td_text_1 = new TableData();
		td_text_1.setHorizontalAlign(HorizontalAlignment.RIGHT);
		td_text_1.setStyleName("m-registerwindow-text");
		layoutContainer_4.add(text_1, td_text_1);
		text_1.setSize("80", "20");
		
		confirmPwdTextfield = new TextField<String>();
		confirmPwdTextfield.setPassword(true);
		layoutContainer_4.add(confirmPwdTextfield);
		confirmPwdTextfield.setSize("200", "30");
		confirmPwdTextfield.setFieldLabel("New TextField");
		
		layoutContainer = new LayoutContainer();
		TableData td_layoutContainer = new TableData();
		td_layoutContainer.setPadding(5);
		td_layoutContainer.setHorizontalAlign(HorizontalAlignment.CENTER);
		td_layoutContainer.setColspan(2);
		
		final Button createButton = new Button("创建账户");
		this.addButton(createButton);
//		layoutContainer.add(createButton);
		createButton.addListener(Events.Select, new Listener<ButtonEvent>() {
			public void handleEvent(ButtonEvent e) {
				if(checkAvailable() && checkPassword()){
					if(user.getName() != null){
						createButton.setEnabled(false);
						EnvConfig.getPineconeService().
							isUserExist(user, new AsyncCallback<Boolean>(){

							@Override
							public void onFailure(Throwable caught) {
								MessageBox.info("错误", "调用后台服务查询用户是否存在失败", null);
								checkButton.setEnabled(true);
							}

							@Override
							public void onSuccess(Boolean result) {
								if(result){
									infoText.setText("用户名已存在或者邮箱地址已经使用");
									createButton.setEnabled(true);
								}else {
									EnvConfig.getPineconeService().register(user, new AsyncCallback<User>(){

										@Override
										public void onFailure(Throwable caught) {
											MessageBox.info("错误", "调用后台服务查询用户是否存在失败", null);
											createButton.setEnabled(true);
										}

										@Override
										public void onSuccess(User result) {
											if(result == null){
												MessageBox.info("确认", "创建用户失败", null);
												createButton.setEnabled(true);
											}else{
												MessageBox.info("错误", "创建用户成功", null);
												createButton.setEnabled(true);
												
												RegisterWindow.this.hide();
											}
										}
										
									});
								}
							}
						});
					}
				}
			}
		});
		createButton.setSize("60", "30");
		FitLayout fl_layoutContainer = new FitLayout();
		fl_layoutContainer.setExtraStyle("m-register-infotext");
		layoutContainer.setLayout(fl_layoutContainer);
		
		infoText = new Text("");
		layoutContainer.add(infoText);
		layoutContainer_4.add(layoutContainer, td_layoutContainer);
		layoutContainer.setHeight("32");
		add(layoutContainer_4, new RowData(Style.DEFAULT, 215.0, new Margins()));
		layoutContainer_4.setHeight("180");
	}
	
	private boolean checkAvailable(){
		cleanInfo();
		if(userNameTextfield.getValue() == null || userNameTextfield.getValue().equals("")){
			infoText.setText("请输入用户名");
			return false;
		}
		user.setName(userNameTextfield.getValue());
		
		if(mailTextfield.getValue() == null || mailTextfield.getValue().equals("")){
			infoText.setText("请输入邮箱地址");
			return false;
		}
		user.setEmail(mailTextfield.getValue());
		
		return true;
	}
	
	private boolean checkPassword(){
		cleanInfo();
		if(passwordTextfield.getValue() == null || passwordTextfield.getValue().equals("")){
			infoText.setText("请输入用户密码");
			return false;
		}
		
		if(confirmPwdTextfield.getValue() == null || confirmPwdTextfield.getValue().equals("")){
			infoText.setText("请确认用户密码");
			return false;
		}
		
		if(!passwordTextfield.getValue().equals(confirmPwdTextfield.getValue())){
			infoText.setText("密码前后输入不一致");
			return false;
		}
		
		user.setPassword(passwordTextfield.getValue());
		return true;
	}
	
	private void cleanInfo(){
		infoText.setText("");
	}
	
}
