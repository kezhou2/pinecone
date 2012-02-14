package com.tenline.pinecone.platform.web.store.client.window;

import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.layout.TableLayout;
import com.extjs.gxt.ui.client.widget.button.SplitButton;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.CheckMenuItem;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.ButtonEvent;

public class Demo extends Window {
	public Demo() {
		setLayout(new TableLayout(1));
		
		SplitButton spltbtnNewSplitbutton = new SplitButton("New SplitButton");
		add(spltbtnNewSplitbutton);
		
		Menu menu = new Menu();
		menu.setSubMenuAlign("l");
		menu.setDefaultAlign("l");
		
		CheckMenuItem chckmntmNewCheckmenuitem = new CheckMenuItem("12");
		menu.add(chckmntmNewCheckmenuitem);
		
		
		CheckMenuItem chckmntmNewCheckmenuitem_1 = new CheckMenuItem("1");
		menu.add(chckmntmNewCheckmenuitem_1);
		spltbtnNewSplitbutton.setMenu(menu);
		
		Button btnNewButton = new Button("New Button");
		btnNewButton.addListener(Events.Select, new Listener<ButtonEvent>() {
			public void handleEvent(ButtonEvent e) {
			}
		});
		add(btnNewButton);
	}

}
