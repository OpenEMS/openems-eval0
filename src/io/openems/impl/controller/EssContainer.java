package io.openems.impl.controller;

import io.openems.api.controller.DataMap;
import io.openems.api.general.data.IntegerValue;
import io.openems.api.general.data.Record;
import io.openems.api.general.dataaccess.IsRequired;
import io.openems.api.general.dataaccess.Permission;

public class EssContainer implements DataMap {
	
	@IsRequired(itemId = "soc",permission = Permission.READ)
	public Record soc;
	
	@IsRequired(itemId = "activePower",permission = Permission.READ)
	public Record activePower;
	
	@IsRequired(itemId = "activePower",permission = Permission.WRITE)
	public IntegerValue setActivePower;
}
