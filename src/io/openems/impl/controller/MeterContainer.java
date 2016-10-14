package io.openems.impl.controller;

import io.openems.api.controller.DataMap;
import io.openems.api.controller.Range;
import io.openems.api.general.dataaccess.IsRequired;
import io.openems.api.general.dataaccess.Permission;

public class MeterContainer implements DataMap{

	@IsRequired(itemId = "ActivePower",permission = Permission.READ)
	public Range activePower;
	
	
	public int getActivePower(){
		return activePower.getReadValue().getValue().asInt();
	}
}
