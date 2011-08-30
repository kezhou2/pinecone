package com.tenline.pinecone.fishshow.application.client;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Image;

public class CameraPanel extends Image {
	Timer refreshTimer;

	public CameraPanel() {
		super();
	
		this.refreshTimer = new Timer(){
			
			String baseUrl = GWT.getModuleBaseURL();
			
			@Override
			public void run() {
				Date date = new Date();
				
//				CameraPanel.this.setUrl("http://pinecone.web.service.10line.cc/api/channel/subscribe/ah1zfjEwbGluZS1waW5lY29uZS13ZWItc2VydmljZXInCxIEVXNlchj7VQwLEgZEZXZpY2UY-VUMCxIIVmFyaWFibGUY4V0M");
				switch((int)(date.getTime()%3)){
				case 0:
					CameraPanel.this.setUrl(baseUrl+"../img/1.jpg");
					break;
				case 1:
					CameraPanel.this.setUrl(baseUrl+"../img/2.jpg");
					break;
				case 2:
					CameraPanel.this.setUrl(baseUrl+"../img/3.jpg");
					break;
				default:
					CameraPanel.this.setUrl(baseUrl+"../img/1.jpg");
					break;
				}
			}
		};
		this.refreshTimer.scheduleRepeating(1000);
	}

	public CameraPanel(Element element) {
		super(element);
		
	}

	public CameraPanel(ImageResource resource) {
		super(resource);
		
	}

	public CameraPanel(String url, int left, int top, int width, int height) {
		super(url, left, top, width, height);
		
	}

	public CameraPanel(String url) {
		super(url);
		
	}
	
	

}
