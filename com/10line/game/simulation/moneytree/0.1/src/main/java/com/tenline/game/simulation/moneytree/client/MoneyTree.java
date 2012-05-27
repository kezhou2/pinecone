/**
 * 
 */
package com.tenline.game.simulation.moneytree.client;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.tenline.game.simulation.moneytree.client.controllers.GameController;
import com.tenline.game.simulation.moneytree.client.controllers.UserController;
import com.tenline.game.simulation.moneytree.client.services.ApplicationService;
import com.tenline.game.simulation.moneytree.client.services.ConsumerService;
import com.tenline.game.simulation.moneytree.client.services.ExchangeService;
import com.tenline.game.simulation.moneytree.client.services.RenRenService;
import com.tenline.game.simulation.moneytree.client.services.TransactionService;
import com.tenline.game.simulation.moneytree.client.services.UserService;

/**
 * 
 * @author Bill
 *
 */
public class MoneyTree implements EntryPoint {
	
	@Override
	public void onModuleLoad() {
		Registry.register(Messages.class.getName(), GWT.create(Messages.class));
		Registry.register(ApplicationService.class.getName(), GWT.create(ApplicationService.class));
		Registry.register(ConsumerService.class.getName(), GWT.create(ConsumerService.class));
		Registry.register(ExchangeService.class.getName(), GWT.create(ExchangeService.class));
		Registry.register(RenRenService.class.getName(), GWT.create(RenRenService.class));
		Registry.register(TransactionService.class.getName(), GWT.create(TransactionService.class));
		Registry.register(UserService.class.getName(), GWT.create(UserService.class));
		Dispatcher dispatcher = Dispatcher.get();
		dispatcher.addController(new GameController());
		dispatcher.addController(new UserController());
	}
	
}
