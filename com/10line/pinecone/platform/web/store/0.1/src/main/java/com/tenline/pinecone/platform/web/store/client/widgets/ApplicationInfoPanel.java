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
import com.tenline.pinecone.platform.model.Application;
import com.tenline.pinecone.platform.model.Consumer;
import com.tenline.pinecone.platform.web.store.client.Images;
import com.tenline.pinecone.platform.web.store.client.Messages;
import com.tenline.pinecone.platform.web.store.client.controllers.ApplicationController;
import com.tenline.pinecone.platform.web.store.client.events.ApplicationEvents;
/**
 * 
 * @author liugy
 *
 */
public class ApplicationInfoPanel extends ContentPanel {
	
	/**application icon*/
	private Image image;
	/**show application information*/
	private Text titleText;
	private Text versionText;
	/**button to open application on the portal*/
	private Button openButton;
	/**button to unintall user's application*/
	private Button removeButton;
	/**application bean model*/
	private BeanModel model;
	
	public ApplicationInfoPanel(BeanModel model) {
		this.model = model;
		
		setSize("225", "54");
		setLayout(new BorderLayout());
		setHeaderVisible(false);
		setBodyBorder(false);
		setBorders(false);
		setBodyStyle("background-color: transparent");
		
		LayoutContainer imageLc = new LayoutContainer();
		imageLc.setLayout(new FitLayout());
		imageLc.addStyleName("");
		
		image = ((Images)Registry.get(Images.class.getName())).account().createImage();
		imageLc.add(image,new FitData(1,1,1,1));
		BorderLayoutData imageLcData = new BorderLayoutData(LayoutRegion.WEST, 54.0f);
		imageLcData.setMargins(new Margins(1,1,1,1));
		imageLc.addStyleName("appstoreviewport-panel");
		add(imageLc, imageLcData);
		
		LayoutContainer rightContentLc = new LayoutContainer();
		rightContentLc.setLayout(new FillLayout(Orientation.VERTICAL));
		
		LayoutContainer titleLc = new LayoutContainer();
		HBoxLayout titleLcLayout = new HBoxLayout();
		titleLcLayout.setPack(BoxLayoutPack.START);
		titleLcLayout.setHBoxLayoutAlign(HBoxLayoutAlign.STRETCHMAX);
		titleLc.setLayout(titleLcLayout);
		
		titleText = new Text("");
		titleLc.add(titleText,new HBoxLayoutData(0, 0, 0, 2));
		titleText.addStyleName("applicationInfo-text-title");
		
		versionText = new Text("");
		titleLc.add(versionText,new HBoxLayoutData(4, 0, 0, 0));
		versionText.addStyleName("applicationInfo-text-version");
		rightContentLc.add(titleLc);
		
		LayoutContainer buttonLc = new LayoutContainer();
		buttonLc.setLayout(new HBoxLayout());
		
		openButton = new Button(((Messages) Registry.get(Messages.class.getName())).ApplicationInfoPanel_open());
		buttonLc.add(openButton, new HBoxLayoutData(0, 0, 0, 0));
		openButton.setWidth("40");
		openButton.addMouseUpHandler(new MouseUpHandler() {
			public void onMouseUp(MouseUpEvent event) {
				AppEvent appEvent = new AppEvent(ApplicationEvents.SETTING);
				appEvent.setData(ApplicationController.APP_INSTANCE, ApplicationInfoPanel.this.model.get(ApplicationController.APP_INSTANCE));
				appEvent.setData("status", Application.OPENED);
				appEvent.setData("default", false);
				appEvent.setHistoryEvent(true);
				Dispatcher.get().dispatch(appEvent);
			}
		});
		openButton.setStyleName("abstract-btn-text");
		
		final Button splitButton = new Button("|");
		splitButton.setWidth("4px");
		splitButton.setEnabled(false);
		buttonLc.add(splitButton);
		splitButton.setStyleName("abstract-btn-text");
		
		removeButton = new Button(((Messages) Registry.get(Messages.class.getName())).ApplicationInfoPanel_remove());
		buttonLc.add(removeButton);
		removeButton.setWidth("40");
		removeButton.addMouseUpHandler(new MouseUpHandler() {
			public void onMouseUp(MouseUpEvent event) {
				System.out.println("ApplicationInfoPanel uninstall");
				AppEvent appEvent = new AppEvent(ApplicationEvents.UNINSTALL);
				appEvent.setData("id", ApplicationInfoPanel.this.model.get("id"));
				appEvent.setData(ApplicationController.APP_INSTANCE, ApplicationInfoPanel.this.model.get(ApplicationController.APP_INSTANCE));
				appEvent.setHistoryEvent(true);
				Dispatcher.get().dispatch(appEvent);
			}
		});
		removeButton.setStyleName("abstract-btn-text");
		
		rightContentLc.add(buttonLc);
		add(rightContentLc, new BorderLayoutData(LayoutRegion.CENTER));
		
		init();
	}
	/**
	 * intialize the panel, show app info.
	 */
	public void init(){
		try{
			Consumer consumer = (Consumer)model.get(ApplicationController.APP_CONSUMER);
			titleText.setText(consumer.getName());
			versionText.setText(consumer.getVersion());
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
}
