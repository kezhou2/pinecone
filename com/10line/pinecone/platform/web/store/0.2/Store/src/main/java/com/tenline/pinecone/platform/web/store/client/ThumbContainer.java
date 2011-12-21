package com.tenline.pinecone.platform.web.store.client;

import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.tenline.pinecone.platform.web.store.client.model.ConsumerInfo;

public class ThumbContainer extends LayoutContainer {
	public ThumbContainer(ConsumerInfo consumerInfo) {
		setLayout(new RowLayout(Orientation.VERTICAL));
		
//		HtmlContainer htmlContainer = new HtmlContainer("<center><img src='"+consumerInfo.getIconUrl()+"'></center>");
//		add(htmlContainer);
		
		Text txtNewText = new Text("<center>"+consumerInfo.getDisplayName()+"</center>");
		add(txtNewText);
	}

}
