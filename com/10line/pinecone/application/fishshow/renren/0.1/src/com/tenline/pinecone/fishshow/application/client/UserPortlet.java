package com.tenline.pinecone.fishshow.application.client;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.widget.custom.Portlet;
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

public class UserPortlet extends Portlet {
	private RenrenApiAsync rapi;
	private Image portraitImage;
	private LabelField label;
	
	private String sessionKey = ""; 
	
	public UserPortlet() {
		setHeaderVisible(false);
		TableLayout tableLayout = new TableLayout(1);
		tableLayout.setCellSpacing(10);
		tableLayout.setCellHorizontalAlign(HorizontalAlignment.CENTER);
		setLayout(tableLayout);
		
		portraitImage = new Image("../img/contact.jpg");
		TableData td_portraitImage = new TableData();
		td_portraitImage.setHorizontalAlign(HorizontalAlignment.CENTER);
		add(portraitImage, td_portraitImage);
		
		label = new LabelField("你好！");
		TableData td_label = new TableData();
		td_label.setHorizontalAlign(HorizontalAlignment.CENTER);
		add(label, td_label);
		//initialize RPC
		rapi = GWT.create(RenrenApi.class);
		
		
//		setLayout(new TableLayout(2));
//		
//		portraitImage = new Image((String) null);
//		add(portraitImage);
//		
//		label = new LabelField("你好");
//		TableData td_lblfldNewLabelfield = new TableData();
//		td_lblfldNewLabelfield.setVerticalAlign(VerticalAlignment.MIDDLE);
//		td_lblfldNewLabelfield.setHorizontalAlign(HorizontalAlignment.LEFT);
//		add(label, td_lblfldNewLabelfield);
	}
	
	public void setContent(String sKey){
		this.sessionKey = sKey;
		rapi.getLoggedInUser(sessionKey, new AsyncCallback<Integer>(){

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Failure getLoggedInUser. sessionKey=" + sessionKey);
			}

			@Override
			public void onSuccess(Integer result) {
//				Window.alert("Now user id:"+result.toString());
//				CopyOfFishshow.this.snsId = ""+result;
				rapi.getInfo(sessionKey, result.toString(), "uid,name,headurl,tinyurl,mainurl", new AsyncCallback<String>(){

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Failure in getLoggedInUser");
						
					}

					@Override
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
