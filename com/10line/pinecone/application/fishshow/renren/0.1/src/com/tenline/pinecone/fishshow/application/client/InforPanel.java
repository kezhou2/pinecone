package com.tenline.pinecone.fishshow.application.client;

import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;

public class InforPanel extends ContentPanel {
	
	private String sessionKey = "";
	
	public InforPanel() {
		setLayout(new BorderLayout());
		
		TextArea txtrNewTextarea = new TextArea();
		txtrNewTextarea.setReadOnly(true);
		add(txtrNewTextarea, new BorderLayoutData(LayoutRegion.CENTER));
		txtrNewTextarea.setFieldLabel("New TextArea");
		
	}
	
	public void setContent(String sKey){
		this.sessionKey = sKey;
		
	}
}
