package com.tenline.game.simulation.moneytree.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * 
 * @author Bill
 *
 */
public class GwtTestMoneyTree extends GWTTestCase {
	
	/**
	 * 
	 */
	public String getModuleName() {
		return "com.tenline.game.simulation.moneytree.MoneyTreeJUnit";
	}
	
	/**
	 * 
	 */
	public void testMoneyTreeService() {
		MoneyTreeServiceAsync service = GWT.create(MoneyTreeService.class);
	    ServiceDefTarget target = (ServiceDefTarget) service;
	    target.setServiceEntryPoint(GWT.getModuleBaseURL() + "MoneyTree/Service");
	    delayTestFinish(10000);
	    service.plantTree(null, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				fail("Request failure: " + caught.getMessage());
			}

			@Override
			public void onSuccess(Void result) {
				// TODO Auto-generated method stub
				assertNotNull(result);
				finishTest();
			}
	    	
	    });
	}

}
