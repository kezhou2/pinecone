package com.tenline.pinecone.fishshow.application.client;

import java.util.Date;

import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.google.gwt.user.client.Timer;
import com.extjs.gxt.ui.client.widget.Text;

public class TestTabItem extends TabItem {
	
	private LayoutContainer lc;
	
	public TestTabItem() {
		setLayout(new BorderLayout());
		
		lc = new LayoutContainer();
		lc.setLayout(new RowLayout(Orientation.VERTICAL));
		
		final TextField txtfldNewTextfield = new TextField();
		lc.add(txtfldNewTextfield);
		txtfldNewTextfield.setFieldLabel("New TextField");
		
		final Text txtNewText = new Text("New Text");
		txtNewText.setBorders(true);
		lc.add(txtNewText);
		add(lc, new BorderLayoutData(LayoutRegion.CENTER));
		lc.setBorders(true);
		
		
		Timer timer = new Timer(){

			@Override
			public void run() {
				txtNewText.setText((new Date()).toGMTString());
			}
			
		};
		
		timer.scheduleRepeating(3000);
	}

}
