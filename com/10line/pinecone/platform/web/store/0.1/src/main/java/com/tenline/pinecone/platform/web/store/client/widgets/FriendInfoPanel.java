package com.tenline.pinecone.platform.web.store.client.widgets;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.BoxLayout.BoxLayoutPack;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.extjs.gxt.ui.client.widget.layout.FitData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout.HBoxLayoutAlign;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayoutData;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.store.client.Images;
import com.tenline.pinecone.platform.web.store.client.Messages;
import com.tenline.pinecone.platform.web.store.client.controllers.FriendController;
import com.tenline.pinecone.platform.web.store.client.events.WidgetEvents;

public class FriendInfoPanel extends ContentPanel {
	
	/**friend icon*/
	private Image image;
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
		
//		LayoutContainer layoutContainer = new LayoutContainer();
//		layoutContainer.setLayout(new FitLayout());
//		Image image = ((Images)Registry.get(Images.class.getName())).user().createImage();
//		layoutContainer.add(image, new FitData(0, 2, 0, 2));
//		layoutContainer.setBorders(false);
//		
//		add(layoutContainer, new BorderLayoutData(LayoutRegion.WEST, 54.0f));
//		layoutContainer.setBorders(false);
		
		LayoutContainer imageLc = new LayoutContainer();
		imageLc.setLayout(new FitLayout());
		imageLc.addStyleName("");
		
		image = ((Images)Registry.get(Images.class.getName())).application().createImage();
		imageLc.add(image,new FitData(1,1,1,1));
		BorderLayoutData imageLcData = new BorderLayoutData(LayoutRegion.WEST, 54.0f);
		imageLcData.setMargins(new Margins(1,1,1,1));
		imageLc.addStyleName("appstoreviewport-panel");
		add(imageLc, imageLcData);
		
		LayoutContainer rightContentLc = new LayoutContainer();
		rightContentLc.setLayout(new FillLayout(Orientation.VERTICAL));
		rightContentLc.setBorders(false);
		
		LayoutContainer titleLc = new LayoutContainer();
		HBoxLayout titleLcLayout = new HBoxLayout();
		titleLcLayout.setPack(BoxLayoutPack.START);
		titleLcLayout.setHBoxLayoutAlign(HBoxLayoutAlign.STRETCHMAX);
		titleLc.setLayout(titleLcLayout);
		
		txtFriendName = new Text(((Messages) Registry.get(Messages.class.getName())).FriendInfoPanel_label_friendname());
		titleLc.add(txtFriendName);
		titleLc.add(txtFriendName,new HBoxLayoutData(0, 0, 0, 2));
		txtFriendName.addStyleName("applicationInfo-text-title");
		rightContentLc.add(titleLc);
		
		LayoutContainer buttonLc = new LayoutContainer();
		buttonLc.setLayout(new HBoxLayout());
		
		Button btnInformation = new Button(((Messages) Registry.get(Messages.class.getName())).FriendInfoPanel_button_information());
		buttonLc.add(btnInformation, new HBoxLayoutData(0, 0, 0, 0));
		btnInformation.setWidth("40");
		rightContentLc.add(buttonLc);
		buttonLc.setBorders(false);
		btnInformation.setStyleName("btn-blue");
		btnInformation.addMouseUpHandler(new MouseUpHandler() {
			public void onMouseUp(MouseUpEvent event) {
				
			}
		});
		btnInformation.setStyleName("abstract-btn-text");
		
		final Button splitButton = new Button("|");
		splitButton.setWidth("4px");
		splitButton.setEnabled(false);
		buttonLc.add(splitButton);
		splitButton.setStyleName("abstract-btn-text");

		Button btnSendMessage = new Button(((Messages) Registry.get(Messages.class.getName())).FriendInfoPanel_button_sendMsg());
		buttonLc.add(btnSendMessage);
		btnSendMessage.setWidth("40");
		btnSendMessage.addMouseUpHandler(new MouseUpHandler() {
			public void onMouseUp(MouseUpEvent event) {
				AppEvent appEvent = new AppEvent(WidgetEvents.UPDATE_SEND_MAIL_TO_PANEL);
				appEvent.setData(FriendController.FRIEND_FRIEND, FriendInfoPanel.this.model.get(FriendController.FRIEND_FRIEND));
				appEvent.setHistoryEvent(true);
				Dispatcher.get().dispatch(appEvent);
			}
		});
		btnSendMessage.setStyleName("abstract-btn-text");
		
		add(rightContentLc, new BorderLayoutData(LayoutRegion.CENTER));
		rightContentLc.setBorders(false);
		
		init();
	}
	
	public void init(){
		try{
			
			User user = (User)model.get(FriendController.FRIEND_FRIEND);
			
			System.out.println("FriendInfoPanel info-----------:"+user);
//			System.out.println("User id:"+user.getId());
			
			txtFriendName.setText((String)user.getName());
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

}
