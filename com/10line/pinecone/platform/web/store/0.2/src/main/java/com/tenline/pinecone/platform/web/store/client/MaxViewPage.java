package com.tenline.pinecone.platform.web.store.client;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.IconButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ToolButton;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.tenline.pinecone.platform.web.store.client.model.AppInfo;
import com.tenline.pinecone.platform.web.store.client.model.ConsumerInfo;

public class MaxViewPage extends ContentPanel {

	private AppInfo appInfo;
	private Button returnButton;
	private Text displayText;
	private ToolButton toolButtonClose;
	
	public MaxViewPage(AppInfo info) {
		setHeaderVisible(false);
		setLayout(new FitLayout());
		setUrl("");
		appInfo = info;
		ToolBar toolBar = new ToolBar();
		
		displayText = new Text("Unkown");
		toolBar.add(displayText);
		
		FillToolItem fillToolItem = new FillToolItem();
		toolBar.add(fillToolItem);
		
		returnButton = new Button("返回");
		returnButton.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				EnvConfig.getHomePage().setPortalMode();
			}
		});
		toolBar.add(returnButton);
		setTopComponent(toolBar);

		toolButtonClose = new ToolButton("x-tool-close");
		toolButtonClose.addSelectionListener(new SelectionListener<IconButtonEvent>() {
			@Override
			public void componentSelected(IconButtonEvent ce) {
				if(appInfo == null)
					return;
				
				toolButtonClose.setEnabled(false);
				
				EnvConfig.getPineconeService().
					deleteAppInfo(appInfo, new AsyncCallback<Void>(){

						@Override
						public void onFailure(Throwable caught) {
							MessageBox.info("错误", "从后台删除用户的应用失败", null);
							toolButtonClose.setEnabled(true);
						}

						@Override
						public void onSuccess(Void result) {
							EnvConfig.getHomePage().setPortalMode();
							toolButtonClose.setEnabled(true);
						}
						
				});
			}
		});
		
		
		this.mask();
		EnvConfig.getPineconeService().getConsumerById(
				info.getConsumerId(), new AsyncCallback<ConsumerInfo>(){

			@Override
			public void onFailure(Throwable caught) {
				MessageBox.info("错误", "调用后台服务获取应用信息失败", null);
			}

			@Override
			public void onSuccess(ConsumerInfo result) {		
				displayText.setText(result.getDisplayName());
				setUrl(result.getConnectURI());
				MaxViewPage.this.unmask();
			}
			
		});
		
		
	}

}
