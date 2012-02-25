package com.tenline.pinecone.platform.web.store.client.window;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.layout.FitData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.tenline.pinecone.platform.web.store.client.CallbackInter;

public class FriendTypeWindow extends Window {
	
	private CallbackInter callback;
	
	public FriendTypeWindow(CallbackInter cb) {
		this.callback = cb;
		setSize("300", "120");
		setHeading("好友类型");
		setLayout(new FitLayout());
		
		LayoutContainer layoutContainer = new LayoutContainer();
		FormLayout fl_layoutContainer = new FormLayout();
		layoutContainer.setLayout(fl_layoutContainer);
		
		final SimpleComboBox<String> typeCombobox = new SimpleComboBox<String>();
		typeCombobox.setAllowBlank(false);
		layoutContainer.add(typeCombobox, new FormData("95%"));
		typeCombobox.setFieldLabel("请输入好友类型");
		add(layoutContainer, new FitData(10));
		
		
		final Button okBtn = new Button("确认");
		okBtn.addListener(Events.Select, new Listener<ButtonEvent>() {
			public void handleEvent(ButtonEvent e) {
				callback.success(typeCombobox.getSimpleValue());
				FriendTypeWindow.this.hide();
			}
		});
		
		final Button closeBtn = new Button("取消");
		closeBtn.addListener(Events.Select, new Listener<ButtonEvent>() {
			public void handleEvent(ButtonEvent e) {
				FriendTypeWindow.this.hide();
			}
		});
		
		this.addButton(okBtn);
		okBtn.setWidth("60");
		this.addButton(closeBtn);
		closeBtn.setWidth("60");
	}

}
