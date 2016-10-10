package io.openems.impl.device.ess;

import io.openems.api.device.Device;
import io.openems.api.device.Ess;
import io.openems.api.general.data.IntegerValue;
import io.openems.api.general.data.element.Element;
import io.openems.api.general.dataaccess.IsItem;

public class Commercial extends Device implements Ess {

	@IsItem
	private Element soc;
	
	public Element getSoc(){
		return soc;
	}
	
	public Commercial(String name) {
		super(name);
	}

}
