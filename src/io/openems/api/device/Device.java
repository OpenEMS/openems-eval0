package io.openems.api.device;

import io.openems.api.general.Thing;

public abstract class Device implements Thing {
	private final String name;
	
	public Device(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return this.name;
	}


}
