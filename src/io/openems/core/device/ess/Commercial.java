package io.openems.core.device.ess;

import io.openems.core.device.Device;
import io.openems.core.item.impl.IntegerItem;
import io.openems.core.item.impl.NumberItem;

public class Commercial extends Device implements Ess {
	
	public Commercial(String name) {
		super(name);
		
		this.activePower = new IntegerItem("activePower", 50);
	}

	protected NumberItem activePower = new IntegerItem("activePower", 10);
	@Override
	public NumberItem activePower() {
		return this.activePower;
	}

}
