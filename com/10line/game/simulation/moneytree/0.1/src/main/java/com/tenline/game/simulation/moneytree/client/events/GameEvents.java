/**
 * 
 */
package com.tenline.game.simulation.moneytree.client.events;

import com.extjs.gxt.ui.client.event.EventType;

/**
 * @author Bill
 *
 */
public interface GameEvents {

	/**
	 * 
	 */
	static final EventType BUY_COIN = new EventType();
	static final EventType PLANT_COIN = new EventType();
	static final EventType SELL_COIN = new EventType();
	
}
