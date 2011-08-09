package com.tenline.pinecone.platform.osgi.monitor.service;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.tenline.pinecone.platform.model.Item;
import com.tenline.pinecone.platform.sdk.APIListener;
import com.tenline.pinecone.platform.sdk.ItemAPI;

public class ItemService {
	private static Logger logger = Logger.getLogger(ItemService.class);
	private ItemAPI addApi;
	private Item addVar;

	private ItemAPI queryApi;
	private ArrayList<Item> queryVars;

	@SuppressWarnings("unused")
	private ItemAPI delApi;
	private static ItemService instance = null;

	private ItemService() {
		addApi = new ItemAPI("pinecone.web.service.10line.cc", "80",
				new APIListener() {
					@Override
					public void onMessage(Object message) {
						Item item = (Item) message;
						setAddVar(item);
						logger.info("add Item ok: " + item.getText());
					}

					@Override
					public void onError(String error) {
						setAddVar(null);
						logger.error("add Item error: " + error);
					}
				});
		queryApi = new ItemAPI("pinecone.web.service.10line.cc", "80",
				new APIListener() {
					@SuppressWarnings({ "unchecked" })
					@Override
					public void onMessage(Object message) {
						ArrayList<Item> vars = (ArrayList<Item>) message;
						setQueryVars(vars);
						logger.info("query Item ok:" + vars.size());
					}

					@Override
					public void onError(String error) {
						setQueryVars(null);
						logger.error("query Item error: " + error);
					}
				});
		delApi = new ItemAPI("pinecone.web.service.10line.cc", "80",
				new APIListener() {
					@Override
					public void onMessage(Object message) {
						logger.info("del Item ok");
					}

					@Override
					public void onError(String error) {
						logger.error("del Item error: " + error);
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

	public void saveItem(Item var) {
		try {
			this.addApi.create(var);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public ArrayList<Item> getItemBySymName(String synName) {
		try {
			this.queryApi.show("name=='" + synName + "'");
			return getQueryVars();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	public Item getAddVar() {
		return addVar;
	}

	public void setAddVar(Item addVar) {
		this.addVar = addVar;
	}

	public ArrayList<Item> getQueryVars() {
		return queryVars;
	}

	public void setQueryVars(ArrayList<Item> queryVars) {
		this.queryVars = queryVars;
	}

}
