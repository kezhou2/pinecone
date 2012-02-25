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
import com.tenline.pinecone.platform.web.store.shared.ApplicationInfo;
import com.tenline.pinecone.platform.web.store.shared.ConsumerInfo;

/**
 * 为应用提供的通用Portlet
 * 
 * @author liugy
 * 
 */
public class CommonPortlet extends Portlet {

	private ApplicationInfo appInfo;
	private ToolButton toolButtonClose;
	private ToolButton toolButtonMax;

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

		toolButtonMax = new ToolButton("x-tool-maximize",
				new SelectionListener<IconButtonEvent>() {

					@Override
					public void componentSelected(IconButtonEvent ce) {
						EnvConfig.getHomePage().setMaxMode(appInfo);
					}
				});
		this.getHeader().addTool(toolButtonMax);
		toolButtonClose = new ToolButton("x-tool-close");
		toolButtonClose
				.addSelectionListener(new SelectionListener<IconButtonEvent>() {
					@Override
					public void componentSelected(IconButtonEvent ce) {
						if (appInfo == null) {
							return;
						}
						close();
					}

				});
		this.getHeader().addTool(toolButtonClose);

	}

	public void close() {
		toolButtonClose.setEnabled(false);
		EnvConfig.getPineconeService().changeAppStatus(appInfo, "Close",
				new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						MessageBox.info("错误", "从后台服务修改应用的状态失败", null);
						toolButtonClose.setEnabled(true);
					}

					@Override
					public void onSuccess(Void result) {
						EnvConfig.getHomePage().closeCommonPortlet(
								CommonPortlet.this);
						EnvConfig.getHomePage().reset(null);
					}

				});
	}

	public void open(ApplicationInfo info) {
		this.appInfo = info;
		this.mask();
		EnvConfig.getPineconeService().getConsumerById(
				info.getConsumer().getId(), new AsyncCallback<ConsumerInfo>() {

					@Override
					public void onFailure(Throwable caught) {
						MessageBox.info("错误", "从后台获取用户的应用失败", null);
					}

					@Override
					public void onSuccess(ConsumerInfo result) {
						setHeading(result.getName());
						setUrl(result.getConnectURI());
						CommonPortlet.this.unmask();

						EnvConfig.getPineconeService().changeAppStatus(appInfo,
								"Open", new AsyncCallback<Void>() {

									@Override
									public void onFailure(Throwable caught) {
										MessageBox.info("错误", "从后台服务修改应用的状态失败",
												null);
									}

									@Override
									public void onSuccess(Void result) {
									}

								});
					}

				});
	}

	public ApplicationInfo getAppInfo() {
		return this.appInfo;
	}
}
