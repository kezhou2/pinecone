package com.tenline.pinecone.platform.web.store.client.window;

import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.layout.TableLayout;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.TableData;
import com.extjs.gxt.ui.client.widget.button.IconButton;

public class DeviceConfigWindow extends Window {
	public DeviceConfigWindow() {
		setLayout(new TableLayout(2));
		
		Button btnNewButton = new Button("New Button");
		add(btnNewButton);
		btnNewButton.addStyleName("button-link-delete-friend");
		
		IconButton iconButton = new IconButton("delete_friend");
		TableData td_iconButton = new TableData();
		add(iconButton, td_iconButton);
//		iconButton.set
	}

}
