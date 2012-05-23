/**
 * 
 */
package com.tenline.game.simulation.moneytree.client.controllers;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.tenline.game.simulation.moneytree.client.events.GameEvents;
import com.tenline.game.simulation.moneytree.client.views.GameView;

/**
 * @author Bill
 *
 */
public class GameController extends Controller {
	
	private GameView view = new GameView(this);

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
				
			} else if (event.getType().equals(GameEvents.PLANT_COIN)) {
				
			} else if (event.getType().equals(GameEvents.SELL_COIN)) {
				
			}
			forwardToView(view, event);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
