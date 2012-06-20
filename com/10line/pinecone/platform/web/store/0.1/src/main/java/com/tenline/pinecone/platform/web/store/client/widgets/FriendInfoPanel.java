package com.tenline.pinecone.platform.web.store.client.widgets;

import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.extjs.gxt.ui.client.widget.layout.FitData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout;
import com.google.gwt.user.client.ui.Image;
import com.tenline.pinecone.platform.model.User;

public class FriendInfoPanel extends ContentPanel {
	
	private BeanModel model;
	private Text txtFriendName;
	
	public FriendInfoPanel(BeanModel model) {
		this.model = model;
		
		setHeaderVisible(false);
		setSize("300", "64");
		setLayout(new BorderLayout());
		
		LayoutContainer layoutContainer = new LayoutContainer();
		layoutContainer.setLayout(new FitLayout());
		Image image = new Image((String) null);
		layoutContainer.add(image, new FitData(0, 8, 0, 8));
		
		add(layoutContainer, new BorderLayoutData(LayoutRegion.WEST, 80.0f));
		layoutContainer.setBorders(true);
		
		LayoutContainer layoutContainer_1 = new LayoutContainer();
		layoutContainer_1.setLayout(new FillLayout(Orientation.VERTICAL));
		
		LayoutContainer layoutContainer_2 = new LayoutContainer();
		layoutContainer_2.setLayout(new FitLayout());
		
		txtFriendName = new Text("friend name");
		layoutContainer_2.add(txtFriendName);
		layoutContainer_1.add(layoutContainer_2);
		layoutContainer_2.setBorders(true);
		
		LayoutContainer layoutContainer_3 = new LayoutContainer();
		layoutContainer_3.setLayout(new HBoxLayout());
		
		Button btnInformation = new Button("information");
		layoutContainer_3.add(btnInformation);
		layoutContainer_1.add(layoutContainer_3);
		layoutContainer_3.setBorders(true);
		
		LayoutContainer layoutContainer_4 = new LayoutContainer();
		layoutContainer_4.setLayout(new HBoxLayout());
		
		Button btnSendMessage = new Button("Send message");
		layoutContainer_4.add(btnSendMessage);
		
		Button btnDelete = new Button("Delete");
		layoutContainer_4.add(btnDelete);
		
		layoutContainer_1.add(layoutContainer_4);
		layoutContainer_4.setBorders(true);
		add(layoutContainer_1, new BorderLayoutData(LayoutRegion.CENTER));
		layoutContainer_1.setBorders(true);
		
		init();
	}
	
	public void init(){
		try{
			User user = (User)model.get("friend");
			
//			System.out.println("User name:"+user.getName());
//			System.out.println("User id:"+user.getId());
			
			txtFriendName.setText((String)user.getName());
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

}
