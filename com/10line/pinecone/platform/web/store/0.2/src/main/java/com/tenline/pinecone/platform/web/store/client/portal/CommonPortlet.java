/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.portal;

import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.event.IconButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.ToolButton;
import com.extjs.gxt.ui.client.widget.custom.Portlet;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.tenline.pinecone.platform.web.store.client.EnvConfig;
import com.tenline.pinecone.platform.web.store.client.model.AppInfo;
import com.tenline.pinecone.platform.web.store.client.model.ConsumerInfo;

/**
 * 为应用提供的通用Portlet
 * 
 * @author liugy
 * 
 */
public class CommonPortlet extends Portlet {

	private AppInfo appInfo;

	public CommonPortlet() {
		setSize("450", "290");
		setHeading("Application");
		setLayout(new BorderLayout());

		ContentPanel cntntpnlNewContentpanel = new ContentPanel();
		cntntpnlNewContentpanel.setHeaderVisible(false);
		cntntpnlNewContentpanel.setHeading("New ContentPanel");
		cntntpnlNewContentpanel.setCollapsible(true);
		cntntpnlNewContentpanel.setUrl("http://www.sina.cn");
		add(cntntpnlNewContentpanel, new BorderLayoutData(LayoutRegion.CENTER));

		
		this.getHeader().addTool(new ToolButton("x-tool-maximize",new SelectionListener<IconButtonEvent>(){

			@Override
			public void componentSelected(IconButtonEvent ce) {
				EnvConfig.getHomePage().setMaxMode(appInfo);
		}}));
		final ToolButton toolButtonClose = new ToolButton("x-tool-close");
		toolButtonClose.addSelectionListener(new SelectionListener<IconButtonEvent>() {
			@Override
			public void componentSelected(IconButtonEvent ce) {
				if(appInfo == null){
					return;
				}
				
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
							EnvConfig.getHomePage().remove(CommonPortlet.this);
							EnvConfig.getHomePage().reset(null);
							toolButtonClose.setEnabled(true);
						}
						
				});
				
//				Database.getMyAppStore().remove(appInfo);
//				Database.getMyAppStore().commitChanges();
////				CommonPortlet.this.removeFromParent();
//				EnvConfig.getHomePage().remove(CommonPortlet.this);
			}
		}
); 
		this.getHeader().addTool(toolButtonClose);
		
		
		
	}

	public void setAppInfo(AppInfo info) {
		this.appInfo = info;
		this.mask();
		EnvConfig.getPineconeService().getConsumerById(
				info.getConsumerId(), new AsyncCallback<ConsumerInfo>(){

					@Override
					public void onFailure(Throwable caught) {
						MessageBox.info("错误", "从后台获取用户的应用失败", null);
					}

					@Override
					public void onSuccess(ConsumerInfo result) {
						setHeading(result.getDisplayName());
						setUrl(result.getConnectURI());
						CommonPortlet.this.unmask();
					}
			
		});
		
		
	}
	
	public AppInfo getAppInfo(){
		return this.appInfo;
	}
}
