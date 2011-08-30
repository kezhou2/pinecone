package com.tenline.pinecone.fishshow.application.client;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.layout.TableData;
import com.extjs.gxt.ui.client.widget.layout.TableLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
import com.tenline.pinecone.fishshow.application.client.service.RenrenApi;
import com.tenline.pinecone.fishshow.application.client.service.RenrenApiAsync;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.CenterLayout;
import com.extjs.gxt.ui.client.widget.HtmlContainer;

public class UserPanel extends Viewport {
	private RenrenApiAsync rapi;
	private Image portraitImage;
	private LabelField label;
	
	private String sessionKey = ""; 
	
	public UserPanel() {
		setLayout(new FitLayout());
		
		HtmlContainer htmlContainer = new HtmlContainer("<img src='../img/contact.jpg'/>");
		add(htmlContainer);
		
//		HtmlContainer htmlContainer = new HtmlContainer("<img src='../img/contact.jpg'/>");
//		add(htmlContainer);
//		portraitImage = new Image("../img/contact.jpg");
//		add(portraitImage);
//		portraitImage.setSize("120", "120");
		
//		TableData td_portraitImage = new TableData();
//		td_portraitImage.setHorizontalAlign(HorizontalAlignment.CENTER);
//		add(portraitImage, td_portraitImage);
//		
		label = new LabelField("你好！");
//		TableData td_label = new TableData();
//		td_label.setHorizontalAlign(HorizontalAlignment.CENTER);
//		add(label, td_label);
		//initialize RPC
		rapi = GWT.create(RenrenApi.class);

	}
	
	public void setContent(String sKey){
		this.sessionKey = sKey;
		rapi.getLoggedInUser(sessionKey, new AsyncCallback<Integer>(){

			public void onFailure(Throwable caught) {
				Window.alert("Failure getLoggedInUser. sessionKey=" + sessionKey);
			}

			public void onSuccess(Integer result) {
				rapi.getInfo(sessionKey, result.toString(), "uid,name,headurl,tinyurl,mainurl", new AsyncCallback<String>(){

					public void onFailure(Throwable caught) {
						Window.alert("Failure in getLoggedInUser");
						
					}

					public void onSuccess(String result) {
						
						JSONArray ja = JSONParser.parseLenient(result).isArray();
						if(ja != null){
							JSONObject o = (JSONObject)ja.get(0);
							if(o != null){
								JSONString url = o.get("headurl").isString();
								portraitImage.setUrl(url.stringValue());
								JSONString name = o.get("name").isString();			
								label.setText(name.stringValue().toString()+",欢迎回来！");
								return;
							}
						}
						Window.alert("Failure in set portrait.");
						
					}});
			}});
	}
}
