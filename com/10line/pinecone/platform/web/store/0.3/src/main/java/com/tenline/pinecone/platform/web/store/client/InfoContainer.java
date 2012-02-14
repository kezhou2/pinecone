package com.tenline.pinecone.platform.web.store.client;

import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.tenline.pinecone.platform.web.store.shared.ConsumerInfo;

public class InfoContainer extends LayoutContainer {
	public InfoContainer(ConsumerInfo consumerInfo) {
		setLayout(new FillLayout(Orientation.VERTICAL));
		
//		Text txtNewText = new Text(consumerInfo.get);
//		add(txtNewText, new FillData(2, 0, 2, 0));
		
//		Text txtNewText_1 = new Text(appInfo.getVersion());
//		add(txtNewText_1, new FillData(2, 0, 2, 0));
		
	}

}
