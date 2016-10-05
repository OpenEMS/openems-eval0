package io.openems.core.device.ess;

import io.openems.core.Thing;
import io.openems.core.item.IsItem;
import io.openems.core.item.impl.NumberItem;

public interface Ess extends Thing {
	@IsItem
	public NumberItem activePower(); 
}
