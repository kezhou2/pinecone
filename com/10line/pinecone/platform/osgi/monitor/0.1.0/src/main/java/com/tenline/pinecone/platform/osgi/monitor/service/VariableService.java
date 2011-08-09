package com.tenline.pinecone.platform.osgi.monitor.service;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.tenline.pinecone.platform.model.Variable;
import com.tenline.pinecone.platform.sdk.APIListener;
import com.tenline.pinecone.platform.sdk.VariableAPI;

public class VariableService {
	private static Logger logger = Logger.getLogger(VariableService.class);
	private VariableAPI addApi;
	private Variable addVar;

	private VariableAPI queryApi;
	private ArrayList<Variable> queryVars;

	@SuppressWarnings("unused")
	private VariableAPI delApi;
	private static VariableService instance = null;

	private VariableService() {
		addApi = new VariableAPI("pinecone.web.service.10line.cc", "80",
				new APIListener() {
					@Override
					public void onMessage(Object message) {
						Variable var = (Variable) message;
						setAddVar(var);
						logger.info("add variable ok: " + var.getName());
					}

					@Override
					public void onError(String error) {
						setAddVar(null);
						logger.error("add variable error: " + error);
					}
				});
		queryApi = new VariableAPI("pinecone.web.service.10line.cc", "80",
				new APIListener() {
					@SuppressWarnings({ "unchecked" })
					@Override
					public void onMessage(Object message) {
						ArrayList<Variable> vars = (ArrayList<Variable>) message;
						setQueryVars(vars);
						logger.info("query variable ok:" + vars.size());
					}

					@Override
					public void onError(String error) {
						setQueryVars(null);
						logger.error("query variable error: " + error);
					}
				});
		delApi = new VariableAPI("pinecone.web.service.10line.cc", "80",
				new APIListener() {
					@Override
					public void onMessage(Object message) {
						logger.info("del variable ok");
					}

					@Override
					public void onError(String error) {
						logger.error("del variable error: " + error);
					}
				});
	}

	/**
	 * @return
	 */
	public static VariableService getInstance() {
		if (instance == null) {
			instance = new VariableService();
		}
		return instance;
	}

	public void saveVariable(Variable var) {
		try {
			this.addApi.create(var);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public ArrayList<Variable> getVariableByDevId(String devid) {
		try {
			this.queryApi.showByDevice("id=='" + devid + "'");
			return getQueryVars();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	public Variable getAddVar() {
		return addVar;
	}

	public void setAddVar(Variable addVar) {
		this.addVar = addVar;
	}

	public ArrayList<Variable> getQueryVars() {
		return queryVars;
	}

	public void setQueryVars(ArrayList<Variable> queryVars) {
		this.queryVars = queryVars;
	}

}
