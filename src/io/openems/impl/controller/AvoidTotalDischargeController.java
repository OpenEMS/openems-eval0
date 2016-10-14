package io.openems.impl.controller;

import java.util.List;

import io.openems.api.controller.Controller;
import io.openems.api.controller.Mapping;
import io.openems.api.controller.Range;
import io.openems.api.device.Ess;
import io.openems.api.general.data.IntegerValue;
import io.openems.api.general.data.Record;
import io.openems.api.general.dataaccess.IsRequired;
import io.openems.api.general.dataaccess.Permission;

public class AvoidTotalDischargeController extends Controller {
	
	public AvoidTotalDischargeController(String name) {
		super(name);
	}
	
	private int minSoc = 25;
	
	@Mapping(type = Ess.class)
	public List<EssContainer> ess;

	@Override
	public void run() {
		for(EssContainer e : ess){
			if(e.soc.getReadValue().getValue().asInt() <= minSoc && e.soc.getReadValue().getValue().asInt() > minSoc-5){
				e.activePower.setRange(e.activePower.getMinWriteValue(), new IntegerValue(0));
			}else if(e.soc.getReadValue().getValue().asInt() <= minSoc-5){
				e.activePower.setRange(e.activePower.getMinWriteValue(), new IntegerValue((int)(e.activePower.getMinWriteValue().asDouble()*0.2)));
			}
		}
	}

	@Override
	public int priority() {
		return 1;
	}
}
