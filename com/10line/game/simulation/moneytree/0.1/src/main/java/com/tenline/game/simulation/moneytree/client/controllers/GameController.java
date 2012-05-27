/**
 * 
 */
package com.tenline.game.simulation.moneytree.client.controllers;

import java.util.Date;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.tenline.game.simulation.moneytree.client.events.GameEvents;
import com.tenline.game.simulation.moneytree.client.services.ConsumerService;
import com.tenline.game.simulation.moneytree.client.services.ConsumerServiceAsync;
import com.tenline.game.simulation.moneytree.client.services.ExchangeService;
import com.tenline.game.simulation.moneytree.client.services.ExchangeServiceAsync;
import com.tenline.game.simulation.moneytree.client.services.TransactionService;
import com.tenline.game.simulation.moneytree.client.services.TransactionServiceAsync;
import com.tenline.game.simulation.moneytree.client.services.UserService;
import com.tenline.game.simulation.moneytree.client.services.UserServiceAsync;
import com.tenline.game.simulation.moneytree.client.views.GameView;
import com.tenline.pinecone.platform.model.Account;
import com.tenline.pinecone.platform.model.Application;
import com.tenline.pinecone.platform.model.Consumer;
import com.tenline.pinecone.platform.model.Exchange;
import com.tenline.pinecone.platform.model.Transaction;
import com.tenline.pinecone.platform.model.User;

/**
 * @author Bill
 *
 */
public class GameController extends Controller {
	
	private GameView view = new GameView(this);
	private ConsumerServiceAsync consumerService = Registry.get(ConsumerService.class.getName());
	private ExchangeServiceAsync exchangeService = Registry.get(ExchangeService.class.getName());
	private TransactionServiceAsync transactionService = Registry.get(TransactionService.class.getName());
	private UserServiceAsync userService = Registry.get(UserService.class.getName());
	
	private static final Integer NUT_PER_PLANT = 2;

	/**
	 * 
	 */
	public GameController() {
		// TODO Auto-generated constructor stub
		registerEventTypes(GameEvents.BUY_COIN);
		registerEventTypes(GameEvents.PLANT_COIN);
		registerEventTypes(GameEvents.SELL_COIN);
	}

	@Override
	public void handleEvent(AppEvent event) {
		// TODO Auto-generated method stub
		try {
			if (event.getType().equals(GameEvents.BUY_COIN)) {
				buyCoin(event);
			} else if (event.getType().equals(GameEvents.PLANT_COIN)) {
				plantCoin(event);
			} else if (event.getType().equals(GameEvents.SELL_COIN)) {
				sellCoin(event);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param event
	 * @throws Exception
	 */
	private void buyCoin(final AppEvent event) throws Exception {
		User user = (User) event.getData("user");
		user.setNut(user.getNut() + Integer.valueOf(event.getData("nut").toString()));
		userService.update(user, new AsyncCallback<User>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(User result) {
				// TODO Auto-generated method stub
				forwardToView(view, event.getType(), result);
			}
			
		});
	}
	
	/**
	 * 
	 * @param event
	 * @throws Exception
	 */
	private void plantCoin(final AppEvent event) throws Exception {
		User user = (User) event.getData("user");
		user.setNut(user.getNut() - NUT_PER_PLANT);
		userService.update(user, new AsyncCallback<User>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(User result) {
				// TODO Auto-generated method stub
				Consumer consumer = event.getData("consumer");
				consumer.setNut(consumer.getNut() + NUT_PER_PLANT);
				consumerService.update(consumer, new AsyncCallback<Consumer>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						caught.printStackTrace();
					}

					@Override
					public void onSuccess(Consumer result) {
						// TODO Auto-generated method stub
						Transaction transaction = new Transaction();
						transaction.setNut(NUT_PER_PLANT);
						transaction.setTimestamp(new Date());
						transaction.setType(Transaction.PAYOUT);
						transaction.setApplication((Application) event.getData("application"));
						transactionService.create(transaction, new AsyncCallback<Transaction>() {

							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub
								caught.printStackTrace();
							}

							@Override
							public void onSuccess(Transaction result) {
								// TODO Auto-generated method stub
								forwardToView(view, event.getType(), result);
							}
							
						});
					}
					
				});
			}
			
		});
	}
	
	/**
	 * 
	 * @param event
	 * @throws Exception
	 */
	private void sellCoin(final AppEvent event) throws Exception {
		User user = (User) event.getData("user");
		user.setNut(user.getNut() - Integer.valueOf(event.getData("nut").toString()));
		userService.update(user, new AsyncCallback<User>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(User result) {
				// TODO Auto-generated method stub
				Exchange exchange = new Exchange();
				exchange.setNut(Integer.valueOf(event.getData("nut").toString()));
				exchange.setTimestamp(new Date());
				exchange.setType(Exchange.PAYOUT);
				exchange.setAccount((Account) event.getData("account"));
				exchangeService.create(exchange, new AsyncCallback<Exchange>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						caught.printStackTrace();
					}

					@Override
					public void onSuccess(Exchange result) {
						// TODO Auto-generated method stub
						forwardToView(view, event.getType(), result);	
					}
					
				});
			}
			
		});
	}

}
