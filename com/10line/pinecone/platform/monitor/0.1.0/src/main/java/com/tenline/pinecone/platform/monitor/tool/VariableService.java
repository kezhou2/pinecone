package com.tenline.pinecone.platform.monitor.tool;

import java.util.ArrayList;

import com.tenline.pinecone.platform.model.Variable;
import com.tenline.pinecone.platform.monitor.IConstants;
import com.tenline.pinecone.platform.sdk.APIListener;
import com.tenline.pinecone.platform.sdk.VariableAPI;

public class VariableService {
	private VariableAPI addApi;
	private Variable addVar;

	private VariableAPI queryApi;
	private ArrayList<Variable> queryVars;

	@SuppressWarnings("unused")
	private VariableAPI delApi;
	private static VariableService instance = null;

	private VariableService() {
		addApi = new VariableAPI(IConstants.WEB_SERVICE_HOST,
				IConstants.WEB_SERVICE_PORT, new APIListener() {
					@Override
					public void onMessage(Object message) {
						Variable var = (Variable) message;
						setAddVar(var);
						System.out.println("add variable ok: " + var.getName());
					}

					@Override
					public void onError(String error) {
						setAddVar(null);
						System.out.println("add variable error: " + error);
					}
				});
		queryApi = new VariableAPI(IConstants.WEB_SERVICE_HOST,
				IConstants.WEB_SERVICE_PORT, new APIListener() {
					@SuppressWarnings({ "unchecked" })
					@Override
					public void onMessage(Object message) {
						ArrayList<Variable> vars = (ArrayList<Variable>) message;
						setQueryVars(vars);
						System.out.println("query variable ok:" + vars.size());
					}

					@Override
					public void onError(String error) {
						setQueryVars(null);
						System.out.println("query variable error: " + error);
					}
				});
		delApi = new VariableAPI(IConstants.WEB_SERVICE_HOST,
				IConstants.WEB_SERVICE_PORT, new APIListener() {
					@Override
					public void onMessage(Object message) {
						System.out.println("del variable ok");
					}

					@Override
					public void onError(String error) {
						System.out.println("del variable error: " + error);
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

	@SuppressWarnings("finally")
	public Variable saveVariable(Variable var) {
		try {
			this.addApi.create(var);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return this.addVar;
		}
	}

	public ArrayList<Variable> getVariableByDevId(String devid) {
		try {
			this.queryApi.showByDevice("id=='" + devid + "'");
			return getQueryVars();
		} catch (Exception e) {
			e.printStackTrace();
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
