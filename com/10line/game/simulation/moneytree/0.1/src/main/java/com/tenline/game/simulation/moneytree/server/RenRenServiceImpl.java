/**
 * 
 */
package com.tenline.game.simulation.moneytree.server;

import org.json.simple.JSONObject;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.renren.api.client.RenrenApiClient;
import com.renren.api.client.param.impl.SessionKey;
import com.tenline.game.simulation.moneytree.client.services.RenRenService;

/**
 * @author Bill
 *
 */
@SuppressWarnings("serial")
public class RenRenServiceImpl extends RemoteServiceServlet implements RenRenService {

	/**
	 * 
	 */
	public RenRenServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public JSONObject getUser() throws Exception {
		// TODO Auto-generated method stub
		SessionKey sessionKey = new SessionKey(getThreadLocalRequest().getParameter("xn_sig_session_key"));
		String userIds = getThreadLocalRequest().getParameter("xn_sig_user");
		return (JSONObject) RenrenApiClient.getInstance().getUserService().getInfo(userIds, sessionKey).get(0);
	}

}
