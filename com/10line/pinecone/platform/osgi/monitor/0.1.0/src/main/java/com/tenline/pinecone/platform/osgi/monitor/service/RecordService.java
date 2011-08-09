package com.tenline.pinecone.platform.osgi.monitor.service;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.tenline.pinecone.platform.model.Record;
import com.tenline.pinecone.platform.sdk.APIListener;
import com.tenline.pinecone.platform.sdk.RecordAPI;

public class RecordService {
	private static Logger logger = Logger.getLogger(RecordService.class);
	private RecordAPI addApi;
	private Record addVar;

	@SuppressWarnings("unused")
	private RecordAPI queryApi;
	private ArrayList<Record> queryVars;

	@SuppressWarnings("unused")
	private RecordAPI delApi;
	private static RecordService instance = null;

	private RecordService() {
		addApi = new RecordAPI("pinecone.web.service.10line.cc", "80",
				new APIListener() {
					@Override
					public void onMessage(Object message) {
						Record var = (Record) message;
						setAddVar(var);
						logger.info("add Record ok: " + var.getId());
					}

					@Override
					public void onError(String error) {
						setAddVar(null);
						logger.error("add Record error: " + error);
					}
				});
		queryApi = new RecordAPI("pinecone.web.service.10line.cc", "80",
				new APIListener() {
					@SuppressWarnings({ "unchecked" })
					@Override
					public void onMessage(Object message) {
						ArrayList<Record> vars = (ArrayList<Record>) message;
						setQueryVars(vars);
						logger.info("query Record ok:" + vars.size());
					}

					@Override
					public void onError(String error) {
						setQueryVars(null);
						logger.error("query Record error: " + error);
					}
				});
		delApi = new RecordAPI("pinecone.web.service.10line.cc", "80",
				new APIListener() {
					@Override
					public void onMessage(Object message) {
						logger.info("del Record ok");
					}

					@Override
					public void onError(String error) {
						logger.error("del Record error: " + error);
					}
				});
	}

	/**
	 * @return
	 */
	public static RecordService getInstance() {
		if (instance == null) {
			instance = new RecordService();
		}
		return instance;
	}

	public void saveRecord(Record var) {
		try {
			this.addApi.create(var);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public Record getAddVar() {
		return addVar;
	}

	public void setAddVar(Record addVar) {
		this.addVar = addVar;
	}

	public ArrayList<Record> getQueryVars() {
		return queryVars;
	}

	public void setQueryVars(ArrayList<Record> queryVars) {
		this.queryVars = queryVars;
	}

}
