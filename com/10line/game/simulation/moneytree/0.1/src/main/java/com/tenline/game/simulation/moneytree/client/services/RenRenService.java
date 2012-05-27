/**
 * 
 */
package com.tenline.game.simulation.moneytree.client.services;

import org.json.simple.JSONObject;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * @author Bill
 *
 */
@RemoteServiceRelativePath("renren/service")
public interface RenRenService extends RemoteService {

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public JSONObject getUser() throws Exception;
	
}
