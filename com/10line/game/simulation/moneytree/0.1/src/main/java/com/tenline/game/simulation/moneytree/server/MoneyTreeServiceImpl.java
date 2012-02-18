/**
 * 
 */
package com.tenline.game.simulation.moneytree.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.tenline.game.simulation.moneytree.client.MoneyTreeService;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.sdk.UserAPI;

/**
 * @author Bill
 *
 */
@SuppressWarnings("serial")
public class MoneyTreeServiceImpl extends RemoteServiceServlet implements MoneyTreeService {
	
	private static final String HOST = "pinecone-service.cloudfoundry.com";
	private static final String PORT = "80";
	private static final Integer PLANTED_NUT = 2;
	
	/**
	 * 
	 */
	public MoneyTreeServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param delegate
	 */
	public MoneyTreeServiceImpl(Object delegate) {
		super(delegate);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void plantTree(User user) throws Exception {
		// TODO Auto-generated method stub
		UserAPI userAPI = new UserAPI(HOST, PORT, null);
		user.setNut(user.getNut() - PLANTED_NUT);
		userAPI.create(user);
	}

}
