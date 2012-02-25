package com.tenline.pinecone.platform.web.store.client.window;

import java.util.List;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.TreePanelEvent;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.extjs.gxt.ui.client.widget.treepanel.TreePanel;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.tenline.pinecone.platform.web.store.client.EnvConfig;
import com.tenline.pinecone.platform.web.store.shared.MailInfo;

public class MailWindow extends Window{
	
	public static String ATT_ID = "id";
	public static String ATT_DISPALY_NAME = "Display";
	
	public static String VALUE_RECEIVED_BOX = "ReceivedBox";
	public static String VALUE_SENT_BOX = "SentBox";
	
	
	private TreePanel<SelItem> menuTreePanel;
	private TreeStore<SelItem> menuTreeStore;
	
	private MailListPanel mailListPanel;
	
	public MailWindow() {
		setSize("640", "450");
		setHeading("邮件");
		setLayout(new BorderLayout());
		
		ContentPanel cntntpnlNewContentpanel = new ContentPanel();
		cntntpnlNewContentpanel.setHeaderVisible(false);
		cntntpnlNewContentpanel.setHeading("New ContentPanel");
		cntntpnlNewContentpanel.setCollapsible(true);
		cntntpnlNewContentpanel.setLayout(new RowLayout(Orientation.VERTICAL));
		
		LayoutContainer layoutContainer = new LayoutContainer();
		layoutContainer.setLayout(new FitLayout());
		
		Button writeMailButton = new Button("写邮件");
		writeMailButton.addListener(Events.Select, new Listener<ButtonEvent>() {
			public void handleEvent(ButtonEvent e) {
				SendMailWindow w = new SendMailWindow(MailWindow.this);
				w.show();
				w.center();
			}
		});
		layoutContainer.add(writeMailButton, new FitData(0));
		writeMailButton.setSize("120", "30");
		cntntpnlNewContentpanel.add(layoutContainer, new RowData(Style.DEFAULT, Style.DEFAULT, new Margins(8, 30, 8, 30)));
		
		LayoutContainer layoutContainer_1 = new LayoutContainer();
		layoutContainer_1.setLayout(new FitLayout());

		this.createSelItem();
		menuTreePanel = new TreePanel<SelItem>(menuTreeStore);
		layoutContainer_1.add(menuTreePanel);
		cntntpnlNewContentpanel.add(layoutContainer_1, new RowData(Style.DEFAULT, 1.0, new Margins()));
		add(cntntpnlNewContentpanel, new BorderLayoutData(LayoutRegion.WEST));
		menuTreePanel.addListener(
				Events.OnClick,	new Listener<BaseEvent>() {

			@Override
			public void handleEvent(BaseEvent be) {
				if(be instanceof TreePanelEvent){
					@SuppressWarnings("unchecked")
					TreePanelEvent<SelItem> tpe = (TreePanelEvent<SelItem>)be;
					if(tpe.getItem().get(ATT_ID) != null &&
							tpe.getItem().get(ATT_ID).equals(VALUE_RECEIVED_BOX)){
						EnvConfig.getPineconeService().getReceiveMails(EnvConfig.getLoginUser(), new AsyncCallback<List<MailInfo>>(){
							@Override
							public void onFailure(Throwable caught) {
								MessageBox.info("错误", "调用后台服务获取用户的邮件失败", null);
							}

							@Override
							public void onSuccess(List<MailInfo> result) {
								if(result != null && mailListPanel != null){
									mailListPanel.loadMails(result);
								}
							}				
						});
					}
					
					if(tpe.getItem().get(ATT_ID) != null &&
							tpe.getItem().get(ATT_ID).equals(VALUE_SENT_BOX)){
						EnvConfig.getPineconeService().getSendMails(EnvConfig.getLoginUser(), new AsyncCallback<List<MailInfo>>(){
							@Override
							public void onFailure(Throwable caught) {
								MessageBox.info("错误", "调用后台服务获取用户的邮件失败", null);
							}

							@Override
							public void onSuccess(List<MailInfo> result) {
								if(result != null && mailListPanel != null){
									mailListPanel.loadMails(result);
								}
							}				
						});
					}
				}
				
				
			}

		});
		menuTreePanel.setDisplayProperty(ATT_DISPALY_NAME);
		
		LayoutContainer layoutContainer_2 = new LayoutContainer();
		layoutContainer_2.setLayout(new FitLayout());
		
		mailListPanel = new MailListPanel();
		layoutContainer_2.add(mailListPanel);
		add(layoutContainer_2, new BorderLayoutData(LayoutRegion.CENTER));
		
		//load received mails firstly
		this.loadMails();
	}
	
	public void loadMails(){
		EnvConfig.getPineconeService().getReceiveMails(EnvConfig.getLoginUser(), new AsyncCallback<List<MailInfo>>(){
			@Override
			public void onFailure(Throwable caught) {
				MessageBox.info("错误", "调用后台服务获取用户的邮件失败", null);
			}

			@Override
			public void onSuccess(List<MailInfo> result) {
				if(result != null && mailListPanel != null){
					mailListPanel.loadMails(result);
				}
			}				
		});
	}
	
	public void createSelItem(){
		menuTreeStore = new TreeStore<SelItem>();
		{
			SelItem si = new SelItem();
			si.set(ATT_ID, VALUE_RECEIVED_BOX);
			si.set(ATT_DISPALY_NAME, "收件箱");
			menuTreeStore.add(si,false);
		}
		
		{
			SelItem si = new SelItem();
			si.set(ATT_ID, VALUE_SENT_BOX);
			si.set(ATT_DISPALY_NAME, "已发邮件");
			menuTreeStore.add(si,false);
		}
		menuTreeStore.commitChanges();
		
	}
	
	@SuppressWarnings("serial")
	class SelItem extends BaseModelData{
	}

}
