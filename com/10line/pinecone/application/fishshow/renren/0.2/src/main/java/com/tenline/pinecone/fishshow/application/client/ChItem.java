package com.tenline.pinecone.fishshow.application.client;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.tenline.pinecone.platform.model.Item;

public class ChItem extends BaseModelData {
	private static final long serialVersionUID = 1644782172788405109L;
	
	private Item item;
	
	public ChItem(String name, String value){
		this.setName(name);
		this.setValue(value);
	}
	
	public ChItem(Item it){
		this.item = it;
		this.setName(this.item.getText());
		this.setValue(this.item.getValue());
	}
	
	public Item getItem(){
		return item;
	}
	
	public void setName(String name){
		this.set("name", name);
	}
	
	public String getName(){
		return this.get("name");
	}
	
	public void setValue(String value) {
		this.set("value", value);
	}

	public String getValue() {
		return this.get("value");
	}
}
