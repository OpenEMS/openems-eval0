package io.openems.impl.controller;

import java.util.List;

import io.openems.api.controller.Controller;
import io.openems.api.controller.Mapping;
import io.openems.api.device.Ess;
import io.openems.api.general.data.IntegerValue;
import io.openems.api.general.dataaccess.IsRequired;
import io.openems.api.general.dataaccess.Permission;

public class AvoidTotalDischargeController extends Controller {
	
	public AvoidTotalDischargeController(String name) {
		super(name);
	}
	
	@Mapping(type = Ess.class)
	public List<EssContainer> ess;

	@Override
	public void run() {
		System.out.println("Run: " + ess.get(0).soc.getValue().asString());
		ess.get(0).setActivePower = new IntegerValue((int) (Math.random()*100.0));
	}

	@Override
	public int priority() {
		return 0;
	}
}
