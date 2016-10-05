package io.openems.core.controller;

import io.openems.core.Thing;
import io.openems.core.item.IsItem;

public abstract class Controller implements Thing {
	private final String name;
	public Controller(String name) {
		this.name = name;
	}

	/**
	 * Returns the priority of this controller. High return value is high priority,
	 * low value is low priority.
	 * 
	 * @return
	 */
	@IsItem
	public abstract int priority();
	
	public abstract void run();

	@Override
	public String getName() {
		return this.name;
	}
}
