package io.openems.impl.controller;

import io.openems.api.controller.Controller;
import io.openems.api.device.Ess;
import io.openems.api.general.dataaccess.IsRequired;
import io.openems.api.general.dataaccess.Permission;

public class AvoidTotalDischargeController extends Controller {
	
	public AvoidTotalDischargeController(String name) {
		super(name);
	}
	
//	@IsRequired(type = Ess.class,itemId = "soc", permission = Permission.READ)
//	final HashMap<ChannelId, Record> test;
//	
//	@IsWritable(class = ..., id = 'ActivePower')
//	HashMap<final ChannelId, Value>
//	
//	@Override
//	public int priority() {
//		return 100;
//	}

	@Override
	public void run() {
		System.out.println("Run: " + toString());
	}

	@Override
	public int priority() {
		return 0;
	}
}
