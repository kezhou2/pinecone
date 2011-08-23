package com.tenline.pinecone.platform.monitor.tool;

import java.util.ArrayList;

import com.tenline.pinecone.platform.model.Item;
import com.tenline.pinecone.platform.monitor.IConstants;
import com.tenline.pinecone.platform.sdk.APIListener;
import com.tenline.pinecone.platform.sdk.ItemAPI;

public class ItemService {
	private ItemAPI addApi;
	private Item addItem;

	private ItemAPI queryApi;
	private ArrayList<Item> queryVars;

	@SuppressWarnings("unused")
	private ItemAPI delApi;
	private static ItemService instance = null;

	private ItemService() {
		addApi = new ItemAPI(IConstants.WEB_SERVICE_HOST,IConstants.WEB_SERVICE_PORT,
				new APIListener() {
					@Override
					public void onMessage(Object message) {
						Item item = (Item) message;
						setAddItem(item);
						System.out.println("add Item ok: " + item.getText());
					}

					@Override
					public void onError(String error) {
						setAddItem(null);
						System.out.println("add Item error: " + error);
					}
				});
		queryApi = new ItemAPI(IConstants.WEB_SERVICE_HOST,IConstants.WEB_SERVICE_PORT,
				new APIListener() {
					@SuppressWarnings({ "unchecked" })
					@Override
					public void onMessage(Object message) {
						ArrayList<Item> vars = (ArrayList<Item>) message;
						setQueryItems(vars);
						System.out.println("query Item ok:" + vars.size());
					}

					@Override
					public void onError(String error) {
						setQueryItems(null);
						System.out.println("query Item error: " + error);
					}
				});
		delApi = new ItemAPI(IConstants.WEB_SERVICE_HOST,IConstants.WEB_SERVICE_PORT,
				new APIListener() {
					@Override
					public void onMessage(Object message) {
						System.out.println("del Item ok");
					}

					@Override
					public void onError(String error) {
						System.out.println("del Item error: " + error);
					}
				});
	}

	/**
	 * @return
	 */
	public static ItemService getInstance() {
		if (instance == null) {
			instance = new ItemService();
		}
		return instance;
	}

	@SuppressWarnings("finally")
	public Item saveItem(Item var) {
		try {
			this.addApi.create(var);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return this.addItem;
		}
	}

	public ArrayList<Item> getItemBySymName(String synName) {
		try {
			this.queryApi.show("name=='" + synName + "'");
			return getQueryItems();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Item getAddItem() {
		return addItem;
	}

	public void setAddItem(Item addVar) {
		this.addItem = addVar;
	}

	public ArrayList<Item> getQueryItems() {
		return queryVars;
	}

	public void setQueryItems(ArrayList<Item> queryVars) {
		this.queryVars = queryVars;
	}

}
