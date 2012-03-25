package com.tenline.game.simulation.moneytree.server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.renren.api.client.RenrenApiConfig;
import com.tenline.game.simulation.moneytree.shared.RenrenConfig;

public class ApiInitListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		RenrenApiConfig.renrenApiKey = RenrenConfig.API_KEY;
		RenrenApiConfig.renrenApiSecret = RenrenConfig.APP_SECRET;
	}

}
