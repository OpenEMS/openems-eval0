package io.openems.core.device;

import io.openems.core.Thing;

public class Device implements Thing {
	private final String name;
	
	public Device(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

}
