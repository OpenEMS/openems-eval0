package io.openems.impl.controller;

import io.openems.core.controller.Controller;
import io.openems.core.controller.IsRequiredReadonly;
import io.openems.core.controller.IsRequiredWritable;
import io.openems.core.device.ess.Ess;
import io.openems.core.item.Items;
import io.openems.core.item.ReadonlyItems;

public class AvoidTotalDischargeController extends Controller {
	
	public AvoidTotalDischargeController(String name) {
		super(name);
	}
	
	@IsRequiredReadonly(clazz=Ess.class, itemId="activePower")
	public ReadonlyItems essActivePower;
	
	@IsRequiredWritable(clazz=Ess.class, itemId="setActivePower")
	public Items essSetActivePower;
	
	@Override
	public int priority() {
		return 100;
	}

	@Override
	public void run() {
		System.out.println("Run: " + toString());
	}
}
