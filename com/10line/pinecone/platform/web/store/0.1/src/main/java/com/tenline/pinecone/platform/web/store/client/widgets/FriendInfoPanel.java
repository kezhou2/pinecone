package com.tenline.pinecone.platform.web.store.client.widgets;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.extjs.gxt.ui.client.widget.layout.FitData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayoutData;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.store.client.Images;
import com.tenline.pinecone.platform.web.store.client.Messages;
import com.tenline.pinecone.platform.web.store.client.events.WidgetEvents;

public class FriendInfoPanel extends ContentPanel {
	
	private BeanModel model;
	private Text txtFriendName;
	
	public FriendInfoPanel(BeanModel model) {
		this.model = model;
		
		setHeaderVisible(false);
		setSize("225", "54");
		setLayout(new BorderLayout());
		setBodyBorder(false);
		setBorders(false);
		setBodyStyle("background-color: transparent");
		
		LayoutContainer layoutContainer = new LayoutContainer();
		layoutContainer.setLayout(new FitLayout());
		Image image = ((Images)Registry.get(Images.class.getName())).user().createImage();
		layoutContainer.add(image, new FitData(0, 2, 0, 2));
		layoutContainer.setBorders(false);
		
		add(layoutContainer, new BorderLayoutData(LayoutRegion.WEST, 54.0f));
		layoutContainer.setBorders(false);
		
		LayoutContainer layoutContainer_1 = new LayoutContainer();
		layoutContainer_1.setLayout(new FillLayout(Orientation.VERTICAL));
		layoutContainer_1.setBorders(false);
		
		LayoutContainer layoutContainer_2 = new LayoutContainer();
		layoutContainer_2.setLayout(new FitLayout());
		
		txtFriendName = new Text(((Messages) Registry.get(Messages.class.getName())).FriendInfoPanel_label_friendname());
		layoutContainer_2.add(txtFriendName);
		layoutContainer_1.add(layoutContainer_2);
		layoutContainer_2.setBorders(false);
		
		LayoutContainer layoutContainer_3 = new LayoutContainer();
		layoutContainer_3.setLayout(new HBoxLayout());
		
		Button btnInformation = new Button(((Messages) Registry.get(Messages.class.getName())).FriendInfoPanel_button_information());
		layoutContainer_3.add(btnInformation, new HBoxLayoutData(0, 10, 0, 4));
		layoutContainer_1.add(layoutContainer_3);
		layoutContainer_3.setBorders(false);
		btnInformation.setStyleName("btn-blue");
		btnInformation.addMouseUpHandler(new MouseUpHandler() {
			public void onMouseUp(MouseUpEvent event) {
				
			}
		});

		Button btnSendMessage = new Button(((Messages) Registry.get(Messages.class.getName())).FriendInfoPanel_button_sendMsg());
		layoutContainer_3.add(btnSendMessage);
		btnSendMessage.setStyleName("btn-blue");
		btnSendMessage.addMouseUpHandler(new MouseUpHandler() {
			public void onMouseUp(MouseUpEvent event) {
				AppEvent appEvent = new AppEvent(WidgetEvents.UPDATE_SEND_MAIL_TO_PANEL);
				appEvent.setData("friend", FriendInfoPanel.this.model.get("friend"));
				Dispatcher.get().dispatch(appEvent);
			}
		});
		
		add(layoutContainer_1, new BorderLayoutData(LayoutRegion.CENTER));
		layoutContainer_1.setBorders(false);
		
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
